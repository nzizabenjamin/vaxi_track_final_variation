package com.finalprojectgroupae.immunization.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.finalprojectgroupae.immunization.data.local.entities.PatientEntity;
import com.finalprojectgroupae.immunization.data.local.entities.ScheduledDoseEntity;
import com.finalprojectgroupae.immunization.data.repository.PatientRepository;
import com.finalprojectgroupae.immunization.data.repository.ScheduledDoseRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private final PatientRepository patientRepository;
    private final ScheduledDoseRepository scheduledDoseRepository;

    private final LiveData<List<PatientEntity>> allPatients;
    private final LiveData<List<ScheduledDoseEntity>> dueDoses;
    private final LiveData<Integer> patientCount;

    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public HomeViewModel(@NonNull Application application) {
        super(application);
        patientRepository = new PatientRepository(application);
        scheduledDoseRepository = new ScheduledDoseRepository(application);

        allPatients = patientRepository.getAllPatients();
        dueDoses = scheduledDoseRepository.getDueDoses();
        patientCount = patientRepository.getActivePatientCount();
    }

    // Getters for LiveData
    public LiveData<List<PatientEntity>> getAllPatients() {
        return allPatients;
    }

    public LiveData<List<ScheduledDoseEntity>> getDueDoses() {
        return dueDoses;
    }

    public LiveData<Integer> getPatientCount() {
        return patientCount;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    // Search patients
    public LiveData<List<PatientEntity>> searchPatients(String query) {
        return patientRepository.searchPatients(query);
    }

    // Refresh data
    public void refresh() {
        // Trigger repository refresh if needed
    }
}