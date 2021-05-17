package com.mpdl.mpgcitymap.mvvm.ui.widget

import android.content.Context
import android.content.pm.ActivityInfo
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import com.fyt.mvvm.common.AppManager
import com.mpdl.mpgcitymap.R
import com.mpdl.mpgcitymap.mvvm.ui.activity.MainActivity
import timber.log.Timber

class ImageDialog(context: Context) : AlertDialog(context) {
    private var webParent:LinearLayout? = null
    private var webView:WebView? = null
    private var url:String? = null
    private var isShowCustomView = false

    override fun show() {
        super.show()
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = (wm.defaultDisplay.width*0.85).toInt()
        val height = LinearLayout.LayoutParams.WRAP_CONTENT

        webParent?.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,610)
        val lp = window?.attributes
        lp?.gravity = Gravity.CENTER
        lp?.width = width
        lp?.height = height
        window?.attributes = lp
    }

    private fun showCustomView(){
        super.show()
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = wm.defaultDisplay.width
        val height = LinearLayout.LayoutParams.MATCH_PARENT
        Timber.e("width=$width")
        webParent?.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(wm.defaultDisplay.height*0.8).toInt())

        val lp = window?.attributes
        lp?.gravity = Gravity.CENTER
        lp?.width = width
        lp?.height = height
        window?.attributes = lp
    }

    override fun dismiss() {
        super.dismiss()
        if (isShowCustomView){
            AppManager.getAppManager()?.getTopActivity()?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        webParent?.removeAllViews()
    }

    open class ImageDialogBuilder(context: Context) : Builder(context){
        private var url:String? = null
        lateinit var webPopupRootView: LinearLayout
        var webProgressBar: ProgressBar? = null
        var webParent:LinearLayout? = null


        fun setUrl(url:String):ImageDialogBuilder{
            this.url = url
            return this
        }

        override fun create(): ImageDialog {
            val dialog = ImageDialog(context)

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

                override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                    if (dialog?.isShowCustomView){
                        onHideCustomView()
                        return
                    }
                    dialog?.isShowCustomView = true
                    AppManager.getAppManager()?.getTopActivity()?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    webParent?.removeAllViews()
                    webParent?.addView(view)
                    dialog?.showCustomView()
                    super.onShowCustomView(view, callback)
                }

                override fun onHideCustomView() {
                    AppManager.getAppManager()?.getTopActivity()?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    webParent?.removeAllViews()
                    webParent?.addView(webView)
                    dialog?.show()
                    dialog?.isShowCustomView = false
                    super.onHideCustomView()
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