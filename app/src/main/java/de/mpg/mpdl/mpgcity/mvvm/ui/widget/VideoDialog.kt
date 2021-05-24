package de.mpg.mpdl.mpgcity.mvvm.ui.widget

import android.content.Context
import android.content.res.Configuration
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import de.mpg.mpdl.mpgcity.R
import de.mpg.mpdl.mpgcity.mvvm.ui.activity.MainActivity

class VideoDialog(context: Context) : AlertDialog(context) {
    private var webParent:LinearLayout? = null
    private var webView:WebView? = null
    private var url:String? = null
    private var ratio:Double = 1.0


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
        webParent?.removeAllViews()
    }

    open class VideoDialogBuilder(context: Context) : Builder(context){
        private var url:String? = null
        private var ratio = 1.0
        lateinit var webPopupRootView: LinearLayout
        var webProgressBar: ProgressBar? = null
        var webParent:LinearLayout? = null

        fun setUrl(url:String):VideoDialogBuilder{
            this.url = url
            return this
        }

        fun setRatio(ratio:Double): VideoDialogBuilder {
            this.ratio = ratio
            return this
        }

        override fun create(): VideoDialog {
            val dialog = VideoDialog(context)
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val height = wm.defaultDisplay.height - (wm.defaultDisplay.height*0.2).toInt()

            webPopupRootView = View.inflate(context, R.layout.popup_web,null) as LinearLayout
            webProgressBar = webPopupRootView.findViewById(R.id.progressbar_web)
            webParent = webPopupRootView.findViewById(R.id.web_parent)

            val webView = MainActivity.createWebView(url!!,context)
            webView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height)
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
            dialog.ratio = ratio
            webView.loadUrl(url!!)
            return dialog
        }

    }
}