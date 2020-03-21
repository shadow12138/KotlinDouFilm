package com.example.kotlin_dou.holder

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.kotlin_dou.R
import com.example.kotlin_dou.utils.Utils
import org.json.JSONArray

class TagHolder(view: View): BaseHolder(view) {
    private val llTags = view.findViewById<LinearLayout>(R.id.ll_tags)

    fun fillData(jsonArray: JSONArray){
        llTags.removeAllViews()
        jsonArray.run {
            val layoutParams = LinearLayout.LayoutParams(-2, -2)
            layoutParams.rightMargin = Utils.dip2px(10f)
            for(i in 0 until length()){
                val name = optJSONObject(i).optString("name")
                val view = View.inflate(context, R.layout.item_tag, null) as TextView
                view.text = name
                llTags.addView(view, layoutParams)
            }
        }
    }
}