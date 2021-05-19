package com.mpdl.mpgcitymap.mvvm.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import com.fyt.mvvm.base.BaseActivity
import com.mpdl.mpgcitymap.R
import com.mpdl.mpgcitymap.mvvm.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import timber.log.Timber
import java.util.*
import kotlin.Boolean as Boolean

class MainActivity : BaseActivity<MainViewModel>(){


    override fun initViewModel(): MainViewModel = getViewModel()

    override fun initView(savedInstanceState: Bundle?): Int = R.layout.activity_main

    override fun initData(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val metric = DisplayMetrics()
        val mWindowManager: WindowManager =
            this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mWindowManager.defaultDisplay.getMetrics(metric)
        val width: Int = metric.widthPixels
        val height: Int = metric.heightPixels

        isPad = height/width.toDouble() < 1.4
        Timber.d("height$height/width$width=${height/width.toDouble()}")

    }

    @SuppressLint("RestrictedApi")
    override fun onBackPressed() {
        val home = Intent(Intent.ACTION_MAIN)
        home.addCategory(Intent.CATEGORY_HOME)
        startActivity(home)
    }

    companion object{
        private var isPad: Boolean = false
        var SCAN_IMAGE_NAMES = listOf("scan_it",
            "scan_it_01","scan_it_02","scan_it_03","scan_it_04",
            "scan_it_05","scan_it_06","scan_it_07","scan_it_08",
            "scan_it_09","scan_it_10","scan_it_11","scan_it_12",
            "scan_it_13", "scan_it_14","scan_it_15","scan_it_16")


        private val urlMap = HashMap<String,WebView>()

        fun createWebView(url:String,context: Context): WebView {
            if (urlMap[url] != null){
                return urlMap[url]!!
            }
            val webView = WebView(context)
            webView.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
//            webView.settings.saveFormData = true
            webView.settings.javaScriptEnabled = true
            webView.settings.setSupportZoom(true)
            webView.settings.domStorageEnabled = true
            webView.settings.loadWithOverviewMode = true
            webView.settings.useWideViewPort = true
            webView.settings.allowUniversalAccessFromFileURLs = true
            webView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            webView.webViewClient = WebViewClient()
            urlMap[url] = webView
            return webView
        }

        fun getRawUri(context: Context,res: Int): Uri {
            return Uri.parse("android.resource://" + context.packageName+ "/" + res)
        }

        fun isPad():Boolean{
            return isPad
        }
    }
}