package com.nousguide.android.tv.screen.screen.home

import android.widget.HorizontalScrollView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.nousguide.android.common.adapter.HorizontalAdapter
import com.nousguide.android.common.adapter.HorizontalAdapter.Companion.CHANNEL_VIEW
import com.nousguide.android.common.adapter.HorizontalAdapter.Companion.COVER_VIEW
import com.nousguide.android.common.adapter.HorizontalAdapter.Companion.FEATURE_CARD
import com.nousguide.android.common.adapter.HorizontalAdapter.Companion.HEADER_VIEW
import com.nousguide.android.common.base.BaseFragment
import com.nousguide.android.common.base.viewBinding
import com.nousguide.android.common.cards.*
import com.nousguide.android.common.commom.CommonLiveEvent
import com.nousguide.android.common.models.LinearChannel.LinearItem
import com.nousguide.android.common.models.ProductCollection.Item
import com.nousguide.android.common.models.core.ProductModel
import com.nousguide.android.common.viewmodel.HomeViewModel
import com.nousguide.android.tv.R
import com.nousguide.android.tv.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()
    private val viewBinding by viewBinding(FragmentHomeBinding::bind)

    override fun bind() {
        viewBinding.videoView.player = ExoPlayer.Builder(requireContext()).build()

        viewModel.apply {
            eventAddCardRows.observe(viewLifecycleOwner, handleAddCardRows)
            eventLoadHeaderItem.observe(viewLifecycleOwner, handleLoadHeaderItem)
            eventShowHud.observe(viewLifecycleOwner, handleHubView)
            loadPrograms()
        }
    }

    private val handleHubView = Observer<Boolean> {
        viewBinding.hudView.isVisible = it
    }

    private val handleLoadHeaderItem = Observer<ProductModel> { model ->
        model.productCollection?.first()?.let {
            if (it is Item) {
                viewBinding.videoView.player?.apply {
                    setMediaItem(MediaItem.fromUri(it.previewVideoUrl))
                    repeatMode = Player.REPEAT_MODE_ONE
                    prepare()
                    play()
                }
                viewBinding.tvBannerText.text = "Now Playing: ${it.title}"
            }
        }
    }

    private val handleAddCardRows = Observer<List<Pair<String, List<Card>>>> { cardRows ->
        cardRows.forEach { cardList ->
            val cardRow = RowCards(requireContext()).apply {
                setTitle(cardList.first)
                addCards(cardList.second)
            }
            viewBinding.recyclerView.addView(cardRow)
        }
    }
}