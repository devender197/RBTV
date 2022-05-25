package com.nousguide.android.tv.screen.screen.channels

import androidx.fragment.app.viewModels
import com.nousguide.android.tv.R
import com.nousguide.android.common.base.BaseFragment
import com.nousguide.android.common.base.viewBinding
import com.nousguide.android.tv.databinding.FragmentHomeBinding

class ChannelsFragment : BaseFragment(R.layout.fragment_channels) {

    private val viewModel: ChannelsViewModel by viewModels()
    private val viewBinding by viewBinding(FragmentHomeBinding::bind)
}