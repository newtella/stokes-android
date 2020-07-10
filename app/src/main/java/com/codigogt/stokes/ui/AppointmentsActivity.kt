package com.codigogt.stokes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.codigogt.stokes.AppointmentAdapter
import com.codigogt.stokes.R
import com.codigogt.stokes.model.Appointment
import kotlinx.android.synthetic.main.fragment_account_dates.*

class AppointmentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_account_dates)

        val appointments = ArrayList<Appointment>()
        appointments.add(
            Appointment(1, "Dorian", "22/06/2020", "07:00 AM")
        )
        appointments.add(
            Appointment(2, "Henry", "22/06/2020", "09:00 AM")
        )
        appointments.add(
            Appointment(3, "Tomas", "22/06/2020", "03:00 PM")
        )

        rvAppointments.layoutManager = LinearLayoutManager(this)
        rvAppointments.adapter =
            AppointmentAdapter(appointments)
    }
}
