package com.finalprojectgroupae.immunization.data.remote.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CodeableConcept {

    @SerializedName("coding")
    // 🚩 CORRECTION: Specify the generic type parameter List<Coding> instead of raw List
    private List<Coding> coding;

    @SerializedName("text")
    private String text;

    // 🚩 CORRECTION: Update the return and parameter types to List<Coding>
    public List<Coding> getCoding() {
        return coding;
    }

    // 🚩 CORRECTION: Update the return and parameter types to List<Coding>
    public void setCoding(List<Coding> coding) {
        this.coding = coding;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static class Coding {
        @SerializedName("system")
        private String system;

        @SerializedName("code")
        private String code;

        @SerializedName("display")
        private String display;

        public String getSystem() {
            return system;
        }

        public void setSystem(String system) {
            this.system = system;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }
    }
}