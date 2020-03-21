package com.example.kotlin_dou.holder

import android.view.View
import android.widget.TextView
import com.example.kotlin_dou.R
import org.json.JSONObject

class IntroductionHolder(view: View): BaseHolder(view){
    private val tvIntroduction = view.findViewById<TextView>(R.id.tv_introduction)

    fun fillData(jsonObject: JSONObject){
        tvIntroduction.text = jsonObject.optString("intro")
    }
}