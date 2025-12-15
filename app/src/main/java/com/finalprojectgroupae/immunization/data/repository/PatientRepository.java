package com.finalprojectgroupae.immunization.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.finalprojectgroupae.immunization.data.local.AppDatabase;
import com.finalprojectgroupae.immunization.data.local.dao.PatientDao;
import com.finalprojectgroupae.immunization.data.local.entities.PatientEntity;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PatientRepository {

    private final PatientDao patientDao;
    private final LiveData<List<PatientEntity>> allPatients;
    private final ExecutorService executorService;

    public PatientRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        patientDao = database.patientDao();
        allPatients = patientDao.getAllPatientsLive();
        executorService = Executors.newSingleThreadExecutor();
    }

    // Insert
    public void insert(PatientEntity patient) {
        AppDatabase.databaseWriteExecutor.execute(() -> patientDao.insert(patient));
    }

    // Update
    public void update(PatientEntity patient) {
        patient.setUpdatedAt(new Date());
        AppDatabase.databaseWriteExecutor.execute(() -> patientDao.update(patient));
    }

    // Delete
    public void delete(PatientEntity patient) {
        AppDatabase.databaseWriteExecutor.execute(() -> patientDao.delete(patient));
    }

    // Get all patients
    public LiveData<List<PatientEntity>> getAllPatients() {
        return allPatients;
    }

    // Get patient by ID
    public LiveData<PatientEntity> getPatientById(String patientId) {
        return patientDao.getPatientByIdLive(patientId);
    }

    // Search patients
    public LiveData<List<PatientEntity>> searchPatients(String query) {
        return patientDao.searchPatients(query);
    }

    // Get patients by guardian
    public LiveData<List<PatientEntity>> getPatientsByGuardian(String guardianId) {
        return patientDao.getPatientsByGuardian(guardianId);
    }

    // Get unsynced patients
    public void getUnsyncedPatients(OnPatientsLoadedCallback callback) {
        executorService.execute(() -> {
            List<PatientEntity> patients = patientDao.getUnsyncedPatients();
            callback.onPatientsLoaded(patients);
        });
    }

    // Mark as synced
    public void markAsSynced(String patientId, String fhirId) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                patientDao.markAsSynced(patientId, fhirId));
    }

    // Get patient count
    public LiveData<Integer> getActivePatientCount() {
        return patientDao.getActivePatientCount();
    }

    // Callback interface
    public interface OnPatientsLoadedCallback {
        void onPatientsLoaded(List<PatientEntity> patients);
    }
}