package com.nousguide.android.common.commom

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.withStyledAttributes
import com.nousguide.android.common.R
import com.nousguide.android.common.commom.shapeable.CommonShapeable
import com.nousguide.android.common.commom.shapeable.CommonShapeableHandler

class CommonStateButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr), CommonShapeable<CommonStateButton> {

    private var checkedStateDrawable: Int = 0
    private var plainStateDrawable: Int = 0
    var isChecked: Boolean = false
        set(value) {
            field = value
            setButtonDrawable()
        }

    override val shapeableHandler = CommonShapeableHandler { this }

    init {
        shapeableHandler.init(attrs)

        context.withStyledAttributes(attrs, R.styleable.CommonStateButton) {
            isChecked = getBoolean(R.styleable.CommonStateButton_isChecked, false)
            checkedStateDrawable = getResourceId(R.styleable.CommonStateButton_checkedState, 0)
            plainStateDrawable = getResourceId(R.styleable.CommonStateButton_plainState, 0)
            setButtonDrawable()
        }
    }

    private fun setButtonDrawable() {
        setImageResource(if (isChecked) checkedStateDrawable else plainStateDrawable)
    }

    fun image(drawable: Int, state: Int) {
        when (state) {
            ACTIVE -> checkedStateDrawable = drawable
            PLAIN -> plainStateDrawable = drawable
        }
        setButtonDrawable()
    }

    fun image(drawable: Int) {
        checkedStateDrawable = drawable
        plainStateDrawable = drawable
    }

    companion object {
        const val ACTIVE = 1
        const val PLAIN = 0
    }
}
