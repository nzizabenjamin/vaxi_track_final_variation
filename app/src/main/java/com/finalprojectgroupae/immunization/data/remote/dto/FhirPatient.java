package com.finalprojectgroupae.immunization.data.remote.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FhirPatient {

    @SerializedName("resourceType")
    private String resourceType = "Patient";

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    // 🚩 CORRECTION: Use generic List<HumanName>
    private List<HumanName> name;

    @SerializedName("gender")
    private String gender;

    @SerializedName("birthDate")
    private String birthDate;

    @SerializedName("contact")
    // 🚩 CORRECTION: Use generic List<Contact>
    private List<Contact> contact;

    @SerializedName("address")
    // 🚩 CORRECTION: Use generic List<Address>
    private List<Address> address;

    // Constructors
    public FhirPatient() {}

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

    // 🚩 CORRECTION: Update return type to List<HumanName>
    public List<HumanName> getName() {
        return name;
    }

    // 🚩 CORRECTION: Update parameter type to List<HumanName>
    public void setName(List<HumanName> name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    // 🚩 CORRECTION: Update return type to List<Contact>
    public List<Contact> getContact() {
        return contact;
    }

    // 🚩 CORRECTION: Update parameter type to List<Contact>
    public void setContact(List<Contact> contact) {
        this.contact = contact;
    }

    // 🚩 CORRECTION: Update return type to List<Address>
    public List<Address> getAddress() {
        return address;
    }

    // 🚩 CORRECTION: Update parameter type to List<Address>
    public void setAddress(List<Address> address) {
        this.address = address;
    }

    // Nested classes
    public static class HumanName {
        @SerializedName("family")
        private String family;

        @SerializedName("given")
        // 🚩 CORRECTION: Use generic List<String> for given names
        private List<String> given;

        public String getFamily() {
            return family;
        }

        public void setFamily(String family) {
            this.family = family;
        }

        // 🚩 CORRECTION: Update return type to List<String>
        public List<String> getGiven() {
            return given;
        }

        // 🚩 CORRECTION: Update parameter type to List<String>
        public void setGiven(List<String> given) {
            this.given = given;
        }
    }

    public static class Contact {
        @SerializedName("relationship")
        // 🚩 CORRECTION: Use generic List<CodeableConcept> (assuming CodeableConcept is in your DTO package)
        private List<CodeableConcept> relationship;

        @SerializedName("name")
        private HumanName name;

        @SerializedName("telecom")
        // 🚩 CORRECTION: Use generic List<ContactPoint> (assuming ContactPoint is defined in your DTO package)
        private List<ContactPoint> telecom;

        // 🚩 CORRECTION: Update return type to List<CodeableConcept>
        public List<CodeableConcept> getRelationship() {
            return relationship;
        }

        // 🚩 CORRECTION: Update parameter type to List<CodeableConcept>
        public void setRelationship(List<CodeableConcept> relationship) {
            this.relationship = relationship;
        }

        public HumanName getName() {
            return name;
        }

        public void setName(HumanName name) {
            this.name = name;
        }

        // 🚩 CORRECTION: Update return type to List<ContactPoint>
        public List<ContactPoint> getTelecom() {
            return telecom;
        }

        // 🚩 CORRECTION: Update parameter type to List<ContactPoint>
        public void setTelecom(List<ContactPoint> telecom) {
            this.telecom = telecom;
        }
    }

    public static class Address {
        @SerializedName("line")
        // 🚩 CORRECTION: Use generic List<String> for address lines
        private List<String> line;

        @SerializedName("city")
        private String city;

        @SerializedName("district")
        private String district;

        @SerializedName("country")
        private String country;

        // 🚩 CORRECTION: Update return type to List<String>
        public List<String> getLine() {
            return line;
        }

        // 🚩 CORRECTION: Update parameter type to List<String>
        public void setLine(List<String> line) {
            this.line = line;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }
}