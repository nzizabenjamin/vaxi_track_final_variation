package com.finalprojectgroupae.immunization.services.sync;

import android.app.Application;
import android.util.Log;

import com.finalprojectgroupae.immunization.data.local.entities.ImmunizationEntity;
import com.finalprojectgroupae.immunization.data.local.entities.PatientEntity;
import com.finalprojectgroupae.immunization.data.remote.FhirApiService;
import com.finalprojectgroupae.immunization.data.remote.RetrofitClient;
import com.finalprojectgroupae.immunization.data.remote.dto.FhirImmunization;
import com.finalprojectgroupae.immunization.data.remote.dto.FhirPatient;
import com.finalprojectgroupae.immunization.data.repository.ImmunizationRepository;
import com.finalprojectgroupae.immunization.data.repository.PatientRepository;
import com.finalprojectgroupae.immunization.utils.FhirMapper;
import com.finalprojectgroupae.immunization.utils.NetworkUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SyncService {

    private static final String TAG = "SyncService";

    private final Application application;
    private final PatientRepository patientRepository;
    private final ImmunizationRepository immunizationRepository;
    private final FhirApiService fhirApiService;

    private SyncListener syncListener;

    public SyncService(Application application) {
        this.application = application;
        this.patientRepository = new PatientRepository(application);
        this.immunizationRepository = new ImmunizationRepository(application);
        this.fhirApiService = RetrofitClient.getInstance().create(FhirApiService.class);
    }

    public void setSyncListener(SyncListener listener) {
        this.syncListener = listener;
    }

    /**
     * Sync all unsynced data to FHIR server
     */
    public void syncAll() {
        if (!NetworkUtils.isNetworkAvailable(application)) {
            Log.w(TAG, "No network connection. Sync aborted.");
            if (syncListener != null) {
                syncListener.onSyncFailed("No network connection");
            }
            return;
        }

        Log.i(TAG, "Starting sync...");
        if (syncListener != null) {
            syncListener.onSyncStarted();
        }

        // Sync patients first, then immunizations
        syncPatients();
    }

    /**
     * Sync unsynced patients
     */
    private void syncPatients() {
        patientRepository.getUnsyncedPatients(patients -> {
            if (patients.isEmpty()) {
                Log.i(TAG, "No patients to sync");
                syncImmunizations();
                return;
            }

            Log.i(TAG, "Syncing " + patients.size() + " patients");
            syncPatientList(patients, 0);
        });
    }

    /**
     * Recursively sync patient list
     */
    private void syncPatientList(List<PatientEntity> patients, int index) {
        if (index >= patients.size()) {
            Log.i(TAG, "All patients synced");
            syncImmunizations();
            return;
        }

        PatientEntity patient = patients.get(index);
        FhirPatient fhirPatient = FhirMapper.toFhirPatient(patient);

        Call<FhirPatient> call = fhirApiService.createPatient(fhirPatient);
        call.enqueue(new Callback<FhirPatient>() {
            @Override
            public void onResponse(Call<FhirPatient> call, Response<FhirPatient> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String fhirId = response.body().getId();
                    patientRepository.markAsSynced(patient.getPatientId(), fhirId);
                    Log.i(TAG, "Patient synced: " + patient.getPatientId() + " -> " + fhirId);
                } else {
                    Log.e(TAG, "Failed to sync patient: " + patient.getPatientId());
                }

                // Continue with next patient
                syncPatientList(patients, index + 1);
            }

            @Override
            public void onFailure(Call<FhirPatient> call, Throwable t) {
                Log.e(TAG, "Error syncing patient: " + t.getMessage());
                // Continue with next patient even on failure
                syncPatientList(patients, index + 1);
            }
        });
    }

    /**
     * Sync unsynced immunizations
     */
    private void syncImmunizations() {
        immunizationRepository.getUnsyncedImmunizations(immunizations -> {
            if (immunizations.isEmpty()) {
                Log.i(TAG, "No immunizations to sync");
                completeSyncSuccessfully();
                return;
            }

            Log.i(TAG, "Syncing " + immunizations.size() + " immunizations");
            syncImmunizationList(immunizations, 0);
        });
    }

    /**
     * Recursively sync immunization list
     */
    private void syncImmunizationList(List<ImmunizationEntity> immunizations, int index) {
        if (index >= immunizations.size()) {
            Log.i(TAG, "All immunizations synced");
            completeSyncSuccessfully();
            return;
        }

        ImmunizationEntity immunization = immunizations.get(index);
        FhirImmunization fhirImmunization = FhirMapper.toFhirImmunization(immunization);

        Call<FhirImmunization> call = fhirApiService.createImmunization(fhirImmunization);
        call.enqueue(new Callback<FhirImmunization>() {
            @Override
            public void onResponse(Call<FhirImmunization> call, Response<FhirImmunization> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String fhirId = response.body().getId();
                    immunizationRepository.markAsSynced(immunization.getImmunizationId(), fhirId);
                    Log.i(TAG, "Immunization synced: " + immunization.getImmunizationId() + " -> " + fhirId);
                } else {
                    Log.e(TAG, "Failed to sync immunization: " + immunization.getImmunizationId());
                }

                // Continue with next immunization
                syncImmunizationList(immunizations, index + 1);
            }

            @Override
            public void onFailure(Call<FhirImmunization> call, Throwable t) {
                Log.e(TAG, "Error syncing immunization: " + t.getMessage());
                // Continue with next immunization even on failure
                syncImmunizationList(immunizations, index + 1);
            }
        });
    }

    /**
     * Complete sync successfully
     */
    private void completeSyncSuccessfully() {
        Log.i(TAG, "Sync completed successfully");
        if (syncListener != null) {
            syncListener.onSyncCompleted();
        }
    }

    /**
     * Sync listener interface
     */
    public interface SyncListener {
        void onSyncStarted();
        void onSyncCompleted();
        void onSyncFailed(String errorMessage);
    }
}