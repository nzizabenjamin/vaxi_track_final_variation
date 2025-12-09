package com.finalprojectgroupae.immunization.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Index;
import androidx.annotation.NonNull;

import java.util.Date;

@Entity(
        tableName = "patients",
        indices = {
                @Index(value = "patient_id", unique = true),
                @Index(value = "guardian_id")
        }
)
public class PatientEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "patient_id")
    private String patientId;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    @ColumnInfo(name = "date_of_birth")
    private Date dateOfBirth;

    @ColumnInfo(name = "gender")
    private String gender; // "male", "female", "other"

    @ColumnInfo(name = "guardian_id")
    private String guardianId;

    @ColumnInfo(name = "guardian_name")
    private String guardianName;

    @ColumnInfo(name = "guardian_phone")
    private String guardianPhone;

    @ColumnInfo(name = "village")
    private String village;

    @ColumnInfo(name = "district")
    private String district;

    @ColumnInfo(name = "birth_certificate_number")
    private String birthCertificateNumber;

    @ColumnInfo(name = "registration_date")
    private Date registrationDate;

    @ColumnInfo(name = "is_active")
    private boolean isActive;

    @ColumnInfo(name = "is_synced")
    private boolean isSynced;

    @ColumnInfo(name = "created_at")
    private Date createdAt;

    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    @ColumnInfo(name = "fhir_id")
    private String fhirId; // ID on FHIR server

    // Constructor
    public PatientEntity() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.isActive = true;
        this.isSynced = false;
    }

    // Getters and Setters
    @NonNull
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(@NonNull String patientId) {
        this.patientId = patientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGuardianId() {
        return guardianId;
    }

    public void setGuardianId(String guardianId) {
        this.guardianId = guardianId;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public String getGuardianPhone() {
        return guardianPhone;
    }

    public void setGuardianPhone(String guardianPhone) {
        this.guardianPhone = guardianPhone;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getBirthCertificateNumber() {
        return birthCertificateNumber;
    }

    public void setBirthCertificateNumber(String birthCertificateNumber) {
        this.birthCertificateNumber = birthCertificateNumber;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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

    // Helper method
    public String getFullName() {
        return firstName + " " + lastName;
    }
}