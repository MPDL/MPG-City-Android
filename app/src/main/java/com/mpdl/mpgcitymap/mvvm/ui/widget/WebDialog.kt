package com.mpdl.mpgcitymap.mvvm.ui.widget

import android.content.Context
import android.content.res.Configuration
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import com.mpdl.mpgcitymap.R
import com.mpdl.mpgcitymap.mvvm.ui.activity.MainActivity

class WebDialog(context: Context) : AlertDialog(context) {
    private var webParent:LinearLayout? = null
    private var webView:WebView? = null
    private var url:String? = null

    override fun show() {
        super.show()
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = wm.defaultDisplay.width - (wm.defaultDisplay.width*0.2).toInt()
        val height = wm.defaultDisplay.height - (wm.defaultDisplay.height*0.2).toInt()

        val lp = window?.attributes
        lp?.gravity = Gravity.CENTER
        lp?.width = width
        lp?.height = height
        window?.attributes = lp
    }

    override fun dismiss() {
        super.dismiss()
        webParent?.removeAllViews()
    }

    open class WebDialogBuilder(context: Context) : Builder(context){
        private var url:String? = null
        lateinit var webPopupRootView: LinearLayout
        var webProgressBar: ProgressBar? = null
        var webParent:LinearLayout? = null

        fun setUrl(url:String):WebDialogBuilder{
            this.url = url
            return this
        }

        override fun create(): WebDialog {
            val dialog = WebDialog(context)

            webPopupRootView = View.inflate(context, R.layout.popup_web,null) as LinearLayout
            webProgressBar = webPopupRootView.findViewById(R.id.progressbar_web)
            webParent = webPopupRootView.findViewById(R.id.web_parent)

            val webView = MainActivity.createWebView(url!!,context)
            webParent?.addView(webView)
            webView.webChromeClient = object : WebChromeClient(){
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    if (newProgress > 99){
                        webProgressBar?.visibility = View.GONE
                    }else{
                        webProgressBar?.visibility = View.VISIBLE
                        webProgressBar?.progress = newProgress
                    }
                }
            }
            dialog.setView(webPopupRootView)
            dialog.webParent = webParent
            dialog.webView = webView
            dialog.url = url
            webView.loadUrl(url!!)
            return dialog
        }

    }
}