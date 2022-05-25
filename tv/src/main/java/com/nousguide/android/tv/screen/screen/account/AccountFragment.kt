package com.nousguide.android.tv.screen.screen.account

import androidx.fragment.app.viewModels
import com.nousguide.android.common.base.BaseFragment
import com.nousguide.android.common.base.viewBinding
import com.nousguide.android.common.viewmodel.HomeViewModel
import com.nousguide.android.tv.R
import com.nousguide.android.tv.databinding.FragmentHomeBinding

class AccountFragment : BaseFragment(R.layout.fragment_account) {

    private val viewModel: HomeViewModel by viewModels()
    private val viewBinding by viewBinding(FragmentHomeBinding::bind)
}