package com.codigogt.stokes.ui.home

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.codigogt.stokes.R

class SettingsFragment: PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, key: String?) {
       setPreferencesFromResource(R.xml.preferences, key)
    }

}