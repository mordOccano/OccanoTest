package com.e.occanotestsidep.ui.main.dashboard

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.e.occanotestsidep.ui.main.alert.AlertFragment
import com.e.occanotestsidep.ui.main.alert.ArchivedAlertFragment
import com.e.occanotestsidep.ui.main.graph.GraphsFragment

class SabaDushCollectionAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 7
    override fun createFragment(position: Int): Fragment {

        var fragment: Fragment? = null
        fragment = when(position){

            0 -> {
                DashboardMainFragment()
//                    .newInstance(0)
            }
            1 -> {
                CylindersFragment()
//                    .newInstance(1)
            }
            2 -> {
                DashFragment()
//                    .newInstance(2)
            }
            3 -> {
                AlertFragment()
//                    .newInstance(3)
            }
            4 -> {
                ArchivedAlertFragment()
//                    .newInstance(4)
            }
            5 -> {
                CalibrationFragment()
            }
            6 -> {
                GraphsFragment()
            }
            else -> {
                GraphsFragment()
            }

        }
        return fragment
    }
}