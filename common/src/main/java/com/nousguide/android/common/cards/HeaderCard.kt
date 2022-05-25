package com.nousguide.android.common.cards

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.nousguide.android.common.R
import com.nousguide.android.common.adapter.HomeRowAdapter
import com.nousguide.android.common.adapter.headercard.HeaderItemAdapter
import com.nousguide.android.common.extensions.findVisibleView
import com.nousguide.android.common.extensions.findVisibleViewPosition
import com.nousguide.android.common.extensions.lifecycleScope
import com.nousguide.android.common.extensions.onViewFocus
import com.nousguide.android.common.models.ProductCollection.Item

class HeaderCard(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : Card(context, attrs, defStyleAttr, defStyleRes) {

    private var recyclerView: RecyclerView
    private var tabLayout: TabLayout
    private val snapHelper: LinearSnapHelper = LinearSnapHelper()
    private var headerItemAdapter: HeaderItemAdapter
    private var currentFocusedLayout: View? = null
    private var oldFocusedLayout: View? = null

    init {
        inflate(context, R.layout.header_layout, this)

        recyclerView = findViewById(R.id.headerView)
        tabLayout = findViewById(R.id.tabLayout)

        recyclerView.apply {
            snapHelper.attachToRecyclerView(this)
            headerItemAdapter = HeaderItemAdapter(context) { lifecycleScope }
            adapter = headerItemAdapter
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

                        tabLayout.apply { if (isVisible) selectTab(getTabAt(visiblePosition)) }
                    }
                }
            })
        }
    }

    fun addItems(list: List<Item>){
        headerItemAdapter.addItems(list)
    }

    fun setTabs(itemSize: Int) {
        tabLayout.apply {
            if (tabCount == 0)
                repeat(itemSize) { addTab(newTab().setIcon(R.drawable.selector_page_indicator)) }
        }
    }

    override fun onViewFocused(isFocused: Boolean) {}


}