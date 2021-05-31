package de.mpg.mpdl.mpgcity.mvvm.ui.activity

import android.content.res.Configuration
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import de.mpg.mpdl.mpgcity.R

class DialogActivity: AppCompatActivity() {

    lateinit var playerView: YouTubePlayerView
    val ratio = 4.0/3.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)
        config(resources.configuration)

        playerView = findViewById(R.id.player_view)
        playerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(intent.getStringExtra("videoId")!!,0f)
            }
        })
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        config(newConfig)
    }

    fun config(newConfig: Configuration){
        val lp = window?.attributes
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            lp?.gravity = Gravity.CENTER
            lp?.width = MainActivity.windowWidth
            lp?.height = WindowManager.LayoutParams.WRAP_CONTENT
        }else{
            lp?.gravity = Gravity.CENTER
            lp?.width = (MainActivity.windowWidth*ratio).toInt()
            lp?.height = WindowManager.LayoutParams.WRAP_CONTENT
        }
        window?.attributes = lp
    }
}