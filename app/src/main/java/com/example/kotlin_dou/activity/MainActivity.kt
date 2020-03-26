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
    private lateinit var recommendHolder: RecommendHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        requestData()
    }

    private fun initViews() {


    }

    private fun requestData() {

        getData(
            get,
            "https://m.douban.com/rexxar/api/v2/movie/modules?need_manual_chart_card=1&for_mobile=1&ck=x6Ls"
        )


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
                val jsonObject = JSONObject(response)

                if (!::recommendHolder.isInitialized)
                    recommendHolder =
                        RecommendHolder(v_recommend, object : RecommendHolder.Callback {
                            override fun loadMore() {
                                page += 1
                                requestData()
                            }
                        })

                val items = jsonObject.optJSONArray("items")
                recommendHolder.fillData(items)

                if (items.length() >= pageSize) {
                    recommendHolder.loadMoreComplete()
                } else {
                    recommendHolder.loadMoreEnd()
                }
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
