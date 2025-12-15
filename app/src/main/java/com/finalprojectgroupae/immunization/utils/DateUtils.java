package com.finalprojectgroupae.immunization.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    private static final SimpleDateFormat DISPLAY_FORMAT =
            new SimpleDateFormat(Constants.DATE_FORMAT_DISPLAY, Locale.getDefault());

    private static final SimpleDateFormat API_FORMAT =
            new SimpleDateFormat(Constants.DATE_FORMAT_API, Locale.getDefault());

    private static final SimpleDateFormat DATETIME_API_FORMAT =
            new SimpleDateFormat(Constants.DATETIME_FORMAT_API, Locale.getDefault());

    /**
     * Format date for display to user
     */
    public static String formatForDisplay(Date date) {
        if (date == null) return "";
        return DISPLAY_FORMAT.format(date);
    }

    /**
     * Format time for display (HH:mm)
     */
    public static String formatTime(Date date) {
        if (date == null) return "";
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return timeFormat.format(date);
    }

    /**
     * Format date for API calls
     */
    public static String formatForApi(Date date) {
        if (date == null) return "";
        return API_FORMAT.format(date);
    }

    /**
     * Format datetime for API calls
     */
    public static String formatDateTimeForApi(Date date) {
        if (date == null) return "";
        return DATETIME_API_FORMAT.format(date);
    }

    /**
     * Parse date from API format
     */
    public static Date parseFromApi(String dateString) {
        if (dateString == null || dateString.isEmpty()) return null;
        try {
            return API_FORMAT.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Add days to a date
     */
    public static Date addDays(Date date, int days) {
        if (date == null) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

    /**
     * Add weeks to a date
     */
    public static Date addWeeks(Date date, int weeks) {
        return addDays(date, weeks * 7);
    }

    /**
     * Add months to a date
     */
    public static Date addMonths(Date date, int months) {
        if (date == null) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    /**
     * Calculate age in months from birth date
     */
    public static int getAgeInMonths(Date birthDate) {
        if (birthDate == null) return 0;

        Calendar birth = Calendar.getInstance();
        birth.setTime(birthDate);

        Calendar now = Calendar.getInstance();

        int years = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
        int months = now.get(Calendar.MONTH) - birth.get(Calendar.MONTH);

        return years * 12 + months;
    }

    /**
     * Calculate age in weeks from birth date
     */
    public static int getAgeInWeeks(Date birthDate) {
        if (birthDate == null) return 0;

        long diffMillis = new Date().getTime() - birthDate.getTime();
        return (int) TimeUnit.MILLISECONDS.toDays(diffMillis) / 7;
    }

    /**
     * Calculate age in days from birth date
     */
    public static int getAgeInDays(Date birthDate) {
        if (birthDate == null) return 0;

        long diffMillis = new Date().getTime() - birthDate.getTime();
        return (int) TimeUnit.MILLISECONDS.toDays(diffMillis);
    }

    /**
     * Get human-readable age (e.g., "2 months", "1 year 3 months")
     */
    public static String getHumanReadableAge(Date birthDate) {
        if (birthDate == null) return "";

        int totalMonths = getAgeInMonths(birthDate);
        int years = totalMonths / 12;
        int months = totalMonths % 12;

        if (years > 0) {
            if (months > 0) {
                return years + (years == 1 ? " year " : " years ") +
                        months + (months == 1 ? " month" : " months");
            } else {
                return years + (years == 1 ? " year" : " years");
            }
        } else {
            if (totalMonths > 0) {
                return totalMonths + (totalMonths == 1 ? " month" : " months");
            } else {
                int weeks = getAgeInWeeks(birthDate);
                if (weeks > 0) {
                    return weeks + (weeks == 1 ? " week" : " weeks");
                } else {
                    int days = getAgeInDays(birthDate);
                    return days + (days == 1 ? " day" : " days");
                }
            }
        }
    }

    /**
     * Check if date is today
     */
    public static boolean isToday(Date date) {
        if (date == null) return false;

        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);

        Calendar today = Calendar.getInstance();

        return dateCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                dateCalendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * Check if date is in the past
     */
    public static boolean isPast(Date date) {
        if (date == null) return false;
        return date.before(new Date());
    }

    /**
     * Check if date is in the future
     */
    public static boolean isFuture(Date date) {
        if (date == null) return false;
        return date.after(new Date());
    }

    /**
     * Get days between two dates
     */
    public static int daysBetween(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) return 0;

        long diffMillis = endDate.getTime() - startDate.getTime();
        return (int) TimeUnit.MILLISECONDS.toDays(diffMillis);
    }

    /**
     * Get start of day (00:00:00)
     */
    public static Date getStartOfDay(Date date) {
        if (date == null) return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Get end of day (23:59:59)
     */
    public static Date getEndOfDay(Date date) {
        if (date == null) return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * Format relative time (e.g., "3 days ago", "in 5 days")
     */
    public static String formatRelativeTime(Date date) {
        if (date == null) return "";

        int days = daysBetween(new Date(), date);

        if (days == 0) {
            return "Today";
        } else if (days == 1) {
            return "Tomorrow";
        } else if (days == -1) {
            return "Yesterday";
        } else if (days > 0) {
            return "In " + days + (days == 1 ? " day" : " days");
        } else {
            return Math.abs(days) + (days == -1 ? " day ago" : " days ago");
        }
    }
}