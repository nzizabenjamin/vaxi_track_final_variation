package com.finalprojectgroupae.immunization.data.remote.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FhirBundle {

    @SerializedName("resourceType")
    private String resourceType = "Bundle";

    @SerializedName("type")
    private String type;

    @SerializedName("total")
    private int total;

    @SerializedName("entry")
    private List<Entry> entry;

    public String getResourceType() {
        return resourceType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Entry> getEntry() {
        return entry;
    }

    public void setEntry(List<Entry> entry) {
        this.entry = entry;
    }

    public static class Entry {
        @SerializedName("fullUrl")
        private String fullUrl;

        @SerializedName("resource")
        private Object resource;

        public String getFullUrl() {
            return fullUrl;
        }

        public void setFullUrl(String fullUrl) {
            this.fullUrl = fullUrl;
        }

        public Object getResource() {
            return resource;
        }

        public void setResource(Object resource) {
            this.resource = resource;
        }
    }
}