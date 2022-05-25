package com.nousguide.android.tv.screen.screen.browse

import androidx.fragment.app.viewModels
import com.nousguide.android.tv.R
import com.nousguide.android.common.base.BaseFragment
import com.nousguide.android.common.base.viewBinding
import com.nousguide.android.tv.databinding.FragmentHomeBinding

class BrowseFragment : BaseFragment(R.layout.fragment_browse) {

    private val viewModel: BrowseViewModel by viewModels()
    private val viewBinding by viewBinding(FragmentHomeBinding::bind)
}