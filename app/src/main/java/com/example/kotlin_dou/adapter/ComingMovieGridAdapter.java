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

public class ComingMovieGridAdapter extends BaseAdapter {
    private JSONArray array;

    public ComingMovieGridAdapter(JSONArray array){
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
            view = View.inflate(viewGroup.getContext(), R.layout.item_coming_grid_movie, null);
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
        private TextView tvWish;
        private TextView tvDate;

        ViewHolder(View view){
            tvTitle = view.findViewById(R.id.tv_title);
            iv = view.findViewById(R.id.iv);
            tvWish = view.findViewById(R.id.tv_wish);
            tvDate = view.findViewById(R.id.tv_date);
        }

        void fillData(JSONObject jsonObject){

            tvTitle.setText(jsonObject.optString("title"));
            ImageUtils.Companion.loadImage(jsonObject.optJSONObject("cover").optString("url"), iv);
            tvWish.setText(jsonObject.optString("wish_count") + "人想看");

            String date = jsonObject.optString("release_date").replace(".", "月") + "日";
            tvDate.setText(date);
        }
    }
}
