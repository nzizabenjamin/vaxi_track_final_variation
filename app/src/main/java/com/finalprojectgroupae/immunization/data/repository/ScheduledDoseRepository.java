package com.finalprojectgroupae.immunization.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.finalprojectgroupae.immunization.data.local.AppDatabase;
import com.finalprojectgroupae.immunization.data.local.dao.ScheduledDoseDao;
import com.finalprojectgroupae.immunization.data.local.entities.ScheduledDoseEntity;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScheduledDoseRepository {

    private final ScheduledDoseDao scheduledDoseDao;
    private final ExecutorService executorService;

    public ScheduledDoseRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        scheduledDoseDao = database.scheduledDoseDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    // Insert
    public void insert(ScheduledDoseEntity scheduledDose) {
        AppDatabase.databaseWriteExecutor.execute(() -> scheduledDoseDao.insert(scheduledDose));
    }

    // Insert multiple
    public void insertAll(List<ScheduledDoseEntity> scheduledDoses) {
        AppDatabase.databaseWriteExecutor.execute(() -> scheduledDoseDao.insertAll(scheduledDoses));
    }

    // Update
    public void update(ScheduledDoseEntity scheduledDose) {
        AppDatabase.databaseWriteExecutor.execute(() -> scheduledDoseDao.update(scheduledDose));
    }

    // Get by ID
    public LiveData<ScheduledDoseEntity> getScheduledDoseById(String scheduledDoseId) {
        return scheduledDoseDao.getScheduledDoseByIdLive(scheduledDoseId);
    }

    // Get all for patient
    public LiveData<List<ScheduledDoseEntity>> getScheduledDosesByPatient(String patientId) {
        return scheduledDoseDao.getScheduledDosesByPatient(patientId);
    }

    // Get due doses
    public LiveData<List<ScheduledDoseEntity>> getDueDoses() {
        return scheduledDoseDao.getDueDoses();
    }

    // Get overdue doses
    public void getOverdueDoses(OnScheduledDosesLoadedCallback callback) {
        executorService.execute(() -> {
            List<ScheduledDoseEntity> doses = scheduledDoseDao.getOverdueDoses();
            callback.onScheduledDosesLoaded(doses);
        });
    }

    // Mark as completed
    public void markAsCompleted(String scheduledDoseId, Date completedDate, String immunizationId) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                scheduledDoseDao.markAsCompleted(scheduledDoseId, completedDate, immunizationId));
    }

    // Update status
    public void updateStatus(String scheduledDoseId, String status) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                scheduledDoseDao.updateStatus(scheduledDoseId, status, new Date()));
    }

    // Get doses due on specific date
    public void getDosesDueOnDate(Date date, OnScheduledDosesLoadedCallback callback) {
        executorService.execute(() -> {
            List<ScheduledDoseEntity> doses = scheduledDoseDao.getDosesDueOnDate(date);
            callback.onScheduledDosesLoaded(doses);
        });
    }

    // Get doses due in date range
    public void getDosesDueInRange(Date startDate, Date endDate,
                                   OnScheduledDosesLoadedCallback callback) {
        executorService.execute(() -> {
            List<ScheduledDoseEntity> doses =
                    scheduledDoseDao.getDosesDueInRange(startDate, endDate);
            callback.onScheduledDosesLoaded(doses);
        });
    }

    // Get unsynced
    public void getUnsyncedScheduledDoses(OnScheduledDosesLoadedCallback callback) {
        executorService.execute(() -> {
            List<ScheduledDoseEntity> doses = scheduledDoseDao.getUnsyncedScheduledDoses();
            callback.onScheduledDosesLoaded(doses);
        });
    }

    // Get completion stats
    public void getCompletionStats(String patientId, OnCompletionStatsCallback callback) {
        executorService.execute(() -> {
            int completed = scheduledDoseDao.getCompletedDoseCount(patientId);
            int total = scheduledDoseDao.getTotalDoseCount(patientId);
            callback.onStatsLoaded(completed, total);
        });
    }

    // Callback interfaces
    public interface OnScheduledDosesLoadedCallback {
        void onScheduledDosesLoaded(List<ScheduledDoseEntity> doses);
    }

    public interface OnCompletionStatsCallback {
        void onStatsLoaded(int completed, int total);
    }
}