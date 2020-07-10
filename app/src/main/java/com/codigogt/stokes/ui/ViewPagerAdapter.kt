package com.codigogt.stokes.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.codigogt.stokes.ui.home.AccountDObjectFragment
import com.codigogt.stokes.ui.home.AccountIObjectFragment



class ViewPagerAdapter(fa: Fragment) : FragmentStateAdapter(fa) {

    companion object {
        private const val ARG_OBJECT = "object"
    }

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position + 1) {
            1 -> {
                AccountIObjectFragment()
            }
            2 -> {
                AccountDObjectFragment()
            }
            else -> {
                AccountDObjectFragment()
            }
        }
    }
}