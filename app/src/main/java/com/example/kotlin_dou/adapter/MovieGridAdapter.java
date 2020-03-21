package com.example.kotlin_dou.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kotlin_dou.R;
import com.example.kotlin_dou.utils.ImageUtils;
import com.hedgehog.ratingbar.RatingBar;

import org.json.JSONArray;
import org.json.JSONObject;

public class MovieGridAdapter extends BaseAdapter {
    private JSONArray array;

    public MovieGridAdapter(JSONArray array){
        this.array = array;
    }

    @Override
    public int getCount() {
        return array.length() > 6 ? 6 : array.length();
    }

    @Override
    public Object getItem(int i) {
        return array.optJSONObject(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = View.inflate(viewGroup.getContext(), R.layout.item_grid_movie, null);
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        if(holder == null){
            holder = new ViewHolder(view);
        }
        holder.fillData(array.optJSONObject(i));

        return view;
    }

    class ViewHolder{
        private TextView tvTitle;
        private ImageView iv;
        private RatingBar ratingBar;
        private TextView tvScore;

        ViewHolder(View view){
            tvTitle = view.findViewById(R.id.tv_title);
            iv = view.findViewById(R.id.iv);
            ratingBar = view.findViewById(R.id.rating_bar);
            tvScore = view.findViewById(R.id.tv_score);
        }

        void fillData(JSONObject jsonObject){
            tvTitle.setText(jsonObject.optString("title"));
            ImageUtils.Companion.loadImage(jsonObject.optJSONObject("cover").optString("url"), iv);

            String score = jsonObject.optJSONObject("rating").optString("value");
            ratingBar.setStar(Float.parseFloat(score) / 2);
            tvScore.setText(score);
        }
    }
}
