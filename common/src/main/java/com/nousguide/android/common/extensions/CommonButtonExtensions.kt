package com.nousguide.android.common.extensions

import android.graphics.Rect
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.nousguide.android.common.R
import com.nousguide.android.common.commom.CommonButton
import com.nousguide.android.common.commom.CommonImageView

fun CommonButton.applySkeleton(
    isSkeleton: Boolean,
    text: String,
    @ColorRes color: Int = R.color.transparent,
    @ColorRes skeletonColor: Int = R.color.greyLight
) {
    if (isSkeleton) {
        this.text = ""
        bgColor(skeletonColor)
    } else {
        this.text = text
        bgColor(color)
    }
}

fun CommonButton.textWidth(): Int {
    val rect = Rect()
    this.paint.getTextBounds(this.text.toString().toCharArray(), 0, text.length, rect)
    return rect.width()
}

fun CommonButton.applyUnderline(offset: Int = 5, @ColorRes color: Int = R.color.blueDark) {
    underlineView?.let {
        it.isVisible = true
        return
    }
    val buttonId = id
    val parentView = this.parent as ViewGroup
    val underlineWidth = this.textWidth() + 2 * offset
    underlineView = CommonImageView(context).apply {
        layoutParams = ConstraintLayout.LayoutParams(underlineWidth, 1.px).apply {
            startToStart = buttonId
            endToEnd = buttonId
            topToBottom = buttonId
        }
        bgColor(color)
    }
    parentView.addView(underlineView)
}

fun CommonButton.removeUnderline() {
    underlineView?.isVisible = false
}
