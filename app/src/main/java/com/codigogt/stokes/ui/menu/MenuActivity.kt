package com.codigogt.stokes.ui.menu

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.codigogt.stokes.R
import com.codigogt.stokes.io.ApiService
import com.codigogt.stokes.ui.home.AccountFragment
import com.codigogt.stokes.ui.home.DatesFragment
import com.codigogt.stokes.ui.home.HomeFragment
import com.codigogt.stokes.ui.home.SettingsFragment
import com.codigogt.stokes.util.PreferenceHelper
import com.codigogt.stokes.util.PreferenceHelper.get
import com.codigogt.stokes.util.PreferenceHelper.set
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val homeFragment = HomeFragment()
        openFragment(homeFragment)

        navigation.setOnNavigationItemSelectedListener(menuOnNavigationItemSelectedListener)
    }

    private val menuOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
        item -> when(item.itemId)
        {
            R.id.bottom_menu_home ->
            {
                val homeFragment =
                    HomeFragment()
                openFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_menu_dates ->
            {
                val datesFragment =
                    DatesFragment()
                openFragment(datesFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_menu_account ->
            {
                val accountFragment =
                    AccountFragment()
                openFragment(accountFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_menu_settings ->
            {
                val settingsFragment =
                    SettingsFragment()
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

}
