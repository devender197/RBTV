package com.nousguide.android.tv.screen.screen.events

import androidx.fragment.app.viewModels
import com.nousguide.android.tv.R
import com.nousguide.android.common.base.BaseFragment
import com.nousguide.android.common.base.viewBinding
import com.nousguide.android.tv.databinding.FragmentHomeBinding

class EventsFragment : BaseFragment(R.layout.fragment_event) {

    private val viewModel: EventViewModel by viewModels()
    private val viewBinding by viewBinding(FragmentHomeBinding::bind)
}