package tech.wcw.simple

import android.app.Application
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import tech.wcw.support.net.RetrofitFactory
import tech.wcw.support.net.progress.ProgressManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitFactory.getInstance().init(
            "http://v.juhe.cn/",
            HttpLoggingInterceptor.Level.BASIC,
            netInterceptors = listOf<Interceptor>(
                ProgressManager.getInstance().generateInterceptor()
            )
        )
    }
}