package com.finalprojectgroupae.immunization.data.remote;

import com.finalprojectgroupae.immunization.utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance;
    private final Retrofit retrofit;

    private RetrofitClient() {
        // Logging interceptor for debugging
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // OkHttp client configuration
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(Constants.FHIR_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(Constants.FHIR_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(Constants.FHIR_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build();

        // Retrofit configuration
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.FHIR_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    // FIXED: Removed generic type parameter T to fix "Cannot resolve symbol 'T'" error
    public FhirApiService getApiService() {
        return retrofit.create(FhirApiService.class);
    }
}