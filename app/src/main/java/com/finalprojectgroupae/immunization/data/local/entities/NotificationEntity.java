package com.finalprojectgroupae.immunization.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.util.Date;

@Entity(
        tableName = "notifications",
        foreignKeys = @ForeignKey(
                entity = ScheduledDoseEntity.class,
                parentColumns = "scheduled_dose_id",
                childColumns = "scheduled_dose_id",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {
                @Index(value = "scheduled_dose_id"),
                @Index(value = "scheduled_send_date"),
                @Index(value = "status")
        }
)
public class NotificationEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "notification_id")
    private String notificationId;

    @ColumnInfo(name = "scheduled_dose_id")
    private String scheduledDoseId;

    @ColumnInfo(name = "recipient_id")
    private String recipientId; // Guardian ID

    @ColumnInfo(name = "recipient_phone")
    private String recipientPhone;

    @ColumnInfo(name = "type")
    private String type; // sms, push, email, whatsapp

    @ColumnInfo(name = "message")
    private String message;

    @ColumnInfo(name = "scheduled_send_date")
    private Date scheduledSendDate;

    @ColumnInfo(name = "sent_date")
    private Date sentDate;

    @ColumnInfo(name = "status")
    private String status; // pending, sent, failed, cancelled

    @ColumnInfo(name = "error_message")
    private String errorMessage;

    @ColumnInfo(name = "retry_count")
    private int retryCount;

    @ColumnInfo(name = "created_at")
    private Date createdAt;

    // Constructor
    public NotificationEntity() {
        this.status = "pending";
        this.retryCount = 0;
        this.createdAt = new Date();
    }

    // Getters and Setters
    @NonNull
    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(@NonNull String notificationId) {
        this.notificationId = notificationId;
    }

    public String getScheduledDoseId() {
        return scheduledDoseId;
    }

    public void setScheduledDoseId(String scheduledDoseId) {
        this.scheduledDoseId = scheduledDoseId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getScheduledSendDate() {
        return scheduledSendDate;
    }

    public void setScheduledSendDate(Date scheduledSendDate) {
        this.scheduledSendDate = scheduledSendDate;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}