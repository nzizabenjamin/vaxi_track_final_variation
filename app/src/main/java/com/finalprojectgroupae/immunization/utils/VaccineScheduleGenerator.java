package com.finalprojectgroupae.immunization.utils;

import com.finalprojectgroupae.immunization.data.local.entities.CarePlanEntity;
import com.finalprojectgroupae.immunization.data.local.entities.ScheduledDoseEntity;
import com.finalprojectgroupae.immunization.data.local.entities.VaccineDoseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class VaccineScheduleGenerator {

    /**
     * Generate a complete vaccination schedule for a patient based on birth date
     */
    public static List generateSchedule(
            String patientId,
            Date birthDate,
            List vaccineDoses,
            String carePlanId) {

        List scheduledDoses = new ArrayList<>();

        for (VaccineDoseEntity dose : vaccineDoses) {
            ScheduledDoseEntity scheduledDose = new ScheduledDoseEntity();
            scheduledDose.setScheduledDoseId(generateId());
            scheduledDose.setPatientId(patientId);
            scheduledDose.setCarePlanId(carePlanId);
            scheduledDose.setDoseId(dose.getDoseId());

            // Calculate scheduled date based on birth date + age in weeks
            Date scheduledDate = DateUtils.addWeeks(birthDate, dose.getAgeInWeeks());
            scheduledDose.setScheduledDate(scheduledDate);

            // Set initial status based on current date
            scheduledDose.setStatus(determineInitialStatus(scheduledDate));

            scheduledDoses.add(scheduledDose);
        }

        return scheduledDoses;
    }

    /**
     * Generate catch-up schedule for missed vaccinations
     */
    public static List generateCatchUpSchedule(
            String patientId,
            Date birthDate,
            List missedDoses,
            String carePlanId) {

        List catchUpDoses = new ArrayList<>();
        Date currentDate = new Date();
        Date nextScheduledDate = currentDate;

        for (VaccineDoseEntity dose : missedDoses) {
            ScheduledDoseEntity scheduledDose = new ScheduledDoseEntity();
            scheduledDose.setScheduledDoseId(generateId());
            scheduledDose.setPatientId(patientId);
            scheduledDose.setCarePlanId(carePlanId);
            scheduledDose.setDoseId(dose.getDoseId());

            // Schedule immediately or respect minimum intervals
            if (dose.getMinIntervalDays() > 0 && !catchUpDoses.isEmpty()) {
                nextScheduledDate = DateUtils.addDays(nextScheduledDate, dose.getMinIntervalDays());
            }

            scheduledDose.setScheduledDate(nextScheduledDate);
            scheduledDose.setStatus("scheduled");

            catchUpDoses.add(scheduledDose);
        }

        return catchUpDoses;
    }

    /**
     * Determine initial status of a scheduled dose based on date
     */
    private static String determineInitialStatus(Date scheduledDate) {
        Date now = new Date();
        int daysUntilDue = DateUtils.daysBetween(now, scheduledDate);

        if (daysUntilDue < 0) {
            return Constants.STATUS_OVERDUE;
        } else if (daysUntilDue <= 7) {
            return Constants.STATUS_DUE;
        } else {
            return Constants.STATUS_SCHEDULED;
        }
    }

    /**
     * Create a care plan for a patient
     */
    public static CarePlanEntity createCarePlan(String patientId, int totalDoses, boolean isCatchUp) {
        CarePlanEntity carePlan = new CarePlanEntity();
        carePlan.setCarePlanId(generateId());
        carePlan.setPatientId(patientId);
        carePlan.setTotalDoses(totalDoses);
        carePlan.setCompletedDoses(0);
        carePlan.setCatchUp(isCatchUp);
        carePlan.setStatus("active");
        return carePlan;
    }

    /**
     * Generate unique ID
     */
    private static String generateId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Calculate next dose date considering minimum interval
     */
    public static Date calculateNextDoseDate(Date previousDoseDate, int minIntervalDays) {
        if (previousDoseDate == null) {
            return new Date();
        }
        return DateUtils.addDays(previousDoseDate, minIntervalDays);
    }

    /**
     * Check if a patient is eligible for a specific dose
     */
    public static boolean isEligibleForDose(
            Date birthDate,
            VaccineDoseEntity dose,
            Date lastDoseDate) {

        // Check age eligibility
        int currentAgeInWeeks = DateUtils.getAgeInWeeks(birthDate);
        if (currentAgeInWeeks < dose.getAgeInWeeks()) {
            return false;
        }

        // Check minimum interval from previous dose
        if (lastDoseDate != null && dose.getMinIntervalDays() > 0) {
            int daysSinceLastDose = DateUtils.daysBetween(lastDoseDate, new Date());
            return daysSinceLastDose >= dose.getMinIntervalDays();
        }

        return true;
    }
}