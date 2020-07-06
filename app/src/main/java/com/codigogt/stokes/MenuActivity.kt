package com.codigogt.stokes

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val homeFragment = HomeFragment()
        openFragment(homeFragment)

        navigation.setOnNavigationItemSelectedListener(menuOnNavigationItemSelectedListener)


/*        btnCreateAppointment.setOnClickListener {
            val intent = Intent(this, CreateAppointmentActivity::class.java)
            startActivity(intent)

        }*/

/*        btnMyAppointments.setOnClickListener {
            val intent = Intent(this, AppointmentsActivity::class.java)
            startActivity(intent)
        }*/

/*        btnLogOut.setOnClickListener {
            clearSessionPreferences()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }*/
    }

    private val menuOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
        item -> when(item.itemId)
        {
            R.id.bottom_menu_home ->
            {
                val homeFragment = HomeFragment()
                openFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_menu_dates ->
            {
                val datesFragment = DatesFragment()
                openFragment(datesFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_menu_settings ->
            {
                val settingsFragment = SettingsFragment()
                openFragment(settingsFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
            false
    }

    private fun openFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun clearSessionPreferences(){
        val preferences = getSharedPreferences("general", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean("session", false)
        editor.apply()
    }
}
