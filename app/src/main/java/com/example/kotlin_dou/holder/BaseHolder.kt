package com.example.kotlin_dou.holder

import android.util.Log
import android.view.View
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.StringCallback
import okhttp3.Call
import java.lang.Exception

open class BaseHolder(val view: View){
    val context = view.context

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
                }
            })
    }

    fun getPcData(what: Int, url: String) {
        val headers = mutableMapOf(
            "Accept" to "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
            "User-Agent" to "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36"
        )
        OkHttpUtils.get()
            .url(url)
            .headers(headers)
            .build()

            .execute(object : StringCallback() {
                override fun onResponse(response: String?, id: Int) {
                    networkResponse(what, response)
                }

                override fun onError(call: Call?, e: Exception?, id: Int) {
                    Log.i("BaseFragment", e.toString())
                }
            })
    }

    open fun networkResponse(what: Int, response: String?){

    }
}