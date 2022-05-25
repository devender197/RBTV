package com.nousguide.android.common.commom

import android.content.Context
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isInvisible
import com.nousguide.android.common.R

class CommonLabel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var skeletonView: View? = null
    var underlineView: View? = null

    fun text(text: Any) {
        when (text) {
            is String -> this.text = text
            is SpannableString -> this.text = text
            is SpannableStringBuilder -> this.text = text
            else -> this.text = ""
        }
    }

    fun textColor(@ColorRes color: Int) {
        this.setTextColor(ContextCompat.getColor(this.context, color))
    }

    fun font(font: Int) {
        this.typeface = ResourcesCompat.getFont(this.context, font)
    }

    fun makeSkeleton(
        width: Int,
        height: Int,
        gravity: Int = 0,
        @ColorRes color: Int = R.color.greyDark
    ) {
        if (skeletonView == null) {
            val labelId = id
            skeletonView = CommonImageView(context).apply {
                layoutParams = ConstraintLayout.LayoutParams(width, height).apply {
                    startToStart = labelId
                    topToTop = labelId
                    bottomToBottom = labelId
                    if(gravity == Gravity.CENTER) endToEnd = labelId
                }
                bgColor(color)
                corners(height / 2)
            }
            (parent as ViewGroup).addView(skeletonView)
            skeletonView?.isInvisible = true
        }
    }

    fun applySkeleton(isSkeleton: Boolean, text: CharSequence) {
        skeletonView?.isInvisible = !isSkeleton
        isInvisible = isSkeleton
        this.text = if (isSkeleton) "" else text
    }

    private fun removeSkeleton() {
        skeletonView?.let { (parent as ViewGroup).removeView(it) }
    }
}
