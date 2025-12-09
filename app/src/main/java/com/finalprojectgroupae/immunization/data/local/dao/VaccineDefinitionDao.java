package com.finalprojectgroupae.immunization.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.finalprojectgroupae.immunization.data.local.entities.VaccineDefinitionEntity;

import java.util.List;

@Dao
public interface VaccineDefinitionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(VaccineDefinitionEntity vaccine);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List vaccines);

    @Update
    void update(VaccineDefinitionEntity vaccine);

    @Query("SELECT * FROM vaccine_definitions WHERE is_active = 1 ORDER BY display_order ASC")
    LiveData<List> getAllVaccinesLive();

    @Query("SELECT * FROM vaccine_definitions WHERE is_active = 1 ORDER BY display_order ASC")
    List getAllVaccines();

    @Query("SELECT * FROM vaccine_definitions WHERE vaccine_def_id = :vaccineDefId LIMIT 1")
    VaccineDefinitionEntity getVaccineById(String vaccineDefId);

    @Query("SELECT * FROM vaccine_definitions WHERE vaccine_code = :vaccineCode LIMIT 1")
    VaccineDefinitionEntity getVaccineByCode(String vaccineCode);

    @Query("SELECT * FROM vaccine_definitions WHERE vaccine_code = :vaccineCode LIMIT 1")
    LiveData getVaccineByCodeLive(String vaccineCode);
}