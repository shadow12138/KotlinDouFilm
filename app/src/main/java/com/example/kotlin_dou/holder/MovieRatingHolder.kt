package com.example.kotlin_dou.holder

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.kotlin_dou.R
import com.example.kotlin_dou.utils.Utils
import com.hedgehog.ratingbar.RatingBar
import org.json.JSONObject

class MovieRatingHolder(view: View): BaseHolder(view) {
    private val tvRating = view.findViewById<TextView>(R.id.tv_rating)
    private val ratingBar = view.findViewById<RatingBar>(R.id.rating_bar)
    private val llCounts = view.findViewById<LinearLayout>(R.id.ll_counts)
    private val tvRatingPeople = view.findViewById<TextView>(R.id.tv_rating_people)
    private val tvWish = view.findViewById<TextView>(R.id.tv_wish)
    private val tvDone = view.findViewById<TextView>(R.id.tv_done)

    fun fillData(jsonObject: JSONObject){
        jsonObject.optJSONObject("rating").run {
            ratingBar.setStar(optString("star_count").toFloat())
            tvRating.text = optString("value")
            tvRatingPeople.text = "${optString("count")}人评分"
        }

        getData(0, "https://m.douban.com/rexxar/api/v2/movie/${jsonObject.optString("id")}/rating?ck=&for_mobile=1")
    }

    override fun networkResponse(what: Int, response: String?) {
        super.networkResponse(what, response)

        val jsonObject = JSONObject(response)
        jsonObject.optJSONArray("stats").run {
            llCounts.removeAllViews()
            val height = Utils.dip2px(7f)
            val margin = Utils.dip2px(3.5f)
            for(i in length() - 1 downTo 0){
                val view = View(context)

                //set width
                val layoutParams = LinearLayout.LayoutParams((optDouble(i) * 300).toInt(), height)
                layoutParams.bottomMargin = margin

                // set background
                view.setBackgroundResource(R.drawable.shape_rating_count)

                llCounts.addView(view, layoutParams)
            }
        }

        tvWish.text = "${getPrettyNumber(jsonObject.optInt("wish_count"))}人想看"
        tvDone.text = "${getPrettyNumber(jsonObject.optInt("done_count"))}人看过"
    }

    fun getPrettyNumber(number: Int): String{
        if(number < 10000)
            return number.toString()
        return String.format("%.1f万", number.toFloat() / 10000)
    }
}