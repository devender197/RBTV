package com.nousguide.android.common.adapter.featurecard

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player.REPEAT_MODE_ONE
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.nousguide.android.common.R
import com.nousguide.android.common.commom.CommonImageView
import com.nousguide.android.common.commom.CommonLabel
import com.nousguide.android.common.commom.CommonView
import com.nousguide.android.common.commom.CommonViewHolder
import com.nousguide.android.common.extensions.applySkeleton
import com.nousguide.android.common.extensions.lifecycleScope
import com.nousguide.android.common.extensions.loadBitmap
import com.nousguide.android.common.extensions.px
import com.nousguide.android.common.listener.CardClickListener
import com.nousguide.android.common.models.ProductCollection.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FeatureViewHolder(val view: View) : CommonViewHolder(view) {

    private val title: TextView = view.findViewById(R.id.tvTitle)
    private val desc: TextView = view.findViewById(R.id.tvDesc)
    private val headerView: ConstraintLayout = view.findViewById(R.id.headerView)
    private val videoView: StyledPlayerView = view.findViewById(R.id.videoView)
    private val previewImage: ImageView = view.findViewById(R.id.imgPreview)
    //private val exoplayer = ExoPlayer.Builder(view.context).build()

    fun setup(model: Item, listener: CardClickListener) {
        /*title.makeSkeleton(100.px, 10.px)
        desc.makeSkeleton(100.px, 10.px)
        title.applySkeleton(model.isSkeleton, model.title)
        desc.applySkeleton(model.isSkeleton, model.subheading)*/

        title.text = model.title
        desc.text = model.title

        /*if (model.playable) {
            videoView.apply {
                player = exoplayer.apply {
                    setMediaItem(MediaItem.fromUri(model.previewVideoUrl))
                    repeatMode = REPEAT_MODE_ONE
                }
            }
        }*/

        /*view.lifecycleScope.launch {
            previewImage.applySkeleton(
                model.isSkeleton,
                urlString = model.previewImageUrl
            )
        }*/

        view.lifecycleScope.launch {
            previewImage.apply { loadBitmap(model.previewImageUrl(previewImage.height.px, 65)) }
        }

        headerView.action { listener.onItemClickListener(model) }
        view.setOnFocusChangeListener { _, isFocused ->
            videoView.player?.apply {
                if (isFocused && model.playable) {
                    previewImage.isVisible = false
                    videoView.isVisible = true
                    play()
                } else {
                    if (isPlaying) pause()
                    previewImage.isVisible = true
                    videoView.isVisible = false
                }
            }
        }
    }
}