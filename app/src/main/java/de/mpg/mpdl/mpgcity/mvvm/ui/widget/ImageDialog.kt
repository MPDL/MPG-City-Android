package de.mpg.mpdl.mpgcity.mvvm.ui.widget

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import de.fyt.mvvm.common.AppManager
import de.mpg.mpdl.mpgcity.R
import de.mpg.mpdl.mpgcity.mvvm.ui.activity.MainActivity
import timber.log.Timber

class ImageDialog(context: Context) : AlertDialog(context) {
    private var webParent:FrameLayout? = null
    private var webView:WebView? = null
    private var url:String? = null
    private var isShowCustomView = false
    private var ratio = 1.0


    fun show(newConfig: Configuration){
        super.show()
        val lp = window?.attributes
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            lp?.gravity = Gravity.CENTER
            lp?.width = (MainActivity.windowWidth*0.95).toInt()
            lp?.height = (MainActivity.windowWidth*0.95/ratio).toInt()
        }else{
            lp?.gravity = Gravity.CENTER
            lp?.width = (MainActivity.windowWidth*0.95*ratio).toInt()
            lp?.height = (MainActivity.windowWidth*0.95).toInt()
        }
        window?.attributes = lp
    }

    override fun show() {
        show(context.resources.configuration)
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
        var webParent:FrameLayout? = null
        var ratio:Double = 1.0


        fun setUrl(url:String):ImageDialogBuilder{
            this.url = url
            return this
        }

        fun setRatio(ratio:Double):ImageDialogBuilder{
            this.ratio = ratio
            return this
        }

        override fun create(): ImageDialog {
            val dialog = ImageDialog(context)

            webPopupRootView = View.inflate(context, R.layout.popup_image,null) as LinearLayout
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
                    dialog?.show()
                    super.onShowCustomView(view, callback)
                }

                override fun onHideCustomView() {
                    AppManager.getAppManager()?.getTopActivity()?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    AppManager.getAppManager()?.getTopActivity()?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR

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
            dialog.ratio = ratio
            webView.loadUrl(url!!)
            return dialog
        }

    }
}