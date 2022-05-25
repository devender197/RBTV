package com.nousguide.android.rbtv.screen.account

import androidx.fragment.app.viewModels
import com.nousguide.android.rbtv.R
import com.nousguide.android.common.base.BaseFragment
import com.nousguide.android.common.base.viewBinding
import com.nousguide.android.rbtv.databinding.FragmentHomeBinding
import com.nousguide.android.common.viewmodel.HomeViewModel

class AccountFragment : BaseFragment(R.layout.fragment_account) {

    private val viewModel: HomeViewModel by viewModels()
    private val viewBinding by viewBinding(FragmentHomeBinding::bind)
}