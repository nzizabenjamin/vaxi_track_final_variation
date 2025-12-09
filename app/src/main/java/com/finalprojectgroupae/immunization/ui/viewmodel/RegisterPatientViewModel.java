package com.finalprojectgroupae.immunization.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.finalprojectgroupae.immunization.data.local.entities.PatientEntity;
import com.finalprojectgroupae.immunization.services.ImmunizationService;

public class RegisterPatientViewModel extends AndroidViewModel {

    private final ImmunizationService immunizationService;

    private final MutableLiveData isLoading = new MutableLiveData<>(false);
    private final MutableLiveData successMessage = new MutableLiveData<>();
    private final MutableLiveData errorMessage = new MutableLiveData<>();

    public RegisterPatientViewModel(@NonNull Application application) {
        super(application);
        immunizationService = new ImmunizationService(application);
    }

    /**
     * Register a new patient
     */
    public void registerPatient(PatientEntity patient) {
        isLoading.setValue(true);

        immunizationService.registerPatient(patient, (patientId, carePlanId) -> {
            isLoading.postValue(false);
            successMessage.postValue("Patient registered successfully! ID: " + patientId);
        });
    }

    // Getters
    public MutableLiveData getIsLoading() {
        return isLoading;
    }

    public MutableLiveData getSuccessMessage() {
        return successMessage;
    }

    public MutableLiveData getErrorMessage() {
        return errorMessage;
    }
}