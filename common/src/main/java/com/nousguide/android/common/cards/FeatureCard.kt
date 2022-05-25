package com.nousguide.android.common.cards

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player.REPEAT_MODE_ONE
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.nousguide.android.common.R
import com.nousguide.android.common.extensions.lifecycleScope
import com.nousguide.android.common.extensions.loadBitmap
import com.nousguide.android.common.extensions.loadThumbnail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FeatureCard(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : Card(context, attrs, defStyleAttr, defStyleRes){

    private var title: TextView
    private var desc: TextView
    private var headerView: ConstraintLayout
    private var videoView: StyledPlayerView
    private var previewImage: ImageView


    init {
        inflate(context,R.layout.feature_item_layout, this)

        title = findViewById(R.id.tvTitle)
        desc = findViewById(R.id.tvDesc)
        headerView = findViewById(R.id.headerView)
        videoView = findViewById(R.id.videoView)
        previewImage = findViewById(R.id.imgPreview)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FeatureCard)

        if(typedArray.hasValue(R.styleable.FeatureCard_title)){
            title.text = typedArray.getString(R.styleable.FeatureCard_title);
        }

        if(typedArray.hasValue(R.styleable.FeatureCard_desc)){
            desc.text = typedArray.getString(R.styleable.FeatureCard_title);
        }

        if(typedArray.hasValue(R.styleable.FeatureCard_imageUrl)){
            typedArray.getString(R.styleable.FeatureCard_imageUrl)?.let { url ->
                previewImage.loadThumbnail(url)
            }
        }

        if(typedArray.hasValue(R.styleable.FeatureCard_videoUrl)){
            typedArray.getString(R.styleable.FeatureCard_videoUrl)?.let { url ->
                videoView.apply {
                    player = ExoPlayer.Builder(context).build().apply {
                        setMediaItem(MediaItem.fromUri(url))
                        repeatMode = REPEAT_MODE_ONE
                    }
                }
            }
        }
    }

    fun setImageUrl(imageUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            previewImage.lifecycleScope.launch {
                previewImage.loadBitmap(imageUrl)
            }
        }
    }

    fun setTitle(text: String) { title.text = text }

    fun setDesc(text: String) { desc.text = text }

    fun setVideoUrl(videoUrl: String) {
        videoView.apply {
            player = exoplayer.apply {
                setMediaItem(MediaItem.fromUri(videoUrl))
                repeatMode = REPEAT_MODE_ONE
            }
        }
    }

    override fun onViewFocused(isFocused : Boolean) {
        videoView.apply {
            player?.apply {
                if (isFocused) {
                    prepare()
                    play()
                } else if (isPlaying) pause()
            }
            isVisible = isFocused
        }
        previewImage.isVisible = !isFocused
    }
}