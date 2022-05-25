package com.nousguide.android.common.adapter.channelcard

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.nousguide.android.common.R
import com.nousguide.android.common.commom.CommonViewHolder
import com.nousguide.android.common.extensions.lifecycleScope
import com.nousguide.android.common.extensions.loadBitmap
import com.nousguide.android.common.listener.CardClickListener
import com.nousguide.android.common.models.LinearChannel.LinearItem
import kotlinx.coroutines.launch

class ChannelViewHolder(val view: View) : CommonViewHolder(view) {

    private val title: TextView = view.findViewById(R.id.tvTitle)
    private val channelImageView: ImageView = view.findViewById(R.id.imgChannel)
    private val headerView: ConstraintLayout = view.findViewById(R.id.headerView)

    fun setup(model: LinearItem, listener: CardClickListener) {
        title.text = model.title
        view.lifecycleScope.launch {
            channelImageView.loadBitmap("https://resources-qa.redbull.tv/${model.id}/rbtv_display_art_square/im:i:w_200,c_fill,q_65?namespace=rbtv")
        }
        headerView.action { listener.onItemClickListener(model) }
    }
}