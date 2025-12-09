package com.finalprojectgroupae.immunization.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.util.Date;

@Entity(
        tableName = "appointments",
        foreignKeys = {
                @ForeignKey(
                        entity = PatientEntity.class,
                        parentColumns = "patient_id",
                        childColumns = "patient_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = ScheduledDoseEntity.class,
                        parentColumns = "scheduled_dose_id",
                        childColumns = "scheduled_dose_id",
                        onDelete = ForeignKey.SET_NULL
                )
        },
        indices = {
                @Index(value = "patient_id"),
                @Index(value = "scheduled_dose_id"),
                @Index(value = "appointment_date"),
                @Index(value = "status")
        }
)
public class AppointmentEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "appointment_id")
    private String appointmentId;

    @ColumnInfo(name = "patient_id")
    private String patientId;

    @ColumnInfo(name = "scheduled_dose_id")
    private String scheduledDoseId;

    @ColumnInfo(name = "appointment_date")
    private Date appointmentDate;

    @ColumnInfo(name = "appointment_type")
    private String appointmentType; // outreach, facility, home_visit

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "status")
    private String status; // scheduled, confirmed, completed, cancelled, no-show

    @ColumnInfo(name = "notes")
    private String notes;

    @ColumnInfo(name = "created_by")
    private String createdBy; // User ID who created appointment

    @ColumnInfo(name = "is_synced")
    private boolean isSynced;

    @ColumnInfo(name = "created_at")
    private Date createdAt;

    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    @ColumnInfo(name = "fhir_id")
    private String fhirId;

    // Constructor
    public AppointmentEntity() {
        this.status = "scheduled";
        this.appointmentType = "facility";
        this.isSynced = false;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    // Getters and Setters
    @NonNull
    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(@NonNull String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getScheduledDoseId() {
        return scheduledDoseId;
    }

    public void setScheduledDoseId(String scheduledDoseId) {
        this.scheduledDoseId = scheduledDoseId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.updatedAt = new Date();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public String getFhirId() {
        return fhirId;
    }

    public void setFhirId(String fhirId) {
        this.fhirId = fhirId;
    }
}