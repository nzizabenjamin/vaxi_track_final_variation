package com.finalprojectgroupae.immunization.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.util.Date;

@Entity(
        tableName = "scheduled_doses",
        foreignKeys = {
                @ForeignKey(
                        entity = PatientEntity.class,
                        parentColumns = "patient_id",
                        childColumns = "patient_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = VaccineDoseEntity.class,
                        parentColumns = "dose_id",
                        childColumns = "dose_id",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index(value = "patient_id"),
                @Index(value = "dose_id"),
                @Index(value = "scheduled_date"),
                @Index(value = "status")
        }
)
public class ScheduledDoseEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "scheduled_dose_id")
    private String scheduledDoseId;

    @ColumnInfo(name = "care_plan_id")
    private String carePlanId;

    @ColumnInfo(name = "patient_id")
    private String patientId;

    @ColumnInfo(name = "dose_id")
    private String doseId;

    @ColumnInfo(name = "scheduled_date")
    private Date scheduledDate;

    @ColumnInfo(name = "status")
    private String status; // scheduled, due, overdue, completed, missed, cancelled

    @ColumnInfo(name = "completed_date")
    private Date completedDate;

    @ColumnInfo(name = "immunization_id")
    private String immunizationId; // Links to actual immunization record

    @ColumnInfo(name = "reminders_sent")
    private String remindersSent; // JSON array of dates

    @ColumnInfo(name = "is_synced")
    private boolean isSynced;

    @ColumnInfo(name = "created_at")
    private Date createdAt;

    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    // Constructor - 🚩 CORRECTION: Added 'public' access modifier
    public ScheduledDoseEntity() {
        this.status = "scheduled";
        this.isSynced = false;
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.remindersSent = "[]";
    }

    // Getters and Setters (omitted for brevity, they remain correct)
    @NonNull
    public String getScheduledDoseId() {
        return scheduledDoseId;
    }

    public void setScheduledDoseId(@NonNull String scheduledDoseId) {
        this.scheduledDoseId = scheduledDoseId;
    }

    public String getCarePlanId() {
        return carePlanId;
    }

    public void setCarePlanId(String carePlanId) {
        this.carePlanId = carePlanId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoseId() {
        return doseId;
    }

    public void setDoseId(String doseId) {
        this.doseId = doseId;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.updatedAt = new Date();
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public String getImmunizationId() {
        return immunizationId;
    }

    public void setImmunizationId(String immunizationId) {
        this.immunizationId = immunizationId;
    }

    public String getRemindersSent() {
        return remindersSent;
    }

    public void setRemindersSent(String remindersSent) {
        this.remindersSent = remindersSent;
    }

    public boolean isSynced() {
        return isSynced;
    }

    public void setSynced(boolean synced) {
        isSynced = synced;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}