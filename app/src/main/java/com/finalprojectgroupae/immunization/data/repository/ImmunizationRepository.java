package com.finalprojectgroupae.immunization.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.finalprojectgroupae.immunization.data.local.AppDatabase;
import com.finalprojectgroupae.immunization.data.local.dao.ImmunizationDao;
import com.finalprojectgroupae.immunization.data.local.entities.ImmunizationEntity;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImmunizationRepository {

    private final ImmunizationDao immunizationDao;
    private final ExecutorService executorService;

    public ImmunizationRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        immunizationDao = database.immunizationDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    // Insert
    public void insert(ImmunizationEntity immunization) {
        AppDatabase.databaseWriteExecutor.execute(() -> immunizationDao.insert(immunization));
    }

    // Update
    public void update(ImmunizationEntity immunization) {
        AppDatabase.databaseWriteExecutor.execute(() -> immunizationDao.update(immunization));
    }

    // Get immunization by ID - FIXED: Returns LiveData<ImmunizationEntity>
    public LiveData<ImmunizationEntity> getImmunizationById(String immunizationId) {
        return immunizationDao.getImmunizationByIdLive(immunizationId);
    }

    // Get immunizations for patient - FIXED: Returns LiveData<List<ImmunizationEntity>>
    public LiveData<List<ImmunizationEntity>> getImmunizationsByPatient(String patientId) {
        return immunizationDao.getImmunizationsByPatient(patientId);
    }

    // Get unsynced immunizations - Uses callback pattern (correct)
    public void getUnsyncedImmunizations(OnImmunizationsLoadedCallback callback) {
        executorService.execute(() -> {
            List<ImmunizationEntity> immunizations = immunizationDao.getUnsyncedImmunizations();
            callback.onImmunizationsLoaded(immunizations);
        });
    }

    // Mark as synced
    public void markAsSynced(String immunizationId, String fhirId) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                immunizationDao.markAsSynced(immunizationId, fhirId, new Date()));
    }

    // Get immunizations in date range - Uses callback pattern (correct)
    public void getImmunizationsInDateRange(Date startDate, Date endDate,
                                            OnImmunizationsLoadedCallback callback) {
        executorService.execute(() -> {
            List<ImmunizationEntity> immunizations =
                    immunizationDao.getImmunizationsInDateRange(startDate, endDate);
            callback.onImmunizationsLoaded(immunizations);
        });
    }

    // Callback interface
    public interface OnImmunizationsLoadedCallback {
        void onImmunizationsLoaded(List<ImmunizationEntity> immunizations);
    }
}