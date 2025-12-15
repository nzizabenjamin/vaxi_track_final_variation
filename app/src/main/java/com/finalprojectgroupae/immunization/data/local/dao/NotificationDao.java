package com.finalprojectgroupae.immunization.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.finalprojectgroupae.immunization.data.local.entities.NotificationEntity;

import java.util.Date;
import java.util.List;

@Dao
public interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NotificationEntity notification);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NotificationEntity> notifications);

    @Update
    void update(NotificationEntity notification);

    @Query("SELECT * FROM notifications WHERE notification_id = :notificationId LIMIT 1")
    NotificationEntity getNotificationById(String notificationId);

    @Query("SELECT * FROM notifications WHERE scheduled_dose_id = :scheduledDoseId ORDER BY scheduled_send_date ASC")
    List<NotificationEntity> getNotificationsByScheduledDose(String scheduledDoseId);

    @Query("SELECT * FROM notifications WHERE status = 'pending' AND scheduled_send_date <= :currentDate ORDER BY scheduled_send_date ASC")
    List<NotificationEntity> getPendingNotifications(Date currentDate);

    @Query("SELECT * FROM notifications WHERE status = :status ORDER BY scheduled_send_date DESC")
    LiveData<List<NotificationEntity>> getNotificationsByStatus(String status);

    @Query("SELECT * FROM notifications WHERE status = 'failed' AND retry_count < 3")
    List<NotificationEntity> getFailedNotificationsForRetry();

    @Query("UPDATE notifications SET status = :status, sent_date = :sentDate WHERE notification_id = :notificationId")
    void updateNotificationStatus(String notificationId, String status, Date sentDate);

    @Query("UPDATE notifications SET status = 'failed', error_message = :errorMessage, retry_count = retry_count + 1 WHERE notification_id = :notificationId")
    void markAsFailed(String notificationId, String errorMessage);

    @Query("UPDATE notifications SET status = 'cancelled' WHERE scheduled_dose_id = :scheduledDoseId AND status = 'pending'")
    void cancelNotificationsForScheduledDose(String scheduledDoseId);

    @Query("DELETE FROM notifications WHERE status = 'sent' AND sent_date < :cutoffDate")
    void deleteOldNotifications(Date cutoffDate);
}