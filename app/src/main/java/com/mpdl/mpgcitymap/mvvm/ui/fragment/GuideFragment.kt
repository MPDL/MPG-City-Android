package com.mpdl.mpgcitymap.mvvm.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.fyt.mvvm.common.Preference
import com.mpdl.mpgcitymap.BuildConfig
import com.mpdl.mpgcitymap.R
import com.mpdl.mpgcitymap.mvvm.ui.activity.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GuideFragment : Fragment(){
    private lateinit var videoView: VideoView
    private lateinit var btnGo: TextView
    private lateinit var ivSplash:ImageView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_guide,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videoView = view.findViewById(R.id.video_view)
        btnGo = view.findViewById(R.id.btn_go)
        ivSplash = view.findViewById(R.id.iv_splash)
        if (MainActivity.isPad()){
            ivSplash.setImageResource(R.mipmap.splash_4_3)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        videoView.setOnCompletionListener { videoView.start() }
        videoView.requestFocus()
        lifecycleScope.launch(Dispatchers.IO){
            if (MainActivity.isPad()){
                videoView.setVideoURI(MainActivity.getRawUri(requireContext(),R.raw.guide_video_4_3))
            }else{
                videoView.setVideoURI(MainActivity.getRawUri(requireContext(),R.raw.guide_video_16_9))
            }
            delay(1300)
            withContext(Dispatchers.Main){
                videoView.visibility = View.VISIBLE
                videoView.start()
                btnGo.visibility = View.VISIBLE


                val alphaAniHide = AlphaAnimation(1f, 0f)
                alphaAniHide.duration = 500
                ivSplash.startAnimation(alphaAniHide)
                alphaAniHide.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationRepeat(animation: Animation?) {
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        ivSplash.visibility = View.INVISIBLE
                    }

                    override fun onAnimationStart(animation: Animation?) {
                    }

                })

            }
        }

        btnGo.setOnClickListener { v ->
            Preference.preferences.edit().putString("version", BuildConfig.VERSION_NAME).apply()
            Navigation.findNavController(requireView()).navigate(R.id.action_guideFragment_to_MainFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        videoView.resume()
    }

    override fun onPause() {
        super.onPause()
        videoView.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        videoView.stopPlayback()
    }



}