package com.finalprojectgroupae.immunization.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.finalprojectgroupae.immunization.data.local.entities.PatientEntity;

import java.util.List;

@Dao
public interface PatientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PatientEntity patient);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List patients);

    @Update
    void update(PatientEntity patient);

    @Delete
    void delete(PatientEntity patient);

    @Query("SELECT * FROM patients WHERE patient_id = :patientId LIMIT 1")
    PatientEntity getPatientById(String patientId);

    @Query("SELECT * FROM patients WHERE patient_id = :patientId LIMIT 1")
    LiveData getPatientByIdLive(String patientId);

    @Query("SELECT * FROM patients WHERE is_active = 1 ORDER BY first_name ASC")
    LiveData<List> getAllPatientsLive();

    @Query("SELECT * FROM patients WHERE is_active = 1 ORDER BY first_name ASC")
    List getAllPatients();

    @Query("SELECT * FROM patients WHERE guardian_id = :guardianId AND is_active = 1")
    LiveData<List> getPatientsByGuardian(String guardianId);

    @Query("SELECT * FROM patients WHERE " +
            "(first_name LIKE '%' || :searchQuery || '%' OR " +
            "last_name LIKE '%' || :searchQuery || '%' OR " +
            "guardian_name LIKE '%' || :searchQuery || '%') " +
            "AND is_active = 1 " +
            "ORDER BY first_name ASC")
    LiveData<List> searchPatients(String searchQuery);

    @Query("SELECT * FROM patients WHERE village = :village AND is_active = 1")
    List getPatientsByVillage(String village);

    @Query("SELECT * FROM patients WHERE district = :district AND is_active = 1")
    List getPatientsByDistrict(String district);

    @Query("SELECT * FROM patients WHERE is_synced = 0")
    List getUnsyncedPatients();

    @Query("UPDATE patients SET is_synced = 1, fhir_id = :fhirId WHERE patient_id = :patientId")
    void markAsSynced(String patientId, String fhirId);

    @Query("SELECT COUNT(*) FROM patients WHERE is_active = 1")
    LiveData getActivePatientCount();

    @Query("DELETE FROM patients WHERE patient_id = :patientId")
    void deleteById(String patientId);
}