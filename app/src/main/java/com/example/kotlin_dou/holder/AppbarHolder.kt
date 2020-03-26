package com.example.kotlin_dou.holder

import android.animation.ObjectAnimator
import android.graphics.Color
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.kotlin_dou.R
import com.example.kotlin_dou.activity.BaseActivity
import com.example.kotlin_dou.utils.ImageUtils
import com.hedgehog.ratingbar.RatingBar
import org.json.JSONObject

class AppbarHolder(view: View,val  callback: Callback): BaseHolder(view) {
    interface Callback{
        fun onBackClick()
    }

    private val tvScore = view.findViewById<TextView>(R.id.tv_score)
    private val ratingBar = view.findViewById<RatingBar>(R.id.rating_bar)
    private val tvTitle = view.findViewById<TextView>(R.id.tv_title)
    private val iv = view.findViewById<ImageView>(R.id.iv)
    private val imbBack = view.findViewById<ImageButton>(R.id.imb_back)

    private val vInfo = view.findViewById<View>(R.id.v_info)
    private val vHint = view.findViewById<View>(R.id.tv_hint)

    fun fillData(jsonObject: JSONObject){
        jsonObject.optJSONObject("rating")?.run {
            ratingBar.setStar(optString("star_count").toFloat())
            tvScore.text = optString("value")
        }

        jsonObject.optJSONObject("pic")?.run {
            ImageUtils.loadImage(optString("normal"), iv)
        }

        tvTitle.text = jsonObject.optString("title")

        imbBack.setOnClickListener {
            callback.onBackClick()
        }

    }

    fun showInfo(){
        if(vInfo.alpha == 0f) {
            ObjectAnimator.ofFloat(vInfo, "alpha", 0f, 1f).start()
            ObjectAnimator.ofFloat(vInfo, "y", 40f, 0f).start()
            ObjectAnimator.ofFloat(vHint, "alpha", 1f, 0f).start()
        }
    }

    fun hideInfo(){
        if(vInfo.alpha == 1f) {
            ObjectAnimator.ofFloat(vHint, "alpha", 0f, 1f).start()
            ObjectAnimator.ofFloat(vHint, "y", -40f, 0f).start()
            ObjectAnimator.ofFloat(vInfo, "alpha", 1f, 0f).start()
        }
    }
}