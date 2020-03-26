package com.example.kotlin_dou.holder

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_dou.R
import com.example.kotlin_dou.adapter.RecommendAdapter
import org.json.JSONArray
import org.json.JSONObject

class RecommendHolder(val view: View, val callback: Callback) {
    private val recyclerView = view.findViewById<RecyclerView>(R.id.rcv)
    private lateinit var layoutAdapter: RecommendAdapter
    private var isLoading = false

    interface Callback{
        fun loadMore()
    }

    fun fillData(jsonArray: JSONArray) {
        val dataList = mutableListOf<JSONObject>()
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.optJSONObject(i)
            if (obj.optJSONObject("pic") != null) {
                dataList.add(obj)
            }
        }


        if (::layoutAdapter.isInitialized) {
            layoutAdapter.addData(dataList)
        } else {
            layoutAdapter = RecommendAdapter(dataList)
            layoutAdapter.loadMoreModule?.let {
                it.setOnLoadMoreListener {
                    if(!isLoading){
                        isLoading = true
                        callback.loadMore()
                    }
                }
                it.isAutoLoadMore = false
            }

            recyclerView.run {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)

                adapter = layoutAdapter

            }
        }

    }

    fun loadMoreEnd(){
        layoutAdapter?.run {
            loadMoreModule?.run {
                loadMoreEnd()
                this@RecommendHolder.isLoading = false
            }
        }
    }

    fun loadMoreComplete(){
        layoutAdapter?.run {
            loadMoreModule?.run {
                loadMoreComplete()
                this@RecommendHolder.isLoading = false
            }
        }
    }




}