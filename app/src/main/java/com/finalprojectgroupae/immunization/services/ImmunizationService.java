package com.finalprojectgroupae.immunization.services;

import android.app.Application;
import android.util.Log;

import com.finalprojectgroupae.immunization.data.local.entities.CarePlanEntity;
import com.finalprojectgroupae.immunization.data.local.entities.ImmunizationEntity;
import com.finalprojectgroupae.immunization.data.local.entities.PatientEntity;
import com.finalprojectgroupae.immunization.data.local.entities.ScheduledDoseEntity;
import com.finalprojectgroupae.immunization.data.local.entities.VaccineDoseEntity;
import com.finalprojectgroupae.immunization.data.repository.CarePlanRepository;
import com.finalprojectgroupae.immunization.data.repository.ImmunizationRepository;
import com.finalprojectgroupae.immunization.data.repository.PatientRepository;
import com.finalprojectgroupae.immunization.data.repository.ScheduledDoseRepository;
import com.finalprojectgroupae.immunization.data.repository.VaccineDoseRepository;
import com.finalprojectgroupae.immunization.services.notification.NotificationScheduler;
import com.finalprojectgroupae.immunization.utils.Constants;
import com.finalprojectgroupae.immunization.utils.IdGenerator;
import com.finalprojectgroupae.immunization.utils.VaccineScheduleGenerator;

import java.util.Date;
import java.util.List;

public class ImmunizationService {

    private static final String TAG = "ImmunizationService";

    private final Application application;
    private final PatientRepository patientRepository;
    private final ImmunizationRepository immunizationRepository;
    private final CarePlanRepository carePlanRepository;
    private final ScheduledDoseRepository scheduledDoseRepository;
    private final VaccineDoseRepository vaccineDoseRepository;
    private final NotificationScheduler notificationScheduler;

    public ImmunizationService(Application application) {
        this.application = application;
        this.patientRepository = new PatientRepository(application);
        this.immunizationRepository = new ImmunizationRepository(application);
        this.carePlanRepository = new CarePlanRepository(application);
        this.scheduledDoseRepository = new ScheduledDoseRepository(application);
        this.vaccineDoseRepository = new VaccineDoseRepository(application);
        this.notificationScheduler = new NotificationScheduler(application);
    }

    /**
     * Register a new patient and generate their vaccination schedule
     */
    public void registerPatient(PatientEntity patient, OnPatientRegisteredCallback callback) {
        // Generate unique patient ID
        patient.setPatientId(IdGenerator.generatePatientId());
        patient.setRegistrationDate(new Date());

        // Insert patient
        patientRepository.insert(patient);

        // Get all required vaccine doses
        vaccineDoseRepository.getRequiredDoses(doses -> {
            // Generate care plan
            CarePlanEntity carePlan = VaccineScheduleGenerator.createCarePlan(
                    patient.getPatientId(),
                    doses.size(),
                    false
            );
            carePlan.setCarePlanId(IdGenerator.generateCarePlanId());

            // Insert care plan
            carePlanRepository.insert(carePlan);

            // Generate vaccination schedule
            List<ScheduledDoseEntity> scheduledDoses = VaccineScheduleGenerator.generateSchedule(
                    patient.getPatientId(),
                    patient.getDateOfBirth(),
                    doses,
                    carePlan.getCarePlanId()
            );

            // Insert scheduled doses
            scheduledDoseRepository.insertAll(scheduledDoses);

            // Schedule notifications for each dose
            for (ScheduledDoseEntity dose : scheduledDoses) {
                notificationScheduler.scheduleNotificationsForDose(dose);
            }

            Log.i(TAG, "Patient registered: " + patient.getPatientId() +
                    " with " + scheduledDoses.size() + " scheduled doses");

            // Callback with IDs
            if (callback != null) {
                callback.onPatientRegistered(patient.getPatientId(), carePlan.getCarePlanId());
            }
        });
    }

    /**
     * Record an immunization
     */
    public void recordImmunization(ImmunizationEntity immunization, OnImmunizationRecordedCallback callback) {
        // Generate unique immunization ID
        immunization.setImmunizationId(IdGenerator.generateImmunizationId());
        immunization.setCreatedAt(new Date());

        // Insert immunization
        immunizationRepository.insert(immunization);

        // Update scheduled dose status
        if (immunization.getScheduledDoseId() != null) {
            scheduledDoseRepository.markAsCompleted(
                    immunization.getScheduledDoseId(),
                    immunization.getAdministeredDate(),
                    immunization.getImmunizationId()
            );

            // Cancel pending notifications for this dose
            notificationScheduler.cancelNotifications(immunization.getScheduledDoseId());
        }

        // Update care plan completion count
        scheduledDoseRepository.getScheduledDosesByPatient(immunization.getPatientId())
                .observeForever(doses -> {
                    if (doses != null) {
                        int completedCount = 0;
                        for (ScheduledDoseEntity dose : doses) {
                            if (Constants.STATUS_COMPLETED.equals(dose.getStatus())) {
                                completedCount++;
                            }
                        }

                        // Store completedCount in a final variable for use in nested lambda
                        final int finalCompletedCount = completedCount;

                        // Update care plan
                        carePlanRepository.getCarePlanByPatient(immunization.getPatientId())
                                .observeForever(carePlan -> {
                                    if (carePlan != null) {
                                        carePlanRepository.updateCompletedDoses(
                                                carePlan.getCarePlanId(),
                                                finalCompletedCount
                                        );
                                    }
                                });
                    }
                });

        Log.i(TAG, "Immunization recorded: " + immunization.getImmunizationId());

        // Callback
        if (callback != null) {
            callback.onImmunizationRecorded(immunization.getImmunizationId());
        }
    }

    /**
     * Update overdue statuses for all scheduled doses
     */
    public void updateOverdueStatuses() {
        scheduledDoseRepository.getOverdueDoses(doses -> {
            Date now = new Date();
            for (ScheduledDoseEntity dose : doses) {
                if (dose.getScheduledDate().before(now) &&
                        !Constants.STATUS_COMPLETED.equals(dose.getStatus())) {

                    scheduledDoseRepository.updateStatus(
                            dose.getScheduledDoseId(),
                            Constants.STATUS_OVERDUE
                    );

                    // Schedule escalation reminders
                    notificationScheduler.scheduleOverdueReminders(dose);
                }
            }
        });
    }

    // Callback interfaces
    public interface OnPatientRegisteredCallback {
        void onPatientRegistered(String patientId, String carePlanId);
    }

    public interface OnImmunizationRecordedCallback {
        void onImmunizationRecorded(String immunizationId);
    }
}