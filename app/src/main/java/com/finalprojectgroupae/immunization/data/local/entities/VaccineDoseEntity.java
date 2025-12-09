package com.finalprojectgroupae.immunization.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(
        tableName = "vaccine_doses",
        foreignKeys = @ForeignKey(
                entity = VaccineDefinitionEntity.class,
                parentColumns = "vaccine_def_id",
                childColumns = "vaccine_def_id",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {
                @Index(value = "vaccine_def_id")
        }
)
public class VaccineDoseEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "dose_id")
    private String doseId;

    @ColumnInfo(name = "vaccine_def_id")
    private String vaccineDefId;

    @ColumnInfo(name = "dose_number")
    private int doseNumber; // 1, 2, 3, etc.

    @ColumnInfo(name = "age_in_weeks")
    private int ageInWeeks; // When should this dose be given

    @ColumnInfo(name = "min_interval_days")
    private int minIntervalDays; // Minimum days between doses

    @ColumnInfo(name = "reminder_days_before")
    private String reminderDaysBefore; // JSON array: "[7,3,1]"

    @ColumnInfo(name = "is_required")
    private boolean isRequired;

    @ColumnInfo(name = "display_order")
    private int displayOrder;

    // Constructor
    public VaccineDoseEntity() {
        this.isRequired = true;
        this.reminderDaysBefore = "[7,3,1]";
    }

    // Getters and Setters
    @NonNull
    public String getDoseId() {
        return doseId;
    }

    public void setDoseId(@NonNull String doseId) {
        this.doseId = doseId;
    }

    public String getVaccineDefId() {
        return vaccineDefId;
    }

    public void setVaccineDefId(String vaccineDefId) {
        this.vaccineDefId = vaccineDefId;
    }

    public int getDoseNumber() {
        return doseNumber;
    }

    public void setDoseNumber(int doseNumber) {
        this.doseNumber = doseNumber;
    }

    public int getAgeInWeeks() {
        return ageInWeeks;
    }

    public void setAgeInWeeks(int ageInWeeks) {
        this.ageInWeeks = ageInWeeks;
    }

    public int getMinIntervalDays() {
        return minIntervalDays;
    }

    public void setMinIntervalDays(int minIntervalDays) {
        this.minIntervalDays = minIntervalDays;
    }

    public String getReminderDaysBefore() {
        return reminderDaysBefore;
    }

    public void setReminderDaysBefore(String reminderDaysBefore) {
        this.reminderDaysBefore = reminderDaysBefore;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }
}