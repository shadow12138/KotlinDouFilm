package com.example.kotlin_dou.holder

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_dou.R
import com.example.kotlin_dou.adapter.RecommendAdapter
import org.json.JSONArray

class RecommendHolder(val view: View) {
    private val recyclerView = view.findViewById<RecyclerView>(R.id.rcv)
    private lateinit var layoutAdapter: RecommendAdapter

    fun fillData(jsonArray: JSONArray) {
        val array = JSONArray()
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.optJSONObject(i)
            if (obj.optJSONObject("pic") != null) {
                array.put(obj)
            }
        }


        if (::layoutAdapter.isInitialized) {
            layoutAdapter.append(array)
        } else {
            layoutAdapter = RecommendAdapter(array)
            recyclerView.run {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = layoutAdapter
            }
        }
    }


}