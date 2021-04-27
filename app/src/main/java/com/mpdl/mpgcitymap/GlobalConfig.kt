package com.mpdl.mpgcitymap

import android.app.Application
import com.fyt.mvvm.globalsetting.IGlobalConfig
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
class GlobalConfig: IGlobalConfig{
    val baseUrl = "https://www.wanandroid.com/"

    override fun configBaseUrl(): String = baseUrl

    override fun configOkHttpClient(application: Application,
                                    builder: OkHttpClient.Builder): OkHttpClient.Builder {

        return builder
            .writeTimeout(3000,TimeUnit.MILLISECONDS)
            .connectTimeout(3000,TimeUnit.MILLISECONDS)
            .readTimeout(3000,TimeUnit.MILLISECONDS)
    }

}