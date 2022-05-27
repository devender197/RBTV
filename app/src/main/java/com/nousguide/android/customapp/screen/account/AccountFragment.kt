package com.nousguide.android.customapp.screen.account

import androidx.fragment.app.viewModels
import com.nousguide.android.common.base.BaseFragment
import com.nousguide.android.common.base.viewBinding
import com.nousguide.android.common.viewmodel.HomeViewModel
import com.nousguide.android.customapp.R
import com.nousguide.android.customapp.databinding.FragmentHomeBinding

class AccountFragment : BaseFragment(R.layout.fragment_account) {

    private val viewModel: HomeViewModel by viewModels()
    private val viewBinding by viewBinding(FragmentHomeBinding::bind)
}