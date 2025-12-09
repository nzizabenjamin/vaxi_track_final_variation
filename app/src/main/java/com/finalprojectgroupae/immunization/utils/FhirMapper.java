package com.finalprojectgroupae.immunization.utils;

import com.finalprojectgroupae.immunization.data.local.entities.ImmunizationEntity;
import com.finalprojectgroupae.immunization.data.local.entities.PatientEntity;
import com.finalprojectgroupae.immunization.data.remote.dto.*; // Import all DTOs
import com.finalprojectgroupae.immunization.data.remote.dto.CodeableConcept; // Explicit DTO import
import com.finalprojectgroupae.immunization.data.remote.dto.Reference;       // Explicit DTO import
import com.finalprojectgroupae.immunization.data.remote.dto.ContactPoint;   // Explicit DTO import

import java.util.Collections;
import java.util.List;

public class FhirMapper {

    /**
     * Convert PatientEntity to FHIR Patient resource
     */
    public static FhirPatient toFhirPatient(PatientEntity entity) {
        FhirPatient patient = new FhirPatient();

        if (entity.getFhirId() != null) {
            patient.setId(entity.getFhirId());
        }

        // Name
        FhirPatient.HumanName name = new FhirPatient.HumanName();
        name.setFamily(entity.getLastName());
        name.setGiven(Collections.singletonList(entity.getFirstName()));
        patient.setName(Collections.singletonList(name));

        // Gender
        patient.setGender(entity.getGender());

        // Birth date
        patient.setBirthDate(DateUtils.formatForApi(entity.getDateOfBirth()));

        // Contact (Guardian)
        if (entity.getGuardianName() != null || entity.getGuardianPhone() != null) {
            FhirPatient.Contact contact = new FhirPatient.Contact();

            // Guardian relationship
            CodeableConcept relationship = new CodeableConcept();
            relationship.setText("Guardian");
            contact.setRelationship(Collections.singletonList(relationship));

            // Guardian name
            if (entity.getGuardianName() != null) {
                FhirPatient.HumanName guardianName = new FhirPatient.HumanName();
                guardianName.setFamily(entity.getGuardianName());
                contact.setName(guardianName);
            }

            // Guardian phone
            if (entity.getGuardianPhone() != null) {
                ContactPoint phone = new ContactPoint();
                phone.setSystem("phone");
                phone.setValue(entity.getGuardianPhone());
                contact.setTelecom(Collections.singletonList(phone));
            }

            patient.setContact(Collections.singletonList(contact));
        }

        // Address
        if (entity.getVillage() != null || entity.getDistrict() != null) {
            FhirPatient.Address address = new FhirPatient.Address();
            if (entity.getVillage() != null) {
                address.setCity(entity.getVillage());
            }
            if (entity.getDistrict() != null) {
                address.setDistrict(entity.getDistrict());
            }
            address.setCountry("Rwanda");
            patient.setAddress(Collections.singletonList(address));
        }

        return patient;
    }

    /**
     * Convert FHIR Patient to PatientEntity
     */
    public static PatientEntity fromFhirPatient(FhirPatient fhirPatient) {
        PatientEntity entity = new PatientEntity();

        entity.setFhirId(fhirPatient.getId());

        // Name
        if (fhirPatient.getName() != null && !fhirPatient.getName().isEmpty()) {
            // Use explicit cast because the DTO list may return Object
            FhirPatient.HumanName name = (FhirPatient.HumanName) fhirPatient.getName().get(0);
            entity.setFirstName(name.getGiven() != null && !name.getGiven().isEmpty()
                    ? name.getGiven().get(0) : "");
            entity.setLastName(name.getFamily());
        }

        // Gender
        entity.setGender(fhirPatient.getGender());

        // Birth date
        entity.setDateOfBirth(DateUtils.parseFromApi(fhirPatient.getBirthDate()));

        // Contact
        if (fhirPatient.getContact() != null && !fhirPatient.getContact().isEmpty()) {
            // Use explicit cast
            FhirPatient.Contact contact = (FhirPatient.Contact) fhirPatient.getContact().get(0);
            if (contact.getName() != null) {
                entity.setGuardianName(contact.getName().getFamily());
            }
            if (contact.getTelecom() != null && !contact.getTelecom().isEmpty()) {
                // Use explicit cast
                ContactPoint phone = (ContactPoint) contact.getTelecom().get(0);
                entity.setGuardianPhone(phone.getValue());
            }
        }

        // Address
        if (fhirPatient.getAddress() != null && !fhirPatient.getAddress().isEmpty()) {
            // Use explicit cast
            FhirPatient.Address address = (FhirPatient.Address) fhirPatient.getAddress().get(0);
            entity.setVillage(address.getCity());
            entity.setDistrict(address.getDistrict());
        }

        return entity;
    }

    /**
     * Convert ImmunizationEntity to FHIR Immunization resource
     */
    public static FhirImmunization toFhirImmunization(ImmunizationEntity entity) {
        FhirImmunization immunization = new FhirImmunization();

        if (entity.getFhirId() != null) {
            immunization.setId(entity.getFhirId());
        }

        // Status
        immunization.setStatus(entity.getStatus());

        // Vaccine code
        CodeableConcept vaccineCode = new CodeableConcept();
        CodeableConcept.Coding coding = new CodeableConcept.Coding();
        coding.setSystem("http://hl7.org/fhir/sid/cvx");
        coding.setCode(entity.getVaccineCode());
        coding.setDisplay(entity.getVaccineName());
        vaccineCode.setCoding(Collections.singletonList(coding));
        vaccineCode.setText(entity.getVaccineName());
        immunization.setVaccineCode(vaccineCode);

        // Patient reference
        Reference patientRef = new Reference();
        patientRef.setReference("Patient/" + entity.getPatientId());
        immunization.setPatient(patientRef);

        // Occurrence date
        immunization.setOccurrenceDateTime(
                DateUtils.formatDateTimeForApi(entity.getAdministeredDate()));

        // Lot number
        immunization.setLotNumber(entity.getLotNumber());

        // Expiration date
        if (entity.getExpirationDate() != null) {
            immunization.setExpirationDate(
                    DateUtils.formatForApi(entity.getExpirationDate()));
        }

        // Route
        if (entity.getRoute() != null) {
            CodeableConcept route = new CodeableConcept();
            route.setText(entity.getRoute());
            immunization.setRoute(route);
        }

        // Site
        if (entity.getSite() != null) {
            CodeableConcept site = new CodeableConcept();
            site.setText(entity.getSite());
            immunization.setSite(site);
        }

        // Dose quantity
        if (entity.getDoseQuantity() != null) {
            FhirImmunization.Quantity quantity = new FhirImmunization.Quantity();
            quantity.setValue(entity.getDoseQuantity());
            quantity.setUnit("ml");
            immunization.setDoseQuantity(quantity);
        }

        // Performer (CHW)
        if (entity.getAdministeredBy() != null) {
            FhirImmunization.Performer performer = new FhirImmunization.Performer();
            Reference actor = new Reference();
            actor.setReference("Practitioner/" + entity.getAdministeredBy());
            performer.setActor(actor);
            immunization.setPerformer(Collections.singletonList(performer));
        }

        // Location
        if (entity.getFacilityId() != null) {
            Reference location = new Reference();
            location.setReference("Location/" + entity.getFacilityId());
            immunization.setLocation(location);
        }

        return immunization;
    }

    /**
     * Convert FHIR Immunization to ImmunizationEntity
     */
    public static ImmunizationEntity fromFhirImmunization(FhirImmunization fhirImmunization) {
        ImmunizationEntity entity = new ImmunizationEntity();

        entity.setFhirId(fhirImmunization.getId());
        entity.setStatus(fhirImmunization.getStatus());

        // Vaccine code
        if (fhirImmunization.getVaccineCode() != null) {
            if (fhirImmunization.getVaccineCode().getCoding() != null
                    && !fhirImmunization.getVaccineCode().getCoding().isEmpty()) {
                // Use explicit cast
                CodeableConcept.Coding coding = (CodeableConcept.Coding) fhirImmunization.getVaccineCode().getCoding().get(0);
                entity.setVaccineCode(coding.getCode());
                entity.setVaccineName(coding.getDisplay());
            }
        }

        // Patient
        if (fhirImmunization.getPatient() != null) {
            String patientRef = fhirImmunization.getPatient().getReference();
            entity.setPatientId(patientRef.replace("Patient/", ""));
        }

        // Administered date
        entity.setAdministeredDate(
                DateUtils.parseFromApi(fhirImmunization.getOccurrenceDateTime()));

        // Lot number
        entity.setLotNumber(fhirImmunization.getLotNumber());

        // Expiration date
        if (fhirImmunization.getExpirationDate() != null) {
            entity.setExpirationDate(
                    DateUtils.parseFromApi(fhirImmunization.getExpirationDate()));
        }

        // Route
        if (fhirImmunization.getRoute() != null) {
            entity.setRoute(fhirImmunization.getRoute().getText());
        }

        // Site
        if (fhirImmunization.getSite() != null) {
            entity.setSite(fhirImmunization.getSite().getText());
        }

        // Dose quantity
        if (fhirImmunization.getDoseQuantity() != null) {
            entity.setDoseQuantity(fhirImmunization.getDoseQuantity().getValue());
        }

        entity.setSynced(true);

        return entity;
    }
}