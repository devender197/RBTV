package com.nousguide.android.common.manager

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.nousguide.android.common.R

abstract class CommomNavigationManager {

    open var isGuestMode: Boolean = false
    abstract var navController: NavController

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

    open fun launch(activity: FragmentActivity, isLoggedIn: Boolean) {
        val navController = navController
        setUpGraph(navController, isLoggedIn)
    }

    abstract fun setUpGraph(navController: NavController, isLoggedIn: Boolean)

    fun params(fragment: Fragment): Any? {
        var extraP: Any? = fragment.arguments?.getParcelable(extraParcelable)
        extraP?.let { safeP -> return safeP }
        extraP = fragment.arguments?.get(extraAny)
        extraP?.let { safeA -> return safeA }
        return null
    }

    fun present(source: Fragment, @IdRes fragment: Int, params: Any? = null) {
        val bundle = when (params) {
            is Parcelable -> bundleOf(extraParcelable to params)
            else -> bundleOf(extraAny to params)
        }
        show(source, fragment, bundle)
    }

    private fun show(fragment: Fragment, @IdRes destination: Int, bundle: Bundle? = null) {
        navController.navigate(destination, bundle)
    }


    fun dismiss(fragment: Fragment) {
        navController.navigateUp()
    }

    private fun popRoot(navController: NavController) {
        val startDestination = navController.graph.startDestinationId
        navController.popBackStack(startDestination, false)
    }

    fun currentFragment(activity: FragmentActivity): Int? {
        return navController.currentDestination?.id
    }

    fun previousFragment(activity: FragmentActivity): Int? {
        return navController.previousBackStackEntry?.destination?.id
    }

}
