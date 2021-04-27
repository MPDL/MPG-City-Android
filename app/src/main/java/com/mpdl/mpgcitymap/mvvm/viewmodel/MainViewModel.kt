package com.mpdl.mpgcitymap.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fyt.mvvm.base.BaseViewModel
import com.fyt.mvvm.globalsetting.IResponseErrorListener
import com.mpdl.mpgcitymap.mvvm.repository.MainRepository
import com.mpdl.mpgcitymap.mvvm.ui.uistate.MainUiState

class MainViewModel(application: Application,
                    mainRepository: MainRepository,
                    responseErrorListener: IResponseErrorListener
): BaseViewModel<MainRepository, MainUiState>(application,mainRepository,responseErrorListener) {
    private var mainUiState = MutableLiveData<MainUiState>(MainUiState())

    override fun getUiState(): LiveData<MainUiState> = mainUiState
}