package com.codigogt.stokes.model

data class Appointment(
    val id: Int,
    val doctorName: String,
    val scheduledDate: String,
    val scheduledTime: String,
    val status: String
)
