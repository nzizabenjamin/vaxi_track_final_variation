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
    private final CarePlanRepository carePlanRepository;
    private final ScheduledDoseRepository scheduledDoseRepository;
    private final VaccineDoseRepository vaccineDoseRepository;
    private final ImmunizationRepository immunizationRepository;
    private final NotificationScheduler notificationScheduler;

    public ImmunizationService(Application application) {
        this.application = application;
        this.patientRepository = new PatientRepository(application);
        this.carePlanRepository = new CarePlanRepository(application);
        this.scheduledDoseRepository = new ScheduledDoseRepository(application);
        this.vaccineDoseRepository = new VaccineDoseRepository(application);
        this.immunizationRepository = new ImmunizationRepository(application);
        this.notificationScheduler = new NotificationScheduler(application);
    }

    /**
     * Register a new patient and generate vaccination schedule
     */
    public void registerPatient(PatientEntity patient, OnPatientRegisteredCallback callback) {
        // Generate patient ID
        patient.setPatientId(IdGenerator.generatePatientId());
        patient.setRegistrationDate(new Date());

        // Save patient
        patientRepository.insert(patient);
        Log.i(TAG, "Patient registered: " + patient.getPatientId());

        // Generate care plan and schedule
        generateCarePlan(patient, callback);
    }

    /**
     * Generate care plan and vaccination schedule for patient
     */
    private void generateCarePlan(PatientEntity patient, OnPatientRegisteredCallback callback) {
        // Get all required vaccine doses
        // 🚩 CORRECTION: Use raw 'List' in lambda parameter, then cast inside.
        vaccineDoseRepository.getRequiredDoses(doses -> {
            // Explicitly cast to the correct type
            List<VaccineDoseEntity> vaccineDoses = (List<VaccineDoseEntity>) doses;

            // Create care plan
            CarePlanEntity carePlan = VaccineScheduleGenerator.createCarePlan(
                    patient.getPatientId(),
                    vaccineDoses.size(),
                    false
            );
            carePlanRepository.insert(carePlan);
            Log.i(TAG, "Care plan created: " + carePlan.getCarePlanId());

            // Generate scheduled doses
            List<ScheduledDoseEntity> scheduledDoses = VaccineScheduleGenerator.generateSchedule(
                    patient.getPatientId(),
                    patient.getDateOfBirth(),
                    vaccineDoses,
                    carePlan.getCarePlanId()
            );

            // Save scheduled doses
            scheduledDoseRepository.insertAll(scheduledDoses);
            Log.i(TAG, "Generated " + scheduledDoses.size() + " scheduled doses");

            for (ScheduledDoseEntity dose : scheduledDoses) {
                notificationScheduler.scheduleNotificationsForDose(dose);
            }

            if (callback != null) {
                callback.onPatientRegistered(patient.getPatientId(), carePlan.getCarePlanId());
            }
        });
    }

    /**
     * Record an immunization
     */
    public void recordImmunization(ImmunizationEntity immunization, OnImmunizationRecordedCallback callback) {
        // Generate immunization ID
        immunization.setImmunizationId(IdGenerator.generateImmunizationId());
        immunization.setCreatedAt(new Date());
        immunization.setStatus(Constants.STATUS_COMPLETED);

        // Save immunization
        immunizationRepository.insert(immunization);
        Log.i(TAG, "Immunization recorded: " + immunization.getImmunizationId());

        // Update scheduled dose if linked
        if (immunization.getScheduledDoseId() != null) {
            markScheduledDoseCompleted(immunization.getScheduledDoseId(), immunization);
        }

        if (callback != null) {
            callback.onImmunizationRecorded(immunization.getImmunizationId());
        }
    }

    /**
     * Mark a scheduled dose as completed
     */
    private void markScheduledDoseCompleted(String scheduledDoseId, ImmunizationEntity immunization) {
        scheduledDoseRepository.markAsCompleted(
                scheduledDoseId,
                immunization.getAdministeredDate(),
                immunization.getImmunizationId()
        );

        // Cancel pending reminders
        notificationScheduler.cancelNotifications(scheduledDoseId);

        // Update care plan progress
        updateCarePlanProgress(immunization.getPatientId());

        Log.i(TAG, "Scheduled dose marked as completed: " + scheduledDoseId);
    }

    /**
     * Update care plan completion progress
     */
    private void updateCarePlanProgress(String patientId) {
        scheduledDoseRepository.getCompletionStats(patientId, (completed, total) -> {
            // Find care plan and update
            // This requires getting care plan by patient - implementation needed
            Log.i(TAG, "Care plan progress: " + completed + "/" + total);
        });
    }

    /**
     * Check and update overdue doses
     */
    public void checkAndUpdateOverdueDoses() {
        // 🚩 CORRECTION: Use raw 'List' in lambda parameter, then cast inside.
        scheduledDoseRepository.getOverdueDoses(doses -> {
            // Explicitly cast to the correct type
            List<ScheduledDoseEntity> scheduledDoses = (List<ScheduledDoseEntity>) doses;

            for (ScheduledDoseEntity dose : scheduledDoses) {
                if (!dose.getStatus().equals(Constants.STATUS_OVERDUE)) {
                    scheduledDoseRepository.updateStatus(dose.getScheduledDoseId(), Constants.STATUS_OVERDUE);
                    notificationScheduler.scheduleOverdueReminders(dose);
                    Log.i(TAG, "Dose marked as overdue: " + dose.getScheduledDoseId());
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