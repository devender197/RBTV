package com.nousguide.android.customapp.screen.events

import androidx.fragment.app.viewModels
import com.nousguide.android.common.base.BaseFragment
import com.nousguide.android.common.base.viewBinding
import com.nousguide.android.customapp.R
import com.nousguide.android.customapp.databinding.FragmentHomeBinding

class EventsFragment : BaseFragment(R.layout.fragment_event) {

    private val viewModel: EventViewModel by viewModels()
    private val viewBinding by viewBinding(FragmentHomeBinding::bind)
}