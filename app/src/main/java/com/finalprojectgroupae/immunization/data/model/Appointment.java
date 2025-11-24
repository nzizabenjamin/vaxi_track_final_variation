package com.finalprojectgroupae.immunization.data.model;

import androidx.annotation.ColorRes;

public class Appointment {
    private final String title;
    private final String location;
    private final String date;
    private final String time;
    private final String mode;
    @ColorRes
    private final int modeColorRes;

    public Appointment(String title,
                       String location,
                       String date,
                       String time,
                       String mode,
                       @ColorRes int modeColorRes) {
        this.title = title;
        this.location = location;
        this.date = date;
        this.time = time;
        this.mode = mode;
        this.modeColorRes = modeColorRes;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getMode() {
        return mode;
    }

    @ColorRes
    public int getModeColorRes() {
        return modeColorRes;
    }
}

