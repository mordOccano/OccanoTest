package com.e.occanotestsidep.ui.main.dashboard

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.e.occanotestsidep.ui.main.status.StatusArchiveFragment

class SabaDushCollectionAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 7
    override fun createFragment(position: Int): Fragment {

        return when(position){
            0 -> DashboardMainFragment()
            1 -> CylindersFragment()
            2 -> DashFragment()
            3 -> AlertFragment()
            4 -> StatusArchiveFragment()
            5 -> CalibrationFragment()
            6 -> GraphsFragment()
            else -> GraphsFragment()

        }
    }
}