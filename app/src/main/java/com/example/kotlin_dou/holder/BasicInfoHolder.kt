package com.example.kotlin_dou.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.kotlin_dou.R
import com.example.kotlin_dou.utils.ImageUtils
import org.json.JSONObject

class BasicInfoHolder(view: View) {
    private val iv = view.findViewById<ImageView>(R.id.iv)
    private val tvTitle = view.findViewById<TextView>(R.id.tv_title)
    private val tvYear = view.findViewById<TextView>(R.id.tv_year)
    private val tvDesc = view.findViewById<TextView>(R.id.tv_desc)

    fun fillData(jsonObject: JSONObject){
        jsonObject.run {
            ImageUtils.loadImage(optJSONObject("pic").optString("normal"), iv)
            tvTitle.text = optString("title")
            tvYear.text = "(${optString("year")})"
            tvDesc.text = optString("card_subtitle")
        }
    }
}