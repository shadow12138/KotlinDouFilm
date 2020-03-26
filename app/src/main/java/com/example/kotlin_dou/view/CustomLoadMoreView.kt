package com.example.kotlin_dou.view

import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.loadmore.BaseLoadMoreView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.kotlin_dou.R

class CustomLoadMoreView: BaseLoadMoreView() {
    override fun getLoadComplete(holder: BaseViewHolder): View {
        return holder.getView(R.id.v_complete)
    }

    override fun getLoadEndView(holder: BaseViewHolder): View {
        return holder.getView(R.id.v_no_more)
    }

    override fun getLoadFailView(holder: BaseViewHolder): View {
        return holder.getView(R.id.v_fail)
    }

    override fun getLoadingView(holder: BaseViewHolder): View {
        return holder.getView(R.id.v_loading)
    }

    override fun getRootView(parent: ViewGroup): View {
        val view = View.inflate(parent.context, R.layout.layout_load_more, null)
        view.layoutParams = ViewGroup.LayoutParams(-1, -2)
        return view
    }
}