## 🩺 *VaxiTrack — Smart Immunization Planner & Tracker*

---

### 🧩 *SECTION 1 — Project Overview (Ruth Ishimwe Gwiza, ID: 26082)*

#### 📘 Summary

VaxiTrack is a mobile-based solution that helps parents and community health workers (CHWs) monitor and manage child immunization schedules. It ensures that every child receives their vaccines on time and that caregivers are reminded automatically before each dose.

#### 🎯 Objectives

* Provide digital immunization tracking
* Reduce missed vaccination appointments
* Centralize child health data using FHIR APIs
* Improve CHW efficiency through reminders and reports

#### 💡 Problem Statement

Many children miss vaccines due to poor follow-up and manual tracking. VaxiTrack solves this by providing automated reminders, access to vaccination history, and real-time data synchronization.

---

### ⚙ *SECTION 2 — Technical Architecture (Baraka Johnson Bright, ID: 25583)*

#### 🛠 Tools & Frameworks

| Component       | Technology                              |
| --------------- | --------------------------------------- |
| Mobile Platform | Android (Java/Kotlin)                   |
| API Standard    | HL7 FHIR R4 (HAPI FHIR Test Server)     |
| Database        | SQLite (for local storage)              |
| Networking      | Retrofit2 / OkHttp                      |
| UI Design       | XML + Material Design                   |
| Notifications   | AlarmManager / Firebase Cloud Messaging |

#### 🔗 System Layers

1. *UI Layer* — Screens for vaccines, reminders, and profiles.
2. *Data Layer* — Local database and API sync.
3. *Integration Layer* — FHIR RESTful APIs for Patient, Immunization, Appointment, CarePlan, Communication.

---

### 📱 *SECTION 3 — Core Features (Ndjogou Mpira O. David, ID: 25713)*

#### 🧩 Main Functionalities

* Child profile registration (via Patient resource)
* Vaccine tracking and progress visualization
* Reminder notifications before due dates
* Appointment scheduling (via Appointment resource)
* Offline access for CHWs in rural areas
* Local data caching and sync with FHIR server

#### 🔔 Key Features

* Auto-generated immunization schedule per child
* Color-coded progress bars for completed vaccines
* Alerts for overdue vaccinations

---

### 🎨 *SECTION 4 — UI/UX Design (Nziza Benjamin 26240)*

#### 🖌 Design Approach

* Minimal, health-oriented interface using Material Design
* Color theme: Blue (#2F80ED), White (#FFFFFF), and Soft Green (#27AE60)
* Focused on readability and ease of navigation

#### 📱 Main Screens

1. *Dashboard:* Displays vaccine status & upcoming doses
2. *Child Profile:* Basic info + immunization card
3. *Appointments:* Scheduled visits
4. *Notifications:* Reminders and alerts

#### 🧭 Navigation

Bottom navigation bar with tabs: Home | Vaccines | Appointments | Profile

---

### 💾 *SECTION 5 — Database & Data Flow ([Member 5])*

#### 🧠 Data Model Overview

| Table         | Description                       |
| ------------- | --------------------------------- |
| Patient       | Stores child info                 |
| Immunization  | Stores vaccine records            |
| Appointment   | Tracks upcoming visits            |
| CarePlan      | Defines schedule for doses        |
| Communication | Handles reminders & notifications |

#### 🔄 Data Flow

1. User registers child → stored as Patient
2. Vaccine details fetched via /Immunization
3. App generates reminders using CarePlan
4. Notification messages via Communication
5. Data synced with HAPI FHIR server when online

---

### 🧠 *SECTION 6 — FHIR API Integration ([Member 6])*

#### 🌐 API Endpoints Used

| Resource      | Endpoint Example             | Description                  |
| ------------- | ---------------------------- | ---------------------------- |
| Patient       | /Patient/{id}              | Fetch child demographic info |
| Immunization  | /Immunization?patient={id} | List vaccine records         |
| Appointment   | /Appointment?patient={id}  | Track appointments           |
| CarePlan      | /CarePlan?patient={id}     | Define vaccine schedules     |
| Communication | /Communication             | Send or log reminders        |

#### 🧩 Sample JSON Example

json
{
  "resourceType": "Immunization",
  "status": "completed",
  "vaccineCode": { "text": "BCG Vaccine" },
  "patient": { "reference": "Patient/1234" },
  "occurrenceDateTime": "2025-02-10"
}


---

### 🔔 *SECTION 7 — Notifications & Reminders ([Member 7])*

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

### 🧪 *SECTION 8 — Testing & Evaluation ([Member 8])*

#### 🧠 Testing Strategy

| Type                | Tool          | Purpose                    |
| ------------------- | ------------- | -------------------------- |
| Unit Testing        | JUnit         | Validate logic & reminders |
| Integration Testing | Retrofit Mock | API testing                |
| UI Testing          | Espresso      | Screen navigation checks   |
| User Testing        | Field Test    | CHW usability feedback     |

#### 📋 Evaluation Criteria

* ✅ Accurate vaccine schedule generation
* ✅ Timely notifications sent
* ✅ Smooth API sync
* ✅ Offline support verified

---

### 🌍 *SECTION 9 — Impact & Future Work ([Member 9])*

#### 💪 Social Impact

* Reduce missed immunization appointments
* Improve record accuracy for CHWs
* Empower parents through reminders
* Support Rwanda’s Smart Health initiative

#### 🚀 Future Enhancements

* Multi-language support (Kinyarwanda, English, French)
* Integration with national health APIs
* Smart analytics for vaccination coverage
* Predictive AI for missed-dose risk detection

---

### 👥 *SECTION 10 — Team & Collaboration ([Member 10])*

#### 👨‍💻 Team Members

| Name                   | ID    | 
| ---------------------- | ----- | 
| Ruth Ishimwe Gwiza     | 26082 | 
| Baraka Johnson Bright  | 25583 | 
| Ndjogou Mpira O. David | 25713 | 
| [Member 4]             | [ID]  | 
| [Member 5]             | [ID]  |
| [Member 6]             | [ID]  | 
| [Member 7]             | [ID]  | 
| [Member 8]             | [ID]  | 
| [Member 9]             | [ID]  | 
| [Member 10]            | [ID]  | 

#### 🤝 Collaboration Workflow

1. Each member edits *only their section*
2. Create a personal branch
3. Commit and push changes
4. Submit Pull Request (PR) to main
5. Merge after review

#### 🧭 Example Commands

bash
git checkout -b section7-isaac
git add README.md
git commit -m "Edited Section 7 - Notifications by Isaac"
git push -u origin section7-isaac


---