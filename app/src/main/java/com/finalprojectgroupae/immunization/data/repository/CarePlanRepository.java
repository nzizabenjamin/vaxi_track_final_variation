package com.finalprojectgroupae.immunization.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.finalprojectgroupae.immunization.data.local.AppDatabase;
import com.finalprojectgroupae.immunization.data.local.dao.CarePlanDao;
import com.finalprojectgroupae.immunization.data.local.entities.CarePlanEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CarePlanRepository {

    private final CarePlanDao carePlanDao;
    private final ExecutorService executorService;

    public CarePlanRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        carePlanDao = database.carePlanDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    // Insert
    public void insert(CarePlanEntity carePlan) {
        AppDatabase.databaseWriteExecutor.execute(() -> carePlanDao.insert(carePlan));
    }

    // Update
    public void update(CarePlanEntity carePlan) {
        AppDatabase.databaseWriteExecutor.execute(() -> carePlanDao.update(carePlan));
    }

    // Get by ID
    public LiveData<CarePlanEntity> getCarePlanById(String carePlanId) {
        return carePlanDao.getCarePlanByIdLive(carePlanId);
    }

    // Get by patient
    public LiveData<CarePlanEntity> getCarePlanByPatient(String patientId) {
        return carePlanDao.getCarePlanByPatientLive(patientId);
    }

    // Get active care plans
    public LiveData<List<CarePlanEntity>> getActiveCarePlans() {
        return carePlanDao.getActiveCarePlans();
    }

    // Update completed doses count
    public void updateCompletedDoses(String carePlanId, int completedDoses) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                carePlanDao.updateCompletedDoses(carePlanId, completedDoses));
    }

    // Get unsynced
    public void getUnsyncedCarePlans(OnCarePlansLoadedCallback callback) {
        executorService.execute(() -> {
            List<CarePlanEntity> carePlans = carePlanDao.getUnsyncedCarePlans();
            callback.onCarePlansLoaded(carePlans);
        });
    }

    // Callback interface
    public interface OnCarePlansLoadedCallback {
        void onCarePlansLoaded(List<CarePlanEntity> carePlans);
    }
}