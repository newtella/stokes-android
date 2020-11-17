package com.codigogt.stokes.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.codigogt.stokes.R
import com.codigogt.stokes.io.ApiService
import com.codigogt.stokes.ui.auth.MainActivity
import com.codigogt.stokes.util.PreferenceHelper
import com.codigogt.stokes.util.PreferenceHelper.get
import com.codigogt.stokes.util.PreferenceHelper.set
import com.codigogt.stokes.util.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SettingsFragment: PreferenceFragmentCompat() {

    private val preferences by lazy{
        PreferenceHelper.defaultPrefs(this.requireContext())
    }

    private val apiService by lazy {
        ApiService.create()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, key: String?) {
       setPreferencesFromResource(R.xml.preferences, key)


    }

    override fun onResume() {
        super.onResume()

        findPreference<Preference>("logout")?.setOnPreferenceClickListener {
                val jwt = preferences["jwt", ""]
                val call = apiService.postLogout("Bearer $jwt")
                call.enqueue(object: Callback<Void>{
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        requireActivity().toast(t.localizedMessage)
                    }

                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        preferences["jwt"] = " "

                        val myIntent = Intent(activity, MainActivity::class.java)
                        activity!!.startActivity(myIntent)
                        this@SettingsFragment.activity?.finish()
                    }
                })
            true
        }

    }

}