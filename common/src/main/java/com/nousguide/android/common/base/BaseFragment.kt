package com.nousguide.android.common.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment


open class BaseFragment : Fragment {

    private val name = this.javaClass.simpleName

    constructor() : super()

    constructor(layoutResId: Int) : super(layoutResId)

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind()
    }

    open fun bind() {}

    override fun onStop() {
        super.onStop()
    }


    open fun onBackPressed() {}
}
