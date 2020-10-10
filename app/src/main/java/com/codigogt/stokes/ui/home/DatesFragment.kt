package com.codigogt.stokes.ui.home

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.codigogt.stokes.R
import com.codigogt.stokes.io.ApiService
import com.codigogt.stokes.model.Doctor
import com.codigogt.stokes.model.Specialty
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_date_create.*
import kotlinx.android.synthetic.main.fragment_date_card_view_step_one.*
import kotlinx.android.synthetic.main.fragment_date_card_view_step_one.view.*
import kotlinx.android.synthetic.main.fragment_date_card_view_step_three.*
import kotlinx.android.synthetic.main.fragment_date_card_view_step_three.view.*
import kotlinx.android.synthetic.main.fragment_date_card_view_step_two.*
import kotlinx.android.synthetic.main.fragment_date_card_view_step_two.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class DatesFragment: Fragment()  {

    private val apiService: ApiService by lazy {

        ApiService.create()
    }

    private val selectedCalendar = Calendar.getInstance()
    private var selectedTimeRadioBtn: RadioButton? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_date_create, container, false)

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

        loadSpecialties()

        val onClickCalendar = view.findViewById<View>(R.id.etScheduleDate)

        onClickCalendar.setOnClickListener {
            Toast.makeText(activity, "Selecciona una fecha", Toast.LENGTH_SHORT).show()
            showDatePickerDialog()}

        return view
    }

    private fun loadSpecialties(){
       val call = apiService.getAllSpecialties()

        call.enqueue(object :Callback<ArrayList<Specialty>> {

            override fun onResponse(
                call: Call<ArrayList<Specialty>>,
                response: Response<ArrayList<Specialty>>
            ) {
                if(response.isSuccessful){
                    val specialties = response.body()
                    spinnerSpecialties.adapter = ArrayAdapter<Specialty>(requireActivity().baseContext, android.R.layout.simple_list_item_1,
                        specialties!!
                    )
                    listenSpecialtyChange()
                }
            }

            override fun onFailure(call: Call<ArrayList<Specialty>>, t: Throwable) {
                Toast.makeText(activity, "Ocurrio un Error al cargar las especialidades", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun listenSpecialtyChange(){
        spinnerSpecialties?.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                adapter: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val specialty = adapter?.getItemAtPosition(position) as Specialty
                loadDoctors(specialty.id)
            }
        }
    }

    private fun loadDoctors(specialtyId: Int){
        val call =  apiService.getDoctorBySpecialty(specialtyId)
        call.enqueue(object : Callback<ArrayList<Doctor>>{
            override fun onFailure(call: Call<ArrayList<Doctor>>, t: Throwable) {
                Toast.makeText(activity, "Ocurrio un problema al cargar los medicos", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<ArrayList<Doctor>>,
                response: Response<ArrayList<Doctor>>
            ) {
                if(response.isSuccessful){
                    val doctors = response.body()
                    spinnerDoctors.adapter = ArrayAdapter<Doctor>(requireActivity().baseContext, android.R.layout.simple_list_item_1,
                        doctors!!
                    )
                }
            }
        })
    }

    private fun openFragment(fragment: Fragment){
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
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
        val newFragment =
            DatePickerFragment.newInstance(
                DatePickerDialog.OnDateSetListener { datepicker, y, m, d ->
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
        newFragment.show(requireActivity().supportFragmentManager, "datePicker")
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
}
