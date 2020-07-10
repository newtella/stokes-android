package com.codigogt.stokes.ui.home

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment : DialogFragment() {

    private var listener: DatePickerDialog.OnDateSetListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireActivity(), listener, year - 18, month, day)

        c.add(Calendar.DAY_OF_MONTH, 1)
        datePickerDialog.datePicker.minDate = c.timeInMillis // +1 now

        c.add(Calendar.DAY_OF_MONTH, 29)
        datePickerDialog.datePicker.maxDate = c.timeInMillis // +30 now

        return datePickerDialog
    }

    companion object {
        fun newInstance(listener: DatePickerDialog.OnDateSetListener): DatePickerFragment {
            val fragment = DatePickerFragment()
            fragment.listener = listener
            return fragment
        }
    }
}