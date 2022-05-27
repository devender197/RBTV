package com.nousguide.android.common.commom

import android.content.Context
import android.graphics.Outline
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import com.nousguide.android.common.R


open class RoundedFrameLayout(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var roundedSide: RoundedSide = RoundedSide.ALL
        set(value) {
            field = value
            invalidateOutline()
        }

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs, R.styleable.RoundedFrameLayout, 0, 0
        )
        roundedSide = RoundedSide.values()[
                typedArray.getInteger(R.styleable.RoundedFrameLayout_rounded_side, RoundedSide.ALL.ordinal)
        ]
        typedArray.recycle()

        outlineProvider = object : ViewOutlineProvider() {

            private val cornerRadius = resources.getDimensionPixelSize(R.dimen.corner_radius)

            override fun getOutline(view: View?, outline: Outline?) {
                val width = view?.width ?: 0
                val height = view?.height ?: 0

                when (roundedSide) {
                    RoundedSide.TOP -> outline?.setRoundRect(
                        0,
                        0,
                        width,
                        height + cornerRadius,
                        cornerRadius.toFloat()
                    )
                    RoundedSide.BOTTOM -> outline?.setRoundRect(
                        0,
                        cornerRadius,
                        width,
                        height,
                        cornerRadius.toFloat()
                    )
                    RoundedSide.LEFT -> outline?.setRoundRect(
                        0,
                        0,
                        width + cornerRadius,
                        height,
                        cornerRadius.toFloat()
                    )
                    RoundedSide.RIGHT -> outline?.setRoundRect(
                        cornerRadius,
                        0,
                        width,
                        height,
                        cornerRadius.toFloat()
                    )
                    RoundedSide.NONE -> outline?.setRoundRect(0, 0, width, height, 0f)
                    else -> outline?.setRoundRect(0, 0, width, height, cornerRadius.toFloat())
                }
            }
        }
        clipToOutline = true
    }

    enum class RoundedSide {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
        ALL,
        NONE
    }
}
