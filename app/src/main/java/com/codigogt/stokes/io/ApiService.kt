package com.codigogt.stokes.io

import com.codigogt.stokes.model.Doctor
import com.codigogt.stokes.model.Schedule
import com.codigogt.stokes.model.Specialty
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("specialties")
    fun getAllSpecialties(): Call<ArrayList<Specialty>>

    @GET("specialties/{specialty}/doctors")
    fun getDoctorBySpecialty(@Path("specialty") specialtyId: Int): Call<ArrayList<Doctor>>

    @GET("schedule/hours")
    fun getHours(@Query("doctor_id") doctorId: Int, @Query("date") date: String ):
            Call<Schedule>

    companion object Factory {
        private const val BASE_URL = "http://104.131.65.75/api/v1/"

        fun create(): ApiService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level  = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
           return retrofit.create(ApiService::class.java)
        }
    }
}