package com.example.kotlin_dou.holder

import android.animation.ObjectAnimator
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.kotlin_dou.R
import com.example.kotlin_dou.fragment.DiscussFragment
import com.example.kotlin_dou.fragment.ReviewFragment

class BottomHolder(view: View, val fragmentManager: FragmentManager) : BaseHolder(view) {
    private val vp = view.findViewById<ViewPager>(R.id.vp)
    private val tvReview = view.findViewById<TextView>(R.id.tv_review)
    private val tvDiscuss = view.findViewById<TextView>(R.id.tv_discuss)
    private val vIndicator = view.findViewById<View>(R.id.v_indicator)
    private val tvDiscussCount = view.findViewById<TextView>(R.id.tv_discuss_counts)
    private val tvReviewCount = view.findViewById<TextView>(R.id.tv_review_counts)

    private val get = 0
    private var subjectId = ""

    fun requestData() {
        getPcData(get, "https://movie.douban.com/subject/${subjectId}/")
    }

    fun fillData(id: String) {
        this.subjectId = id

        val fragments = arrayListOf(
            ReviewFragment.newInstance(subjectId),
            DiscussFragment.newInstance(subjectId)
        )
        vp.adapter = object : FragmentPagerAdapter(fragmentManager) {
            override fun getCount(): Int = fragments.size
            override fun getItem(position: Int): Fragment = fragments[position]
        }

        tvReview.setOnClickListener {
            ObjectAnimator.ofFloat(vIndicator, "x", vIndicator.x, it.x).start()
            tvReview.setTextColor(Color.BLACK)
            tvDiscuss.setTextColor(Color.parseColor("#666666"))
            vp.currentItem = 0
        }

        tvDiscuss.setOnClickListener {
            ObjectAnimator.ofFloat(vIndicator, "x", vIndicator.x, it.x).start()
            tvDiscuss.setTextColor(Color.BLACK)
            tvReview.setTextColor(Color.parseColor("#666666"))
            vp.currentItem = 1
        }

        vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    tvReview.performClick()
                } else {
                    tvDiscuss.performClick()
                }
            }
        })

        requestData()
    }

    override fun networkResponse(what: Int, response: String?) {
        super.networkResponse(what, response)

        response?.run {
            val reviewRegex = Regex("<a href=\"reviews\">全部 (\\d+) 条</a>")
            reviewRegex.find(response)?.let {
                tvReviewCount.text = it.groupValues[1]
            }

            val commentRegex =
                Regex("去这部影片的讨论区（全部(\\d+)条）")
            commentRegex.find(response)?.let {
                tvDiscussCount.text = it.groupValues[1]
            }

        }


    }

}