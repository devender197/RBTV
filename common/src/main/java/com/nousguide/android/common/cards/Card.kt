package com.nousguide.android.common.cards

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import com.google.android.exoplayer2.ExoPlayer
import com.nousguide.android.common.R

abstract class Card(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes), View.OnFocusChangeListener {

    var exoplayer: ExoPlayer = ExoPlayer.Builder(context).build()

    abstract fun onViewFocused(isFocused: Boolean)

    init {
        isFocusable = true
        isFocusableInTouchMode = true
        onFocusChangeListener = this
    }

    private val scaleIn: Animation = AnimationUtils.loadAnimation(context, R.anim.scale_in_tv).apply {
        fillAfter = true
    }
    private val scaleOut: Animation = AnimationUtils.loadAnimation(context, R.anim.scale_out_tv).apply {
        fillAfter = true
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (hasFocus) {
            startAnimation(scaleIn)
        } else {
            startAnimation(scaleOut)
        }
        onViewFocused(hasFocus)
    }
}