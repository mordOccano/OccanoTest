package com.e.occanotestsidep.ui.main.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.e.occanotestsidep.ui.main.status.StatusArchiveFragment
import com.e.occanotestsidep.ui.main.status.StatusFragment

class SabaDushCollectionAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 7
    override fun createFragment(position: Int): Fragment {

        return when(position){

            0 -> DashboardMainFragment()
            1 -> CylindersFragment()
            2 -> DashFragment()
            3 -> StatusFragment.newInstance("StatusFragment","1")
            4 -> StatusArchiveFragment()
            5 -> CalibrationFragment()
            6 -> GraphsFragment()

            else -> CylindersFragment()

            /*
            TODO(
             0. delete the btns that pass between fragments
            ")
             */
        }
    }
}