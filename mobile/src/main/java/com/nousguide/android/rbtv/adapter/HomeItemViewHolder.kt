package com.nousguide.android.rbtv.adapter

import android.view.Gravity
import android.view.View
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.nousguide.android.common.adapter.HomeRowAdapter
import com.nousguide.android.common.adapter.HorizontalAdapter
import com.nousguide.android.common.adapter.channelcard.ChannelItemAdapter
import com.nousguide.android.common.adapter.covercard.CoverItemAdapter
import com.nousguide.android.common.adapter.featurecard.FeatureItemAdapter
import com.nousguide.android.common.adapter.headercard.HeaderItemAdapter
import com.nousguide.android.common.commom.CommonLabel
import com.nousguide.android.common.commom.CommonRecyclerView
import com.nousguide.android.common.commom.CommonViewHolder
import com.nousguide.android.common.extensions.findVisibleView
import com.nousguide.android.common.extensions.findVisibleViewPosition
import com.nousguide.android.common.extensions.onViewFocus
import com.nousguide.android.common.extensions.px
import com.nousguide.android.common.listener.CardClickListener
import com.nousguide.android.common.models.ProductCollection.Item
import com.nousguide.android.common.models.LinearChannel.LinearItem
import com.nousguide.android.common.models.core.ProductModel
import com.nousguide.android.rbtv.R
import kotlinx.coroutines.CoroutineScope
import com.nousguide.android.common.R as commonR

class HomeItemViewHolder(
    view: View,
    private val scopeProvider: () -> CoroutineScope,
    viewPool: RecyclerView.RecycledViewPool
) :
    CommonViewHolder(view) {

    private val recyclerView: CommonRecyclerView = view.findViewById(R.id.horizontalRecyclerView)
    private val tabLayout: TabLayout = view.findViewById(R.id.tabLayout)
    private val title: CommonLabel = view.findViewById(R.id.tvTitle)
    private val snapHelper: LinearSnapHelper = LinearSnapHelper()
    private var currentFocusedLayout: View? = null
    private var oldFocusedLayout: View? = null


    companion object {
        private const val HEADER_VIEW = 0
        private const val FEATURE_CARD = 1
        private const val CHANNEL_VIEW = 2
        private const val COVER_VIEW = 3
    }

    init {
        snapHelper.attachToRecyclerView(recyclerView)
        //recyclerView.setRecycledViewPool(viewPool)
        recyclerView.apply {
            (layoutManager as LinearLayoutManager).initialPrefetchItemCount = 4
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        val visiblePosition = layoutManager?.findVisibleViewPosition() ?: 0
                        oldFocusedLayout?.onViewFocus(false)

                        if (recyclerView.adapter is HomeRowAdapter && (recyclerView.adapter as HomeRowAdapter).items[visiblePosition].playable) {
                            currentFocusedLayout = layoutManager?.findVisibleView()
                            currentFocusedLayout?.onViewFocus(true)
                            oldFocusedLayout = currentFocusedLayout
                        }

                        tabLayout.apply {
                            if (isVisible) {
                                selectTab(getTabAt(visiblePosition))
                            }
                        }
                    }
                }
            })
        }
    }

    fun setup(model: ProductModel, listener: CardClickListener) {
        val viewType = getItemViewType(model)

        title.makeSkeleton(100.px, 10.px)
        title.applySkeleton(model.isSkeleton, model.title)

        title.isVisible = viewType != HEADER_VIEW

        tabLayout.apply {
            isVisible = if (viewType == HEADER_VIEW) {
                if (tabCount == 0) model.productCollection?.size?.let { size ->
                    repeat(size) {
                        addTab(newTab().setIcon(commonR.drawable.selector_page_indicator))
                    }
                }
                true
            } else false
        }

        recyclerView.apply {
            model.productCollection?.let { list ->
                adapter = HorizontalAdapter(context, listener, list, scopeProvider, viewType).apply {

                }
            }
        }
    }

    private fun getItemViewType(model: ProductModel): Int {
        return when (model.productCollection?.first()) {
            is Item -> {
                when {
                    model.title.equals("Featuring Test Cases", true) -> HEADER_VIEW
                    model.title.equals("Surf: Enjoy the best shows", true) -> COVER_VIEW
                    model.title.equals("Shows", true) -> COVER_VIEW
                    model.title.equals("Vitalize Body & Mind", true) -> COVER_VIEW
                    model.title.equals("H265 HFR Renditions", true) -> COVER_VIEW
                    model.title.equals("H264 HFR Renditions", true) -> COVER_VIEW
                    model.title.equals("Films and Documentaries", true) -> COVER_VIEW
                    else -> FEATURE_CARD
                }
            }
            is LinearItem -> CHANNEL_VIEW
            else -> FEATURE_CARD
        }
    }


}