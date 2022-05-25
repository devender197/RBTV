package com.nousguide.android.customapp.screen.channels

import androidx.fragment.app.viewModels
import com.nousguide.android.common.base.BaseFragment
import com.nousguide.android.common.base.viewBinding
import com.nousguide.android.customapp.R
import com.nousguide.android.customapp.databinding.FragmentHomeBinding

class ChannelsFragment : BaseFragment(R.layout.fragment_channels) {

    private val viewModel: ChannelsViewModel by viewModels()
    private val viewBinding by viewBinding(FragmentHomeBinding::bind)
}