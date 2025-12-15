package com.finalprojectgroupae.immunization.data.remote.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FhirCarePlan {

    @SerializedName("resourceType")
    private String resourceType = "CarePlan";

    @SerializedName("id")
    private String id;

    @SerializedName("status")
    private String status;

    @SerializedName("intent")
    private String intent;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("subject")
    private Reference subject;

    @SerializedName("period")
    private Period period;

    @SerializedName("created")
    private String created;

    @SerializedName("author")
    private Reference author;

    @SerializedName("activity")
    private List<Activity> activity;

    public FhirCarePlan() {}

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

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Reference getSubject() {
        return subject;
    }

    public void setSubject(Reference subject) {
        this.subject = subject;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Reference getAuthor() {
        return author;
    }

    public void setAuthor(Reference author) {
        this.author = author;
    }

    public List<Activity> getActivity() {
        return activity;
    }

    public void setActivity(List<Activity> activity) {
        this.activity = activity;
    }

    public static class Period {
        @SerializedName("start")
        private String start;

        @SerializedName("end")
        private String end;

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
    }

    public static class Activity {
        @SerializedName("detail")
        private ActivityDetail detail;

        public ActivityDetail getDetail() {
            return detail;
        }

        public void setDetail(ActivityDetail detail) {
            this.detail = detail;
        }
    }

    public static class ActivityDetail {
        @SerializedName("code")
        private CodeableConcept code;

        @SerializedName("status")
        private String status;

        @SerializedName("scheduledTiming")
        private String scheduledTiming;

        public CodeableConcept getCode() {
            return code;
        }

        public void setCode(CodeableConcept code) {
            this.code = code;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getScheduledTiming() {
            return scheduledTiming;
        }

        public void setScheduledTiming(String scheduledTiming) {
            this.scheduledTiming = scheduledTiming;
        }
    }
}