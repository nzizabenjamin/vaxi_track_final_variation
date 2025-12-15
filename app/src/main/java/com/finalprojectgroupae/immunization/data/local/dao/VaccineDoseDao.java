package com.finalprojectgroupae.immunization.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.finalprojectgroupae.immunization.data.local.entities.VaccineDoseEntity;

import java.util.List;

@Dao
public interface VaccineDoseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(VaccineDoseEntity dose);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<VaccineDoseEntity> doses);

    @Update
    void update(VaccineDoseEntity dose);

    @Query("SELECT * FROM vaccine_doses ORDER BY display_order ASC")
    List<VaccineDoseEntity> getAllDoses();

    @Query("SELECT * FROM vaccine_doses WHERE vaccine_def_id = :vaccineDefId ORDER BY dose_number ASC")
    List<VaccineDoseEntity> getDosesByVaccine(String vaccineDefId);

    @Query("SELECT * FROM vaccine_doses WHERE vaccine_def_id = :vaccineDefId ORDER BY dose_number ASC")
    LiveData<List<VaccineDoseEntity>> getDosesByVaccineLive(String vaccineDefId);

    @Query("SELECT * FROM vaccine_doses WHERE dose_id = :doseId LIMIT 1")
    VaccineDoseEntity getDoseById(String doseId);

    @Query("SELECT * FROM vaccine_doses WHERE is_required = 1 ORDER BY display_order ASC")
    List<VaccineDoseEntity> getRequiredDoses();
}
