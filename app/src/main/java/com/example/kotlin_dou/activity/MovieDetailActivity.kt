package com.example.kotlin_dou.activity

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.NestedScrollView
import com.example.kotlin_dou.R
import com.example.kotlin_dou.holder.*
import com.example.kotlin_dou.utils.Utils
import com.example.kotlin_dou.view.slide_layout.slide.configSlideChildTypeHeader
import kotlinx.android.synthetic.main.activity_movie_detail.*
import org.json.JSONObject

class MovieDetailActivity: BaseActivity(), AppbarHolder.Callback {
    override fun onBackClick() {
        if(slide_layout.isSliderCollapsed()){
            finish()
        } else{
            slide_layout.quickReturn()
        }
    }

    val detail = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        initViews()
        requestData()
    }

    private fun initViews(){
        nsv_container.configSlideChildTypeHeader()
        v_slider.setMinVerticalMargin(resources.getDimensionPixelSize(R.dimen.bottom_sheet_height))
        v_header.minimumHeight = resources.getDimensionPixelSize(R.dimen.bottom_sheet_height)
        slide_layout.expandHeader()
    }

    private fun requestData(){
        val subjectId = intent.getStringExtra("id")
        getData(detail, "https://m.douban.com/rexxar/api/v2/movie/${subjectId}?ck=x6Ls&for_mobile=1")
    }

    override fun networkResponse(what: Int, response: String?) {
        super.networkResponse(what, response)
        when(what){
            detail -> {
                setData(JSONObject(response))
            }
        }
    }

    private fun setData(jsonObject: JSONObject){
        val darkColor = "#${jsonObject.optJSONObject("color_scheme").optString("primary_color_dark")}"
        val lightColor = "#${jsonObject.optJSONObject("color_scheme").optString("primary_color_light")}"
        val gradientDrawable = GradientDrawable()
        gradientDrawable.colors = intArrayOf(Color.parseColor(lightColor), Color.parseColor(darkColor))
        v_root.setBackgroundDrawable(gradientDrawable)

        val basicInfoHolder = BasicInfoHolder(v_basic_info)
        basicInfoHolder.fillData(jsonObject)

        val ratingHolder = MovieRatingHolder(v_rating)
        ratingHolder.fillData(jsonObject)

        val tagHolder = TagHolder(v_tags)
        tagHolder.fillData(jsonObject.optJSONArray("tags"))

        val introHolder = IntroductionHolder(v_introduction)
        introHolder.fillData(jsonObject)

        val actorHolder = ActorHolder(v_actors)
        actorHolder.fillData(jsonObject)

        val commentHolder = CommentHolder(v_comment)
        commentHolder.requestData(jsonObject.optString("id"))

        val bottomHolder = BottomHolder(v_review, supportFragmentManager)
        bottomHolder.fillData(jsonObject.optString("id"))

        val appbarHolder = AppbarHolder(v_appbar, this)
        appbarHolder.fillData(jsonObject)

        nsv_container.viewTreeObserver.addOnScrollChangedListener {
            if(nsv_container.scrollY > Utils.dip2px(50f)){
                appbarHolder.showInfo()
            } else{
                appbarHolder.hideInfo()
            }
        }

        slide_layout.doOnSliderExpandChange {
            if(it){
                
            }
        }

    }

}