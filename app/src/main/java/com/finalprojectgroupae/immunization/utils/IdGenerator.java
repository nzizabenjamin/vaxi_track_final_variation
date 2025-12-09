package com.finalprojectgroupae.immunization.utils;

import java.util.UUID;

public class IdGenerator {

    /**
     * Generate unique patient ID
     */
    public static String generatePatientId() {
        return "PAT-" + generateShortId();
    }

    /**
     * Generate unique immunization ID
     */
    public static String generateImmunizationId() {
        return "IMM-" + generateShortId();
    }

    /**
     * Generate unique scheduled dose ID
     */
    public static String generateScheduledDoseId() {
        return "SCH-" + generateShortId();
    }

    /**
     * Generate unique care plan ID
     */
    public static String generateCarePlanId() {
        return "CP-" + generateShortId();
    }

    /**
     * Generate unique appointment ID
     */
    public static String generateAppointmentId() {
        return "APT-" + generateShortId();
    }

    /**
     * Generate unique notification ID
     */
    public static String generateNotificationId() {
        return "NOT-" + generateShortId();
    }

    /**
     * Generate generic unique ID
     */
    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generate short alphanumeric ID (8 characters)
     */
    private static String generateShortId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
    }
}