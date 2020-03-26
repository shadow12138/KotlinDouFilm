package com.example.kotlin_dou.holder

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_dou.R
import com.example.kotlin_dou.adapter.SimilarAdapter
import com.example.kotlin_dou.utils.HttpUtils

class SimilarHolder(view: View): BaseHolder(view) {
    private val rcv = view.findViewById<RecyclerView>(R.id.rcv)

    fun requestData(id: String) {
        val url = "https://m.douban.com/movie/subject/${id}/?event_source=movie_hot_gaia"
        getData(0, url)
    }

    override fun networkResponse(what: Int, response: String?) {
        super.networkResponse(what, response)

        val startTags = arrayListOf("<a href=\"", "<img data-src=\"", "<h3>")
        val endTags = arrayListOf("\" rel=\"\">", "\"", "</h3>")
        val properties = arrayListOf("link", "cover", "title")
        val listStart = "<section class=\"subject-rec\">"
        val itemStart = "<li>"
        val results = HttpUtils.getListFromHtml(
            response,
            listStart,
            itemStart,
            startTags,
            endTags,
            properties
        )
        fillData(results)
    }

    fun fillData(dataList: List<Map<String, String>>){
        rcv.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = SimilarAdapter(dataList)
        }
    }
}