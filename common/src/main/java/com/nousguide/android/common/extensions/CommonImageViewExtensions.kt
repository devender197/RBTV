package com.nousguide.android.common.extensions

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.nousguide.android.common.R
import com.nousguide.android.common.commom.CommonImageView
import com.nousguide.android.common.manager.LogManager
import timber.log.Timber


fun ImageView.loadBitmap(
    imageUrl: String,
    listener: ((Bitmap)->Unit)? = null
) {
    Glide.with(context)
        .asBitmap()
        .load(imageUrl)
        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(R.drawable.ic_nodata)
        .fallback(R.drawable.ic_nodata)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                Timber.d("onResourceReady: ----> hello")
                setImageBitmap(resource)
                listener?.invoke(resource)
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                errorDrawable?.let {
                    scaleType = ImageView.ScaleType.CENTER
                    setImageDrawable(errorDrawable)
                }
            }
            override fun onLoadCleared(placeholder: Drawable?) {}
        })
}

fun ImageView.loadThumbnail(
    imageUrl: String,
    thumbnailUrl: String? = null
) {
    Glide.with(context)
        .load(imageUrl)
        .override(this.width.px, this.height.px)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        //.thumbnail(Glide.with(context).load(thumbnailUrl))
        .fallback(R.drawable.ic_nodata)
        .into(this)
}

fun CommonImageView.load(
    url: String
) {
    Glide.with(context)
        .load(url)
        .apply(
            RequestOptions().apply {
                placeholder(R.drawable.ic_img_placeholder)
                fallback(R.drawable.ic_img_placeholder)
            }
        )
        .into(this)
}

fun CommonImageView.setTint(@ColorRes colorId: Int) {
    val color = ContextCompat.getColor(context, colorId)
    val colorStateList = ColorStateList.valueOf(color)
    ImageViewCompat.setImageTintList(this, colorStateList)
}

/*fun CommonImageView.border(@ColorInt color: Int, width: Float) {
    strokeColor = ColorStateList.valueOf(color)
    strokeWidth = width * 2 // ShapeableImageView scales down stroke width by 1/2 before drawing
}*/

fun CommonImageView.applySkeleton(
    isSkeleton: Boolean = false,
    @ColorRes color: Int = R.color.greyLight,
    @DrawableRes image: Int? = null,
    urlString: String? = null,
    listener: ((Bitmap)->Unit)? = null
) {
    when {
        isSkeleton -> {
            setImageResource(android.R.color.transparent)
            bgColor(color)
        }
        image != null -> setImageResource(image)
        urlString != null -> loadThumbnail(urlString)
    }
}
