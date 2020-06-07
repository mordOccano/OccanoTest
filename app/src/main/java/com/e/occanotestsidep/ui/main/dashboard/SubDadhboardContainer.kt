package com.e.occanotestsidep.ui.main.dashboard


import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

import com.e.occanotestsidep.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * A simple [Fragment] subclass.
 */
class SubDadhboardContainer : Fragment() {

    private lateinit var sabaDushCollectionAdapter: SabaDushCollectionAdapter
    private lateinit var viewPager: ViewPager2


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sub_dadhboard_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sabaDushCollectionAdapter = SabaDushCollectionAdapter(this)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = sabaDushCollectionAdapter
        viewPager.isUserInputEnabled = false
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)

        TabLayoutMediator(tabLayout,
            viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->

                when(position){
                    0 -> {
                        //main dash
                        tab.text = "Main"
                        tab.setIcon(R.drawable.ic_report_black_24dp)
                    }
                    1->{
                        //cylinders
                        tab.text = "Cylinders"
                        tab.setIcon(R.drawable.ic_filter_center_focus_black_24dp)
                    }
                    2->{
                        //dash
                        tab.text = "Dashboard"
                        tab.setIcon(R.drawable.ic_dashboard_black_24dp)
                    }
                    3 -> {
                        //status
                        tab.text = "Status"
                        tab.setIcon(R.drawable.ic_assignment_black_24dp)
                    }
                    4 -> {
                        tab.text = "History"
                        tab.setIcon(R.drawable.ic_history_black_24dp)
                    }
                    5 -> {
                        tab.text = "Report"
                        tab.setIcon(R.drawable.ic_report_problem_black_24dp)
                    }
                    6 -> {
                        tab.text = "Graphs"
                        tab.setIcon(R.drawable.ic_trending_up_black_24dp)
                    }
                }
            }).attach()
    }

    }





