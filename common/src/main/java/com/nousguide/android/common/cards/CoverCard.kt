package com.nousguide.android.common.cards

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import com.nousguide.android.common.extensions.loadThumbnail
import com.nousguide.android.common.R as commonR

class CoverCard(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : Card(context, attrs, defStyleAttr, defStyleRes) {

    private var previewImage: ImageView

    init {
        inflate(context, commonR.layout.cover_item_layout, this)

        previewImage = findViewById(commonR.id.imgCover)


        val typedArray = context.obtainStyledAttributes(attrs, commonR.styleable.FeatureCard)

        if (typedArray.hasValue(commonR.styleable.FeatureCard_imageUrl)) {
            typedArray.getString(commonR.styleable.FeatureCard_imageUrl)?.let { url ->
                previewImage.loadThumbnail(url)
            }
        }
    }

    fun setImageUrl(imageUrl: String) {
        previewImage.loadThumbnail(imageUrl)
    }

    override fun onViewFocused(isFocused: Boolean) {}

}