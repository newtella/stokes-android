package com.codigogt.stokes.ui.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.codigogt.stokes.AppointmentAdapter

import com.codigogt.stokes.R
import com.codigogt.stokes.model.Appointment
import kotlinx.android.synthetic.main.fragment_account_dates.view.*

/**
 * A simple [Fragment] subclass.
 */
class AccountDObjectFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_account_dates, container, false)

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


        view.rvAppointments.layoutManager = LinearLayoutManager(requireActivity())
        view.rvAppointments.adapter =
            AppointmentAdapter(appointments)

        return view
    }
}
