🩺 VaxiTrack — Smart Immunization Planner & Tracker



🧩 SECTION 1 — Project Overview (Ruth Ishimwe Gwiza, ID: 26082)


📘 Summary
VaxiTrack is a mobile-based solution that helps parents and community health workers (CHWs) monitor and manage child immunization schedules. It ensures that every child receives their vaccines on time and that caregivers are reminded automatically before each dose.

🎯 Objectives

Provide digital immunization tracking
Reduce missed vaccination appointments
Centralize child health data using FHIR APIs
Improve CHW efficiency through reminders and reports


💡 Problem Statement
Many children miss vaccines due to poor follow-up and manual tracking. VaxiTrack solves this by providing automated reminders, access to vaccination history, and real-time data synchronization.


⚙️ SECTION 2 — Technical Architecture (Baraka Johnson Bright, ID: 25583)


🛠️ Tools & Frameworks



Component
Technology




Mobile Platform
Android (Java/Kotlin)


API Standard
HL7 FHIR R4 (HAPI FHIR Test Server)


Database
SQLite (for local storage)


Networking
Retrofit2 / OkHttp


UI Design
XML + Material Design


Notifications
AlarmManager / Firebase Cloud Messaging




🔗 System Layers


UI Layer — Screens for vaccines, reminders, and profiles.

### 🔔 *SECTION 7 — Notifications & Reminders (Habamenshi Ineza Darryl 25948)

#### ⏰ Reminder System

* Android AlarmManager triggers notifications before vaccine due dates
* Firebase Cloud Messaging used for real-time alerts if connected to server

#### 🔄 Notification Flow

1. Retrieve vaccine due dates from CarePlan
2. Schedule local notifications using AlarmManager
3. Log each reminder via Communication resource

#### 📨 Example Use Case

> “John (Age 1) is due for Polio Vaccine tomorrow.”
> Parents receive push notification 24h before and on the day of vaccination.

---


Data Layer — Local database and API sync.

Integration Layer — FHIR RESTful APIs for Patient, Immunization, Appointment, CarePlan, Communication.

