package com.example.kotlin_dou.holder

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.kotlin_dou.R
import com.example.kotlin_dou.utils.ImageUtils
import com.example.kotlin_dou.utils.Utils
import org.json.JSONObject

class ActorHolder(view: View): BaseHolder(view) {
    private val llActors = view.findViewById<LinearLayout>(R.id.ll_actors)
    private val tvAll = view.findViewById<TextView>(R.id.tv_all)

    fun fillData(jsonObject: JSONObject){
        val width = Utils.dip2px(90f)
        llActors.removeAllViews()

        jsonObject.optJSONArray("directors").run {
            val layoutParams = LinearLayout.LayoutParams(width, -2)
            layoutParams.leftMargin = Utils.dip2px(10f)
            llActors.addView(getItem(optJSONObject(0)), layoutParams)
        }

        jsonObject.optJSONArray("actors").run {
            tvAll.text = "全部${length()}"

            for(i in 0 until length()){
                val layoutParams = LinearLayout.LayoutParams(Utils.dip2px(90f), -2)
                layoutParams.leftMargin = Utils.dip2px(10f)
                layoutParams.rightMargin = if(i == length() - 1) Utils.dip2px(10f) else 0
                llActors.addView(getItem(optJSONObject(i)), layoutParams)
            }
        }
    }

    fun getItem(jsonObject: JSONObject): View{
        val itemView = View.inflate(context, R.layout.item_actor, null)
        val iv = itemView.findViewById<ImageView>(R.id.iv)
        val tvName = itemView.findViewById<TextView>(R.id.tv_name)
        val tvPosition = itemView.findViewById<TextView>(R.id.tv_position)

        jsonObject.run {
            ImageUtils.loadImage(optString("cover_url"), iv)
            tvName.text = optString("name")
            tvPosition.text = optJSONArray("roles").optString(0)
        }

        return itemView
    }
}