package com.finalprojectgroupae.immunization.data.local;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
        version = Constants.DATABASE_VERSION,
        exportSchema = true
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
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration() // For development only
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // Callback to populate database on first creation
    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                // Populate vaccine definitions
                AppDatabase database = getInstance(null);
                populateVaccineDefinitions(database);
                populateVaccineDoses(database);
            });
        }
    };

    private static void populateVaccineDefinitions(AppDatabase db) {
        VaccineDefinitionDao dao = db.vaccineDefinitionDao();

        // BCG
        VaccineDefinitionEntity bcg = new VaccineDefinitionEntity();
        bcg.setVaccineDefId("VAC-BCG");
        bcg.setVaccineName("BCG (Bacillus Calmette-Guérin)");
        bcg.setVaccineCode(Constants.VACCINE_BCG);
        bcg.setDescription("Protects against tuberculosis");
        bcg.setDisplayOrder(1);
        dao.insert(bcg);

        // OPV
        VaccineDefinitionEntity opv = new VaccineDefinitionEntity();
        opv.setVaccineDefId("VAC-OPV");
        opv.setVaccineName("OPV (Oral Polio Vaccine)");
        opv.setVaccineCode(Constants.VACCINE_OPV);
        opv.setDescription("Protects against poliomyelitis");
        opv.setDisplayOrder(2);
        dao.insert(opv);

        // Pentavalent
        VaccineDefinitionEntity penta = new VaccineDefinitionEntity();
        penta.setVaccineDefId("VAC-PENTA");
        penta.setVaccineName("Pentavalent (5-in-1)");
        penta.setVaccineCode(Constants.VACCINE_PENTA);
        penta.setDescription("Protects against DTP, Hepatitis B, and Hib");
        penta.setDisplayOrder(3);
        dao.insert(penta);

        // PCV
        VaccineDefinitionEntity pcv = new VaccineDefinitionEntity();
        pcv.setVaccineDefId("VAC-PCV");
        pcv.setVaccineName("PCV (Pneumococcal Conjugate Vaccine)");
        pcv.setVaccineCode(Constants.VACCINE_PCV);
        pcv.setDescription("Protects against pneumococcal disease");
        pcv.setDisplayOrder(4);
        dao.insert(pcv);

        // Rotavirus
        VaccineDefinitionEntity rota = new VaccineDefinitionEntity();
        rota.setVaccineDefId("VAC-ROTA");
        rota.setVaccineName("Rotavirus Vaccine");
        rota.setVaccineCode(Constants.VACCINE_ROTA);
        rota.setDescription("Protects against rotavirus gastroenteritis");
        rota.setDisplayOrder(5);
        dao.insert(rota);

        // MMR
        VaccineDefinitionEntity mmr = new VaccineDefinitionEntity();
        mmr.setVaccineDefId("VAC-MMR");
        mmr.setVaccineName("MMR (Measles-Rubella)");
        mmr.setVaccineCode(Constants.VACCINE_MMR);
        mmr.setDescription("Protects against measles and rubella");
        mmr.setDisplayOrder(6);
        dao.insert(mmr);
    }

    private static void populateVaccineDoses(AppDatabase db) {
        VaccineDoseDao dao = db.vaccineDoseDao();

        // BCG - At birth
        VaccineDoseEntity bcgDose = new VaccineDoseEntity();
        bcgDose.setDoseId("DOSE-BCG-1");
        bcgDose.setVaccineDefId("VAC-BCG");
        bcgDose.setDoseNumber(1);
        bcgDose.setAgeInWeeks(0); // At birth
        bcgDose.setMinIntervalDays(0);
        bcgDose.setDisplayOrder(1);
        dao.insert(bcgDose);

        // OPV - At birth and 6, 10, 14 weeks
        VaccineDoseEntity opv0 = new VaccineDoseEntity();
        opv0.setDoseId("DOSE-OPV-0");
        opv0.setVaccineDefId("VAC-OPV");
        opv0.setDoseNumber(0);
        opv0.setAgeInWeeks(0);
        opv0.setMinIntervalDays(0);
        opv0.setDisplayOrder(2);
        dao.insert(opv0);

        VaccineDoseEntity opv1 = new VaccineDoseEntity();
        opv1.setDoseId("DOSE-OPV-1");
        opv1.setVaccineDefId("VAC-OPV");
        opv1.setDoseNumber(1);
        opv1.setAgeInWeeks(6);
        opv1.setMinIntervalDays(28);
        opv1.setDisplayOrder(3);
        dao.insert(opv1);

        VaccineDoseEntity opv2 = new VaccineDoseEntity();
        opv2.setDoseId("DOSE-OPV-2");
        opv2.setVaccineDefId("VAC-OPV");
        opv2.setDoseNumber(2);
        opv2.setAgeInWeeks(10);
        opv2.setMinIntervalDays(28);
        opv2.setDisplayOrder(4);
        dao.insert(opv2);

        VaccineDoseEntity opv3 = new VaccineDoseEntity();
        opv3.setDoseId("DOSE-OPV-3");
        opv3.setVaccineDefId("VAC-OPV");
        opv3.setDoseNumber(3);
        opv3.setAgeInWeeks(14);
        opv3.setMinIntervalDays(28);
        opv3.setDisplayOrder(5);
        dao.insert(opv3);

        // Pentavalent - 6, 10, 14 weeks
        VaccineDoseEntity penta1 = new VaccineDoseEntity();
        penta1.setDoseId("DOSE-PENTA-1");
        penta1.setVaccineDefId("VAC-PENTA");
        penta1.setDoseNumber(1);
        penta1.setAgeInWeeks(6);
        penta1.setMinIntervalDays(0);
        penta1.setDisplayOrder(6);
        dao.insert(penta1);

        VaccineDoseEntity penta2 = new VaccineDoseEntity();
        penta2.setDoseId("DOSE-PENTA-2");
        penta2.setVaccineDefId("VAC-PENTA");
        penta2.setDoseNumber(2);
        penta2.setAgeInWeeks(10);
        penta2.setMinIntervalDays(28);
        penta2.setDisplayOrder(7);
        dao.insert(penta2);

        VaccineDoseEntity penta3 = new VaccineDoseEntity();
        penta3.setDoseId("DOSE-PENTA-3");
        penta3.setVaccineDefId("VAC-PENTA");
        penta3.setDoseNumber(3);
        penta3.setAgeInWeeks(14);
        penta3.setMinIntervalDays(28);
        penta3.setDisplayOrder(8);
        dao.insert(penta3);

        // PCV - 6, 10, 14 weeks
        VaccineDoseEntity pcv1 = new VaccineDoseEntity();
        pcv1.setDoseId("DOSE-PCV-1");
        pcv1.setVaccineDefId("VAC-PCV");
        pcv1.setDoseNumber(1);
        pcv1.setAgeInWeeks(6);
        pcv1.setMinIntervalDays(0);
        pcv1.setDisplayOrder(9);
        dao.insert(pcv1);

        VaccineDoseEntity pcv2 = new VaccineDoseEntity();
        pcv2.setDoseId("DOSE-PCV-2");
        pcv2.setVaccineDefId("VAC-PCV");
        pcv2.setDoseNumber(2);
        pcv2.setAgeInWeeks(10);
        pcv2.setMinIntervalDays(28);
        pcv2.setDisplayOrder(10);
        dao.insert(pcv2);

        VaccineDoseEntity pcv3 = new VaccineDoseEntity();
        pcv3.setDoseId("DOSE-PCV-3");
        pcv3.setVaccineDefId("VAC-PCV");
        pcv3.setDoseNumber(3);
        pcv3.setAgeInWeeks(14);
        pcv3.setMinIntervalDays(28);
        pcv3.setDisplayOrder(11);
        dao.insert(pcv3);

        // Rotavirus - 6, 10 weeks
        VaccineDoseEntity rota1 = new VaccineDoseEntity();
        rota1.setDoseId("DOSE-ROTA-1");
        rota1.setVaccineDefId("VAC-ROTA");
        rota1.setDoseNumber(1);
        rota1.setAgeInWeeks(6);
        rota1.setMinIntervalDays(0);
        rota1.setDisplayOrder(12);
        dao.insert(rota1);

        VaccineDoseEntity rota2 = new VaccineDoseEntity();
        rota2.setDoseId("DOSE-ROTA-2");
        rota2.setVaccineDefId("VAC-ROTA");
        rota2.setDoseNumber(2);
        rota2.setAgeInWeeks(10);
        rota2.setMinIntervalDays(28);
        rota2.setDisplayOrder(13);
        dao.insert(rota2);

        // MMR - 9 months and 15 months
        VaccineDoseEntity mmr1 = new VaccineDoseEntity();
        mmr1.setDoseId("DOSE-MMR-1");
        mmr1.setVaccineDefId("VAC-MMR");
        mmr1.setDoseNumber(1);
        mmr1.setAgeInWeeks(36); // 9 months
        mmr1.setMinIntervalDays(0);
        mmr1.setDisplayOrder(14);
        dao.insert(mmr1);

        VaccineDoseEntity mmr2 = new VaccineDoseEntity();
        mmr2.setDoseId("DOSE-MMR-2");
        mmr2.setVaccineDefId("VAC-MMR");
        mmr2.setDoseNumber(2);
        mmr2.setAgeInWeeks(65); // 15 months (65 weeks)
        mmr2.setMinIntervalDays(168); // 24 weeks minimum
        mmr2.setDisplayOrder(15);
        dao.insert(mmr2);
    }
}