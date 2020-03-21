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

class ReviewAdapter(private val dataList: List<Map<String, String>>) :
    RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_review, null)
        view.layoutParams = RecyclerView.LayoutParams(-1, -2)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.fillData(dataList[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val contentStartIndex = " abs-clamp clamp-2\">".length
        private val ivAvatar = view.findViewById<ImageView>(R.id.iv_avatar)
        private val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        private val tvContent = view.findViewById<TextView>(R.id.tv_content)
        private val ratingBar = view.findViewById<RatingBar>(R.id.rating_bar)
        private val tvUserName = view.findViewById<TextView>(R.id.tv_user_name)
        private val tvResponse = view.findViewById<TextView>(R.id.tv_response)

        fun fillData(data: Map<String, String>) {

            data["avatar"]?.let { ImageUtils.loadImage(it, ivAvatar) }
            data["rating"]?.let { ratingBar.setStar(it.toFloat() / 20)}
            tvTitle.text = data["title"]
            tvContent.text = data["content"]?.substring(contentStartIndex)
            tvUserName.text = data["nickname"]
            tvResponse.text = data["response"]

        }
    }
}