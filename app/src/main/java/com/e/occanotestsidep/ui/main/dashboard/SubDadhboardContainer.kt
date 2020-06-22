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
                        tab.text = "Calibrate"
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







//package com.e.occanotestsidep.ui.main.dashboard
//
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.viewpager.widget.ViewPager
//import androidx.viewpager2.widget.ViewPager2
//import com.e.occanotestsidep.R
//import com.e.occanotestsidep.ui.main.alert.AlertFragment
//import com.e.occanotestsidep.ui.main.alert.ArchivedAlertFragment
//import com.e.occanotestsidep.ui.main.graph.GraphsFragment
//import com.google.android.material.tabs.TabLayout
//import com.google.android.material.tabs.TabLayoutMediator
//
//
///**
// * A simple [Fragment] subclass.
// */
//class SubDadhboardContainer : Fragment()
////    , TabLayout.OnTabSelectedListener
////    BottomNavController.OnNavigationGraphChanged,
////    BottomNavController.OnNavigationReselectedListener
//{
//    private lateinit var sabaDushCollectionAdapter: SabaDushCollectionAdapter
//    private lateinit var viewPager: ViewPager2
//    lateinit var tabLayout : TabLayout
////    lateinit var bottomNav: BottomNavigationView
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_sub_dadhboard_container, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        sabaDushCollectionAdapter = SabaDushCollectionAdapter(this)
//        viewPager = view.findViewById(R.id.pager)
//        viewPager.adapter = sabaDushCollectionAdapter
//        viewPager.isUserInputEnabled = false
//
//        tabLayout = view.findViewById(R.id.tab_layout)
//
//        TabLayoutMediator(tabLayout,
//            viewPager,
//            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
//
//                when(position){
//                    0 -> {
//                        //main dash
//                        tab.text = "Main"
//                        tab.setIcon(R.drawable.ic_report_black_24dp)
//                    }
//                    1->{
//                        //cylinders
//                        tab.text = "Cylinders"
//                        tab.setIcon(R.drawable.ic_filter_center_focus_black_24dp)
//                    }
//                    2->{
//                        //dash
//                        tab.text = "Dashboard"
//                        tab.setIcon(R.drawable.ic_dashboard_black_24dp)
//                    }
//                    3 -> {
//                        //status
//                        tab.text = "Status"
//                        tab.setIcon(R.drawable.ic_assignment_black_24dp)
//                    }
//                    4 -> {
//                        tab.text = "History"
//                        tab.setIcon(R.drawable.ic_history_black_24dp)
//                    }
//                    5 -> {
//                        tab.text = "Report"
//                        tab.setIcon(R.drawable.ic_report_problem_black_24dp)
//                    }
//                    6 -> {
//                        tab.text = "Graphs"
//                        tab.setIcon(R.drawable.ic_trending_up_black_24dp)
//                    }
//                }
//                viewPager.setCurrentItem(tab.position,true)
//
//            }).attach()
//    }


//        with(tabLayout){
//            addTab(tabLayout.newTab().setText("Main").setIcon(R.drawable.ic_report_black_24dp),0,true)
//            addTab(tabLayout.newTab().setText("Cylinders").setIcon(R.drawable.ic_filter_center_focus_black_24dp),1)
//            addTab(tabLayout.newTab().setText("Dashboard").setIcon(R.drawable.ic_dashboard_black_24dp),2)
//            addTab(tabLayout.newTab().setText("Alerts").setIcon(R.drawable.ic_assignment_black_24dp),3)
//            addTab(tabLayout.newTab().setText("Archive").setIcon(R.drawable.ic_history_black_24dp),4)
//            addTab(tabLayout.newTab().setText("Report").setIcon(R.drawable.ic_report_problem_black_24dp),5)
//            addTab(tabLayout.newTab().setText("Graphs").setIcon(R.drawable.ic_trending_up_black_24dp),6)
//        }

//        tabLayout.addOnTabSelectedListener(this)


//        bottomNav = view.findViewById(R.id.navigation)
//        bottomNav.setOnNavigationItemSelectedListener(navListener)
        //I added this if statement to keep the selected fragment when rotating the device
//        if (savedInstanceState == null)
//        {
//            if (findNavController().currentDestination?.id == R.id.subDadhboardContainer) {
//            findNavController().navigate(R.id.action_global_dashboard)}
//        }
//        TabLayoutMediator(
//            tabLayout
//            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
//
//                when(position){
//                    0 -> {
//                        //main dash
//                        tab.text = "Main"
//                        tab.setIcon(R.drawable.ic_report_black_24dp)
//                    }
//                    1->{
//                        //cylinders
//                        tab.text = "Cylinders"
//                        tab.setIcon(R.drawable.ic_filter_center_focus_black_24dp)
//                    }
//                    2->{
//                        //dash
//                        tab.text = "Dashboard"
//                        tab.setIcon(R.drawable.ic_dashboard_black_24dp)
//                    }
//                    3 -> {
//                        //status
//                        tab.text = "Status"
//                        tab.setIcon(R.drawable.ic_assignment_black_24dp)
//                    }
//                    4 -> {
//                        tab.text = "History"
//                        tab.setIcon(R.drawable.ic_history_black_24dp)
//                    }
//                    5 -> {
//                        tab.text = "Report"
//                        tab.setIcon(R.drawable.ic_report_problem_black_24dp)
//                    }
//                    6 -> {
//                        tab.text = "Graphs"
//                        tab.setIcon(R.drawable.ic_trending_up_black_24dp)
//                    }
//                }
//            }).attach()

        // Set up the ViewPager with the sections adapter.

        // Set up the ViewPager with the sections adapter.



//    }


//    private val bottomNavController by lazy(LazyThreadSafetyMode.NONE) {
//        BottomNavController(
//            requireContext(),
//            R.id.nav_graph_fragment,
//            R.id.dashboardMainFragment,
//            this)
//    }

//    override fun onGraphChange() {
//        expandAppBar()
//    }
//    override fun expandAppBar() {
////        findViewById<AppBarLayout>(R.id.app_bar).setExpanded(true)
//    }

//    override fun onReselectNavItem(
//        navController: NavController,
//        fragment: Fragment
//    ){
//        Log.d("TAG", "logInfo: onReSelectItem")
//        when(fragment){
//
//            is AlertFragment -> {
//                navController.navigate(R.id.action_global_alertFragment)
//            }
//
//            is ArchivedAlertFragment -> {
//                navController.navigate(R.id.action_global_archivedAlertFragment)
//            }
//
//            is DashFragment -> {
//                navController.navigate(R.id.action_global_dashFragment)
//            }
//
//            is DashboardMainFragment -> {
//                navController.navigate(R.id.action_global_dashboardMainFragment)
//            }
//
//            is CylindersFragment -> {
//                navController.navigate(R.id.action_global_cylindersFragment)
//            }
//
//            is CalibrationFragment -> {
//                navController.navigate(R.id.action_global_calibrationFragment)
//            }
//
//            is GraphsFragment -> {
//            navController.navigate(R.id.action_global_graphsFragment)
//        }
//
//            else -> {
//                // do nothing
//            }
//        }
//    }


//
//private val navListener: BottomNavigationView.OnNavigationItemSelectedListener =
//    BottomNavigationView.OnNavigationItemSelectedListener { item ->
//        when (item.itemId) {
//            R.id.dashboard_nav_graph -> {
//                if (findNavController().currentDestination?.id == R.id.dashboard_nav_graph) {
//                    findNavController().navigate(R.id.dashboard_nav_graph)}
//
////                findNavController().navigate(R.id.dashboard_nav_graph)
//                with(tab_layout){
//                    removeAllTabs()
//                    addTab(tabLayout.newTab().setText("Main").setIcon(R.drawable.ic_report_black_24dp),0,true)
//                    addTab(tabLayout.newTab().setText("Cylinders").setIcon(R.drawable.ic_filter_center_focus_black_24dp),1)
//                    addTab(tabLayout.newTab().setText("Dashboard").setIcon(R.drawable.ic_dashboard_black_24dp),2)
//                    addTab(tabLayout.newTab().setText("Report").setIcon(R.drawable.ic_report_problem_black_24dp),3)
//                }
//            }
//            R.id.alerts_nav_graph ->  {findNavController().navigate(R.id.alerts_nav_graph)
//                with(tab_layout){
//                    removeAllTabs()
//                    addTab(tabLayout.newTab().setText("Alerts").setIcon(R.drawable.ic_assignment_black_24dp),4)
//                    addTab(tabLayout.newTab().setText("Archive").setIcon(R.drawable.ic_history_black_24dp),5)
//
//                    addOnTabSelectedListener(this@SubDadhboardContainer)
//                }
//            }
//            R.id.graph_nav_graph -> {findNavController().navigate(R.id.graph_nav_graph)
//                with(tab_layout){
//                    removeAllTabs()
//                    addTab(tabLayout.newTab().setText("Graphs").setIcon(R.drawable.ic_trending_up_black_24dp),6)
//
//                }
//
//            }
//        }
//        true
//    }
//
//    override fun onTabReselected(tab: TabLayout.Tab?) {
//    }
//
//    override fun onTabUnselected(tab: TabLayout.Tab?) {
//
//    }
//
//    override fun onTabSelected(tab: TabLayout.Tab?) {
//
//
////        viewPager.currentItem = tab!!.position
//        var fragment: Fragment? = null
//        when(tab?.position){
//
//            0 -> {
////                findNavController().navigate(R.id.action_global_dashboardMainFragment)
//                DashboardMainFragment.newInstance(0)
//            }
//            1 -> {
////                findNavController().navigate(R.id.action_global_cylindersFragment)
//
//                CylindersFragment.newInstance(1)
//            }
//            2 -> {
////                findNavController().navigate(R.id.action_global_dashFragment)
//
//                DashFragment.newInstance(2)
//            }
//            3 -> {
////                findNavController().navigate(R.id.action_global_calibrationFragment)
//
//                AlertFragment.newInstance(3)
//            }
//            4 -> {
////                findNavController().navigate(R.id.action_global_alertFragment)
//
//                ArchivedAlertFragment.newInstance(4)
//            }
//            5 -> {
////                findNavController().navigate(R.id.action_global_archivedAlertFragment)
//
//                CalibrationFragment()
//            }
//            6 -> {
////                findNavController().navigate(R.id.action_global_graphsFragment)
//
//                GraphsFragment()
//            }
//            else -> {
////                GraphsFragment()
//            }
//
//        }
//    }







