# final-project-group-ae
### MediTrack – A Simple FHIR-Enabled Mobile Health App  

---

## 🧠 Concept Note  

**MediTrack** is a lightweight mobile application designed to help users manage their medications and medical appointments while integrating with the **FHIR (Fast Healthcare Interoperability Resources)** standard to access and synchronize health data securely.

The goal is to demonstrate how modern mobile applications can interact with healthcare systems through FHIR APIs, ensuring interoperability and standardized data exchange between different healthcare platforms.

---

## 🎯 Objectives  

- Build a simple mobile app using Android Studio.  
- Retrieve and display **Patient**, **MedicationStatement**, and **Appointment** data via public FHIR APIs.  
- Let users:
  - View their profile (FHIR Patient resource).  
  - See active medications (FHIR MedicationStatement resource).  
  - Add and view upcoming appointments (FHIR Appointment resource or locally stored).  
  - Set medication reminders locally.

---

## 🔗 FHIR API Integration  

We will use the **public HAPI FHIR test server**:  
👉 [https://hapi.fhir.org/baseR4](https://hapi.fhir.org/baseR4)

### Example API Endpoints  

| Data Type | HTTP Request | Example |
|------------|---------------|----------|
| Patient | `GET /Patient/{id}` | `https://hapi.fhir.org/baseR4/Patient/example` |
| Medications | `GET /MedicationStatement?patient={id}` | `https://hapi.fhir.org/baseR4/MedicationStatement?patient=example` |
| Appointments | `GET /Appointment?patient={id}` | `https://hapi.fhir.org/baseR4/Appointment?patient=example` |

The app’s `fhir_service.dart` file will handle these API calls and parse JSON responses into Dart models.

---

## ⚙️ Tech Stack  

| Component | Technology |
|------------|-------------|
| Mobile Framework | Android Studio (Java) |
| Backend / API | Public FHIR REST API (HAPI server) |
| Version Control | Git + GitHub |
| UI | Flutter Widgets / Material Design |

---

## 👥 Team Collaboration  

Each team member works on a **separate branch** using their **student ID**:

