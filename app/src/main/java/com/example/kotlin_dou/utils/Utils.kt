package com.example.kotlin_dou.utils

import android.content.Context
import com.example.kotlin_dou.MyApplication

class Utils {

    companion object{

        fun dip2px(dip: Float): Int{
            val density = getContext().resources.displayMetrics.density
            return (dip * density + 0.5f).toInt()
        }

        fun getContext(): Context{
            return MyApplication.context
        }
    }



}