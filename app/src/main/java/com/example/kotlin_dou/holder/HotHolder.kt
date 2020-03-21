package com.example.kotlin_dou.holder

import android.content.Intent
import android.view.View
import android.widget.GridView
import android.widget.TextView
import com.example.kotlin_dou.R
import com.example.kotlin_dou.activity.MovieDetailActivity
import com.example.kotlin_dou.activity.TestActivity
import com.example.kotlin_dou.adapter.MovieGridAdapter
import org.json.JSONArray
import org.json.JSONObject

class HotHolder(view: View) {
    private val tvTitle = view.findViewById<TextView>(R.id.tv_title)
    private val tvCount = view.findViewById<TextView>(R.id.tv_count)
    private val gvContent = view.findViewById<GridView>(R.id.gv)
    private val context = view.context

    fun fillData(jsonObject: JSONObject){
        val board = (jsonObject["subject_collection_boards"] as JSONArray)[0] as JSONObject
        fillTitle(board["subject_collection"] as JSONObject)
        fillContent(board["items"] as JSONArray)
    }

    private fun fillTitle(jsonObject: JSONObject){
        tvTitle.text = jsonObject["name"] as String
        tvCount.text = "全部 ${jsonObject.optInt("subject_count")}"
    }

    private fun fillContent(jsonArray: JSONArray){
        val adapter = MovieGridAdapter(jsonArray)
        gvContent.adapter = adapter

        gvContent.setOnItemClickListener { adapterView, view, i, l ->
            val jsonObject = jsonArray.optJSONObject(i)
            val intent = Intent(context, MovieDetailActivity::class.java)
//            val intent = Intent(context, TestActivity::class.java)
            intent.putExtra("id", jsonObject.optString("id"))
            context.startActivity(intent)
        }
    }

}