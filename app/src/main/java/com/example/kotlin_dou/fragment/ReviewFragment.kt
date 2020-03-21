package com.example.kotlin_dou.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlin_dou.R
import com.example.kotlin_dou.holder.ReviewHolder

class ReviewFragment: BaseFragment() {

    companion object{
        fun newInstance(id: String): ReviewFragment{
            val fragment = ReviewFragment()
            val bundle = Bundle()
            bundle.putString("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(!::rootView.isInitialized){
            rootView = inflater.inflate(R.layout.page_bottom, null)
            initViews()
        }
        return rootView
    }

    private fun initViews(){
        val holder = ReviewHolder(rootView)
        arguments?.getString("id")?.let { holder.requestData(it) }
    }
}