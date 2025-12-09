package com.finalprojectgroupae.immunization.services.notification;

import android.app.Application;
import android.util.Log;

import com.finalprojectgroupae.immunization.data.local.entities.NotificationEntity;
import com.finalprojectgroupae.immunization.data.local.entities.PatientEntity;
import com.finalprojectgroupae.immunization.data.local.entities.ScheduledDoseEntity;
import com.finalprojectgroupae.immunization.data.local.entities.VaccineDoseEntity;
import com.finalprojectgroupae.immunization.data.repository.PatientRepository;
import com.finalprojectgroupae.immunization.data.repository.ScheduledDoseRepository;
import com.finalprojectgroupae.immunization.data.repository.VaccineDoseRepository;
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

    public NotificationScheduler(Application application) {
        this.application = application;
        this.scheduledDoseRepository = new ScheduledDoseRepository(application);
        this.vaccineDoseRepository = new VaccineDoseRepository(application);
        this.patientRepository = new PatientRepository(application);
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
            patientRepository.getUnsyncedPatients(patients -> {
                // This is a workaround - ideally use getPatientById
                // For now, create notifications with placeholder
                createNotifications(scheduledDose, dose);
            });
        });
    }

    /**
     * Create notification entities
     */
    private void createNotifications(ScheduledDoseEntity scheduledDose, VaccineDoseEntity dose) {
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
                notification.setType(Constants.NOTIFICATION_TYPE_SMS);
                notification.setScheduledSendDate(reminderDate);
                notification.setStatus("pending");

                // Build message
                String message = buildReminderMessage(scheduledDose, daysBefore);
                notification.setMessage(message);

                notifications.add(notification);
                Log.i(TAG, "Notification scheduled for " + DateUtils.formatForDisplay(reminderDate));
            }
        }

        // Save notifications to database
        // (Requires NotificationRepository - to be created)
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
    private String buildReminderMessage(ScheduledDoseEntity scheduledDose, int daysBefore) {
        if (daysBefore == 1) {
            return "Reminder: Your child's vaccination is due TOMORROW. " +
                    "Please bring them to the clinic. Scheduled date: " +
                    DateUtils.formatForDisplay(scheduledDose.getScheduledDate());
        } else {
            return "Reminder: Your child's vaccination is due in " + daysBefore + " days. " +
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
        // This requires NotificationDao - implementation needed
        Log.i(TAG, "Cancelling notifications for: " + scheduledDoseId);
    }
}