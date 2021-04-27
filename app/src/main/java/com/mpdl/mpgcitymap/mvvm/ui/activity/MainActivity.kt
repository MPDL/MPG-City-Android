package com.mpdl.mpgcitymap.mvvm.ui.activity

import android.content.res.Configuration
import android.os.Bundle
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

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    companion object{
        var SCAN_IMAGE_NAMES = listOf("scan_it","scan_it_01","scan_it_02","scan_it_03","scan_it_04","scan_it_05","scan_it_06","scan_it_07","scan_it_08")
    }
}