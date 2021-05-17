package com.mpdl.mpgcitymap.mvvm.ui.activity

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.Toast
import com.fyt.mvvm.base.BaseActivity
import com.google.ar.core.AugmentedImage
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.ux.ArFragment
import com.mpdl.mpgcitymap.R
import com.mpdl.mpgcitymap.mvvm.ui.fragment.CustomArFragment
import com.mpdl.mpgcitymap.mvvm.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.util.*

class MainActivity : BaseActivity<MainViewModel>(){

    override fun initViewModel(): MainViewModel = getViewModel()

    override fun initView(savedInstanceState: Bundle?): Int = R.layout.activity_main

    override fun initData(savedInstanceState: Bundle?) {}

    companion object{
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
            webView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            webView.webViewClient = WebViewClient()
            urlMap[url] = webView
            return webView
        }
    }
}