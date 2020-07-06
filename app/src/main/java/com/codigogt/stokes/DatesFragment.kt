package com.codigogt.stokes

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.codigogt.stokes.ui.DatePickerFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_create_appointment.*
import kotlinx.android.synthetic.main.card_view_step_one.*
import kotlinx.android.synthetic.main.card_view_step_one.view.*
import kotlinx.android.synthetic.main.card_view_step_three.*
import kotlinx.android.synthetic.main.card_view_step_three.view.*
import kotlinx.android.synthetic.main.card_view_step_two.*
import kotlinx.android.synthetic.main.card_view_step_two.view.*
import java.util.*


class DatesFragment: Fragment()  {

    private val selectedCalendar = Calendar.getInstance()
    private var selectedTimeRadioBtn: RadioButton? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.activity_create_appointment, container, false)

        view.btnNext.setOnClickListener {
            if (etDescription.text.toString().length < 3) {
                Toast.makeText(activity, getString(R.string.validate_appointment_description), Toast.LENGTH_SHORT ).show()
                etDescription.error = getString(R.string.validate_appointment_description)
            } else {
                cvStep1.visibility = View.GONE
                cvStep2.visibility = View.VISIBLE
            }
        }

        view.btnNext2.setOnClickListener {

            when {
                etScheduleDate.text.toString().isEmpty() -> {
                    Snackbar.make(createAppointmentLinearLayout,
                        R.string.validate_appointment_date, Snackbar.LENGTH_SHORT).show()
                    etScheduleDate.error = getString(R.string.validate_appointment_date)
                }
                selectedTimeRadioBtn == null ->
                    Snackbar.make(createAppointmentLinearLayout,
                        R.string.validate_appointment_time, Snackbar.LENGTH_SHORT).show()
                else -> {
                    showAppointmentDataToConfirm()
                    cvStep2.visibility = View.GONE
                    cvStep3.visibility = View.VISIBLE
                }
            }
        }

        view.btnConfirmAppointment.setOnClickListener {
            Toast.makeText(activity, "Cita creada correctamente", Toast.LENGTH_SHORT).show()
            val homeFragment = HomeFragment()
            openFragment(homeFragment)
        }

        val specialtyOptions = arrayOf("Neurologia", "Cardiologia", "Medico y Cirujano")
        view.spinnerSpecialties.adapter =
            ArrayAdapter<String>(activity!!.baseContext, android.R.layout.simple_list_item_1, specialtyOptions)

        val doctorOptions = arrayOf("Henry Diaz", "Tomas Ramos", "Dorian Velasquez")
        view.spinnerDoctors.adapter =
            ArrayAdapter<String>(activity!!.baseContext, android.R.layout.simple_list_item_1, doctorOptions)

        val onClickCalendar = view.findViewById<View>(R.id.etScheduleDate)

        onClickCalendar.setOnClickListener {
            Toast.makeText(activity, "aqui si llega", Toast.LENGTH_SHORT).show()
            showDatePickerDialog()}

        return view
    }

    private fun openFragment(fragment: Fragment){
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun showAppointmentDataToConfirm(){
        tvConfirmDescription.text = etDescription.text.toString()
        tvConfirmSpecialty.text = spinnerSpecialties.selectedItem.toString()
        val selectedRadioBtnId = radioGroupType.checkedRadioButtonId
        val selectedType = radioGroupType.findViewById<RadioButton>(selectedRadioBtnId)

        tvConfirmType.text = selectedType.text.toString()

        tvConfirmDoctor.text = spinnerDoctors.selectedItem.toString()
        tvConfirmDate.text = etScheduleDate.text.toString()
        tvConfirmTime.text = selectedTimeRadioBtn?.text.toString()
    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { datepicker, y, m, d ->
            selectedCalendar.set(y, m, d)
            etScheduleDate.setText(
                resources.getString(
                    R.string.date_format,
                    y,
                    m.twoDigits(),
                    d.twoDigits()
                )
            )
            val selectedDate = d.toString() + " / " + (m + 1) + " / " + y
            etScheduleDate.setText(selectedDate)
            etScheduleDate.error = null
            displayRadioButtons()
        })
        newFragment.show(activity!!.supportFragmentManager, "datePicker")
    }

    private fun displayRadioButtons() {
        // radioGroup.clearCheck()
        //  radioGroup.removeAllViews()

        selectedTimeRadioBtn = null
        radioGroupLeft.removeAllViews()
        radioGroupRight.removeAllViews()

        val hours = arrayOf("3:00", "3:30", "4:00", "4:30")
        var goToLeft = true

        hours.forEach {
            val radioButton = RadioButton(activity)
            radioButton.id = it.lastIndex
            radioButton.text = it
            radioButton.setOnClickListener { view ->
                selectedTimeRadioBtn?.isChecked = false

                selectedTimeRadioBtn = view as RadioButton?
                selectedTimeRadioBtn?.isChecked = true
            }
            if (goToLeft)
                radioGroupLeft.addView(radioButton)
            else
                radioGroupRight.addView(radioButton)
            goToLeft = !goToLeft

        }
    }

    private fun Int.twoDigits() = if (this <= 9) this.toString() else "0$this"


 //OLD
   /* fun onClickScheduleDate(v: View?) {


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
            etScheduleDate.error = null
            displayRadioButtons()
        }

        //new dialog
        val datePickerDialog =
            DatePickerDialog(activity!!.application, listener, year, month, dayOfMonth)

        val datePicker = datePickerDialog.datePicker
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        datePicker.minDate = calendar.timeInMillis // +1 now
        calendar.add(Calendar.DAY_OF_MONTH, 29)
        datePicker.maxDate = calendar.timeInMillis // +30 now

        datePickerDialog.show()

    }*/

}
