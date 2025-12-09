package com.finalprojectgroupae.immunization.utils;

public class Constants {

    // Database
    public static final String DATABASE_NAME = "immunization_db";
    public static final int DATABASE_VERSION = 1;

    // FHIR API
    public static final String FHIR_BASE_URL = "http://hapi.fhir.org/baseR4/";
    public static final int FHIR_TIMEOUT_SECONDS = 30;

    // Notification Settings
    public static final int[] REMINDER_DAYS_BEFORE = {7, 3, 1};
    public static final int[] OVERDUE_REMINDER_DAYS = {1, 3, 7, 14};

    // SharedPreferences Keys
    public static final String PREF_NAME = "VaxiTrackPrefs";
    public static final String KEY_LAST_SYNC = "last_sync_timestamp";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_USER_ROLE = "user_role";

    // Vaccine CVX Codes (Rwanda EPI)
    public static final String VACCINE_BCG = "19";
    public static final String VACCINE_OPV = "02";
    public static final String VACCINE_PENTA = "20"; // DTaP
    public static final String VACCINE_PCV = "133";
    public static final String VACCINE_ROTA = "122";
    public static final String VACCINE_MMR = "03";

    // Intent Actions
    public static final String ACTION_SYNC_DATA = "com.finalprojectgroupae.immunization.SYNC_DATA";
    public static final String ACTION_SEND_REMINDERS = "com.finalprojectgroupae.immunization.SEND_REMINDERS";

    // Work Manager Tags
    public static final String WORK_SYNC = "sync_work";
    public static final String WORK_REMINDERS = "reminder_work";

    // Date Formats
    public static final String DATE_FORMAT_DISPLAY = "dd MMM yyyy";
    public static final String DATE_FORMAT_API = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT_API = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    // Status Values
    public static final String STATUS_SCHEDULED = "scheduled";
    public static final String STATUS_DUE = "due";
    public static final String STATUS_OVERDUE = "overdue";
    public static final String STATUS_COMPLETED = "completed";
    public static final String STATUS_MISSED = "missed";
    public static final String STATUS_CANCELLED = "cancelled";

    // Notification Types
    public static final String NOTIFICATION_TYPE_SMS = "sms";
    public static final String NOTIFICATION_TYPE_PUSH = "push";
    public static final String NOTIFICATION_TYPE_EMAIL = "email";
    public static final String NOTIFICATION_TYPE_WHATSAPP = "whatsapp";

    private Constants() {
        // Private constructor to prevent instantiation
    }
}