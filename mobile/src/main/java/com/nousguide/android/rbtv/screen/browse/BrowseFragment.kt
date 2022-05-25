package com.nousguide.android.rbtv.screen.browse

import androidx.fragment.app.viewModels
import com.nousguide.android.rbtv.R
import com.nousguide.android.common.base.BaseFragment
import com.nousguide.android.common.base.viewBinding
import com.nousguide.android.rbtv.databinding.FragmentHomeBinding

class BrowseFragment : BaseFragment(R.layout.fragment_browse) {

    private val viewModel: BrowseViewModel by viewModels()
    private val viewBinding by viewBinding(FragmentHomeBinding::bind)
}