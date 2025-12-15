package com.finalprojectgroupae.immunization.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.finalprojectgroupae.immunization.R;
import com.finalprojectgroupae.immunization.data.AuthManager;
import com.finalprojectgroupae.immunization.data.local.entities.CarePlanEntity;
import com.finalprojectgroupae.immunization.data.local.entities.PatientEntity;
import com.finalprojectgroupae.immunization.data.local.entities.ScheduledDoseEntity;
import com.finalprojectgroupae.immunization.data.local.entities.VaccineDoseEntity;
import com.finalprojectgroupae.immunization.data.repository.CarePlanRepository;
import com.finalprojectgroupae.immunization.data.repository.PatientRepository;
import com.finalprojectgroupae.immunization.data.repository.ScheduledDoseRepository;
import com.finalprojectgroupae.immunization.data.repository.VaccineDoseRepository;
import com.finalprojectgroupae.immunization.databinding.ActivityPatientRegistrationBinding;
import com.finalprojectgroupae.immunization.utils.DateUtils;
import com.finalprojectgroupae.immunization.utils.IdGenerator;
import com.finalprojectgroupae.immunization.utils.VaccineScheduleGenerator;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PatientRegistrationActivity extends AppCompatActivity {

    private ActivityPatientRegistrationBinding binding;
    private PatientRepository patientRepository;
    private CarePlanRepository carePlanRepository;
    private ScheduledDoseRepository scheduledDoseRepository;
    private VaccineDoseRepository vaccineDoseRepository;
    private AuthManager authManager;
    private Date selectedDateOfBirth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authManager = new AuthManager(this);
        patientRepository = new PatientRepository(getApplication());
        carePlanRepository = new CarePlanRepository(getApplication());
        scheduledDoseRepository = new ScheduledDoseRepository(getApplication());
        vaccineDoseRepository = new VaccineDoseRepository(getApplication());

        // Check if user is CHW or Admin
        String role = authManager.getActiveRole();
        if (!AuthManager.ROLE_ADMIN.equals(role) && !AuthManager.ROLE_USER.equals(role)) {
            Toast.makeText(this, "Only CHWs and Admins can register patients", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setupToolbar();
        setupDatePicker();
        setupListeners();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Register New Child");
        }
    }

    private void setupDatePicker() {
        binding.inputDateOfBirth.setOnClickListener(v -> showDatePicker());
        binding.inputDateOfBirth.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                showDatePicker();
            }
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        if (selectedDateOfBirth != null) {
            calendar.setTime(selectedDateOfBirth);
        } else {
            // Default to 1 year ago (typical for child registration)
            calendar.add(Calendar.YEAR, -1);
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    Calendar selectedCalendar = Calendar.getInstance();
                    selectedCalendar.set(year, month, dayOfMonth);
                    selectedDateOfBirth = selectedCalendar.getTime();
                    binding.inputDateOfBirth.setText(DateUtils.formatForDisplay(selectedDateOfBirth));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        // Set max date to today (can't register future births)
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        // Set min date to 10 years ago (reasonable limit for child registration)
        Calendar minDate = Calendar.getInstance();
        minDate.add(Calendar.YEAR, -10);
        datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());

        datePickerDialog.show();
    }

    private void setupListeners() {
        binding.btnRegister.setOnClickListener(v -> attemptRegistration());
    }

    private void attemptRegistration() {
        if (!validateInputs()) {
            return;
        }

        // Create patient entity
        PatientEntity patient = new PatientEntity();
        patient.setPatientId(IdGenerator.generatePatientId());
        patient.setFirstName(binding.inputFirstName.getText().toString().trim());
        patient.setLastName(binding.inputLastName.getText().toString().trim());
        patient.setDateOfBirth(selectedDateOfBirth);
        patient.setGender(getSelectedGender());
        patient.setGuardianName(binding.inputGuardianName.getText().toString().trim());
        patient.setGuardianPhone(binding.inputGuardianPhone.getText().toString().trim());
        patient.setVillage(binding.inputVillage.getText().toString().trim());
        patient.setDistrict(binding.inputDistrict.getText().toString().trim());
        patient.setBirthCertificateNumber(binding.inputBirthCertificate.getText().toString().trim());
        patient.setRegistrationDate(new Date());
        patient.setActive(true);
        patient.setSynced(false);

        // Save patient
        patientRepository.insert(patient);

        // Generate vaccination schedule
        generateVaccinationSchedule(patient.getPatientId(), selectedDateOfBirth);

        Toast.makeText(this, "Child registered successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void generateVaccinationSchedule(String patientId, Date birthDate) {
        // Get all required vaccine doses
        vaccineDoseRepository.getRequiredDoses(doses -> {
            if (doses == null || doses.isEmpty()) {
                return;
            }

            // Create care plan
            CarePlanEntity carePlan = VaccineScheduleGenerator.createCarePlan(
                    patientId,
                    doses.size(),
                    false
            );
            carePlanRepository.insert(carePlan);

            // Generate scheduled doses
            List<ScheduledDoseEntity> scheduledDoses = VaccineScheduleGenerator.generateSchedule(
                    patientId,
                    birthDate,
                    doses,
                    carePlan.getCarePlanId()
            );

            // Save scheduled doses
            scheduledDoseRepository.insertAll(scheduledDoses);

            // Schedule notifications for each dose
            com.finalprojectgroupae.immunization.services.notification.NotificationScheduler notificationScheduler =
                    new com.finalprojectgroupae.immunization.services.notification.NotificationScheduler(getApplication());
            for (ScheduledDoseEntity scheduledDose : scheduledDoses) {
                notificationScheduler.scheduleNotificationsForDose(scheduledDose);
            }
        });
    }

    private boolean validateInputs() {
        boolean isValid = true;

        // First name
        if (TextUtils.isEmpty(binding.inputFirstName.getText())) {
            binding.layoutFirstName.setError("First name is required");
            isValid = false;
        } else {
            binding.layoutFirstName.setError(null);
        }

        // Last name
        if (TextUtils.isEmpty(binding.inputLastName.getText())) {
            binding.layoutLastName.setError("Last name is required");
            isValid = false;
        } else {
            binding.layoutLastName.setError(null);
        }

        // Date of birth
        if (selectedDateOfBirth == null) {
            binding.layoutDateOfBirth.setError("Date of birth is required");
            isValid = false;
        } else {
            binding.layoutDateOfBirth.setError(null);
        }

        // Gender
        if (getSelectedGender() == null) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        // Guardian name
        if (TextUtils.isEmpty(binding.inputGuardianName.getText())) {
            binding.layoutGuardianName.setError("Guardian name is required");
            isValid = false;
        } else {
            binding.layoutGuardianName.setError(null);
        }

        // Guardian phone
        if (TextUtils.isEmpty(binding.inputGuardianPhone.getText())) {
            binding.layoutGuardianPhone.setError("Guardian phone is required");
            isValid = false;
        } else {
            binding.layoutGuardianPhone.setError(null);
        }

        // Village
        if (TextUtils.isEmpty(binding.inputVillage.getText())) {
            binding.layoutVillage.setError("Village is required");
            isValid = false;
        } else {
            binding.layoutVillage.setError(null);
        }

        // District
        if (TextUtils.isEmpty(binding.inputDistrict.getText())) {
            binding.layoutDistrict.setError("District is required");
            isValid = false;
        } else {
            binding.layoutDistrict.setError(null);
        }

        return isValid;
    }

    private String getSelectedGender() {
        int checkedId = binding.radioGroupGender.getCheckedRadioButtonId();
        if (checkedId == R.id.radioMale) {
            return "male";
        } else if (checkedId == R.id.radioFemale) {
            return "female";
        } else if (checkedId == R.id.radioOther) {
            return "other";
        }
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}

