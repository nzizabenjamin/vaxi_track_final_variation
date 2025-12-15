package com.finalprojectgroupae.immunization.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.finalprojectgroupae.immunization.R;
import com.finalprojectgroupae.immunization.data.local.entities.PatientEntity;
import com.finalprojectgroupae.immunization.data.local.entities.ScheduledDoseEntity;
import com.finalprojectgroupae.immunization.data.local.entities.VaccineDoseEntity;
import com.finalprojectgroupae.immunization.data.model.Appointment;
import com.finalprojectgroupae.immunization.data.repository.PatientRepository;
import com.finalprojectgroupae.immunization.data.repository.ScheduledDoseRepository;
import com.finalprojectgroupae.immunization.data.repository.VaccineDoseRepository;
import com.finalprojectgroupae.immunization.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ScheduleViewModel extends AndroidViewModel {

    private final ScheduledDoseRepository scheduledDoseRepository;
    private final PatientRepository patientRepository;
    private final VaccineDoseRepository vaccineDoseRepository;
    private final MutableLiveData<List<Appointment>> upcomingAppointments = new MutableLiveData<>();

    public ScheduleViewModel(@NonNull Application application) {
        super(application);
        scheduledDoseRepository = new ScheduledDoseRepository(application);
        patientRepository = new PatientRepository(application);
        vaccineDoseRepository = new VaccineDoseRepository(application);
        
        loadUpcomingAppointments();
    }

    private void loadUpcomingAppointments() {
        // Get doses due in the next 30 days
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_YEAR, 30);
        Date endDate = calendar.getTime();

        scheduledDoseRepository.getDosesDueInRange(today, endDate, scheduledDoses -> {
            if (scheduledDoses == null || scheduledDoses.isEmpty()) {
                upcomingAppointments.postValue(new ArrayList<>());
                return;
            }

            List<Appointment> appointments = new ArrayList<>();
            
            // Get all patients for lookup
            patientRepository.getAllPatients().observeForever(patients -> {
                if (patients == null) return;
                
                for (ScheduledDoseEntity scheduledDose : scheduledDoses) {
                    // Find patient
                    PatientEntity patient = null;
                    for (PatientEntity p : patients) {
                        if (p.getPatientId().equals(scheduledDose.getPatientId())) {
                            patient = p;
                            break;
                        }
                    }
                    
                    if (patient == null) continue;
                    
                    // Create final copies for use in lambda
                    final PatientEntity finalPatient = patient;
                    final ScheduledDoseEntity finalScheduledDose = scheduledDose;
                    
                    // Get vaccine dose info
                    vaccineDoseRepository.getDoseById(finalScheduledDose.getDoseId(), vaccineDose -> {
                        if (vaccineDose == null) return;
                        
                        String patientName = finalPatient.getFirstName() + " " + finalPatient.getLastName();
                        String vaccineName = vaccineDose.getVaccineDefId() + " - Dose " + vaccineDose.getDoseNumber();
                        String location = finalPatient.getVillage() + ", " + finalPatient.getDistrict();
                        String date = DateUtils.formatForDisplay(finalScheduledDose.getScheduledDate());
                        String time = DateUtils.formatTime(finalScheduledDose.getScheduledDate());
                        
                        // Determine mode based on status
                        String mode = "Facility";
                        int modeColorRes = R.color.colorPrimary;
                        if ("overdue".equals(finalScheduledDose.getStatus())) {
                            mode = "Overdue";
                            modeColorRes = android.R.color.holo_red_dark;
                        } else if ("due".equals(finalScheduledDose.getStatus())) {
                            mode = "Due";
                            modeColorRes = android.R.color.holo_orange_dark;
                        }
                        
                        Appointment appointment = new Appointment(
                                patientName + " - " + vaccineName,
                                location,
                                date,
                                time,
                                mode,
                                modeColorRes
                        );
                        
                        appointments.add(appointment);
                        
                        // Post when all appointments are created
                        if (appointments.size() == scheduledDoses.size()) {
                            upcomingAppointments.postValue(appointments);
                        }
                    });
                }
            });
        });
    }

    public LiveData<List<Appointment>> getUpcomingAppointments() {
        return upcomingAppointments;
    }
}

