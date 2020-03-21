package com.example.kotlin_dou.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageUtils {

    companion object{
        fun loadImage(url: String, imageView: ImageView){
            Glide.with(imageView).load(url).into(imageView)
        }
    }
}