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
