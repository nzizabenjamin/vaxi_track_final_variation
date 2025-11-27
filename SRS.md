# Software Requirements Specification (SRS)

**Project:** Immunization Planner & Tracker  
**Version:** 1.0  
**Date:** November 17, 2025  
**Status:** Draft  

---

## 1. Introduction

### 1.1 Purpose
This document specifies requirements for the Immunization Planner & Tracker, an Android mobile application to track children's vaccinations, send automated reminders, and improve immunization coverage rates.

### 1.2 Scope
The system will:
- Track children's immunization records and schedules
- Generate personalized vaccination schedules based on birth date
- Send automated reminders via SMS and push notifications
- Record vaccinations with offline capability
- Generate coverage reports for health officers
- Integrate with national health information systems using FHIR standards

### 1.3 Definitions and Acronyms

| Term  | Definition |
|-------|------------|
| CHW   | Community Health Worker |
| FHIR  | Fast Healthcare Interoperability Resources (HL7 standard) |
| SMS   | Short Message Service |
| API   | Application Programming Interface |

---

## 2. Overall Description

### 2.1 Product Perspective
- Android mobile applications (Parents & CHWs)
- Dashboard (Health Officers)
- Cloud-based backend with FHIR API

### 2.2 User Classes
- **Parents/Guardians**: View records, receive reminders  
- **CHWs**: Register children, record vaccinations, manage appointments  
- **Health Officers**: View statistics, generate reports  
- **System Administrators**: Manage users, monitor system

---

## 3. Functional Requirements

### 3.1 Patient Registration and Management
- Register children with demographics
- Detect duplicates
- Update patient info
- Search by name, ID, phone
- Link children to guardians

### 3.2 Vaccination Schedule Generation
- Auto-generate schedule on registration
- Calculate due dates
- Enforce vaccine dependencies and intervals
- Generate catch-up schedules
- Update schedule dynamically

### 3.3 Vaccination Recording
- Record vaccination details
- Offline recording with sync
- Validate data
- Capture lot numbers
- Barcode scanning
- Prevent duplicates
- Mark appointments as completed


### 3.4 Appointment Management
- Auto-create appointments
- Reschedule support
- Daily appointment list
- Flag missed appointments

### 3.5 Reminder and Notification System
- Send reminders (7, 3, 1 days before)
- SMS and push notifications
- Personalized messages
- Escalation for overdue (1, 3, 7, 14 days)
- Track delivery status
- Retry failed notifications
- Respect user preferences
### 3.6 Reporting and Analytics
- Coverage reports by area/age
- Defaulter lists
- CHW performance metrics
- Vaccination trends
- Real-time dashboard
- Export to PDF, Excel, CSV

### 3.7 Care Plan Management
- Generate care plan
- Track progress
- Update on vaccination
- Generate certificate

## 4. Visual Representation

### 4.1 Use Case Diagram
![Use Case.png](/uploads/cd327da304b2698759e82c0a82014aed/Use_Case.png){width=700 height=600}

### 4.2 Class Diagram
![Class_Diagram_Easy](/uploads/5db1e5e19bbd0bbf172dcf80177c2902/Class_Diagram_Easy.png){width=900 height=432}

### 4.3 Data Flow Diagrams
### Level O DFD Context Diagram
![Level_0_DFD_Context.drawio](/uploads/9fbef4f274d73497312b49509838a16b/Level_0_DFD_Context.drawio.png){width=900 height=239}

### Level 1 DFD Detailed Diagram
![Level_1_DFD_Detailed.drawio](/uploads/8c5addbb1efa7a7aff5a18592e8e19fc/Level_1_DFD_Detailed.drawio.png){width=770 height=600}
---

## 5. Prototypes

### 5.1 Login
![Login](/uploads/fe962e9fa7493bbb019ac905314d85fc/Login.png){width=362 height=600}

### 5.2 Roles
![Roles](/uploads/dcbcc7dc57835370712c4d194037b781/Roles.png){width=364 height=600}

### 5.3 Parent Dashboard
![Parent_Dashboard](/uploads/7ada3711a02aa134016a7b4e10460303/Parent_Dashboard.png){width=435 height=600}

### 5.4 CHW Dashboard
![CHW_Dashboard](/uploads/5a415918d34d594dfc5e604a9fa5a2dc/CHW_Dashboard.png){width=381 height=600}

### 5.5 Registering New Child
![Register](/uploads/36378b7a777a650c95b67926b64be384/Register.png){width=384 height=600}

### 5.6 Notifications
![Notifications](/uploads/76b4d2744babbeaf12c1fe540df225dd/Notifications.png){width=379 height=600}


--- 

## 6. FHIR Integration

### 6.1 Resources Used
- Patient
- Immunization
- Appointment
- Communication
- CarePlan

### 6.2 Sample Patient Resource
json
{
  "resourceType": "Patient",
  "id": "patient-001",
  "name": [{"family": "Uwera", "given": ["Aisha"]}],
  "gender": "female",
  "birthDate": "2025-02-15",
  "contact": [{
    "relationship": [{"text": "Guardian"}],
    "name": {"text": "Marie Uwera"},
    "telecom": [{"value": "+250788123456"}]
  }]
}


### 6.3 Sample Immunization Resource
json
{
  "resourceType": "Immunization",
  "id": "imm-001",
  "status": "completed",
  "vaccineCode": {
    "coding": [{
      "system": "http://hl7.org/fhir/sid/cvx",
      "code": "03",
      "display": "MMR"
    }]
  },
  "patient": {"reference": "Patient/patient-001"},
  "occurrenceDateTime": "2025-11-16T09:30:00Z",
  "lotNumber": "AAJN11K",
  "location": {"reference": "Location/facility-001"}
}

## 7. Data Model

The Immunization Planner & Tracker data model outlines the core entities, their attributes, and how they relate to each other within the system. This structure ensures seamless integration with FHIR resources while supporting core app functionalities such as medication tracking, appointment scheduling, and care management.

### 7.1 Core Entities

#### 1. Patient

Represents the app user or medical patient within the system.

**FHIR Equivalent:** `Patient`

**Key Attributes:**
- PatientID: Unique identifier for the patient
- Name: Full name of the patient
- Gender: Patient's gender
- BirthDate: Patient's date of birth
- ContactInfo: Primary contact information (phone, email)
- Address: Patient's residential address

**Purpose:** Central entity that links all patient-related records including immunizations, appointments, and care plans.

---

#### 2. Guardian

Represents a caretaker or parent associated with a patient, mainly for minors and dependents.

**Key Attributes:**
- GuardianID: Unique identifier for the guardian
- Name: Full name of the guardian
- Relationship: Type of relationship (e.g., parent, legal guardian, caregiver)
- ContactInfo: Contact information for the guardian

**Purpose:** Enables tracking of caretaker relationships and ensures appropriate notification and consent management.

---

#### 3. Immunization

Tracks patient immunizations and vaccination records with comprehensive medical history.

**FHIR Equivalent:** `Immunization`

**Key Attributes:**
- ImmunizationID: Unique identifier for the immunization record
- VaccineName: Name of the vaccine administered
- DateAdministered: Date when the vaccine was given
- Dose: Dose information (e.g., dose number in series)
- Status: Current status of the immunization (e.g., completed, pending, refused)

**Purpose:** Maintains a complete immunization history for clinical decision-making and public health tracking.

---

#### 4. Appointment

Represents scheduled healthcare visits, either fetched from FHIR or added locally within the system.

**FHIR Equivalent:** `Appointment`

**Key Attributes:**
- AppointmentID: Unique identifier for the appointment
- Date & Time: Scheduled date and time of the visit
- Status: Current appointment status (booked, arrived, cancelled)
- HealthcareProvider: Name or ID of the healthcare provider
- Notes: Additional notes or special instructions

**Purpose:** Manages and tracks healthcare provider visits, enabling patient reminders and provider scheduling coordination.

---

#### 5. CarePlan

Represents individualized care or vaccination plans tailored for each patient.

**FHIR Equivalent:** `CarePlan`

**Key Attributes:**
- CarePlanID: Unique identifier for the care plan
- Goals: Clinical or wellness goals to be achieved
- Period: Start and end dates of the care plan
- Activities: Specific actions or interventions included in the plan
- Status: Current status of the care plan (active, completed, cancelled)

**Purpose:** Provides a structured approach to patient care management with defined goals and measurable activities.

---

#### 6. Communication

Manages reminders, notifications, and messages related to vaccinations or appointments.

**FHIR Equivalent:** `Communication`

**Key Attributes:**
- CommunicationID: Unique identifier for the communication record
- Type: Category of communication (notification, reminder, alert)
- Content: Message content or reminder details
- Timestamp: Date and time when the communication was created or sent
- Status: Current status (sent, pending, failed)

**Purpose:** Facilitates patient engagement through timely notifications and vaccination reminders.

---

#### 7. User

Represents internal system users such as Community Health Workers (CHWs), health officers, clinicians, or administrators who interact with the Immunization Planner & Tracker system.

**Key Attributes:**
- UserID: Unique identifier for the system user
- Username: Login username for system access
- Role: User role defining permissions (Admin, CHW, Health Officer, Clinician, etc.)
- Email: Contact email for the user

**Purpose:** Enables role-based access control and audit trails for system activities.

---

### 7.2 Relationships

The following table outlines the relationships between core entities and their cardinality:

| Relationship | Cardinality | Description |
|---|---|---|
| Guardian → Patient | 1 : N | A guardian can be responsible for multiple patients (e.g., multiple children) |
| Patient → Immunization | 1 : N | A patient can have multiple immunization records over their lifetime |
| Patient → Appointment | 1 : N | A patient may have multiple appointments across different healthcare providers |
| Patient → CarePlan | 1 : 1 | Each patient has one active care plan at any given time |
| User → Immunization | 1 : N | A system user (CHW/admin) may record multiple immunizations |

#### Relationship Details

**Guardian to Patient (1:N)**
- One guardian can manage immunization records for multiple children or dependents
- Supports flexible family structures and multiple guardianships
- Enables grouped reminders and notifications for multiple dependents under one guardian

**Patient to Immunization (1:N)**
- Patients accumulate immunization records over their lifetime
- Supports complete vaccine history tracking from birth through adulthood
- Enables compliance reporting and vaccination gap analysis

**Patient to Appointment (1:N)**
- Patients can schedule multiple vaccination appointments across different healthcare facilities
- Tracks appointment history for continuity of care
- Enables appointment reminders, rescheduling, and follow-up management

**Patient to CarePlan (1:1)**
- Each patient has one active vaccination care plan at any given time
- Ensures focused and personalized immunization strategy
- Previous care plans are maintained for historical reference and audit trails

**User to Immunization (1:N)**
- System users (CHWs, nurses, clinicians) record and validate multiple immunizations
- Creates audit trail for quality assurance and regulatory compliance
- Supports delegation of immunization recording tasks among healthcare workers

























---
