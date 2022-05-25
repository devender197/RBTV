package com.nousguide.android.common.adapter.covercard

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.nousguide.android.common.R
import com.nousguide.android.common.commom.CommonImageView
import com.nousguide.android.common.commom.CommonView
import com.nousguide.android.common.commom.CommonViewHolder
import com.nousguide.android.common.extensions.lifecycleScope
import com.nousguide.android.common.extensions.loadBitmap
import com.nousguide.android.common.extensions.loadThumbnail
import com.nousguide.android.common.listener.CardClickListener
import com.nousguide.android.common.models.ProductCollection.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoverViewHolder(val view: View) : CommonViewHolder(view) {

    private val coverImageView: ImageView = view.findViewById(R.id.imgCover)
    private val headerView: ConstraintLayout = view.findViewById(R.id.headerView)

    fun setup(model: Item, listener: CardClickListener) {
        view.lifecycleScope.launch {
            coverImageView.loadBitmap("https://resources-qa.redbull.tv/${model.id}/rbtv_display_art_square/im:i:w_200,c_fill,q_65?namespace=rbtv")
        }
        headerView.action { listener.onItemClickListener(model) }
    }
}