package com.finalprojectgroupae.immunization.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.finalprojectgroupae.immunization.R;
import com.finalprojectgroupae.immunization.data.AuthManager;
import com.finalprojectgroupae.immunization.data.local.entities.ImmunizationEntity;
import com.finalprojectgroupae.immunization.data.local.entities.PatientEntity;
import com.finalprojectgroupae.immunization.data.local.entities.ScheduledDoseEntity;
import com.finalprojectgroupae.immunization.data.local.entities.VaccineDoseEntity;
import com.finalprojectgroupae.immunization.data.repository.PatientRepository;
import com.finalprojectgroupae.immunization.data.repository.ScheduledDoseRepository;
import com.finalprojectgroupae.immunization.data.repository.VaccineDoseRepository;
import com.finalprojectgroupae.immunization.databinding.ActivityRecordVaccinationBinding;
import com.finalprojectgroupae.immunization.services.ImmunizationService;
import com.finalprojectgroupae.immunization.utils.DateUtils;
import com.finalprojectgroupae.immunization.utils.IdGenerator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RecordVaccinationActivity extends AppCompatActivity {

    private ActivityRecordVaccinationBinding binding;
    private PatientRepository patientRepository;
    private ScheduledDoseRepository scheduledDoseRepository;
    private VaccineDoseRepository vaccineDoseRepository;
    private ImmunizationService immunizationService;
    private AuthManager authManager;

    private List<PatientEntity> patients;
    private List<ScheduledDoseEntity> scheduledDoses;
    private PatientEntity selectedPatient;
    private ScheduledDoseEntity selectedScheduledDose;
    private VaccineDoseEntity selectedVaccineDose;
    private Date selectedAdministeredDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecordVaccinationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authManager = new AuthManager(this);
        patientRepository = new PatientRepository(getApplication());
        scheduledDoseRepository = new ScheduledDoseRepository(getApplication());
        vaccineDoseRepository = new VaccineDoseRepository(getApplication());
        immunizationService = new ImmunizationService(getApplication());

        // Check if user is CHW or Admin
        String role = authManager.getActiveRole();
        if (!AuthManager.ROLE_ADMIN.equals(role) && !AuthManager.ROLE_USER.equals(role)) {
            Toast.makeText(this, "Only CHWs and Admins can record vaccinations", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setupToolbar();
        setupDatePicker();
        setupPatientSpinner();
        setupScheduledDoseSpinner();
        setupListeners();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Record Vaccination");
        }
    }

    private void setupDatePicker() {
        binding.inputAdministeredDate.setOnClickListener(v -> showDatePicker());
        binding.inputAdministeredDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                showDatePicker();
            }
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        if (selectedAdministeredDate != null) {
            calendar.setTime(selectedAdministeredDate);
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    Calendar selectedCalendar = Calendar.getInstance();
                    selectedCalendar.set(year, month, dayOfMonth);
                    selectedAdministeredDate = selectedCalendar.getTime();
                    binding.inputAdministeredDate.setText(DateUtils.formatForDisplay(selectedAdministeredDate));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        // Set max date to today (can't record future vaccinations)
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void setupPatientSpinner() {
        patientRepository.getAllPatients().observe(this, patientList -> {
            if (patientList != null && !patientList.isEmpty()) {
                patients = patientList;
                List<String> patientNames = new ArrayList<>();
                patientNames.add("Select Patient");
                for (PatientEntity patient : patients) {
                    patientNames.add(patient.getFirstName() + " " + patient.getLastName());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        patientNames
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spinnerPatient.setAdapter(adapter);
            }
        });
    }

    private void setupScheduledDoseSpinner() {
        binding.spinnerPatient.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                if (position > 0 && patients != null && position <= patients.size()) {
                    selectedPatient = patients.get(position - 1);
                    loadScheduledDoses(selectedPatient.getPatientId());
                } else {
                    selectedPatient = null;
                    scheduledDoses = new ArrayList<>();
                    updateScheduledDoseSpinner();
                }
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                selectedPatient = null;
            }
        });
    }

    private void loadScheduledDoses(String patientId) {
        scheduledDoseRepository.getScheduledDosesByPatient(patientId).observe(this, doses -> {
            if (doses != null) {
                scheduledDoses = new ArrayList<>();
                for (ScheduledDoseEntity dose : doses) {
                    // Only show doses that are not completed
                    if (!"completed".equals(dose.getStatus())) {
                        scheduledDoses.add(dose);
                    }
                }
                updateScheduledDoseSpinner();
            }
        });
    }

    private void updateScheduledDoseSpinner() {
        List<String> doseDescriptions = new ArrayList<>();
        doseDescriptions.add("Select Scheduled Dose (Optional)");

        for (ScheduledDoseEntity dose : scheduledDoses) {
            // Get vaccine dose info
            vaccineDoseRepository.getDoseById(dose.getDoseId(), vaccineDose -> {
                if (vaccineDose != null) {
                    String description = vaccineDose.getVaccineDefId() + " - Dose " + vaccineDose.getDoseNumber() +
                            " (Due: " + DateUtils.formatForDisplay(dose.getScheduledDate()) + ")";
                    doseDescriptions.add(description);
                }
            });
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                doseDescriptions
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerScheduledDose.setAdapter(adapter);
    }

    private void setupListeners() {
        binding.btnRecord.setOnClickListener(v -> attemptRecordVaccination());
    }

    private void attemptRecordVaccination() {
        if (!validateInputs()) {
            return;
        }

        // Create immunization entity
        ImmunizationEntity immunization = new ImmunizationEntity();
        immunization.setImmunizationId(IdGenerator.generateImmunizationId());
        immunization.setPatientId(selectedPatient.getPatientId());

        if (selectedScheduledDose != null) {
            immunization.setScheduledDoseId(selectedScheduledDose.getScheduledDoseId());
        }

        // Get vaccine info from selected scheduled dose or manual entry
        if (selectedScheduledDose != null && selectedVaccineDose != null) {
            // Get vaccine definition to get the vaccine code
            // We'll fetch this in the callback when we get the vaccine dose
            vaccineDoseRepository.getDoseById(selectedScheduledDose.getDoseId(), dose -> {
                if (dose == null) {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Vaccine dose not found", Toast.LENGTH_SHORT).show();
                    });
                    return;
                }
                
                // Get vaccine definition
                com.finalprojectgroupae.immunization.data.local.AppDatabase database = 
                        com.finalprojectgroupae.immunization.data.local.AppDatabase.getInstance(getApplication());
                com.finalprojectgroupae.immunization.data.local.entities.VaccineDefinitionEntity vaccineDef = 
                        database.vaccineDefinitionDao().getVaccineById(dose.getVaccineDefId());
                
                if (vaccineDef != null) {
                    immunization.setVaccineCode(vaccineDef.getVaccineCode());
                    immunization.setVaccineName(vaccineDef.getVaccineName());
                } else {
                    immunization.setVaccineCode(dose.getVaccineDefId());
                    immunization.setVaccineName(dose.getVaccineDefId());
                }
                
                // Set other fields
                immunization.setAdministeredDate(selectedAdministeredDate);
                immunization.setLotNumber(binding.inputLotNumber.getText().toString().trim());
                immunization.setRoute(binding.inputRoute.getText().toString().trim());
                immunization.setSite(binding.inputSite.getText().toString().trim());
                immunization.setAdministeredBy(authManager.getActiveRole());
                immunization.setStatus("completed");
                immunization.setSynced(false);

                // Record immunization
                runOnUiThread(() -> {
                    immunizationService.recordImmunization(immunization, immunizationId -> {
                        Toast.makeText(this, "Vaccination recorded successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                });
            });
        } else {
            // Manual entry - would need vaccine selection
            Toast.makeText(this, "Please select a scheduled dose or enter vaccine manually", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private boolean validateInputs() {
        boolean isValid = true;

        // Patient
        if (selectedPatient == null) {
            Toast.makeText(this, "Please select a patient", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        // Administered date
        if (selectedAdministeredDate == null) {
            binding.layoutAdministeredDate.setError("Administered date is required");
            isValid = false;
        } else {
            binding.layoutAdministeredDate.setError(null);
        }

        // Lot number
        if (TextUtils.isEmpty(binding.inputLotNumber.getText())) {
            binding.layoutLotNumber.setError("Lot number is required");
            isValid = false;
        } else {
            binding.layoutLotNumber.setError(null);
        }

        // Scheduled dose or manual vaccine entry
        if (selectedScheduledDose == null) {
            Toast.makeText(this, "Please select a scheduled dose", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else {
            // Load vaccine dose info
            vaccineDoseRepository.getDoseById(selectedScheduledDose.getDoseId(), dose -> {
                selectedVaccineDose = dose;
            });
        }

        return isValid;
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

