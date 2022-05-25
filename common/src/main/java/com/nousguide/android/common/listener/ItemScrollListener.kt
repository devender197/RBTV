package com.nousguide.android.common.listener

/*
class ItemScrollListener : RecyclerView.OnScrollListener() {
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            val visiblePosition = layoutManager?.findVisibleViewPosition() ?: 0
            oldFocusedLayout?.onViewFocus(false)

            if ((recyclerView.adapter as HomeRowAdapter).items[visiblePosition].playable) {
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

}*/
