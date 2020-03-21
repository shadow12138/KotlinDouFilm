package com.example.kotlin_dou.holder

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.kotlin_dou.R
import com.example.kotlin_dou.utils.ImageUtils
import org.json.JSONObject

class HeaderHolder(val view: View) {

    val llContainer = view as LinearLayout

    fun fillData(jsonObject: JSONObject){
        llContainer.removeAllViews()

        val jsonArray = jsonObject.optJSONArray("subject_entraces")
        val layoutParams = LinearLayout.LayoutParams(-1, -2, 1F)
        for(i in 0 until jsonArray.length()){
            val itemObject = jsonArray.optJSONObject(i)

            val itemView = View.inflate(llContainer.context, R.layout.item_header, null)
            val tv = itemView.findViewById<TextView>(R.id.tv)
            val iv = itemView.findViewById<ImageView>(R.id.iv)
            tv.text = itemObject.optString("title")
            ImageUtils.loadImage(itemObject.optString("icon"), iv)
            llContainer.addView(itemView, layoutParams)
        }
    }

}