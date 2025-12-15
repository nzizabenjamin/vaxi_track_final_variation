package com.finalprojectgroupae.immunization.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.finalprojectgroupae.immunization.data.local.AppDatabase;
import com.finalprojectgroupae.immunization.data.local.dao.VaccineDoseDao;
import com.finalprojectgroupae.immunization.data.local.entities.VaccineDoseEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VaccineDoseRepository {

    private final VaccineDoseDao vaccineDoseDao;
    private final ExecutorService executorService;

    public VaccineDoseRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        vaccineDoseDao = database.vaccineDoseDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    // Get all doses
    public void getAllDoses(OnDosesLoadedCallback callback) {
        executorService.execute(() -> {
            List<VaccineDoseEntity> doses = vaccineDoseDao.getAllDoses();
            callback.onDosesLoaded(doses);
        });
    }

    // Get doses by vaccine
    public LiveData<List<VaccineDoseEntity>> getDosesByVaccine(String vaccineDefId) {
        return vaccineDoseDao.getDosesByVaccineLive(vaccineDefId);
    }

    // Get required doses
    public void getRequiredDoses(OnDosesLoadedCallback callback) {
        executorService.execute(() -> {
            List<VaccineDoseEntity> doses = vaccineDoseDao.getRequiredDoses();
            callback.onDosesLoaded(doses);
        });
    }

    // Get dose by ID
    public void getDoseById(String doseId, OnDoseLoadedCallback callback) {
        executorService.execute(() -> {
            VaccineDoseEntity dose = vaccineDoseDao.getDoseById(doseId);
            callback.onDoseLoaded(dose);
        });
    }

    // Callback interfaces
    public interface OnDosesLoadedCallback {
        void onDosesLoaded(List<VaccineDoseEntity> doses);
    }

    public interface OnDoseLoadedCallback {
        void onDoseLoaded(VaccineDoseEntity dose);
    }
}