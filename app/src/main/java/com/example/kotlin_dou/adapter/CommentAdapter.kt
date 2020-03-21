package com.example.kotlin_dou.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_dou.R
import com.example.kotlin_dou.utils.ImageUtils
import com.hedgehog.ratingbar.RatingBar
import org.json.JSONArray
import org.json.JSONObject

class CommentAdapter(private val jsonArray: JSONArray) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    fun append(array: JSONArray) {
        for (i in 0 until array.length()) {
            jsonArray.put(array.optJSONObject(i))
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_comment, null)
        view.layoutParams = RecyclerView.LayoutParams(-1, -2)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = jsonArray.length()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val jsonObject = jsonArray.optJSONObject(position)
        holder.fillData(jsonObject, position != jsonArray.length() - 1)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val iv = view.findViewById<ImageView>(R.id.iv_avatar)
        private val tvUsername = view.findViewById<TextView>(R.id.tv_user_name)
        private val tvDate = view.findViewById<TextView>(R.id.tv_date)
        private val ratingBar = view.findViewById<RatingBar>(R.id.rating_bar)
        private val tvContent = view.findViewById<TextView>(R.id.tv_content)
        private val tvLike = view.findViewById<TextView>(R.id.tv_like)
        private val vDivider = view.findViewById<View>(R.id.v_divider)

        fun fillData(jsonObject: JSONObject, showDivider: Boolean) {

            vDivider.visibility = if (showDivider) View.VISIBLE else View.GONE

            jsonObject.run {
                optJSONObject("user")?.run {
                    ImageUtils.loadImage(optString("avatar"), iv)
                    tvUsername.text = optString("name")
                }

                optJSONObject("rating")?.run {
                    ratingBar.setStar(optString("star_count").toFloat())
                }

                tvContent.text = optString("comment")
                tvLike.text = optString("vote_count")
                tvDate.text = prettyDate(optString("create_time"))
            }
        }

        private fun prettyDate(date: String): String {
            val g1 = date.split(" ")[0]
            val dates = g1.split("-")
            return "${dates[1]}月${dates[2]}日"
        }
    }

}