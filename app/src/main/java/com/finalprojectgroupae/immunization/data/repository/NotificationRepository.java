package com.finalprojectgroupae.immunization.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.finalprojectgroupae.immunization.data.local.AppDatabase;
import com.finalprojectgroupae.immunization.data.local.dao.NotificationDao;
import com.finalprojectgroupae.immunization.data.local.entities.NotificationEntity;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotificationRepository {

    private final NotificationDao notificationDao;
    private final ExecutorService executorService;

    public NotificationRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        notificationDao = database.notificationDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    // Insert
    public void insert(NotificationEntity notification) {
        AppDatabase.databaseWriteExecutor.execute(() -> notificationDao.insert(notification));
    }

    // Insert multiple
    public void insertAll(List<NotificationEntity> notifications) {
        AppDatabase.databaseWriteExecutor.execute(() -> notificationDao.insertAll(notifications));
    }

    // Update
    public void update(NotificationEntity notification) {
        AppDatabase.databaseWriteExecutor.execute(() -> notificationDao.update(notification));
    }

    // Get by ID
    public void getNotificationById(String notificationId, OnNotificationLoadedCallback callback) {
        executorService.execute(() -> {
            NotificationEntity notification = notificationDao.getNotificationById(notificationId);
            callback.onNotificationLoaded(notification);
        });
    }

    // Get pending notifications
    public void getPendingNotifications(OnNotificationsLoadedCallback callback) {
        executorService.execute(() -> {
            List<NotificationEntity> notifications = notificationDao.getPendingNotifications(new Date());
            callback.onNotificationsLoaded(notifications);
        });
    }

    // Get by status
    public LiveData<List<NotificationEntity>> getNotificationsByStatus(String status) {
        return notificationDao.getNotificationsByStatus(status);
    }

    // Get by scheduled dose
    public void getNotificationsByScheduledDose(String scheduledDoseId, OnNotificationsLoadedCallback callback) {
        executorService.execute(() -> {
            List<NotificationEntity> notifications = notificationDao.getNotificationsByScheduledDose(scheduledDoseId);
            callback.onNotificationsLoaded(notifications);
        });
    }

    // Update status
    public void updateNotificationStatus(String notificationId, String status, Date sentDate) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                notificationDao.updateNotificationStatus(notificationId, status, sentDate));
    }

    // Mark as failed
    public void markAsFailed(String notificationId, String errorMessage) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                notificationDao.markAsFailed(notificationId, errorMessage));
    }

    // Cancel notifications for scheduled dose
    public void cancelNotificationsForScheduledDose(String scheduledDoseId) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                notificationDao.cancelNotificationsForScheduledDose(scheduledDoseId));
    }

    // Callback interfaces
    public interface OnNotificationLoadedCallback {
        void onNotificationLoaded(NotificationEntity notification);
    }

    public interface OnNotificationsLoadedCallback {
        void onNotificationsLoaded(List<NotificationEntity> notifications);
    }
}

