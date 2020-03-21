package com.example.kotlin_dou.holder

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.kotlin_dou.R
import com.example.kotlin_dou.activity.MovieDetailActivity
import com.example.kotlin_dou.utils.ImageUtils
import com.example.kotlin_dou.utils.Utils
import org.json.JSONObject

class RankHolder(val view: View) {
    private val tvTitle = view.findViewById<TextView>(R.id.tv_title)
    private val tvCount = view.findViewById<TextView>(R.id.tv_count)
    private val llContent = view.findViewById<LinearLayout>(R.id.ll)
    private val context = view.context

    fun fillData(jsonObject: JSONObject){

        tvTitle.text = jsonObject.optString("title")
        tvCount.text = "全部 ${jsonObject.optString("total")}"

        llContent.removeAllViews()
        val jsonArray = jsonObject.optJSONArray("selected_collections")

        for(i in 0 until jsonArray.length()){
            val itemView = getItemView(jsonArray.optJSONObject(i))

            val layoutParams = LinearLayout.LayoutParams(Utils.dip2px(220F), -2)
            layoutParams.leftMargin = Utils.dip2px(10F)
            if(i == jsonArray.length() - 1)
                layoutParams.rightMargin = Utils.dip2px(14F)

            llContent.addView(itemView, layoutParams)
        }
    }

    fun getItemView(jsonObject: JSONObject): View{

        val itemView = View.inflate(context, R.layout.item_rank, null)
        val iv = itemView.findViewById<ImageView>(R.id.iv)
        val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
        val tvSubTitle = itemView.findViewById<TextView>(R.id.tv_sub_title)
        val ll = itemView.findViewById<LinearLayout>(R.id.ll)

        val color = "#${jsonObject.optJSONObject("background_color_scheme").optString("primary_color_dark")}"
        val bgDrawable = ll.background as GradientDrawable
        bgDrawable.setColor(Color.parseColor(color))

        tvTitle.text = jsonObject.optString("name")
        tvSubTitle.text = jsonObject.optString("subtitle")
        ImageUtils.loadImage(jsonObject.optString("header_bg_image"), iv)

        ll.removeAllViews()
        val jsonArray = jsonObject.optJSONArray("items")
        val layoutParams = LinearLayout.LayoutParams(-2, -2)
        layoutParams.bottomMargin = Utils.dip2px(10F)

        for(i in 0 until jsonArray.length()){
            val titleView = View.inflate(context, R.layout.item_rank_title, null)
            val vTitle = titleView.findViewById<TextView>(R.id.tv_title)
            val vRating = titleView.findViewById<TextView>(R.id.tv_rating)

            jsonArray.optJSONObject(i).run {
                vTitle.text = "${i+1}. ${optString("title")}"
                vRating.text = "${optJSONObject("rating").optString("value")}"

                titleView.setOnClickListener {
                    val intent = Intent(context, MovieDetailActivity::class.java)
                    intent.putExtra("id", optString("id"))
                    context.startActivity(intent)
                }
            }


            ll.addView(titleView, layoutParams)
        }

        return itemView
    }

}