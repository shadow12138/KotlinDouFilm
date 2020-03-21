package com.example.kotlin_dou.activity

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.StringCallback
import okhttp3.Call
import java.lang.Exception

open class BaseActivity : AppCompatActivity() {
    val TAG = "BaseActivity"

    fun getData(what: Int, url: String){
        val headers = mutableMapOf(
            "User-Agent" to "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Mobile Safari/537.36",
            "Sec-Fetch-Dest" to "empty",
            "Referer" to "https://m.douban.com/"
        )
        OkHttpUtils.get()
            .url(url)
            .headers(headers)
            .build()

            .execute(object: StringCallback(){
                override fun onResponse(response: String?, id: Int) {
                    networkResponse(what, response)
                }

                override fun onError(call: Call?, e: Exception?, id: Int) {
                    Log.i(TAG, e.toString())
                }
            })
    }

    open fun networkResponse(what: Int, response: String?){

    }

}