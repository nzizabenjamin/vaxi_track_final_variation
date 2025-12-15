package com.finalprojectgroupae.immunization.services.notification;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.finalprojectgroupae.immunization.data.local.entities.NotificationEntity;
import com.finalprojectgroupae.immunization.data.local.entities.PatientEntity;
import com.finalprojectgroupae.immunization.data.local.entities.ScheduledDoseEntity;
import com.finalprojectgroupae.immunization.data.local.entities.VaccineDoseEntity;
import com.finalprojectgroupae.immunization.data.repository.NotificationRepository;
import com.finalprojectgroupae.immunization.data.repository.PatientRepository;
import com.finalprojectgroupae.immunization.data.repository.ScheduledDoseRepository;
import com.finalprojectgroupae.immunization.data.repository.VaccineDoseRepository;
import com.finalprojectgroupae.immunization.receivers.AlarmReceiver;
import com.finalprojectgroupae.immunization.utils.Constants;
import com.finalprojectgroupae.immunization.utils.DateUtils;
import com.finalprojectgroupae.immunization.utils.IdGenerator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationScheduler {

    private static final String TAG = "NotificationScheduler";

    private final Application application;
    private final ScheduledDoseRepository scheduledDoseRepository;
    private final VaccineDoseRepository vaccineDoseRepository;
    private final PatientRepository patientRepository;
    private final NotificationRepository notificationRepository;
    private final AlarmManager alarmManager;

    public NotificationScheduler(Application application) {
        this.application = application;
        this.scheduledDoseRepository = new ScheduledDoseRepository(application);
        this.vaccineDoseRepository = new VaccineDoseRepository(application);
        this.patientRepository = new PatientRepository(application);
        this.notificationRepository = new NotificationRepository(application);
        this.alarmManager = (AlarmManager) application.getSystemService(Context.ALARM_SERVICE);
    }

    /**
     * Schedule notifications for a scheduled dose
     */
    public void scheduleNotificationsForDose(ScheduledDoseEntity scheduledDose) {
        // Get vaccine dose to determine reminder schedule
        vaccineDoseRepository.getDoseById(scheduledDose.getDoseId(), dose -> {
            if (dose == null) {
                Log.e(TAG, "Dose not found: " + scheduledDose.getDoseId());
                return;
            }

            // Get patient info for notification
            patientRepository.getPatientById(scheduledDose.getPatientId()).observeForever(patient -> {
                if (patient == null) {
                    Log.e(TAG, "Patient not found: " + scheduledDose.getPatientId());
                    return;
                }
                createNotifications(scheduledDose, dose, patient);
            });
        });
    }

    /**
     * Create notification entities and schedule alarms
     */
    private void createNotifications(ScheduledDoseEntity scheduledDose, VaccineDoseEntity dose, PatientEntity patient) {
        List<NotificationEntity> notifications = new ArrayList<>();

        // Parse reminder days from JSON
        List<Integer> reminderDays = parseReminderDays(dose.getReminderDaysBefore());

        // Create notifications for each reminder day
        for (int daysBefore : reminderDays) {
            Date reminderDate = DateUtils.addDays(scheduledDose.getScheduledDate(), -daysBefore);

            // Only schedule if in the future
            if (reminderDate.after(new Date())) {
                NotificationEntity notification = new NotificationEntity();
                notification.setNotificationId(IdGenerator.generateNotificationId());
                notification.setScheduledDoseId(scheduledDose.getScheduledDoseId());
                notification.setRecipientId(patient.getGuardianId());
                notification.setRecipientPhone(patient.getGuardianPhone());
                notification.setType(Constants.NOTIFICATION_TYPE_SMS);
                notification.setScheduledSendDate(reminderDate);
                notification.setStatus("pending");

                // Build message
                String message = buildReminderMessage(scheduledDose, daysBefore, patient);
                notification.setMessage(message);

                notifications.add(notification);
                
                // Schedule alarm
                scheduleAlarm(notification, patient);
                
                Log.i(TAG, "Notification scheduled for " + DateUtils.formatForDisplay(reminderDate));
            }
        }

        // Save notifications to database
        if (!notifications.isEmpty()) {
            notificationRepository.insertAll(notifications);
        }
    }
    
    /**
     * Schedule an alarm for a notification
     */
    private void scheduleAlarm(NotificationEntity notification, PatientEntity patient) {
        Intent intent = new Intent(application, AlarmReceiver.class);
        intent.setAction("com.finalprojectgroupae.immunization.SEND_REMINDER");
        intent.putExtra(AlarmReceiver.EXTRA_NOTIFICATION_MESSAGE, notification.getMessage());
        intent.putExtra(AlarmReceiver.EXTRA_PATIENT_NAME, patient.getFirstName() + " " + patient.getLastName());
        intent.putExtra(AlarmReceiver.EXTRA_SCHEDULED_DOSE_ID, notification.getScheduledDoseId());
        
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                application,
                notification.getNotificationId().hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        
        // Schedule alarm
        if (alarmManager != null) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    notification.getScheduledSendDate().getTime(),
                    pendingIntent
            );
            Log.i(TAG, "Alarm scheduled for notification: " + notification.getNotificationId());
        }
    }

    /**
     * Schedule overdue escalation reminders
     */
    public void scheduleOverdueReminders(ScheduledDoseEntity scheduledDose) {
        List<NotificationEntity> overdueNotifications = new ArrayList<>();

        for (int daysAfter : Constants.OVERDUE_REMINDER_DAYS) {
            Date reminderDate = DateUtils.addDays(scheduledDose.getScheduledDate(), daysAfter);

            NotificationEntity notification = new NotificationEntity();
            notification.setNotificationId(IdGenerator.generateNotificationId());
            notification.setScheduledDoseId(scheduledDose.getScheduledDoseId());
            notification.setType(Constants.NOTIFICATION_TYPE_SMS);
            notification.setScheduledSendDate(reminderDate);
            notification.setStatus("pending");

            String message = buildOverdueMessage(scheduledDose, daysAfter);
            notification.setMessage(message);

            overdueNotifications.add(notification);
        }

        // Save overdue notifications
        Log.i(TAG, "Scheduled " + overdueNotifications.size() + " overdue reminders");
    }

    /**
     * Build reminder message
     */
    private String buildReminderMessage(ScheduledDoseEntity scheduledDose, int daysBefore, PatientEntity patient) {
        String childName = patient.getFirstName() + " " + patient.getLastName();
        if (daysBefore == 1) {
            return "Reminder: " + childName + "'s vaccination is due TOMORROW. " +
                    "Please bring them to the clinic. Scheduled date: " +
                    DateUtils.formatForDisplay(scheduledDose.getScheduledDate());
        } else {
            return "Reminder: " + childName + "'s vaccination is due in " + daysBefore + " days. " +
                    "Scheduled date: " + DateUtils.formatForDisplay(scheduledDose.getScheduledDate());
        }
    }

    /**
     * Build overdue message
     */
    private String buildOverdueMessage(ScheduledDoseEntity scheduledDose, int daysAfter) {
        return "URGENT: Your child's vaccination is now " + daysAfter + " days overdue. " +
                "Please bring them to the clinic as soon as possible. " +
                "Due date was: " + DateUtils.formatForDisplay(scheduledDose.getScheduledDate());
    }

    /**
     * Parse reminder days from JSON string
     */
    private List<Integer> parseReminderDays(String reminderDaysJson) {
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Integer>>(){}.getType();
            return gson.fromJson(reminderDaysJson, listType);
        } catch (Exception e) {
            Log.e(TAG, "Error parsing reminder days: " + e.getMessage());
            // Return default reminder schedule
            List<Integer> defaultReminders = new ArrayList<>();
            defaultReminders.add(7);
            defaultReminders.add(3);
            defaultReminders.add(1);
            return defaultReminders;
        }
    }

    /**
     * Cancel all pending notifications for a scheduled dose
     */
    public void cancelNotifications(String scheduledDoseId) {
        notificationRepository.getNotificationsByScheduledDose(scheduledDoseId, notifications -> {
            if (notifications != null) {
                for (NotificationEntity notification : notifications) {
                    if ("pending".equals(notification.getStatus())) {
                        // Cancel alarm
                        Intent intent = new Intent(application, AlarmReceiver.class);
                        intent.setAction("com.finalprojectgroupae.immunization.SEND_REMINDER");
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                application,
                                notification.getNotificationId().hashCode(),
                                intent,
                                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                        );
                        
                        if (alarmManager != null) {
                            alarmManager.cancel(pendingIntent);
                        }
                    }
                }
                
                // Update status in database
                notificationRepository.cancelNotificationsForScheduledDose(scheduledDoseId);
                Log.i(TAG, "Cancelled notifications for: " + scheduledDoseId);
            }
        });
    }
}