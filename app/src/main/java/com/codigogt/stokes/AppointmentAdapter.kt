package com.codigogt.stokes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codigogt.stokes.model.Appointment
import kotlinx.android.synthetic.main.fragment_account_date_item_appointment.view.*

class AppointmentAdapter(private val appointments: ArrayList<Appointment>):
    RecyclerView.Adapter<AppointmentAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

          fun bind(appointment: Appointment)= with(itemView) {
                tvAppointmentId.text = itemView.context.getString(R.string.item_appointment_id, appointment.id)
                tvDoctorName.text = appointment.doctorName
                tvScheduledDate.text = appointment.scheduledDate
                tvScheduledTime.text = itemView.context.getString(R.string.item_appointment_time, appointment.scheduledTime)
                status1.text = appointment.status
          }
    }

    // Inflar XML items
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_account_date_item_appointment, parent, false)
        )
    }

    // Devolver la cantidad de elementos
    override fun getItemCount() = appointments.size

    // Bind de la data
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appointment = appointments[position]

        holder.bind(appointment)

    }
}