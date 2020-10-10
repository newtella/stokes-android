package com.codigogt.stokes.io

import com.codigogt.stokes.model.Doctor
import com.codigogt.stokes.model.Specialty
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("specialties")
    fun getAllSpecialties(): Call<ArrayList<Specialty>>

    @GET("specialties/{specialty}/doctors")
    fun getDoctorBySpecialty(@Path("specialty") specialtyId: Int): Call<ArrayList<Doctor>>

    companion object Factory {
        private const val BASE_URL = "http://104.131.65.75/api/v1/"

        private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        private val okHttp = OkHttpClient.Builder().addInterceptor(logger)

        private val builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp.build())

        private val retrofit = builder.build()

        fun create(): ApiService {
           return retrofit.create(ApiService::class.java)
        }
    }
}