package com.example.kotlin_dou.holder

import android.view.View
import com.example.kotlin_dou.R
import com.example.kotlin_dou.view.ExpandableTextView
import org.json.JSONObject

class IntroductionHolder(view: View) : BaseHolder(view) {
    private val tvIntroduction = view.findViewById<ExpandableTextView>(R.id.tv_introduction)

    fun fillData(jsonObject: JSONObject) {
        tvIntroduction.setExpandableText(jsonObject.optString("intro"))
    }

}