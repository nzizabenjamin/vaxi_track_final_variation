package com.finalprojectgroupae.immunization.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.finalprojectgroupae.immunization.data.local.dao.*;
import com.finalprojectgroupae.immunization.data.local.entities.*;
import com.finalprojectgroupae.immunization.utils.Constants;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {
                PatientEntity.class,
                VaccineDefinitionEntity.class,
                VaccineDoseEntity.class,
                ScheduledDoseEntity.class,
                ImmunizationEntity.class,
                CarePlanEntity.class,
                AppointmentEntity.class,
                NotificationEntity.class
        },
        version = 1,
        exportSchema = false
)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    // DAOs
    public abstract PatientDao patientDao();
    public abstract VaccineDefinitionDao vaccineDefinitionDao();
    public abstract VaccineDoseDao vaccineDoseDao();
    public abstract ScheduledDoseDao scheduledDoseDao();
    public abstract ImmunizationDao immunizationDao();
    public abstract CarePlanDao carePlanDao();
    public abstract AppointmentDao appointmentDao();
    public abstract NotificationDao notificationDao();

    // Singleton instance
    private static volatile AppDatabase INSTANCE;

    // Thread pool for database operations
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    Constants.DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .build();

                    // Populate initial data
                    populateInitialData();
                }
            }
        }
        return INSTANCE;
    }

    private static void populateInitialData() {
        databaseWriteExecutor.execute(() -> {
            if (INSTANCE != null) {
                VaccineDefinitionDao vaccineDao = INSTANCE.vaccineDefinitionDao();
                VaccineDoseDao doseDao = INSTANCE.vaccineDoseDao();

                // Check if data already exists
                try {
                    java.util.List<VaccineDefinitionEntity> existing = vaccineDao.getAllVaccines();
                    if (existing != null && !existing.isEmpty()) {
                        return; // Data already populated
                    }
                } catch (Exception e) {
                    // First run, continue with population
                }

                // Populate vaccines
                insertVaccineDefinitions(vaccineDao);

                // Populate doses
                insertVaccineDoses(doseDao);
            }
        });
    }

    private static void insertVaccineDefinitions(VaccineDefinitionDao dao) {
        // BCG
        VaccineDefinitionEntity bcg = new VaccineDefinitionEntity();
        bcg.setVaccineDefId("VAC-BCG");
        bcg.setVaccineName("BCG");
        bcg.setVaccineCode("19");
        bcg.setDescription("Tuberculosis vaccine");
        bcg.setDisplayOrder(1);
        dao.insert(bcg);

        // OPV
        VaccineDefinitionEntity opv = new VaccineDefinitionEntity();
        opv.setVaccineDefId("VAC-OPV");
        opv.setVaccineName("OPV");
        opv.setVaccineCode("02");
        opv.setDescription("Oral Polio Vaccine");
        opv.setDisplayOrder(2);
        dao.insert(opv);

        // Pentavalent
        VaccineDefinitionEntity penta = new VaccineDefinitionEntity();
        penta.setVaccineDefId("VAC-PENTA");
        penta.setVaccineName("Pentavalent");
        penta.setVaccineCode("20");
        penta.setDescription("5-in-1 vaccine");
        penta.setDisplayOrder(3);
        dao.insert(penta);

        // PCV
        VaccineDefinitionEntity pcv = new VaccineDefinitionEntity();
        pcv.setVaccineDefId("VAC-PCV");
        pcv.setVaccineName("PCV");
        pcv.setVaccineCode("133");
        pcv.setDescription("Pneumococcal vaccine");
        pcv.setDisplayOrder(4);
        dao.insert(pcv);

        // Rotavirus
        VaccineDefinitionEntity rota = new VaccineDefinitionEntity();
        rota.setVaccineDefId("VAC-ROTA");
        rota.setVaccineName("Rotavirus");
        rota.setVaccineCode("122");
        rota.setDescription("Rotavirus vaccine");
        rota.setDisplayOrder(5);
        dao.insert(rota);

        // MMR
        VaccineDefinitionEntity mmr = new VaccineDefinitionEntity();
        mmr.setVaccineDefId("VAC-MMR");
        mmr.setVaccineName("MMR");
        mmr.setVaccineCode("03");
        mmr.setDescription("Measles-Rubella vaccine");
        mmr.setDisplayOrder(6);
        dao.insert(mmr);
    }

    private static void insertVaccineDoses(VaccineDoseDao dao) {
        // BCG at birth
        VaccineDoseEntity dose = new VaccineDoseEntity();
        dose.setDoseId("DOSE-BCG-1");
        dose.setVaccineDefId("VAC-BCG");
        dose.setDoseNumber(1);
        dose.setAgeInWeeks(0);
        dose.setMinIntervalDays(0);
        dose.setDisplayOrder(1);
        dao.insert(dose);

        // OPV doses
        insertDose(dao, "DOSE-OPV-0", "VAC-OPV", 0, 0, 0, 2);
        insertDose(dao, "DOSE-OPV-1", "VAC-OPV", 1, 6, 28, 3);
        insertDose(dao, "DOSE-OPV-2", "VAC-OPV", 2, 10, 28, 4);
        insertDose(dao, "DOSE-OPV-3", "VAC-OPV", 3, 14, 28, 5);

        // Pentavalent doses
        insertDose(dao, "DOSE-PENTA-1", "VAC-PENTA", 1, 6, 0, 6);
        insertDose(dao, "DOSE-PENTA-2", "VAC-PENTA", 2, 10, 28, 7);
        insertDose(dao, "DOSE-PENTA-3", "VAC-PENTA", 3, 14, 28, 8);

        // PCV doses
        insertDose(dao, "DOSE-PCV-1", "VAC-PCV", 1, 6, 0, 9);
        insertDose(dao, "DOSE-PCV-2", "VAC-PCV", 2, 10, 28, 10);
        insertDose(dao, "DOSE-PCV-3", "VAC-PCV", 3, 14, 28, 11);

        // Rotavirus doses
        insertDose(dao, "DOSE-ROTA-1", "VAC-ROTA", 1, 6, 0, 12);
        insertDose(dao, "DOSE-ROTA-2", "VAC-ROTA", 2, 10, 28, 13);

        // MMR doses
        insertDose(dao, "DOSE-MMR-1", "VAC-MMR", 1, 36, 0, 14);
        insertDose(dao, "DOSE-MMR-2", "VAC-MMR", 2, 65, 168, 15);
    }

    private static void insertDose(VaccineDoseDao dao, String doseId, String vaccineDefId,
                                   int doseNumber, int ageInWeeks, int minIntervalDays, int displayOrder) {
        VaccineDoseEntity dose = new VaccineDoseEntity();
        dose.setDoseId(doseId);
        dose.setVaccineDefId(vaccineDefId);
        dose.setDoseNumber(doseNumber);
        dose.setAgeInWeeks(ageInWeeks);
        dose.setMinIntervalDays(minIntervalDays);
        dose.setDisplayOrder(displayOrder);
        dao.insert(dose);
    }
}