package com.example.kotlin_dou.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlin_dou.utils.Utils

class TestFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = View(context)
        rootView.layoutParams = ViewGroup.LayoutParams(-1, Utils.dip2px(500f))
        return rootView
    }
}