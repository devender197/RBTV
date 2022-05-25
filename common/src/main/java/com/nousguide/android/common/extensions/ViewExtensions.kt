package com.nousguide.android.common.extensions

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.nousguide.android.common.R
import com.nousguide.android.common.commom.CommonActionListener
import com.nousguide.android.common.commom.CommonImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

inline var View.isHidden: Boolean
    get() = visibility == View.GONE
    set(value) {
        visibility = if (value) View.GONE else View.VISIBLE
    }

inline val View.lifecycleScope: CoroutineScope
    get() = findViewTreeLifecycleOwner()?.lifecycleScope ?: MainScope()

fun View.margin(
    left: Float? = null,
    top: Float? = null,
    right: Float? = null,
    bottom: Float? = null
) {
    layoutParams<ViewGroup.MarginLayoutParams> {
        left?.let { leftMargin = dpToPx(it) }
        top?.let { topMargin = dpToPx(it) }
        right?.let { rightMargin = dpToPx(it) }
        bottom?.let { bottomMargin = dpToPx(it) }
    }
}

fun View.containsOnScreen(x: Float, y: Float): Boolean {
    return Rect().also { this.getGlobalVisibleRect(it) }.contains(x.toInt(), y.toInt())
}

fun View.color(@ColorRes color: Int): Int = context.color(color)

inline fun View.action(timeIntervalMillis: Long = 500, crossinline action: (view: View) -> Unit) {
    val actionListener = CommonActionListener(timeIntervalMillis, lifecycleScope) { action(it) }
    setOnClickListener(actionListener)
}

inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(block: T.() -> Unit) {
    updateLayoutParams(block)
}

fun RecyclerView.LayoutManager.findVisibleView(): View? =
    findViewByPosition(findVisibleViewPosition())


fun RecyclerView.LayoutManager.findVisibleViewPosition(): Int {
    return if (this is LinearLayoutManager) {
        val position = findFirstCompletelyVisibleItemPosition()
        if (position == -1) 0 else position
    } else 0
}

fun View.onViewFocus(isViewInFocus: Boolean) {
    findViewById<StyledPlayerView>(R.id.videoView)?.apply {
        player?.apply {
            if (isViewInFocus) {
                prepare()
                play()
            } else if (isPlaying) pause()
        }
        isVisible = isViewInFocus
    }
    findViewById<CommonImageView>(R.id.imgPreview)?.isVisible = !isViewInFocus
}




