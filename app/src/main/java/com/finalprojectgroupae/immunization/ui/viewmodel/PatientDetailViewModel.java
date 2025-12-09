package com.finalprojectgroupae.immunization.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.finalprojectgroupae.immunization.data.local.entities.CarePlanEntity;
import com.finalprojectgroupae.immunization.data.local.entities.ImmunizationEntity;
import com.finalprojectgroupae.immunization.data.local.entities.PatientEntity;
import com.finalprojectgroupae.immunization.data.local.entities.ScheduledDoseEntity;
import com.finalprojectgroupae.immunization.data.repository.CarePlanRepository;
import com.finalprojectgroupae.immunization.data.repository.ImmunizationRepository;
import com.finalprojectgroupae.immunization.data.repository.PatientRepository;
import com.finalprojectgroupae.immunization.data.repository.ScheduledDoseRepository;

import java.util.List;

public class PatientDetailViewModel extends AndroidViewModel {

    private final PatientRepository patientRepository;
    private final CarePlanRepository carePlanRepository;
    private final ScheduledDoseRepository scheduledDoseRepository;
    private final ImmunizationRepository immunizationRepository;

    private final MutableLiveData<String> patientIdLiveData = new MutableLiveData<>();

    private final LiveData<PatientEntity> patient;
    private final LiveData<CarePlanEntity> carePlan;
    private final LiveData<List<ScheduledDoseEntity>> scheduledDoses;
    private final LiveData<List<ImmunizationEntity>> immunizations;

    public PatientDetailViewModel(@NonNull Application application) {
        super(application);
        patientRepository = new PatientRepository(application);
        carePlanRepository = new CarePlanRepository(application);
        scheduledDoseRepository = new ScheduledDoseRepository(application);
        immunizationRepository = new ImmunizationRepository(application);

        // FIXED: Use Transformations.switchMap with proper generic types
        patient = Transformations.switchMap(patientIdLiveData,
                patientId -> {
                    if (patientId != null) {
                        return patientRepository.getPatientById(patientId);
                    }
                    return new MutableLiveData<>(null);
                });

        carePlan = Transformations.switchMap(patientIdLiveData,
                patientId -> {
                    if (patientId != null) {
                        return carePlanRepository.getCarePlanByPatient(patientId);
                    }
                    return new MutableLiveData<>(null);
                });

        scheduledDoses = Transformations.switchMap(patientIdLiveData,
                patientId -> {
                    if (patientId != null) {
                        return scheduledDoseRepository.getScheduledDosesByPatient(patientId);
                    }
                    return new MutableLiveData<>(null);
                });

        immunizations = Transformations.switchMap(patientIdLiveData,
                patientId -> {
                    if (patientId != null) {
                        return immunizationRepository.getImmunizationsByPatient(patientId);
                    }
                    return new MutableLiveData<>(null);
                });
    }

    // Set patient ID to load data
    public void setPatientId(String patientId) {
        patientIdLiveData.setValue(patientId);
    }

    // Getters
    public LiveData<PatientEntity> getPatient() {
        return patient;
    }

    public LiveData<CarePlanEntity> getCarePlan() {
        return carePlan;
    }

    public LiveData<List<ScheduledDoseEntity>> getScheduledDoses() {
        return scheduledDoses;
    }

    public LiveData<List<ImmunizationEntity>> getImmunizations() {
        return immunizations;
    }
}
