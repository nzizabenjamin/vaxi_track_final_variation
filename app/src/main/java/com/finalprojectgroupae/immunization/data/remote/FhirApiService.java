package com.finalprojectgroupae.immunization.data.remote;

import com.finalprojectgroupae.immunization.data.remote.dto.FhirBundle;
import com.finalprojectgroupae.immunization.data.remote.dto.FhirPatient;
import com.finalprojectgroupae.immunization.data.remote.dto.FhirImmunization;
import com.finalprojectgroupae.immunization.data.remote.dto.FhirAppointment;
import com.finalprojectgroupae.immunization.data.remote.dto.FhirCarePlan;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FhirApiService {

    // Patient endpoints
    @POST("Patient")
    Call createPatient(@Body FhirPatient patient);

    @GET("Patient/{id}")
    Call getPatient(@Path("id") String id);

    @PUT("Patient/{id}")
    Call updatePatient(@Path("id") String id, @Body FhirPatient patient);

    @GET("Patient")
    Call searchPatients(@Query("name") String name);

    // Immunization endpoints
    @POST("Immunization")
    Call createImmunization(@Body FhirImmunization immunization);

    @GET("Immunization/{id}")
    Call getImmunization(@Path("id") String id);

    @GET("Immunization")
    Call getImmunizationsByPatient(@Query("patient") String patientId);

    // Appointment endpoints
    @POST("Appointment")
    Call createAppointment(@Body FhirAppointment appointment);

    @GET("Appointment/{id}")
    Call getAppointment(@Path("id") String id);

    @PUT("Appointment/{id}")
    Call updateAppointment(@Path("id") String id, @Body FhirAppointment appointment);

    // CarePlan endpoints
    @POST("CarePlan")
    Call createCarePlan(@Body FhirCarePlan carePlan);

    @GET("CarePlan/{id}")
    Call getCarePlan(@Path("id") String id);

    @GET("CarePlan")
    Call getCarePlansByPatient(@Query("patient") String patientId);
}