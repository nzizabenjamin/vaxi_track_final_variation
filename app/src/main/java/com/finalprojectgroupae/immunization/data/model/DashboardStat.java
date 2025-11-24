package com.finalprojectgroupae.immunization.data.model;

import androidx.annotation.ColorRes;

public class DashboardStat {
    private final String label;
    private final String value;
    private final String subtitle;
    @ColorRes
    private final int accentColorRes;

    public DashboardStat(String label, String value, String subtitle, @ColorRes int accentColorRes) {
        this.label = label;
        this.value = value;
        this.subtitle = subtitle;
        this.accentColorRes = accentColorRes;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public String getSubtitle() {
        return subtitle;
    }

    @ColorRes
    public int getAccentColorRes() {
        return accentColorRes;
    }
}

