package com.nousguide.android.customapp.screen.login

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.nousguide.android.common.base.BaseFragment
import com.nousguide.android.common.base.viewBinding
import com.nousguide.android.common.extensions.action
import com.nousguide.android.common.manager.LogManager
import com.nousguide.android.customapp.R
import com.nousguide.android.customapp.databinding.FragmentLoginBinding
import com.nousguide.android.customapp.manager.NavigationManager
import com.nousguide.android.rbtv.screen.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.nousguide.android.common.R as commonR

@AndroidEntryPoint
class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels()
    private val viewBinding by viewBinding(FragmentLoginBinding::bind)
    lateinit var googleActivityResultLauncher: ActivityResultLauncher<Intent>
    private val loginMap: MutableMap<String, String> = mutableMapOf()

    override fun bind() {

        viewBinding.apply {
            btnLoginEmail.action { viewModel.onEmailClick() }
            btnLoginFb.action { viewModel.onFbClick() }
            btnLoginGoogle.action { viewModel.onGoogleClick() }
            btnClose.action { viewModel.onCloseClick() }
            loginWebView.apply {
                settings.javaScriptEnabled = true
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        request?.url?.toString()?.let { urlString ->
                            if (urlString.contains("redbulltv://login_success")) {
                                Uri.parse(urlString).let { uri ->
                                    viewModel.onSuccessLogin(uri)
                                }
                            }
                        }
                        return super.shouldOverrideUrlLoading(view, request)
                    }

                }
            }
        }

        viewModel.apply {
            eventLoadVideo.observe(viewLifecycleOwner, handleLoadVideo)
            eventLoadView.observe(viewLifecycleOwner, handleLoadView)
            eventLoadHud.observe(viewLifecycleOwner, handleLoadHud)
            eventHandleCloseClick.observe(viewLifecycleOwner, handleCloseClick)
            eventLoadDescription.observe(viewLifecycleOwner, handleLoadDescription)
            eventOnSuccessLogin.observe(viewLifecycleOwner, handleSuccessLogin)

            eventEmailClick.observe(viewLifecycleOwner, handleEmailClick)
            eventFbClick.observe(viewLifecycleOwner, handleFbClick)
            eventGoogleClick.observe(viewLifecycleOwner, handleGoogleClick)

            isLandScape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
            createOrSignin = true
            loadView(requireContext())
        }
    }

    override fun onBackPressed() {
        viewBinding.apply {
            if (!loginGroup.isVisible) {
                loginGroup.isVisible = true
                webViewGroup.isVisible = false
                loginWebView.stopLoading()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        googleActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    LogManager.log(it.data?.extras.toString())
                }
            }
    }

    private val handleCloseClick = Observer<Void> {
        onBackPressed()
    }

    private val handleLoadView = Observer<Void> {
        val isLandScape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        viewBinding.title.text =
            resources.getString(if (isLandScape) commonR.string.account_cta else commonR.string.account_cta)
    }

    private val handleLoadDescription = Observer<Int> { descTextRef ->
        viewBinding.description.apply { text = context.getString(descTextRef) }
    }

    private val handleEmailClick = Observer<Void> {
        viewBinding.apply {
            loginGroup.isVisible = false
            webViewGroup.isVisible = true
            loginWebView.apply {
                loadUrl("https://qa-account.redbull.com/validate?language=en&country=US&application=lhd3xxj8w")
            }
        }
    }

    private val handleFbClick = Observer<Void> {}

    private val handleLoadHud = Observer<Boolean> { shouldShow ->
        viewBinding.hudView.isVisible = shouldShow
    }

    private val handleGoogleClick = Observer<Intent> { signInIntent ->
        viewModel.showHud(true)
        googleActivityResultLauncher.launch(signInIntent)
        viewModel.showHud(false)
    }

    private val handleLoadVideo = Observer<String> { videoUrl ->
        viewBinding.videoView.apply {
            setVideoURI(Uri.parse(videoUrl))
            setOnPreparedListener {
                it.isLooping = true
                it.start()
            }
        }
    }

    private val handleSuccessLogin = Observer<Void> {
        onBackPressed()
        NavigationManager.shared.present(this, R.id.home_fragment, null)
    }
}