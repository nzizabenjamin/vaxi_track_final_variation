package com.finalprojectgroupae.immunization.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "vaccine_definitions")
public class VaccineDefinitionEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "vaccine_def_id")
    private String vaccineDefId;

    @ColumnInfo(name = "vaccine_name")
    private String vaccineName;

    @ColumnInfo(name = "vaccine_code")
    private String vaccineCode; // CVX code

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "manufacturer")
    private String manufacturer;

    @ColumnInfo(name = "is_active")
    private boolean isActive;

    @ColumnInfo(name = "display_order")
    private int displayOrder;

    // Constructor
    public VaccineDefinitionEntity() {
        this.isActive = true;
    }

    // Getters and Setters
    @NonNull
    public String getVaccineDefId() {
        return vaccineDefId;
    }

    public void setVaccineDefId(@NonNull String vaccineDefId) {
        this.vaccineDefId = vaccineDefId;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getVaccineCode() {
        return vaccineCode;
    }

    public void setVaccineCode(String vaccineCode) {
        this.vaccineCode = vaccineCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }
}