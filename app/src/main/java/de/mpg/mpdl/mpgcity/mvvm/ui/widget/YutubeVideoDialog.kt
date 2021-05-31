package de.mpg.mpdl.mpgcity.mvvm.ui.widget

import android.content.Context
import android.content.res.Configuration
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import de.mpg.mpdl.mpgcity.R
import de.mpg.mpdl.mpgcity.mvvm.ui.activity.MainActivity

class YutubeVideoDialog(context: Context) : AlertDialog(context) {
    private var player:YouTubePlayer? = null
    private var playerView:YouTubePlayerView? = null
    private var url:String? = null
    private var ratio:Double = 1.0
    private var listener: DismissListener? = null


    fun show(newConfig: Configuration){
        super.show()
        val lp = window?.attributes
        var curWidth: Int
        var curHeight: Int
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            lp?.gravity = Gravity.CENTER
            curWidth = (MainActivity.windowWidth*0.95).toInt()
            curHeight = (MainActivity.windowWidth*0.95/ratio).toInt()
        }else{
            curWidth = (MainActivity.windowWidth*0.95*ratio).toInt()
            curHeight = (MainActivity.windowWidth*0.95).toInt()
        }
        playerView?.layoutParams = ViewGroup.LayoutParams(curWidth,curHeight)
        lp?.gravity = Gravity.CENTER
        lp?.width = curWidth
        lp?.height = curHeight

        window?.attributes = lp
    }

    override fun show() {
        show(context.resources.configuration)
    }

    override fun dismiss() {
        super.dismiss()
        player?.pause()
        playerView?.release()
        listener?.onDismiss()
    }

    open class VideoDialogBuilder(context: Context) : Builder(context){
        private var url:String? = null
        private var ratio = 1.0
        lateinit var webPopupRootView: ConstraintLayout
        lateinit var playerView:YouTubePlayerView
        var listener: DismissListener? = null

        fun setUrl(url:String):VideoDialogBuilder{
            this.url = url
            return this
        }

        fun setRatio(ratio:Double): VideoDialogBuilder {
            this.ratio = ratio
            return this
        }

        fun setDismissListener(listener: DismissListener): VideoDialogBuilder {
            this.listener = listener
            return this
        }

        override fun create(): YutubeVideoDialog {
            val dialog = YutubeVideoDialog(context)
            webPopupRootView = View.inflate(context, R.layout.popup_video,null) as ConstraintLayout
            playerView = webPopupRootView.findViewById(R.id.player_view)
//            playerView.inflateCustomPlayerUi(R.layout.ayp_empty_layout)
//            val iFramePlayerOptions = IFramePlayerOptions.Builder()
//                    .controls(0)
//                    .rel(0)
//                    .ivLoadPolicy(1)
//                    .ccLoadPolicy(1)
//                    .build()
//
//            playerView.initialize(object : AbstractYouTubePlayerListener() {
//                override fun onReady(youTubePlayer: YouTubePlayer) {
//                    dialog.player = youTubePlayer
//                    youTubePlayer.loadVideo("X2yDs5FHDok",0f)
//                }
//            }, true, iFramePlayerOptions)

            playerView.addYouTubePlayerListener(object :AbstractYouTubePlayerListener(){
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    dialog.player = youTubePlayer
                    youTubePlayer.loadVideo("X2yDs5FHDok",0f)
                }
            })

            dialog.setView(webPopupRootView)
            dialog.url = url
            dialog.ratio = ratio
            dialog.listener = listener
            return dialog
        }

    }

    open interface DismissListener{
        fun onDismiss()
    }
}