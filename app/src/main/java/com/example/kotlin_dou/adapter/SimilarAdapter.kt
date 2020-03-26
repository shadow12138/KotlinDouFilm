package com.example.kotlin_dou.adapter

import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_dou.R
import com.example.kotlin_dou.activity.MovieDetailActivity
import com.example.kotlin_dou.utils.ImageUtils
import com.example.kotlin_dou.utils.Utils

class SimilarAdapter(private val dataList: List<Map<String, String>>) :
    RecyclerView.Adapter<SimilarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_similar, null)
//        view.layoutParams = RecyclerView.LayoutParams(Utils.dip2px(90f), -2)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.fillData(dataList[position], position == 0, position == dataList.size - 1)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val iv = view.findViewById<ImageView>(R.id.iv)
        private val tv = view.findViewById<TextView>(R.id.tv)

        fun fillData(item: Map<String, String>, isFirst: Boolean, isLast: Boolean) {

            item["cover"]?.let {
                ImageUtils.loadImage(it, iv)
            }
            tv.text = item["title"]

            val paddingLeft = Utils.dip2px(if (isFirst) 14f else 8f)
            val paddingRight = Utils.dip2px(if(isLast) 14f else 0f)
            view.setPadding(paddingLeft, 0, paddingRight, 0)

            view.setOnClickListener {
                item["link"]?.let{
                    val r = "\\d+"
                    Regex(r).find(it)?.run {
                        val intent = Intent(this@ViewHolder.view.context, MovieDetailActivity::class.java)
                        intent.putExtra("id", value);
                        this@ViewHolder.view.context.startActivity(intent)
                    }
                }
            }
        }
    }
}