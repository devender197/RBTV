package com.nousguide.android.common.extensions

import android.graphics.Rect
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.nousguide.android.common.R
import com.nousguide.android.common.commom.CommonImageView
import com.nousguide.android.common.commom.CommonLabel

fun CommonLabel.textWidth(): Int {
    val rect = Rect()
    this.paint.getTextBounds(this.text.toString().toCharArray(), 0, text.length, rect)
    return rect.width()
}

fun CommonLabel.applyUnderline(offset: Int = 5, @ColorRes color: Int = R.color.blueDark) {
    underlineView?.let {
        it.isVisible = true
        return
    }
    val labelId = id
    val parentView = this.parent as ViewGroup
    val underlineWidth = this.textWidth() + 2 * offset
    underlineView = CommonImageView(context).apply {
        layoutParams = ConstraintLayout.LayoutParams(underlineWidth, 1.px).apply {
            startToStart = labelId
            endToEnd = labelId
            topToBottom = labelId
        }
        bgColor(color)
    }
    parentView.addView(underlineView)
}

fun CommonLabel.removeUnderline() {
    underlineView?.isVisible = false
}
