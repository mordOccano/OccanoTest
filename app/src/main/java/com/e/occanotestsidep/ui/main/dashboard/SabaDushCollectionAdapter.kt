package com.e.occanotestsidep.ui.main.dashboard

import android.util.Log
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
                Log.e("SabaDushCollectionAdapter","DashboardMainFragment" )

                DashboardMainFragment()
//                    .newInstance(0)
            }
            1 -> {
                Log.e("SabaDushCollectionAdapter","CylindersFragment" )

                CylindersFragment
                    .newInstance(1)
            }
            2 -> {
                Log.e("SabaDushCollectionAdapter","DashFragment" )
                DashFragment.newInstance(2)
            }
            3 -> {
                Log.e("SabaDushCollectionAdapter","AlertFragment" )

                AlertFragment.newInstance(3)
            }
            4 -> {
                Log.e("SabaDushCollectionAdapter","ArchivedAlertFragment" )

                ArchivedAlertFragment.newInstance(4)
            }
            5 -> {
                Log.e("SabaDushCollectionAdapter","CalibrationFragment" )

                CalibrationFragment()
            }
            6 -> {
                Log.e("SabaDushCollectionAdapter","GraphsFragment" )

                GraphsFragment()
            }
            else -> {
                GraphsFragment()
            }

        }
        return fragment
    }
}