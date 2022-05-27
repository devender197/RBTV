package com.nousguide.android.customapp.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.nousguide.android.customapp.databinding.ActivityMainBinding
import com.nousguide.android.customapp.manager.NavigationManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        NavigationManager.shared.launch(this, false)
    }

    fun start() {
        val activity = this
        //permissionsManager.register(activity)
        lifecycleScope.launch {
            //val isLoggedIn = cacheManager.isLoggedIn()
            NavigationManager.shared.launch(activity, false)
            //if (!isLoggedIn) backendManager.updateBeforeLogout()
        }
    }

    fun setTabVisibility(shouldVisible: Boolean) {
        viewBinding.tabBar.isVisible = shouldVisible
    }
}