package com.finalprojectgroupae.immunization.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.finalprojectgroupae.immunization.data.local.entities.ScheduledDoseEntity;

import java.util.Date;
import java.util.List;

@Dao
public interface ScheduledDoseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ScheduledDoseEntity scheduledDose);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List scheduledDoses);

    @Update
    void update(ScheduledDoseEntity scheduledDose);

    @Query("SELECT * FROM scheduled_doses WHERE scheduled_dose_id = :scheduledDoseId LIMIT 1")
    ScheduledDoseEntity getScheduledDoseById(String scheduledDoseId);

    @Query("SELECT * FROM scheduled_doses WHERE scheduled_dose_id = :scheduledDoseId LIMIT 1")
    LiveData getScheduledDoseByIdLive(String scheduledDoseId);

    @Query("SELECT * FROM scheduled_doses WHERE patient_id = :patientId ORDER BY scheduled_date ASC")
    LiveData<List> getScheduledDosesByPatient(String patientId);

    @Query("SELECT * FROM scheduled_doses WHERE patient_id = :patientId ORDER BY scheduled_date ASC")
    List getScheduledDosesByPatientSync(String patientId);

    @Query("SELECT * FROM scheduled_doses WHERE care_plan_id = :carePlanId ORDER BY scheduled_date ASC")
    List getScheduledDosesByCarePlan(String carePlanId);

    @Query("SELECT * FROM scheduled_doses WHERE status = :status ORDER BY scheduled_date ASC")
    LiveData<List> getDosesByStatus(String status);

    @Query("SELECT * FROM scheduled_doses WHERE status = 'due' OR status = 'overdue' ORDER BY scheduled_date ASC")
    LiveData<List> getDueDoses();

    @Query("SELECT * FROM scheduled_doses WHERE status = 'overdue' ORDER BY scheduled_date ASC")
    List getOverdueDoses();

    @Query("SELECT * FROM scheduled_doses WHERE scheduled_date = :date AND status != 'completed'")
    List getDosesDueOnDate(Date date);

    @Query("SELECT * FROM scheduled_doses WHERE scheduled_date BETWEEN :startDate AND :endDate AND status != 'completed' ORDER BY scheduled_date ASC")
    List getDosesDueInRange(Date startDate, Date endDate);

    @Query("SELECT * FROM scheduled_doses WHERE is_synced = 0")
    List getUnsyncedScheduledDoses();

    @Query("UPDATE scheduled_doses SET status = :status, updated_at = :updatedAt WHERE scheduled_dose_id = :scheduledDoseId")
    void updateStatus(String scheduledDoseId, String status, Date updatedAt);

    @Query("UPDATE scheduled_doses SET status = 'completed', completed_date = :completedDate, immunization_id = :immunizationId WHERE scheduled_dose_id = :scheduledDoseId")
    void markAsCompleted(String scheduledDoseId, Date completedDate, String immunizationId);

    @Query("SELECT COUNT(*) FROM scheduled_doses WHERE patient_id = :patientId AND status = 'completed'")
    int getCompletedDoseCount(String patientId);

    @Query("SELECT COUNT(*) FROM scheduled_doses WHERE patient_id = :patientId")
    int getTotalDoseCount(String patientId);
}