package com.finalprojectgroupae.immunization.data.remote.dto;

import com.google.gson.annotations.SerializedName;

public class Reference {

    @SerializedName("reference")
    private String reference;

    @SerializedName("display")
    private String display;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}