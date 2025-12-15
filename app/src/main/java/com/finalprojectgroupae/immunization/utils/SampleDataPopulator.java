package com.finalprojectgroupae.immunization.utils;

import android.app.Application;
import android.util.Log;

import com.finalprojectgroupae.immunization.data.local.entities.PatientEntity;
import com.finalprojectgroupae.immunization.data.repository.PatientRepository;
import com.finalprojectgroupae.immunization.services.ImmunizationService;

import java.util.Calendar;
import java.util.Date;

/**
 * Populates the database with sample patient data for testing
 */
public class SampleDataPopulator {

    private static final String TAG = "SampleDataPopulator";

    private final Application application;
    private final ImmunizationService immunizationService;
    private final PatientRepository patientRepository;

    public SampleDataPopulator(Application application) {
        this.application = application;
        this.immunizationService = new ImmunizationService(application);
        this.patientRepository = new PatientRepository(application);
    }

    /**
     * Populate sample patients - call this from MainActivity or a debug menu
     */
    public void populateSampleData() {
        // Check if data already exists
        patientRepository.getAllPatients().observeForever(patients -> {
            if (patients != null && !patients.isEmpty()) {
                Log.i(TAG, "Sample data already exists. Skipping population.");
                return;
            }

            Log.i(TAG, "Populating sample data...");

            // Create sample patients
            createPatient("Aisha", "Uwera", getBirthDate(0, 3, 15), "Marie Uwera", "+250788123456", "Kigali", "Gasabo");
            createPatient("Jean", "Mugabo", getBirthDate(0, 6, 10), "Claire Mugabo", "+250788234567", "Kigali", "Kicukiro");
            createPatient("Grace", "Uwase", getBirthDate(1, 2, 5), "Paul Uwase", "+250788345678", "Kigali", "Nyarugenge");
            createPatient("David", "Nkusi", getBirthDate(0, 1, 20), "Sarah Nkusi", "+250788456789", "Kigali", "Gasabo");
            createPatient("Hope", "Mutesi", getBirthDate(2, 0, 10), "John Mutesi", "+250788567890", "Kigali", "Kicukiro");

            Log.i(TAG, "Sample data population completed!");
        });
    }

    private void createPatient(String firstName, String lastName, Date birthDate,
                               String guardianName, String guardianPhone,
                               String village, String district) {

        PatientEntity patient = new PatientEntity();
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setDateOfBirth(birthDate);
        patient.setGender(Math.random() > 0.5 ? "male" : "female");
        patient.setGuardianName(guardianName);
        patient.setGuardianPhone(guardianPhone);
        patient.setVillage(village);
        patient.setDistrict(district);
        patient.setActive(true);

        // Register patient (this will create care plan and schedule)
        immunizationService.registerPatient(patient, (patientId, carePlanId) -> {
            Log.i(TAG, "Created patient: " + firstName + " " + lastName + " (ID: " + patientId + ")");
        });
    }

    /**
     * Helper to create birth dates
     * @param years Years ago
     * @param months Months ago (additional to years)
     * @param days Days ago (additional to months)
     */
    private Date getBirthDate(int years, int months, int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -years);
        cal.add(Calendar.MONTH, -months);
        cal.add(Calendar.DAY_OF_MONTH, -days);
        return cal.getTime();
    }
}