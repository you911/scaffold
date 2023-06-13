package tech.wcw.simple.vm

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import tech.wcw.scaffold.base.BaseViewModel
import tech.wcw.simple.api.ApiService
import tech.wcw.simple.model.NewsItem
import tech.wcw.support.net.progress.ProgressListener
import tech.wcw.support.net.progress.ProgressManager
import tech.wcw.support.retrofit
import tech.wcw.support.service

class IndexViewModel : BaseViewModel() {
    val countDay: LiveData<String> = MutableLiveData("32")
    var info: MutableLiveData<List<NewsItem>?> = MutableLiveData()
    var photo: MutableLiveData<Uri?> = MutableLiveData()
    val TAG = "IndexViewModel"
    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate")
    }


    override fun onPause() {
        Log.i(TAG, "onPause")
    }

    override fun onResume() {
        Log.i(TAG, "onResume")
    }

    override fun onStop() {
        Log.i(TAG, "onStop")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
        retrofit(
            request = { service(ApiService::class.java).news("top", 1) },
            success = { it ->
                if (it.isSuccess()) {
                    it.result?.let {
                        info.postValue(it.data)
                    }
                } else {
                    Log.e(TAG, it.reason)
                }
            },
            "正在加载中",
        )
        download(url = "https://img0.baidu.com/it/u=4162443464,2854908495&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }

    fun download(url: String) {
        ProgressManager.getInstance()
            .addResponseListener(url,
                object : ProgressListener {
                    override fun onProgress(written: Long, total: Long, done: Boolean) {
                        Log.e("WWW", "$written  $total $done")
                    }
                })
        retrofit(
            request = { service(ApiService::class.java).download(url) },
            success = {
                val byteStream = it.byteStream()
                byteStream.readBytes()
                byteStream.close()
            }
        )
    }
}