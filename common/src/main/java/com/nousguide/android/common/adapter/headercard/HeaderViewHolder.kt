package com.nousguide.android.common.adapter.headercard

import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
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
import com.nousguide.android.common.models.Resource
import kotlinx.coroutines.launch

class HeaderViewHolder(val view: View) : CommonViewHolder(view) {

    private val title: TextView = view.findViewById(R.id.tvTitle)
    private val desc: TextView = view.findViewById(R.id.tvSubHeading)
    private val headerView: ConstraintLayout = view.findViewById(R.id.headerView)
    private val videoView: StyledPlayerView = view.findViewById(R.id.videoView)
    private val previewImage: ImageView = view.findViewById(R.id.imgPreview)
    private val imgTitle: ImageView = view.findViewById(R.id.imgTitle)
    //private val exoplayer = ExoPlayer.Builder(view.context).build()


    fun setup(model: Item, listener: CardClickListener?) {
        title.isVisible = true
        imgTitle.isVisible = false

        /*title.makeSkeleton(100.px, 10.px, gravity = Gravity.CENTER)
        desc.makeSkeleton(100.px, 10.px, gravity = Gravity.CENTER)
        title.applySkeleton(model.isSkeleton, model.title)
        desc.applySkeleton(model.isSkeleton, model.subheading)*/

        title.text = model.title
        desc.text = model.title

        /*if (model.playable) {
            videoView.apply {
                player = exoplayer.apply {
                    setMediaItem(MediaItem.fromUri(model.previewVideoUrl))
                    prepare()
                    repeatMode = Player.REPEAT_MODE_ONE
                }
            }
        }*/
        model.getResource?.let { it ->
            if (it.contains(Resource.RBTV_TITLE_TREATMENT_LANDSCAPE)) {
                title.isVisible = false
                imgTitle.isVisible = true
                imgTitle.loadBitmap(model.titleImageUrl){}
            }
        }

        /*view.lifecycleScope.apply {
            previewImage.applySkeleton(
                model.isSkeleton,
                urlString = model.previewImageUrl
            )
        }*/

        view.lifecycleScope.launch {
            previewImage.loadBitmap(model.previewImageUrl)
        }


        headerView.action { listener?.onItemClickListener(model) }
    }
}