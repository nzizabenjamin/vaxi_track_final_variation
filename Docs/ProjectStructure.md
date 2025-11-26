# Project Structure

```
final-project-group-ae/
│
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/finalprojectgroupae/immunization/
│   │   │   │   │
│   │   │   │   ├── MainActivity.java                      ✓ EXISTS
│   │   │   │   ├── LoginActivity.java                     ✓ EXISTS
│   │   │   │   ├── SignUpActivity.java                    ✓ EXISTS
│   │   │   │   │
│   │   │   │   ├── data/                                   [DATA LAYER]
│   │   │   │   │   ├── local/                             [Local Database]
│   │   │   │   │   │   ├── AppDatabase.java              ⚠ TO CREATE
│   │   │   │   │   │   ├── dao/
│   │   │   │   │   │   │   ├── PatientDao.java           ⚠ TO CREATE
│   │   │   │   │   │   │   ├── ImmunizationDao.java      ⚠ TO CREATE
│   │   │   │   │   │   │   ├── AppointmentDao.java       ⚠ TO CREATE
│   │   │   │   │   │   │   ├── CarePlanDao.java          ⚠ TO CREATE
│   │   │   │   │   │   │   └── CommunicationDao.java     ⚠ TO CREATE
│   │   │   │   │   │   └── entities/
│   │   │   │   │   │       ├── PatientEntity.java        ⚠ TO CREATE
│   │   │   │   │   │       ├── ImmunizationEntity.java   ⚠ TO CREATE
│   │   │   │   │   │       ├── AppointmentEntity.java    ⚠ TO CREATE
│   │   │   │   │   │       ├── CarePlanEntity.java       ⚠ TO CREATE
│   │   │   │   │   │       └── CommunicationEntity.java  ⚠ TO CREATE
│   │   │   │   │   │
│   │   │   │   │   ├── remote/                            [FHIR API]
│   │   │   │   │   │   ├── FhirApiService.java           ⚠ TO CREATE
│   │   │   │   │   │   ├── RetrofitClient.java           ⚠ TO CREATE
│   │   │   │   │   │   └── dto/
│   │   │   │   │   │       ├── PatientDto.java           ⚠ TO CREATE
│   │   │   │   │   │       ├── ImmunizationDto.java      ⚠ TO CREATE
│   │   │   │   │   │       ├── AppointmentDto.java       ⚠ TO CREATE
│   │   │   │   │   │       ├── CarePlanDto.java          ⚠ TO CREATE
│   │   │   │   │   │       └── CommunicationDto.java     ⚠ TO CREATE
│   │   │   │   │   │
│   │   │   │   │   ├── repository/                        [Repository Pattern]
│   │   │   │   │   │   ├── PatientRepository.java        ⚠ TO CREATE
│   │   │   │   │   │   ├── ImmunizationRepository.java   ⚠ TO CREATE
│   │   │   │   │   │   ├── AppointmentRepository.java    ⚠ TO CREATE
│   │   │   │   │   │   ├── CarePlanRepository.java       ⚠ TO CREATE
│   │   │   │   │   │   └── CommunicationRepository.java  ⚠ TO CREATE
│   │   │   │   │   │
│   │   │   │   │   ├── model/                             [Domain Models]
│   │   │   │   │   │   ├── Child.java                    ✓ EXISTS
│   │   │   │   │   │   ├── Appointment.java              ✓ EXISTS
│   │   │   │   │   │   ├── Reminder.java                 ✓ EXISTS
│   │   │   │   │   │   ├── DashboardStat.java            ✓ EXISTS
│   │   │   │   │   │   ├── Patient.java                  ⚠ TO CREATE
│   │   │   │   │   │   ├── Immunization.java             ⚠ TO CREATE
│   │   │   │   │   │   ├── Vaccine.java                  ⚠ TO CREATE
│   │   │   │   │   │   ├── VaccineSchedule.java          ⚠ TO CREATE
│   │   │   │   │   │   └── Guardian.java                 ⚠ TO CREATE
│   │   │   │   │   │
│   │   │   │   │   ├── AuthManager.java                   ✓ EXISTS
│   │   │   │   │   └── DemoDataProvider.java              ✓ EXISTS
│   │   │   │   │
│   │   │   │   ├── ui/                                     [UI LAYER]
│   │   │   │   │   ├── fragments/
│   │   │   │   │   │   ├── HomeFragment.java             ✓ EXISTS
│   │   │   │   │   │   ├── ChildDetailFragment.java      ✓ EXISTS
│   │   │   │   │   │   ├── ScheduleFragment.java         ✓ EXISTS
│   │   │   │   │   │   ├── RemindersFragment.java        ✓ EXISTS
│   │   │   │   │   │   ├── DashboardFragment.java        ✓ EXISTS
│   │   │   │   │   │   ├── RegisterChildFragment.java    ⚠ TO CREATE
│   │   │   │   │   │   ├── VaccinationRecordFragment.java ⚠ TO CREATE
│   │   │   │   │   │   └── ReportsFragment.java          ⚠ TO CREATE
│   │   │   │   │   │
│   │   │   │   │   ├── adapters/
│   │   │   │   │   │   ├── ChildRosterAdapter.java       ✓ EXISTS
│   │   │   │   │   │   ├── AppointmentAdapter.java       ✓ EXISTS
│   │   │   │   │   │   ├── ReminderAdapter.java          ✓ EXISTS
│   │   │   │   │   │   ├── VaccinationAdapter.java       ⚠ TO CREATE
│   │   │   │   │   │   └── TimelineAdapter.java          ⚠ TO CREATE
│   │   │   │   │   │
│   │   │   │   │   └── viewmodel/
│   │   │   │   │       ├── HomeViewModel.java            ⚠ TO CREATE
│   │   │   │   │       ├── ChildDetailViewModel.java     ⚠ TO CREATE
│   │   │   │   │       ├── ScheduleViewModel.java        ⚠ TO CREATE
│   │   │   │   │       ├── ReminderViewModel.java        ⚠ TO CREATE
│   │   │   │   │       └── DashboardViewModel.java       ⚠ TO CREATE
│   │   │   │   │
│   │   │   │   ├── services/                               [BACKGROUND SERVICES]
│   │   │   │   │   ├── notification/
│   │   │   │   │   │   ├── NotificationService.java      ⚠ TO CREATE
│   │   │   │   │   │   ├── ReminderScheduler.java        ⚠ TO CREATE
│   │   │   │   │   │   └── NotificationHelper.java       ⚠ TO CREATE
│   │   │   │   │   ├── sync/
│   │   │   │   │   │   ├── SyncService.java              ⚠ TO CREATE
│   │   │   │   │   │   └── SyncAdapter.java              ⚠ TO CREATE
│   │   │   │   │   └── sms/
│   │   │   │   │       └── SmsService.java               ⚠ TO CREATE
│   │   │   │   │
│   │   │   │   ├── utils/                                  [UTILITIES]
│   │   │   │   │   ├── DateUtils.java                    ⚠ TO CREATE
│   │   │   │   │   ├── NetworkUtils.java                 ⚠ TO CREATE
│   │   │   │   │   ├── Constants.java                    ⚠ TO CREATE
│   │   │   │   │   ├── VaccineScheduleGenerator.java     ⚠ TO CREATE
│   │   │   │   │   ├── DuplicateDetector.java            ⚠ TO CREATE
│   │   │   │   │   └── FhirMapper.java                   ⚠ TO CREATE
│   │   │   │   │
│   │   │   │   └── receivers/                              [BROADCAST RECEIVERS]
│   │   │   │       ├── BootReceiver.java                 ⚠ TO CREATE
│   │   │   │       ├── AlarmReceiver.java                ⚠ TO CREATE
│   │   │   │       └── NetworkChangeReceiver.java        ⚠ TO CREATE
│   │   │   │
│   │   │   ├── res/                                        [RESOURCES]
│   │   │   │   ├── layout/                                ✓ EXISTS (All current layouts)
│   │   │   │   │   ├── activity_login.xml
│   │   │   │   │   ├── activity_main.xml
│   │   │   │   │   ├── activity_sign_up.xml
│   │   │   │   │   ├── fragment_home.xml
│   │   │   │   │   ├── fragment_child_detail.xml
│   │   │   │   │   ├── fragment_schedule.xml
│   │   │   │   │   ├── fragment_reminders.xml
│   │   │   │   │   ├── fragment_dashboard.xml
│   │   │   │   │   ├── fragment_register_child.xml        ⚠ TO CREATE
│   │   │   │   │   ├── fragment_vaccination_record.xml    ⚠ TO CREATE
│   │   │   │   │   ├── dialog_vaccine_details.xml         ⚠ TO CREATE
│   │   │   │   │   └── bottom_sheet_filter.xml            ⚠ TO CREATE
│   │   │   │   │
│   │   │   │   ├── drawable/                              ✓ EXISTS (Icons & Illustrations)
│   │   │   │   ├── values/                                ✓ EXISTS (Strings, Colors, Themes)
│   │   │   │   ├── menu/                                  ✓ EXISTS
│   │   │   │   ├── xml/                                   
│   │   │   │   │   ├── network_security_config.xml        ⚠ TO CREATE
│   │   │   │   │   └── file_paths.xml                     ⚠ TO CREATE
│   │   │   │   └── raw/
│   │   │   │       └── vaccine_schedule.json              ⚠ TO CREATE
│   │   │   │
│   │   │   └── AndroidManifest.xml                         ✓ EXISTS (Needs updates)
│   │   │
│   │   ├── test/                                           [UNIT TESTS]
│   │   │   └── java/com/finalprojectgroupae/immunization/
│   │   │       ├── data/
│   │   │       │   ├── repository/
│   │   │       │   │   └── PatientRepositoryTest.java     ⚠ TO CREATE
│   │   │       │   └── AuthManagerTest.java               ⚠ TO CREATE
│   │   │       ├── utils/
│   │   │       │   ├── VaccineScheduleGeneratorTest.java  ⚠ TO CREATE
│   │   │       │   └── DateUtilsTest.java                 ⚠ TO CREATE
│   │   │       └── viewmodel/
│   │   │           └── HomeViewModelTest.java             ⚠ TO CREATE
│   │   │
│   │   └── androidTest/                                    [INSTRUMENTED TESTS]
│   │       └── java/com/finalprojectgroupae/immunization/
│   │           ├── ui/
│   │           │   ├── LoginActivityTest.java             ⚠ TO CREATE
│   │           │   └── MainActivityTest.java              ⚠ TO CREATE
│   │           └── database/
│   │               └── DatabaseTest.java                   ⚠ TO CREATE
│   │
│   ├── build.gradle                                        ✓ EXISTS (Needs dependency updates)
│   └── proguard-rules.pro                                  ✓ EXISTS
│
├── docs/                                                    [DOCUMENTATION]
│   ├── SRS.md                                              ✓ EXISTS
│   ├── API_DOCUMENTATION.md                                ⚠ TO CREATE
│   ├── USER_GUIDE.md                                       ⚠ TO CREATE
│   ├── TESTING_GUIDE.md                                    ⚠ TO CREATE
│   └── diagrams/
│       ├── use_case.png                                    ✓ EXISTS (Referenced in SRS)
│       ├── class_diagram.png                               ✓ EXISTS (Referenced in SRS)
│       └── dfd_diagrams.png                                ✓ EXISTS (Referenced in SRS)
│
├── README.md                                                ✓ EXISTS
├── build.gradle                                             ✓ EXISTS
├── settings.gradle                                          ✓ EXISTS
├── gradle.properties                                        ✓ EXISTS
├── .gitignore                                               ✓ EXISTS
└── LICENSE                                                  ⚠ TO CREATE (Optional)
```

---