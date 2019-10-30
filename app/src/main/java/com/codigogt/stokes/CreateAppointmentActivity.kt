package com.codigogt.stokes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create_appointment.*


class CreateAppointmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_appointment)

        btnNext.setOnClickListener {
            cvStep1.visibility = View.GONE
            cvStep2.visibility = View.VISIBLE
        }

        btnConfirm.setOnClickListener {
            Toast.makeText(this, "Cita creada correctamente", Toast.LENGTH_SHORT).show()
            this.finish()
        }

        val specialtyOptions = arrayOf("Neurologia", "Cardiologia", "Medico y Cirujano")
        spinnerSpecialties.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, specialtyOptions)

        val doctorOptions = arrayOf("Henry Diaz", "Tomas Ramos", "Dorian Velasquez")
        spinnerDoctors.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, doctorOptions)
    }
}
