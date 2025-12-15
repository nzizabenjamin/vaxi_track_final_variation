package com.finalprojectgroupae.immunization.services;

import android.app.Application;
import android.util.Log;

import com.finalprojectgroupae.immunization.data.local.entities.CarePlanEntity;
import com.finalprojectgroupae.immunization.data.local.entities.ImmunizationEntity;
import com.finalprojectgroupae.immunization.data.local.entities.PatientEntity;
import com.finalprojectgroupae.immunization.data.local.entities.ScheduledDoseEntity;
import com.finalprojectgroupae.immunization.data.local.entities.VaccineDoseEntity;
import com.finalprojectgroupae.immunization.data.repository.CarePlanRepository;
import com.finalprojectgroupae.immunization.data.repository.ImmunizationRepository;
import com.finalprojectgroupae.immunization.data.repository.PatientRepository;
import com.finalprojectgroupae.immunization.data.repository.ScheduledDoseRepository;
import com.finalprojectgroupae.immunization.data.repository.VaccineDoseRepository;
import com.finalprojectgroupae.immunization.services.notification.NotificationScheduler;
import com.finalprojectgroupae.immunization.utils.Constants;
import com.finalprojectgroupae.immunization.utils.IdGenerator;
import com.finalprojectgroupae.immunization.utils.VaccineScheduleGenerator;

import java.util.Date;
import java.util.List;

public class ImmunizationService {
}