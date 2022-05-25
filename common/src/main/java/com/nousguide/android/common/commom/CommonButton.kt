package com.nousguide.android.common.commom

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.nousguide.android.common.R
import com.nousguide.android.common.commom.shapeable.CommonShapeable
import com.nousguide.android.common.commom.shapeable.CommonShapeableHandler


class CommonButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr), CommonShapeable<CommonButton> {

    var underlineView: View? = null
    var icon: Drawable? = null
    var iconSize: Float = 0f

    override val shapeableHandler = CommonShapeableHandler { this }

    init {
        shapeableHandler.init(attrs)
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.CommonButton, 0, 0).apply {
            try {
                icon = getDrawable(R.styleable.CommonButton_icon)
                iconSize = getDimension(R.styleable.CommonButton_iconSize, 0f)
                setIcon()
            } finally {
                recycle()
            }
        }
    }

    @Deprecated(
        message = "This method is deprecated",
        replaceWith = ReplaceWith(
            expression = "view.action() {}",
            imports = ["com.nousguide.android.common.extensions"]
        )
    )
    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
    }

    fun text(text: Any) {
        when (text) {
            is String -> this.text = text
            is SpannableString -> this.text = text
            is SpannableStringBuilder -> this.text = text
            else -> this.text = ""
        }
    }

    fun textColor(color: Int) {
        this.setTextColor(ContextCompat.getColor(this.context, color))
    }

    private fun setIcon() {

        icon?.let { it ->
            val size = if(iconSize.toInt() == 0 ) it.intrinsicWidth else iconSize.toInt()
            it.setBounds(0, 0, size, size)
            val buttonLabel: Spannable = SpannableString("   $text")
            buttonLabel.setSpan(
                ImageSpan(it, ImageSpan.ALIGN_BASELINE), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            text(buttonLabel)
        }
    }
}
