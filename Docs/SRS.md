# Software Requirements Specification (SRS)

**Project:** Immunization Planner & Tracker  
**Version:** 1.0  
**Date:** November 17, 2025  
**Status:** Final Draft  

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
- Web dashboard (Health Officers)
- Cloud-based backend with FHIR API
- Integration with NHIS, SMS gateway, and Firebase

### 2.2 User Classes
- **Parents/Guardians**: View records, receive reminders  
- **CHWs**: Register children, record vaccinations, manage appointments  
- **Health Officers**: View statistics, generate reports  
- **System Administrators**: Manage users, monitor system

### 2.3 Operating Environment
- Android 8.0+ (API 26+)
- 2GB RAM, 1.5GB storage
- 2G/3G/4G/WiFi, offline support
- Server: Linux, PostgreSQL 13+, Redis, RabbitMQ

### 2.4 Constraints
- FHIR R4 compliance
- Offline operation
- TLS 1.3, AES-256 encryption
- English, Kinyarwanda, French support
- Preference for open-source technologies

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
- Respect user preferences

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

---

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
```json
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
```

### 6.3 Sample Immunization Resource
```json
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
```

---

## 7. Data Model

### 7.1 Core Entities
- Patient
- Guardian
- Immunization
- Appointment
- CarePlan
- Communication
- User

### 7.2 Relationships
- Guardian 1 → N Patient  
- Patient 1 → N Immunization  
- Patient 1 → N Appointment  
- Patient 1 → 1 CarePlan  
- User 1 → N Immunization

---

## 8. User Interface

### 8.1 Mobile App Screens

**Parent App**
- Login
- Dashboard
- Vaccination Timeline
- Notifications
- Profile Settings

**CHW App**
- Login
- Dashboard
- Patient Search
- Record Vaccination
- Register Child
- Appointment List
- Notifications

**Design**
- Material Design 3
- Primary: #667eea
- Secondary: #4caf50
- Min screen: 360x640dp
- Bottom navigation
- Offline indicators

---