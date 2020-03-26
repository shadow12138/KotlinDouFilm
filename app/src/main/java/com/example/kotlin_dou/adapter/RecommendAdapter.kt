package com.example.kotlin_dou.adapter

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.kotlin_dou.R
import com.example.kotlin_dou.activity.MovieDetailActivity
import com.example.kotlin_dou.utils.ImageUtils
import com.hedgehog.ratingbar.RatingBar
import org.json.JSONArray
import org.json.JSONObject

class RecommendAdapter(private val dataList: MutableList<JSONObject>) :
    BaseQuickAdapter<JSONObject, RecommendAdapter.ViewHolder>(R.layout.item_recommend, dataList), LoadMoreModule {
    override fun convert(helper: ViewHolder, item: JSONObject) {
        helper.fillData(item, dataList.indexOf(item) != 0)
    }


    class ViewHolder(val view: View) : BaseViewHolder(view) {
        private val iv = view.findViewById<ImageView>(R.id.iv)
        private val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        private val tvDesc = view.findViewById<TextView>(R.id.tv_desc)
        private val ratingBar = view.findViewById<RatingBar>(R.id.rating_bar)
        private val tvScore = view.findViewById<TextView>(R.id.tv_score)
        private val tvReason = view.findViewById<TextView>(R.id.tv_recommend_reason)
        private val vDivider = view.findViewById<View>(R.id.v_divider)

        fun fillData(jsonObject: JSONObject, showDivider: Boolean) {

            vDivider.visibility = if (showDivider) View.VISIBLE else View.INVISIBLE

            jsonObject.run {
                optJSONObject("pic")?.let {
                    ImageUtils.loadImage(it.optString("normal"), iv)
                }
                tvTitle.text = optString("title")
                tvDesc.text = optString("card_subtitle")

                val score = optJSONObject("rating").optString("value")
                ratingBar.setStar(score.toFloat() / 2)
                tvScore.text = score

                view.setOnClickListener {
                    val intent = Intent(view.context, MovieDetailActivity::class.java)
                    intent.putExtra("id", optString("id"))
                    view.context.startActivity(intent)
                }
            }

        }
    }
}