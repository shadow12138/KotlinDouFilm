package com.example.kotlin_dou.holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_dou.R
import com.example.kotlin_dou.adapter.CommentAdapter
import org.json.JSONArray
import org.json.JSONObject

class CommentHolder(view: View): BaseHolder(view) {
    private val tvAll = view.findViewById<TextView>(R.id.tv_all)
    private val rcv = view.findViewById<RecyclerView>(R.id.rcv)

    fun requestData(id: String){
        getData(0, "https://m.douban.com/rexxar/api/v2/movie/${id}/interests?count=4&order_by=hot&start=0&ck=&for_mobile=1")
    }

    override fun networkResponse(what: Int, response: String?) {
        super.networkResponse(what, response)

        val jsonObject = JSONObject(response)
        jsonObject.run {
            tvAll.text = "全部${optString("total")}"
            fillData(optJSONArray("interests"))
        }
    }

    fun fillData(jsonArray: JSONArray){
        rcv.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = CommentAdapter(jsonArray)
        }
    }
}