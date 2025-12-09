package com.finalprojectgroupae.immunization.data.remote.dto;

import com.google.gson.annotations.SerializedName;

// 🚩 CORRECTION: REMOVE imports for the HAPI FHIR SDK
// import org.hl7.fhir.r4.model.CodeableConcept; // REMOVED
// import org.hl7.fhir.r4.model.Reference;       // REMOVED

import java.util.List;

public class FhirImmunization {

    @SerializedName("resourceType")
    private String resourceType = "Immunization";

    @SerializedName("id")
    private String id;

    @SerializedName("status")
    private String status;

    @SerializedName("vaccineCode")
    // 🚩 CORRECTION: Use the DTO class (implicit from package import)
    private CodeableConcept vaccineCode;

    @SerializedName("patient")
    // 🚩 CORRECTION: Use the DTO class
    private Reference patient;

    @SerializedName("occurrenceDateTime")
    private String occurrenceDateTime;

    @SerializedName("lotNumber")
    private String lotNumber;

    @SerializedName("expirationDate")
    private String expirationDate;

    @SerializedName("site")
    // 🚩 CORRECTION: Use the DTO class
    private CodeableConcept site;

    @SerializedName("route")
    // 🚩 CORRECTION: Use the DTO class
    private CodeableConcept route;

    @SerializedName("doseQuantity")
    private Quantity doseQuantity;

    @SerializedName("performer")
    // 🚩 CORRECTION: Use the generic type List<Performer> instead of raw java.util.List
    private List<Performer> performer;

    @SerializedName("location")
    // 🚩 CORRECTION: Use the DTO class
    private Reference location;

    // Constructors
    public FhirImmunization() {}

    // Getters and Setters
    public String getResourceType() {
        return resourceType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // 🚩 CORRECTION: Update parameter type to the DTO class
    public CodeableConcept getVaccineCode() {
        return vaccineCode;
    }

    // 🚩 CORRECTION: Update parameter type to the DTO class
    public void setVaccineCode(CodeableConcept vaccineCode) {
        this.vaccineCode = vaccineCode;
    }

    // 🚩 CORRECTION: Update parameter type to the DTO class
    public Reference getPatient() {
        return patient;
    }

    // 🚩 CORRECTION: Update parameter type to the DTO class
    public void setPatient(Reference patient) {
        this.patient = patient;
    }

    public String getOccurrenceDateTime() {
        return occurrenceDateTime;
    }

    public void setOccurrenceDateTime(String occurrenceDateTime) {
        this.occurrenceDateTime = occurrenceDateTime;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    // 🚩 CORRECTION: Update parameter type to the DTO class
    public CodeableConcept getSite() {
        return site;
    }

    // 🚩 CORRECTION: Update parameter type to the DTO class
    public void setSite(CodeableConcept site) {
        this.site = site;
    }

    // 🚩 CORRECTION: Update parameter type to the DTO class
    public CodeableConcept getRoute() {
        return route;
    }

    // 🚩 CORRECTION: Update parameter type to the DTO class
    public void setRoute(CodeableConcept route) {
        this.route = route;
    }

    public Quantity getDoseQuantity() {
        return doseQuantity;
    }

    public void setDoseQuantity(Quantity doseQuantity) {
        this.doseQuantity = doseQuantity;
    }

    // 🚩 CORRECTION: Update return and parameter types to List<Performer>
    public List<Performer> getPerformer() {
        return performer;
    }

    // 🚩 CORRECTION: Update return and parameter types to List<Performer>
    public void setPerformer(List<Performer> performer) {
        this.performer = performer;
    }

    // 🚩 CORRECTION: Update parameter type to the DTO class
    public Reference getLocation() {
        return location;
    }

    // 🚩 CORRECTION: Update parameter type to the DTO class
    public void setLocation(Reference location) {
        this.location = location;
    }

    // Nested classes
    public static class Performer {
        @SerializedName("actor")
        // 🚩 CORRECTION: Use the DTO class (assuming Reference is defined in the DTO package)
        private Reference actor;

        // 🚩 CORRECTION: Update parameter type to the DTO class
        public Reference getActor() {
            return actor;
        }

        // 🚩 CORRECTION: Update parameter type to the DTO class
        public void setActor(Reference actor) {
            this.actor = actor;
        }
    }

    public static class Quantity {
        @SerializedName("value")
        private double value;

        @SerializedName("unit")
        private String unit;

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }
}