package com.codigogt.stokes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.codigogt.stokes.model.Appointment
import kotlinx.android.synthetic.main.activity_appointments.*

class AppointmentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointments)

        val appointments = ArrayList<Appointment>()
        appointments.add(
            Appointment(1, "MedicoTest", "22/06/2020", "07:00 AM")
        )
        appointments.add(
            Appointment(1, "Henry", "22/06/2020", "09:00 AM")
        )
        appointments.add(
            Appointment(1, "Tomas", "22/06/2020", "03:00 PM")
        )

        rvAppointments.layoutManager = LinearLayoutManager(this)
        rvAppointments.adapter = AppointmentAdapter(appointments)
    }
}
