package com.finalprojectgroupae.immunization.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.util.Date;

@Entity(
        tableName = "immunizations",
        foreignKeys = @ForeignKey(
                entity = PatientEntity.class,
                parentColumns = "patient_id",
                childColumns = "patient_id",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {
                @Index(value = "patient_id"),
                @Index(value = "scheduled_dose_id"),
                @Index(value = "administered_date")
        }
)
public class ImmunizationEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "immunization_id")
    private String immunizationId;

    @ColumnInfo(name = "patient_id")
    private String patientId;

    @ColumnInfo(name = "scheduled_dose_id")
    private String scheduledDoseId;

    @ColumnInfo(name = "vaccine_code")
    private String vaccineCode; // CVX code

    @ColumnInfo(name = "vaccine_name")
    private String vaccineName;

    @ColumnInfo(name = "administered_date")
    private Date administeredDate;

    @ColumnInfo(name = "lot_number")
    private String lotNumber;

    @ColumnInfo(name = "expiration_date")
    private Date expirationDate;

    @ColumnInfo(name = "manufacturer")
    private String manufacturer;

    @ColumnInfo(name = "administered_by")
    private String administeredBy; // CHW user ID

    @ColumnInfo(name = "facility_id")
    private String facilityId;

    @ColumnInfo(name = "route")
    private String route; // intramuscular, oral, subcutaneous, intradermal

    @ColumnInfo(name = "site")
    private String site; // left arm, right arm, left thigh, right thigh

    @ColumnInfo(name = "dose_quantity")
    private Double doseQuantity; // in ml

    @ColumnInfo(name = "status")
    private String status; // completed, entered-in-error, not-done

    @ColumnInfo(name = "notes")
    private String notes;

    @ColumnInfo(name = "had_reaction")
    private boolean hadReaction;

    @ColumnInfo(name = "reaction_details")
    private String reactionDetails;

    @ColumnInfo(name = "is_synced")
    private boolean isSynced;

    @ColumnInfo(name = "fhir_id")
    private String fhirId;

    @ColumnInfo(name = "created_at")
    private Date createdAt;

    @ColumnInfo(name = "synced_at")
    private Date syncedAt;

    // Constructor
    public ImmunizationEntity() {
        this.status = "completed";
        this.hadReaction = false;
        this.isSynced = false;
        this.createdAt = new Date();
    }

    // Getters and Setters
    @NonNull
    public String getImmunizationId() {
        return immunizationId;
    }

    public void setImmunizationId(@NonNull String immunizationId) {
        this.immunizationId = immunizationId;
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

    public String getVaccineCode() {
        return vaccineCode;
    }

    public void setVaccineCode(String vaccineCode) {
        this.vaccineCode = vaccineCode;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public Date getAdministeredDate() {
        return administeredDate;
    }

    public void setAdministeredDate(Date administeredDate) {
        this.administeredDate = administeredDate;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getAdministeredBy() {
        return administeredBy;
    }

    public void setAdministeredBy(String administeredBy) {
        this.administeredBy = administeredBy;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Double getDoseQuantity() {
        return doseQuantity;
    }

    public void setDoseQuantity(Double doseQuantity) {
        this.doseQuantity = doseQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isHadReaction() {
        return hadReaction;
    }

    public void setHadReaction(boolean hadReaction) {
        this.hadReaction = hadReaction;
    }

    public String getReactionDetails() {
        return reactionDetails;
    }

    public void setReactionDetails(String reactionDetails) {
        this.reactionDetails = reactionDetails;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getSyncedAt() {
        return syncedAt;
    }

    public void setSyncedAt(Date syncedAt) {
        this.syncedAt = syncedAt;
    }
}