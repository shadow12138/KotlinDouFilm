package com.example.kotlin_dou.adapter

import android.graphics.Color
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

class DiscussAdapter(private val dataList: List<Map<String, String>>) :
    RecyclerView.Adapter<DiscussAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_discuss, null)
        view.layoutParams = RecyclerView.LayoutParams(-1, -2)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.fillData(dataList[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ivResponse = view.findViewById<ImageView>(R.id.iv_response)
        private val tvResponse = view.findViewById<TextView>(R.id.tv_response)
        private val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        private val ivAvatar = view.findViewById<ImageView>(R.id.iv_avatar)
        private val tvNickname = view.findViewById<TextView>(R.id.tv_nickname)
        private val tvDate = view.findViewById<TextView>(R.id.tv_date)

        fun fillData(data: Map<String, String>) {

            data["avatar"]?.let { ImageUtils.loadImage(it, ivAvatar) }
            tvTitle.text = data["title"]
            tvDate.text = data["date"]
            data["nickname"]?.let {
                tvNickname.text = it.replace(Regex("\\s"), "")
            }

            data["response"]?.let {
                val count = it.toInt()
                tvResponse.text = if (count == 0) "" else it

                val color = when (count) {
                    0 -> "#DDDDDD"
                    in 1..50 -> "#F5D393"
                    in 51..100 -> "#EEB478"
                    in 101..500 -> "#EA9570"
                    in 501..1000 -> "#E17362"
                    else -> "#D74C43"
                }
                ivResponse.setColorFilter(Color.parseColor(color))
            }
        }
    }
}