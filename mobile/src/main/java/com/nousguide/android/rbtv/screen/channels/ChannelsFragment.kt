package com.nousguide.android.rbtv.screen.channels

import androidx.fragment.app.viewModels
import com.nousguide.android.rbtv.R
import com.nousguide.android.common.base.BaseFragment
import com.nousguide.android.common.base.viewBinding
import com.nousguide.android.rbtv.databinding.FragmentHomeBinding

class ChannelsFragment : BaseFragment(R.layout.fragment_channels) {

    private val viewModel: ChannelsViewModel by viewModels()
    private val viewBinding by viewBinding(FragmentHomeBinding::bind)
}