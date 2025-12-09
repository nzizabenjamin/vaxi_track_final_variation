package com.finalprojectgroupae.immunization.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.finalprojectgroupae.immunization.data.local.entities.CarePlanEntity;

import java.util.List;

@Dao
public interface CarePlanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CarePlanEntity carePlan);

    @Update
    void update(CarePlanEntity carePlan);

    @Query("SELECT * FROM care_plans WHERE care_plan_id = :carePlanId LIMIT 1")
    CarePlanEntity getCarePlanById(String carePlanId);

    @Query("SELECT * FROM care_plans WHERE care_plan_id = :carePlanId LIMIT 1")
    LiveData getCarePlanByIdLive(String carePlanId);

    @Query("SELECT * FROM care_plans WHERE patient_id = :patientId LIMIT 1")
    CarePlanEntity getCarePlanByPatient(String patientId);

    @Query("SELECT * FROM care_plans WHERE patient_id = :patientId LIMIT 1")
    LiveData getCarePlanByPatientLive(String patientId);

    @Query("SELECT * FROM care_plans WHERE status = 'active'")
    LiveData<List> getActiveCarePlans();

    @Query("SELECT * FROM care_plans WHERE is_synced = 0")
    List getUnsyncedCarePlans();

    @Query("UPDATE care_plans SET completed_doses = :completedDoses WHERE care_plan_id = :carePlanId")
    void updateCompletedDoses(String carePlanId, int completedDoses);
}