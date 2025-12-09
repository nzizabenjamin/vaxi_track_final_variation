package com.finalprojectgroupae.immunization.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.finalprojectgroupae.immunization.data.local.entities.AppointmentEntity;

import java.util.Date;
import java.util.List;

@Dao
public interface AppointmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AppointmentEntity appointment);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List appointments);

    @Update
    void update(AppointmentEntity appointment);

    @Query("SELECT * FROM appointments WHERE appointment_id = :appointmentId LIMIT 1")
    AppointmentEntity getAppointmentById(String appointmentId);

    @Query("SELECT * FROM appointments WHERE appointment_id = :appointmentId LIMIT 1")
    LiveData getAppointmentByIdLive(String appointmentId);

    @Query("SELECT * FROM appointments WHERE patient_id = :patientId ORDER BY appointment_date DESC")
    LiveData<List> getAppointmentsByPatient(String patientId);

    @Query("SELECT * FROM appointments WHERE appointment_date = :date AND status = 'scheduled' ORDER BY appointment_date ASC")
    List getAppointmentsByDate(Date date);

    @Query("SELECT * FROM appointments WHERE appointment_date BETWEEN :startDate AND :endDate AND status != 'cancelled' ORDER BY appointment_date ASC")
    LiveData<List> getAppointmentsInRange(Date startDate, Date endDate);

    @Query("SELECT * FROM appointments WHERE status = :status ORDER BY appointment_date ASC")
    LiveData<List> getAppointmentsByStatus(String status);

    @Query("SELECT * FROM appointments WHERE is_synced = 0")
    List getUnsyncedAppointments();

    @Query("UPDATE appointments SET status = :status WHERE appointment_id = :appointmentId")
    void updateStatus(String appointmentId, String status);
}