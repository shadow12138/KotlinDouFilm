package com.example.kotlin_dou.holder

import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_dou.R
import com.example.kotlin_dou.adapter.ReviewAdapter
import com.example.kotlin_dou.utils.HttpUtils
import io.iftech.android.library.slide.configSlideChildTypeSlider
import javax.xml.xpath.XPath
import javax.xml.xpath.XPathFactory

class ReviewHolder(view: View) : BaseHolder(view) {
    private val rcvReviews = view.findViewById<RecyclerView>(R.id.rcv)
    private val nestedScrollView = view.findViewById<NestedScrollView>(R.id.nsv)

    fun requestData(id: String) {
        val url = "https://m.douban.com/movie/subject/${id}/?event_source=movie_hot_gaia"
        getData(0, url)
    }

    override fun networkResponse(what: Int, response: String?) {
        super.networkResponse(what, response)

        //TODO xpath
        //val xPath = XPathFactory.newInstance().newXPath()


        val startTags = arrayListOf(
            "<img src=\"",
            "<span class=\"user-name\">",
            "<span class=\"rating-stars\" data-rating=\"",
            "<h3>",
            "<p class=\"abstract",
            "<div class=\"info\">"
        )
        val endTags = arrayListOf("\"", "</span>", "\"", "</h3>", "</p>", "</div>")
        val properties = arrayListOf("avatar", "nickname", "rating", "title", "content", "response")
        val listStart = "<div class=\"bd movie-reviews\">"
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

    fun fillData(dataList: List<Map<String, String>>) {
        nestedScrollView.configSlideChildTypeSlider()
        rcvReviews.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ReviewAdapter(dataList)
        }
    }
}