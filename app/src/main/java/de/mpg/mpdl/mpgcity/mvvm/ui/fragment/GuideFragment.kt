package de.mpg.mpdl.mpgcity.mvvm.ui.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.google.ar.core.ArCoreApk
import de.fyt.mvvm.common.Preference
import de.mpg.mpdl.mpgcity.BuildConfig
import de.mpg.mpdl.mpgcity.R
import de.mpg.mpdl.mpgcity.mvvm.ui.activity.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class GuideFragment : Fragment(){
    private lateinit var videoView: VideoView
    private lateinit var btnGo: TextView
    private lateinit var ivSplash:ImageView
    private var videoPosition = 0
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
        }
        maybeEnableArButton()

        btnGo.setOnClickListener { v ->
            Preference.preferences.edit().putString("version", BuildConfig.VERSION_NAME).apply()
            Navigation.findNavController(requireView()).navigate(R.id.action_guideFragment_to_MainFragment)
        }
    }


    private fun maybeEnableArButton(){
        val availability = ArCoreApk.getInstance().checkAvailability(requireContext())
        if (availability.isTransient) {
            // Continue to query availability at 5Hz while compatibility is checked in the background.
            Handler(Looper.getMainLooper()).postDelayed({
                maybeEnableArButton()
            }, 200)
        }
        showVideo(availability.isSupported)

    }

    private fun showVideo(isSupported: Boolean){
        lifecycleScope.launch(Dispatchers.IO){
            delay(1000)
            withContext(Dispatchers.Main){
                videoView.visibility = View.VISIBLE
                videoView.start()

                if (isSupported) {
                    btnGo.visibility = View.VISIBLE
                } else { // The device is unsupported or unknown.
                    btnGo.visibility = View.GONE
                    AlertDialog.Builder(requireContext())
                        .setMessage("The device does support AR")
                        .setNegativeButton("CONTINUE") { dialog, _ ->
                            dialog.dismiss()
                            requireActivity().finish()
                        }.setCancelable(false)
                        .show()
                }
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
    }



    override fun onResume() {
        super.onResume()
        videoView.resume()
        videoView.seekTo(videoPosition)
        videoView.start()
    }

    override fun onPause() {
        super.onPause()
        videoPosition = videoView.currentPosition
        videoView.pause()
    }

    override fun onDestroy(){
        super.onDestroy()
        videoView.stopPlayback()
    }



}