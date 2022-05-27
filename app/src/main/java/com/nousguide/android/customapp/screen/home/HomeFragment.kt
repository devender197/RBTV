package com.nousguide.android.customapp.screen.home

import android.widget.HorizontalScrollView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.nousguide.android.common.base.BaseFragment
import com.nousguide.android.common.base.viewBinding
import com.nousguide.android.common.cards.Card
import com.nousguide.android.common.cards.RowCards
import com.nousguide.android.common.models.core.ProductModel
import com.nousguide.android.common.viewmodel.HomeViewModel
import com.nousguide.android.customapp.R
import com.nousguide.android.customapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    private val viewBinding by viewBinding(FragmentHomeBinding::bind)

    override fun bind() {

        viewModel.apply {
            eventAddCardRows.observe(viewLifecycleOwner, handleAddCardRows)
            eventAddHeaderRow.observe(viewLifecycleOwner, handleAddHeaderCard)
            eventShowHud.observe(viewLifecycleOwner, handleHubView)
            loadPrograms()
        }
    }

    private val handleHubView = Observer<Boolean> {
        viewBinding.hudView.isVisible = it
    }

    private val handleAddHeaderCard = Observer<Card> {
        viewBinding.recyclerView.addView(it)
    }

    private val handleAddCardRows = Observer<List<Pair<String,List<Card>>>> { cardRows ->
        cardRows.forEach {cardList ->
            val cardRow = RowCards(requireContext()).apply {
                setTitle(cardList.first)
                addCards(cardList.second)
            }
            viewBinding.recyclerView.addView(cardRow)
        }
    }
}