package com.example.kotlin_dou.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.NestedScrollView
import com.example.kotlin_dou.R
import com.example.kotlin_dou.holder.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_recommend.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : BaseActivity() {
    private val get = 0
    private val recommend = 1
    private var page = 0
    private val pageSize = 10
    private var loadingMore = false
    private lateinit var recommendHolder: RecommendHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        requestData()
    }

    private fun initViews() {
        nsv.setOnScrollChangeListener { v: NestedScrollView?, _: Int, scrollY: Int, _: Int, _: Int ->
            v?.run {
                if (scrollY == (getChildAt(0).measuredHeight) - measuredHeight) {
                    if (!loadingMore) {
                        page += 1
                        requestData()
                    }
                }
            }
        }
    }

    private fun requestData() {

        getData(
            get,
            "https://m.douban.com/rexxar/api/v2/movie/modules?need_manual_chart_card=1&for_mobile=1&ck=x6Ls"
        )

        loadingMore = true
        getData(
            recommend,
            "https://m.douban.com/rexxar/api/v2/movie/suggestion?start=${pageSize * page}&count=${pageSize}&new_struct=1&with_review=1&for_mobile=1&ck=x6Ls"
        )
    }

    override fun networkResponse(what: Int, response: String?) {
        when (what) {
            get -> {
                val jsonObject = JSONObject(response)
                val modules = jsonObject["modules"] as JSONArray
                for (i in 0 until modules.length()) {
                    val module = modules[i] as JSONObject
                    fillData(module)
                }
            }
            recommend -> {
                loadingMore = false

                val jsonObject = JSONObject(response)

                if(!::recommendHolder.isInitialized)
                    recommendHolder = RecommendHolder(v_recommend)

                val items = jsonObject.optJSONArray("items")
                recommendHolder.fillData(items)

                v_loading.visibility = if(items.length() >= pageSize) View.VISIBLE else View.GONE
                v_no_more.visibility = if(items.length() < pageSize) View.VISIBLE else View.GONE
            }
        }

    }

    private fun fillData(jsonObject: JSONObject) {
        when (jsonObject["module_name"]) {
            "movie_hot_gaia" -> {
                val holder = HotHolder(layout_hot_movies)
                holder.fillData(jsonObject["data"] as JSONObject)
            }
            "movie_subject_entrances" -> {
                val holder = HeaderHolder(v_header)
                holder.fillData(jsonObject["data"] as JSONObject)
            }
            "movie_selected_collections" -> {
                val holder = RankHolder(v_rank)
                holder.fillData(jsonObject["data"] as JSONObject)
            }
            "movie_soon" -> {
                val holder = SoonHolder(v_soon)
                holder.fillData(jsonObject["data"] as JSONObject)
            }
        }
    }


}
