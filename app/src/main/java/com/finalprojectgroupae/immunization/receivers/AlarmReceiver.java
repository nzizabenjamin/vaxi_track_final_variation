package com.finalprojectgroupae.immunization.receivers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.finalprojectgroupae.immunization.MainActivity;
import com.finalprojectgroupae.immunization.R;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";
    private static final String CHANNEL_ID = "vaccination_reminders";
    private static final int NOTIFICATION_ID = 1001;

    public static final String EXTRA_NOTIFICATION_MESSAGE = "notification_message";
    public static final String EXTRA_PATIENT_NAME = "patient_name";
    public static final String EXTRA_SCHEDULED_DOSE_ID = "scheduled_dose_id";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if ("com.finalprojectgroupae.immunization.SEND_REMINDER".equals(action)) {
            String message = intent.getStringExtra(EXTRA_NOTIFICATION_MESSAGE);
            String patientName = intent.getStringExtra(EXTRA_PATIENT_NAME);

            Log.i(TAG, "Sending reminder notification: " + message);
            sendNotification(context, message, patientName);

        } else if ("com.finalprojectgroupae.immunization.CHECK_OVERDUE".equals(action)) {
            Log.i(TAG, "Checking for overdue doses");
            checkOverdueDoses(context);
        }
    }

    private void sendNotification(Context context, String message, String patientName) {
        createNotificationChannel(context);

        // Intent to open app when notification is clicked
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
        );

        // Build notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher) // Use your app icon
                .setContentTitle("Vaccination Reminder")
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        // Show notification
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, builder.build());
            Log.i(TAG, "Notification sent successfully");
        }
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Vaccination Reminders";
            String description = "Notifications for upcoming vaccinations";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager =
                    context.getSystemService(NotificationManager.class);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void checkOverdueDoses(Context context) {
        // TODO: Query database for overdue doses and send notifications
        Log.i(TAG, "Checking overdue doses...");
    }
}