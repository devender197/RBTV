package com.nousguide.android.common.commom

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatImageButton

class CommonImageButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageButton(context, attrs, defStyleAttr) {

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
}
