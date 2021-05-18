package com.mpdl.mpgcitymap.mvvm.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.fyt.mvvm.common.Preference
import com.mpdl.mpgcitymap.BuildConfig
import com.mpdl.mpgcitymap.R
import com.mpdl.mpgcitymap.mvvm.ui.activity.MainActivity

class GuideFragment : Fragment(){
    private lateinit var videoView: VideoView
    private lateinit var btnGo: TextView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_guide,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videoView = view.findViewById(R.id.video_view)
        btnGo = view.findViewById(R.id.btn_go)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        videoView.setOnCompletionListener { videoView.start() }
        videoView.setVideoURI(MainActivity.getRawUri(requireContext(),R.raw.guide_video))
        videoView.requestFocus()
        videoView.start()
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