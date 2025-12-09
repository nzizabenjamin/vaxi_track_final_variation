package com.finalprojectgroupae.immunization.data.remote.dto;

import com.google.gson.annotations.SerializedName;

public class ContactPoint {

    @SerializedName("system")
    private String system; // phone, email, etc.

    @SerializedName("value")
    private String value;

    @SerializedName("use")
    private String use; // home, work, mobile

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }
}