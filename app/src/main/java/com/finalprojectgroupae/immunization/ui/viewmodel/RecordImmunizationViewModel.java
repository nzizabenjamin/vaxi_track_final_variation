package com.finalprojectgroupae.immunization.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.finalprojectgroupae.immunization.data.local.entities.ImmunizationEntity;
import com.finalprojectgroupae.immunization.services.ImmunizationService;

public class RecordImmunizationViewModel extends AndroidViewModel {

    private final ImmunizationService immunizationService;

    private final MutableLiveData isLoading = new MutableLiveData<>(false);
    private final MutableLiveData successMessage = new MutableLiveData<>();
    private final MutableLiveData errorMessage = new MutableLiveData<>();

    public RecordImmunizationViewModel(@NonNull Application application) {
        super(application);
        immunizationService = new ImmunizationService(application);
    }

    /**
     * Record an immunization
     */
    public void recordImmunization(ImmunizationEntity immunization) {
        isLoading.setValue(true);

        immunizationService.recordImmunization(immunization, immunizationId -> {
            isLoading.postValue(false);
            successMessage.postValue("Vaccination recorded successfully!");
        });
    }

    /**
     * Validate lot number
     */
    public boolean validateLotNumber(String lotNumber) {
        if (lotNumber == null || lotNumber.trim().isEmpty()) {
            errorMessage.setValue("Lot number is required");
            return false;
        }

        if (!lotNumber.matches("^[A-Z0-9]{6,12}$")) {
            errorMessage.setValue("Invalid lot number format");
            return false;
        }

        return true;
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