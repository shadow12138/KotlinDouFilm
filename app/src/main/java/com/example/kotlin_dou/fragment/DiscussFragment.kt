package com.example.kotlin_dou.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_dou.R
import com.example.kotlin_dou.adapter.DiscussAdapter
import com.example.kotlin_dou.utils.HttpUtils
import io.iftech.android.library.slide.configSlideChildTypeSlider

class DiscussFragment : BaseFragment() {

    companion object {
        fun newInstance(id: String): DiscussFragment {
            val fragment = DiscussFragment()
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
        if (!::rootView.isInitialized) {
            rootView = inflater.inflate(R.layout.page_bottom, null)
            initViews()
        }
        return rootView
    }

    private fun initViews() {
        rootView.findViewById<TextView>(R.id.tv_title).text = "讨论列表"
        rootView.findViewById<NestedScrollView>(R.id.nsv).configSlideChildTypeSlider()

        val id = arguments?.getString("id")
        getPcData(0, "https://movie.douban.com/subject/${id}/discussion/")
    }

    override fun networkResponse(what: Int, response: String?) {
        super.networkResponse(what, response)

        val startTags = arrayListOf(
            "title=\"",
            "itemprop=\"author\">",
            "<td itemprop='interactionCount' content=\"UserComments:",
            "<td class=\"time\" itemprop=\"datePublished\">"
        )
        val endTags = arrayListOf("\"", "</a>", "\">", "</td>")
        val properties = arrayListOf("title", "nickname", "response", "date")
        val itemStart = "<tr class=\"\""
        val listStart = "<div class=\"discussion-posts\">"
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

    private fun fillData(dataList: List<Map<String, String>>) {

        rootView.findViewById<RecyclerView>(R.id.rcv).run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = DiscussAdapter(dataList)
        }
    }
}