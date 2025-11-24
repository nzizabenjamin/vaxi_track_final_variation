package com.finalprojectgroupae.immunization.data.model;

import androidx.annotation.ColorRes;

public class Child {
    private final String id;
    private final String name;
    private final String caregiver;
    private final String village;
    private final String dueVaccine;
    private final String dueDate;
    private final String status;
    private final int months;
    @ColorRes
    private final int statusColorRes;

    public Child(String id,
                 String name,
                 String caregiver,
                 String village,
                 String dueVaccine,
                 String dueDate,
                 String status,
                 int months,
                 @ColorRes int statusColorRes) {
        this.id = id;
        this.name = name;
        this.caregiver = caregiver;
        this.village = village;
        this.dueVaccine = dueVaccine;
        this.dueDate = dueDate;
        this.status = status;
        this.months = months;
        this.statusColorRes = statusColorRes;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCaregiver() {
        return caregiver;
    }

    public String getVillage() {
        return village;
    }

    public String getDueVaccine() {
        return dueVaccine;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getStatus() {
        return status;
    }

    public int getMonths() {
        return months;
    }

    @ColorRes
    public int getStatusColorRes() {
        return statusColorRes;
    }
}

