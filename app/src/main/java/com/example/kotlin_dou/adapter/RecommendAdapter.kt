package com.example.kotlin_dou.adapter

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_dou.R
import com.example.kotlin_dou.activity.MovieDetailActivity
import com.example.kotlin_dou.utils.ImageUtils
import com.hedgehog.ratingbar.RatingBar
import org.json.JSONArray
import org.json.JSONObject

class RecommendAdapter(private val jsonArray: JSONArray): RecyclerView.Adapter<RecommendAdapter.ViewHolder>() {
    fun append(array: JSONArray){
        for(i in 0 until array.length()){
            jsonArray.put(array.optJSONObject(i))
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_recommend, null)
        view.layoutParams = RecyclerView.LayoutParams(-1, -2)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int  = jsonArray.length()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val jsonObject = jsonArray.optJSONObject(position)
        holder.fillData(jsonObject, position != jsonArray.length() - 1)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        private val iv = view.findViewById<ImageView>(R.id.iv)
        private val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        private val tvDesc = view.findViewById<TextView>(R.id.tv_desc)
        private val ratingBar = view.findViewById<RatingBar>(R.id.rating_bar)
        private val tvScore = view.findViewById<TextView>(R.id.tv_score)
        private val tvReason = view.findViewById<TextView>(R.id.tv_recommend_reason)
        private val vDivider = view.findViewById<View>(R.id.v_divider)

        fun fillData(jsonObject: JSONObject, showDivider: Boolean){

            vDivider.visibility = if(showDivider) View.VISIBLE else View.GONE

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