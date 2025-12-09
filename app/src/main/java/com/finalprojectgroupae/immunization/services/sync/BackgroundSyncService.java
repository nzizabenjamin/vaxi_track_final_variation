package com.finalprojectgroupae.immunization.services.sync;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.finalprojectgroupae.immunization.R;

public class BackgroundSyncService extends Service {

    private static final String TAG = "BackgroundSyncService";
    private static final String CHANNEL_ID = "sync_channel";
    private static final int NOTIFICATION_ID = 2001;

    private SyncService syncService;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service created");
        syncService = new SyncService(getApplication());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Service started");

        // Create notification channel
        createNotificationChannel();

        // Start as foreground service
        Notification notification = createNotification("Syncing data...");
        startForeground(NOTIFICATION_ID, notification);

        // Perform sync
        performSync();

        return START_NOT_STICKY;
    }

    private void performSync() {
        syncService.setSyncListener(new SyncService.SyncListener() {
            @Override
            public void onSyncStarted() {
                Log.i(TAG, "Sync started");
                updateNotification("Syncing vaccination data...");
            }

            @Override
            public void onSyncCompleted() {
                Log.i(TAG, "Sync completed");
                updateNotification("Sync completed successfully");
                stopSelfAfterDelay();
            }

            @Override
            public void onSyncFailed(String errorMessage) {
                Log.e(TAG, "Sync failed: " + errorMessage);
                updateNotification("Sync failed. Will retry later.");
                stopSelfAfterDelay();
            }
        });

        syncService.syncAll();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Data Synchronization",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel.setDescription("Background data sync notifications");

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    private Notification createNotification(String message) {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("VaxiTrack Sync")
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true)
                .build();
    }

    private void updateNotification(String message) {
        Notification notification = createNotification(message);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(NOTIFICATION_ID, notification);
        }
    }

    private void stopSelfAfterDelay() {
        // Stop service after 2 seconds to show completion message
        new android.os.Handler().postDelayed(() -> {
            stopForeground(true);
            stopSelf();
        }, 2000);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service destroyed");
    }
}
