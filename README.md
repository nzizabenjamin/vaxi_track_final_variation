# final-project-group-ae
# 🏥 MediTrack — Smart Medication & Appointment Tracker

## 📘 Concept Note

**MediTrack** is a simple yet powerful mobile application designed to help users **track their medications and medical appointments** efficiently. The app aims to enhance **patient engagement and healthcare continuity** by leveraging the **FHIR (Fast Healthcare Interoperability Resources)** standard to connect with healthcare data systems.

### 🎯 Project Concept

In today’s healthcare landscape, patients often struggle to manage their medication schedules, remember appointments, or access their medical records across different providers. MediTrack solves this problem by offering an **intuitive and secure mobile app** that centralizes medication reminders, appointment tracking, and patient data access — all in one place.

By integrating **FHIR APIs**, the app ensures **interoperability** with hospital systems, pharmacies, and healthcare providers, allowing basic access to patient and medication data using public FHIR APIs.. This allows users to stay informed and healthcare providers to maintain accurate, up-to-date records.

---

## 💡 Why FHIR?

**FHIR (Fast Healthcare Interoperability Resources)** is a global standard created by **HL7** for exchanging healthcare information electronically.  
By using FHIR APIs, MediTrack can:

- 🔗 **Integrate with existing Electronic Health Records (EHRs):** Fetch basic patient demographics, medication prescriptions, and appointment data.  
- ⚙️ **Ensure interoperability:** Seamlessly connect to any system that supports FHIR standards.  
- 🔒 **Enhance data security:** Uses a public FHIR test server (HAPI FHIR) to demonstrate how healthcare data can be exchanged between systems in a standardized format. 
- 🚀 **Simplify development:** Use structured RESTful APIs to easily access, query, and update healthcare data resources.

Key FHIR resources used in MediTrack include:
- **Patient** – to retrieve patient demographic data.  
- **Medication** – to display prescribed medications.  
- **MedicationRequest** – to manage prescribed medications and dosages.  
- **Appointment** – to schedule and remind users of medical appointments.  

---

## 📱 App Overview

### 🧩 Core Features

| Feature | Description |
|----------|-------------|
| 🕒 **Medication Tracker** | Allows users to log medications, set reminders, and receive alerts when it’s time to take them. |
| 📅 **Appointment Scheduler** | Syncs with healthcare provider systems to track appointments and notify users of upcoming visits. |
| 🧑‍⚕️ **Patient Profile** | Displays basic patient information fetched securely from FHIR servers. |
| 💊 **Medication Data Integration** | Retrieves prescribed medications from FHIR’s `Medication` and `MedicationRequest` endpoints. |
| 🔔 **Smart Notifications** | Sends push notifications for medication intake and appointments. |
| 📊 **Health Summary Dashboard** | Provides a summary of active prescriptions, next appointments, and adherence reports. |
| 🔐 **Data Security & Privacy** | Uses basic HTTPS requests to access test FHIR data (no login needed). |

---

## 🧠 Technical Architecture

**Development Environment:** Android Studio  
**Language:** Java  
**API Standard:** HL7 FHIR (R4) — using the public HAPI FHIR server  
**Networking:** Retrofit or HttpURLConnection for REST API calls  
**Local Data:** SharedPreferences / SQLite for reminders  
**UI Design:** XML layouts & Material Design components


---

## 🔄 FHIR Integration Flow

1. **Data Access**  
   The app connects directly to the public FHIR test server (no authentication needed) to fetch sample patient, medication, and appointment data.

2. **Data Retrieval**  
   The app calls FHIR endpoints to retrieve:
   - `/Patient` → fetch patient demographics  
   - `/MedicationRequest` → retrieve prescribed medications  
   - `/Appointment` → get scheduled medical visits  

3. **Local Storage & Sync**  
   Retrieved data is cached locally and periodically synced for offline support.

4. **User Interaction**  
   The user can view, edit, or set reminders linked to these FHIR-based records.

---

## ⚙️ Example FHIR Endpoints Used
The following read-only endpoints from the HAPI FHIR test server are used for demonstration purposes:

| Resource | Endpoint | Description |
|-----------|-----------|-------------|
| Patient | `/Patient/{id}` | Retrieves patient demographic data |
| Medication | `/Medication/{id}` | Retrieves medication details |
| MedicationRequest | `/MedicationRequest?patient={id}` | Lists all medications prescribed to a patient |
| Appointment | `/Appointment?patient={id}` | Fetches upcoming or past appointments |

---

## 🧩 Expected Impact

- 💪 **Empowered patients** through active involvement in their medication and care routines.  
- 🧠 **Smarter healthcare decisions** enabled by up-to-date, accurate data.  
- 🏥 **Improved collaboration** between healthcare providers and patients.  
- 🇷🇼 **Aligned with Rwanda’s Smart Health initiatives** and **Vision 2050**, promoting the use of digital health innovations.

---

## 👥 Team Members

| Name | Names |
|------|------|
| ID: 25713 | NDJOGOU MPIRA O. David| 
| ID: 25583 | Baraka Johnson Bright | 
| ID: 26082 |  ISHIMWE GWIZA Ruth  | 
| ID:  |       | 
| ID:  |       | 
| ID:  |       | 
| ID:  |       | 
| ID:  |       | 
| ID:  |       | 
| ID:  |       | 

---

## 🧾 Future Enhancements

- 🪪 Integration with OAuth2.0 and Push Notifications.
- 🩺 Integration with wearable devices for vital tracking (heart rate, blood pressure).  
- 🌍 Multi-language support (English, Kinyarwanda, French).  
- 🧬 AI-based medication adherence predictions.  
- 🩹 Integration with national eHealth systems for real-time updates.

---