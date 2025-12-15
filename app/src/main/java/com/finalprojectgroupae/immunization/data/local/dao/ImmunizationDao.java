package com.finalprojectgroupae.immunization.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.finalprojectgroupae.immunization.data.local.entities.ImmunizationEntity;

import java.util.Date;
import java.util.List;

@Dao
public interface ImmunizationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ImmunizationEntity immunization);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ImmunizationEntity> immunizations);

    @Update
    void update(ImmunizationEntity immunization);

    @Query("SELECT * FROM immunizations WHERE immunization_id = :immunizationId LIMIT 1")
    ImmunizationEntity getImmunizationById(String immunizationId);

    @Query("SELECT * FROM immunizations WHERE immunization_id = :immunizationId LIMIT 1")
    LiveData<ImmunizationEntity> getImmunizationByIdLive(String immunizationId);

    @Query("SELECT * FROM immunizations WHERE patient_id = :patientId ORDER BY administered_date DESC")
    LiveData<List<ImmunizationEntity>> getImmunizationsByPatient(String patientId);

    @Query("SELECT * FROM immunizations WHERE patient_id = :patientId ORDER BY administered_date DESC")
    List<ImmunizationEntity> getImmunizationsByPatientSync(String patientId);

    @Query("SELECT * FROM immunizations WHERE scheduled_dose_id = :scheduledDoseId LIMIT 1")
    ImmunizationEntity getImmunizationByScheduledDose(String scheduledDoseId);

    @Query("SELECT * FROM immunizations WHERE administered_by = :chwId ORDER BY administered_date DESC")
    List<ImmunizationEntity> getImmunizationsByAdministrator(String chwId);

    @Query("SELECT * FROM immunizations WHERE administered_date BETWEEN :startDate AND :endDate ORDER BY administered_date DESC")
    List<ImmunizationEntity> getImmunizationsInDateRange(Date startDate, Date endDate);

    @Query("SELECT * FROM immunizations WHERE vaccine_code = :vaccineCode AND administered_date BETWEEN :startDate AND :endDate")
    List<ImmunizationEntity> getImmunizationsByVaccineInRange(String vaccineCode, Date startDate, Date endDate);

    @Query("SELECT * FROM immunizations WHERE is_synced = 0")
    List<ImmunizationEntity> getUnsyncedImmunizations();

    @Query("UPDATE immunizations SET is_synced = 1, fhir_id = :fhirId, synced_at = :syncedAt WHERE immunization_id = :immunizationId")
    void markAsSynced(String immunizationId, String fhirId, Date syncedAt);

    @Query("SELECT COUNT(*) FROM immunizations WHERE patient_id = :patientId")
    int getImmunizationCountForPatient(String patientId);

    @Query("SELECT COUNT(*) FROM immunizations WHERE administered_date BETWEEN :startDate AND :endDate")
    LiveData<Integer> getImmunizationCountInRange(Date startDate, Date endDate);
}