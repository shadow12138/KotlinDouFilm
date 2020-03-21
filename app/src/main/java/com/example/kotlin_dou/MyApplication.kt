package com.example.kotlin_dou

import android.app.Application
import android.content.Context
import com.zhy.http.okhttp.OkHttpUtils
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class MyApplication: Application() {
    companion object{
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(10000L, TimeUnit.MILLISECONDS)
            .readTimeout(10000L, TimeUnit.MILLISECONDS)
            .build()
        OkHttpUtils.initClient(okHttpClient)

        context = applicationContext
    }
}