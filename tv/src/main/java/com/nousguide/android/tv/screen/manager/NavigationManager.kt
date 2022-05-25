package com.nousguide.android.tv.screen.manager

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationBarView
import com.nousguide.android.common.manager.CommomNavigationManager
import com.nousguide.android.tv.R

class NavigationManager() : CommomNavigationManager() {


    override var isGuestMode: Boolean = false
    override lateinit var navController: NavController

    private val extraParcelable = "EXTRA_PARCELABLE"
    private val extraAny = "EXTRA_ANY"

    private val listTab = listOf(
        R.id.home_fragment,
        R.id.browse_fragment,
        R.id.channels_fragment,
        R.id.events_fragment,
        R.id.account_fragment
    )

    private val listIgnoreTab = listOf<Int>()

    override fun launch(activity: FragmentActivity, isLoggedIn: Boolean) {
        navController = getNavController(activity)
        setUpGraph(navController, isLoggedIn)
        //setUpTabs(navController, activity)
    }

    fun notLoggedInLaunchSequence(fragment: Fragment) {
        launchSequence(fragment.requireActivity(), login = false)
    }

    fun loggedInLaunchSequence(fragment: Fragment) {
        launchSequence(fragment.requireActivity())
    }

    fun guestLaunchSequence(fragment: Fragment) {
        launchSequence(fragment.requireActivity(), guest = true)
    }

    override fun setUpGraph(navController: NavController, isLoggedIn: Boolean) {
        val graphInflater = navController.navInflater
        val graph = graphInflater.inflate(R.navigation.nav_main)
        graph.setStartDestination(R.id.home_fragment)
        //val args = if (isLoggedIn) bundleOf(extraAny to LoadingViewState.LAUNCH) else null
        navController.setGraph(graph, null)
    }

    /*private fun setUpTabs(navController: NavController, activity: FragmentActivity) {
        getTabBar(activity).apply {
            setupWithNavController(navController)
            setOnItemSelectedListener(handleItemListener(activity, navController))
            setOnItemReselectedListener { }
        }
        navController.addOnDestinationChangedListener(handleDestinationChangeListener(activity))
    }*/

    private fun handleItemListener(activity: FragmentActivity, navController: NavController) =
        NavigationBarView.OnItemSelectedListener { menuItem ->
            val menuItemId = menuItem.itemId
            NavigationUI.onNavDestinationSelected(menuItem, navController)
        }

    private fun handleDestinationChangeListener(activity: FragmentActivity) =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            if (!listIgnoreTab.contains(destination.id)) {
                //getTabBar(activity).isVisible = listTab.contains(destination.id)
            }
        }


    fun loadTab(activity: FragmentActivity, show: Boolean) {
        //getTabBar(activity).isVisible = show
    }

    private fun launchSequence(
        activity: FragmentActivity,
        login: Boolean = true,
        guest: Boolean = false
    ) {
        isGuestMode = guest
        val navController = getNavController(activity)
        val start = if (login) R.id.home_fragment else R.id.splash_fragment
        val graph = navController.graph
        graph.setStartDestination(start)
        navController.graph = graph
    }

    private fun show(fragment: Fragment, @IdRes destination: Int, bundle: Bundle? = null) {
        getNavController(fragment.requireActivity()).navigate(destination, bundle)
    }

    private fun getNavController(activity: FragmentActivity): NavController {
        val navHostFragment = activity
            .supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }


    private fun popRoot(navController: NavController) {
        val startDestination = navController.graph.startDestinationId
        navController.popBackStack(startDestination, false)
    }

    /*private fun getTabBar(activity: FragmentActivity): BottomNavigationView {
        return activity.findViewById(R.id.tabBar)
    }*/

    companion object {

        val shared: NavigationManager = getInstance()

        @Volatile
        private var instance: NavigationManager? = null

        private fun init(): NavigationManager = instance ?: synchronized(this) {
            instance ?: NavigationManager().also {
                instance = it
            }
        }

        private fun getInstance(): NavigationManager =
            instance ?: init()
    }
}
