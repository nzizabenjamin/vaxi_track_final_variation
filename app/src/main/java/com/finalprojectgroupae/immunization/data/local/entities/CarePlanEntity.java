package com.finalprojectgroupae.immunization.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.util.Date;

@Entity(
        tableName = "care_plans",
        foreignKeys = @ForeignKey(
                entity = PatientEntity.class,
                parentColumns = "patient_id",
                childColumns = "patient_id",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {
                @Index(value = "patient_id", unique = true)
        }
)
public class CarePlanEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "care_plan_id")
    private String carePlanId;

    @ColumnInfo(name = "patient_id")
    private String patientId;

    @ColumnInfo(name = "status")
    private String status; // active, completed, cancelled

    @ColumnInfo(name = "created_date")
    private Date createdDate;

    @ColumnInfo(name = "updated_date")
    private Date updatedDate;

    @ColumnInfo(name = "total_doses")
    private int totalDoses;

    @ColumnInfo(name = "completed_doses")
    private int completedDoses;

    @ColumnInfo(name = "is_catch_up")
    private boolean isCatchUp; // Is this a catch-up schedule

    @ColumnInfo(name = "is_synced")
    private boolean isSynced;

    @ColumnInfo(name = "fhir_id")
    private String fhirId;

    // Constructor
    public CarePlanEntity() {
        this.status = "active";
        this.completedDoses = 0;
        this.isCatchUp = false;
        this.isSynced = false;
        this.createdDate = new Date();
        this.updatedDate = new Date();
    }

    // Getters and Setters
    @NonNull
    public String getCarePlanId() {
        return carePlanId;
    }

    public void setCarePlanId(@NonNull String carePlanId) {
        this.carePlanId = carePlanId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.updatedDate = new Date();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getTotalDoses() {
        return totalDoses;
    }

    public void setTotalDoses(int totalDoses) {
        this.totalDoses = totalDoses;
    }

    public int getCompletedDoses() {
        return completedDoses;
    }

    public void setCompletedDoses(int completedDoses) {
        this.completedDoses = completedDoses;
    }

    public boolean isCatchUp() {
        return isCatchUp;
    }

    public void setCatchUp(boolean catchUp) {
        isCatchUp = catchUp;
    }

    public boolean isSynced() {
        return isSynced;
    }

    public void setSynced(boolean synced) {
        isSynced = synced;
    }

    public String getFhirId() {
        return fhirId;
    }

    public void setFhirId(String fhirId) {
        this.fhirId = fhirId;
    }

    // Helper method
    public int getCompletionPercentage() {
        if (totalDoses == 0) return 0;
        return (completedDoses * 100) / totalDoses;
    }
}