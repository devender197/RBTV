package com.nousguide.android.common.cards

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import com.nousguide.android.common.commom.CommonImageView
import com.nousguide.android.common.extensions.loadThumbnail
import com.nousguide.android.common.R as commonR

class ChannelCard(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : Card(context, attrs, defStyleAttr, defStyleRes) {

    private var title: TextView
    private var previewImage: ImageView

    init {
        inflate(context, commonR.layout.channel_item_layout, this)

        title = findViewById(commonR.id.tvTitle)
        previewImage = findViewById(commonR.id.imgChannel)

        val typedArray = context.obtainStyledAttributes(attrs, commonR.styleable.FeatureCard)

        if (typedArray.hasValue(commonR.styleable.FeatureCard_title)) {
            title.text =
                typedArray.getString(commonR.styleable.FeatureCard_title);
        }

        if (typedArray.hasValue(commonR.styleable.FeatureCard_imageUrl)) {
            typedArray.getString(commonR.styleable.FeatureCard_imageUrl)
                ?.let { url ->
                    previewImage.loadThumbnail(url)
                }
        }
    }

    fun setImageUrl(imageUrl: String) {
        previewImage.loadThumbnail(imageUrl)
    }

    fun setTitle(text: String) {
        title.text = text
    }

    override fun onViewFocused(isFocused: Boolean) {}
}