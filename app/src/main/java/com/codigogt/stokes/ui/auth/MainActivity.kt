package com.codigogt.stokes.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codigogt.stokes.PreferenceHelper.get
import com.codigogt.stokes.PreferenceHelper.set
import android.widget.Toast
import com.codigogt.stokes.ui.menu.MenuActivity
import com.codigogt.stokes.PreferenceHelper
import com.codigogt.stokes.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val snackBar by lazy {
        Snackbar.make(mainLayout,
            R.string.press_back_again, Snackbar.LENGTH_SHORT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferences = PreferenceHelper.defaultPrefs(this)
        if(preferences["Session", false]) {
            goToMenuActivity()
        }
        else {
            btnLogin.setOnClickListener {
                createSessionPreferences()
                goToMenuActivity()
            }
            tvGoToRegister.setOnClickListener {
                Toast.makeText(this, getString(R.string.please_fill_your_register_data), Toast.LENGTH_SHORT).show()

                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)

            }
        }
    }

    private fun createSessionPreferences(){
        /*
        val preferences = getSharedPreferences("general", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean("session", true)
        editor.apply()
         */
        val preferences = PreferenceHelper.defaultPrefs(this)
        preferences["session"] = true
    }

    private fun goToMenuActivity() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        if (snackBar.isShown)
            super.onBackPressed()
        else
            snackBar.show()
    }
}
