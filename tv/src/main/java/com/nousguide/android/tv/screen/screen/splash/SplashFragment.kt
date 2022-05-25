package com.nousguide.android.tv.screen.screen.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.media.MediaPlayer
import android.view.View
import androidx.core.view.animation.PathInterpolatorCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.nousguide.android.common.base.BaseFragment
import com.nousguide.android.common.base.viewBinding
import com.nousguide.android.common.manager.LogManager
import com.nousguide.android.common.onetrust.ConsentManager
import com.nousguide.android.tv.R
import com.nousguide.android.tv.databinding.FragmentSplashBinding
import com.nousguide.android.tv.screen.manager.NavigationManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.nousguide.android.common.R as commonR

@AndroidEntryPoint
class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    private val viewModel: SplashViewModel by viewModels()
    private val viewBinding by viewBinding(FragmentSplashBinding::bind)
    private var animateLogo = true

    lateinit var consentManager: ConsentManager

    override fun bind() {

        viewModel.apply {
            eventLoadView.observe(viewLifecycleOwner, handleLoadView)
            eventShowLogin.observe(viewLifecycleOwner, handleShowLogin)
            eventShowHome.observe(viewLifecycleOwner, handleShowHome)
            eventLogoDisplayFinish.observe(viewLifecycleOwner, handleLogoDisplayFinish)
        }
        consentManager //=  (requireActivity().application as MainApplication).consentManager
        viewModel.doLoadView()
    }

    private val handleLoadView = Observer<Void> {
        LogManager.log("handleLoadView")
        showLogo(animateLogo)
    }

    private val handleShowLogin = Observer<Void> {
        LogManager.log("handleShowLogin")
        NavigationManager.shared.present(this, R.id.login_fragment, null)
    }

    private val handleShowHome = Observer<Void> {
        LogManager.log("handleShowHome")
        NavigationManager.shared.present(this, R.id.home_fragment, null)
    }

    private val handleLogoDisplayFinish = Observer<Void> {
        LogManager.log("handleLogoDisplayFinish")
        animateLogo = false
        showConsentBanner()
    }

    private fun showConsentBanner() {
        /*if (consentManager.shouldShowConsentBanner()) {
            consentManager.showConsentBannerUI(requireActivity())
            consentManager.getConsentEvents().observe(viewLifecycleOwner, handleConsentViewState)
        } else {
            viewModel.handleLoginFlow()
        }*/
    }

    private val handleConsentViewState  = Observer<ConsentManager.ConsentViewState> { state ->
        LogManager.log("handleConsentViewState $state")
    }

    private fun showLogo(withAnimation: Boolean) {
        val logo = viewBinding.logo
        val logoAnimator = AnimatorSet()
        if (withAnimation) {
            logo.scaleX = 0f
            logo.scaleY = 0f
            val scaleXAnim = ObjectAnimator.ofFloat(logo, "scaleX", 0f, 1f)
            val scaleYAnim = ObjectAnimator.ofFloat(logo, "scaleY", 0f, 1f)
            logoAnimator.play(scaleXAnim).with(scaleYAnim)
            logoAnimator.interpolator = PathInterpolatorCompat.create(0f, 1.13f, .0f, .90f)
            logoAnimator.duration = 200
            logoAnimator.startDelay = 100
            logoAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    viewModel.onLogoDisplayFinished()
                }
            })
            logoAnimator.start()
            logo.visibility = View.VISIBLE
        } else {
            logoAnimator.cancel()
            logo.scaleX = 1f
            logo.scaleY = 1f
            logo.visibility = View.VISIBLE
            viewModel.onLogoDisplayFinished()
        }
    }

    fun showAPIError() {
        viewBinding.apply {
            normalContainer.visibility = View.GONE
            apiErrorContainer.visibility = View.VISIBLE
            apiErrorVideoOverlay.visibility = View.VISIBLE
            //splashErrorResourceLoader.loadSplashErrorVideo(apiErrorVideoView)
            apiErrorVideoView.setOnPreparedListener { mp: MediaPlayer ->
                apiErrorVideoView.setOnPreparedListener(null)
                mp.isLooping = true
            }
            // if there's an error playing the error video, handle the error ourselves(by doing nothing) rather than Android's alert dialog
            apiErrorVideoView.setOnErrorListener { _, _, _ -> true }
            apiErrorVideoView.visibility = View.VISIBLE
            apiErrorVideoView.start()
        }
    }

    fun hideError() {
        viewBinding.apply {
            normalContainer.visibility = View.VISIBLE
            apiErrorContainer.visibility = View.GONE
            apiErrorVideoOverlay.visibility = View.GONE
            apiErrorVideoView.stopPlayback()
            apiErrorVideoView.visibility = View.GONE
            title.visibility = View.GONE
            message.visibility = View.GONE
        }
    }

    fun showNoNetworkError() {
        viewBinding.apply {
            normalContainer.visibility = View.VISIBLE
            apiErrorContainer.visibility = View.GONE
            title.visibility = View.VISIBLE
            message.visibility = View.VISIBLE
            title.setText(commonR.string.offline)
            message.setText(commonR.string.error_offline)
        }
    }

}
