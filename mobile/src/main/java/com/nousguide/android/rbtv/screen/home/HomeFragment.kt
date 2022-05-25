package com.nousguide.android.rbtv.screen.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nousguide.android.rbtv.R
import com.nousguide.android.rbtv.adapter.HomeAdapter
import com.nousguide.android.common.base.BaseFragment
import com.nousguide.android.common.base.viewBinding
import com.nousguide.android.common.models.core.ProductModel
import com.nousguide.android.common.viewmodel.HomeViewModel
import com.nousguide.android.rbtv.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var homeAdapter: HomeAdapter
    private val viewBinding by viewBinding(FragmentHomeBinding::bind)

    override fun bind() {
        homeAdapter = HomeAdapter(requireContext(), viewModel) { lifecycleScope }
        viewBinding.recyclerView.adapter = homeAdapter

        viewModel.apply {
            eventLoadProducts.observe(viewLifecycleOwner, handleLoadProducts)
            eventUpdateProduct.observe(viewLifecycleOwner, handleUpdateProduct)
            eventLoadProduct.observe(viewLifecycleOwner, handleAddProduct)
            eventShowHud.observe(viewLifecycleOwner, handleHubView)
            getProduct()
        }

        homeAdapter = HomeAdapter(requireContext(), viewModel) { lifecycleScope }
        viewBinding.recyclerView.apply {
            this.adapter = homeAdapter
        }
    }

    private val handleHubView = Observer<Boolean> {
        viewBinding.hudView.isVisible = it
    }

    private val handleLoadProducts = Observer<List<ProductModel>?> { list ->
        list?.let {
            homeAdapter.addItems(it)
        }
    }

    private val handleUpdateProduct = Observer<ProductModel> { model ->
        homeAdapter.updateItem(model)
    }

    private val handleAddProduct = Observer<ProductModel> { model ->
        homeAdapter.addItem(model)
    }
}