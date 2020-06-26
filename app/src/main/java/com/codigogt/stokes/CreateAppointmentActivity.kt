package com.codigogt.stokes

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create_appointment.*
import java.util.*


class CreateAppointmentActivity : AppCompatActivity() {

    private val selectedCalendar = Calendar.getInstance()
    private var selectedRadioButton: RadioButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super. onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_appointment)

        btnNext.setOnClickListener {
            if (etDescription.text.toString().length < 3)
                etDescription.error = getString(R.string.validate_appointment_description)
            else
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

    fun onClickScheduleDate(v: View?){

        val year = selectedCalendar.get(Calendar.YEAR)
        val month = selectedCalendar.get(Calendar.MONTH)
        val dayOfMonth = selectedCalendar.get(Calendar.DAY_OF_MONTH)

        val listener = DatePickerDialog.OnDateSetListener { datePicker, y, m, d ->
            //Toast.makeText(this, "$y-$m-$d", Toast.LENGTH_SHORT).show()
            selectedCalendar.set(y, m, d)
            etScheduleDate.setText(
                resources.getString(
                    R.string.date_format,
                    y,
                    m.twoDigits(),
                    d.twoDigits()

                )
            )
            displayRadioButtons()
        }

        //new dialog
        val datePickerDialog = DatePickerDialog(this, listener, year, month, dayOfMonth)

        val datePicker = datePickerDialog.datePicker
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        datePicker.minDate = calendar.timeInMillis // +1 now
        calendar.add(Calendar.DAY_OF_MONTH, 29)
        datePicker.maxDate = calendar.timeInMillis // +30 now

        datePickerDialog.show()

    }

    private fun displayRadioButtons(){
       // radioGroup.clearCheck()
      //  radioGroup.removeAllViews()

        selectedRadioButton = null
        radioGroupLeft.removeAllViews()
        radioGroupRight.removeAllViews()

        val hours = arrayOf("3:00","3:30","4:00","4:30")
        var goToLeft = true

        hours.forEach {
            val radioButton = RadioButton(this)
            radioButton.id = it.lastIndex
            radioButton.text = it
            radioButton.setOnClickListener { view ->
                selectedRadioButton?.isChecked = false

                selectedRadioButton = view as RadioButton?
                selectedRadioButton?.isChecked = true
            }
            if(goToLeft)
                radioGroupLeft.addView(radioButton)
            else
                radioGroupRight.addView(radioButton)
            goToLeft = !goToLeft

        }
    }

    private fun Int.twoDigits()= if (this<=9) this.toString() else "0$this"

    override fun onBackPressed() {
        if(cvStep2.visibility == View.VISIBLE){
            cvStep2.visibility = View.GONE
            cvStep1.visibility = View.VISIBLE
        }else if (cvStep1.visibility == View.VISIBLE){

            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.dialog_create_appointment_exit_title))
            builder.setMessage(getString(R.string.dialog_create_appointment_exit_message))
            builder.setPositiveButton(getString(R.string.dialog_create_appointment_positive_button)){
                    _, _ -> finish()
            }

            builder.setNegativeButton(getString(R.string.dialog_create_appointment_negative_button)){
                    dialog, _ -> dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()

        }

    }
}
