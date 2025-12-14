package com.finalprojectgroupae.immunization.data.remote.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FhirAppointment {

    @SerializedName("resourceType")
    private String resourceType = "Appointment";

    @SerializedName("id")
    private String id;

    @SerializedName("status")
    private String status; // proposed, pending, booked, arrived, fulfilled, cancelled, noshow

    @SerializedName("description")
    private String description;

    @SerializedName("start")
    private String start; // DateTime

    @SerializedName("end")
    private String end; // DateTime

    @SerializedName("participant")
    private List<Participant> participant;

    @SerializedName("serviceType")
    private List<CodeableConcept> serviceType;

    // Constructors
    public FhirAppointment() {}

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public List<Participant> getParticipant() {
        return participant;
    }

    public void setParticipant(List<Participant> participant) {
        this.participant = participant;
    }

    public List<CodeableConcept> getServiceType() {
        return serviceType;
    }

    public void setServiceType(List<CodeableConcept> serviceType) {
        this.serviceType = serviceType;
    }

    // Nested classes
    public static class Participant {
        @SerializedName("actor")
        private Reference actor;

        @SerializedName("status")
        private String status; // accepted, declined, tentative, needs-action

        public Reference getActor() {
            return actor;
        }

        public void setActor(Reference actor) {
            this.actor = actor;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}