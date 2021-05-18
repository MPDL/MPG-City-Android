package com.mpdl.mpgcitymap.mvvm.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.fyt.mvvm.common.Preference
import com.mpdl.mpgcitymap.BuildConfig
import com.mpdl.mpgcitymap.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LaunchFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_launch,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycleScope.launch {
            delay(1300)
            withContext(Dispatchers.Main){
                if (BuildConfig.VERSION_NAME == Preference.preferences.getString("version","")){
                    Navigation.findNavController(requireView()).navigate(R.id.action_launchFragment_to_MainFragment)
                }else{
                    Navigation.findNavController(requireView()).navigate(R.id.action_launchFragment_to_guideFragment)
                }
            }
        }
    }
}