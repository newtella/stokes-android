package com.codigogt.stokes.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.codigogt.stokes.R
import com.codigogt.stokes.ui.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_account_header.*

class AccountFragment : Fragment() {

    private lateinit var demoCollectionAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater!!.inflate(R.layout.fragment_account_header, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        demoCollectionAdapter = ViewPagerAdapter(this)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = demoCollectionAdapter

        val tabLayoutMediator = TabLayoutMediator(tab_layout,viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position + 1) {

                    1 -> {
                        tab.text = "Informacion"
//                        tab.setIcon(R.drawable.ic_account)
                    }
                    2 -> {
                        tab.text = "Citas"
//                        tab.setIcon(R.drawable.ic_book)
                    }
                }
            })
        tabLayoutMediator.attach()
    }
}