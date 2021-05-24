package de.mpg.mpdl.mpgcity.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.fyt.mvvm.base.BaseViewModel
import de.fyt.mvvm.globalsetting.IResponseErrorListener
import de.mpg.mpdl.mpgcity.mvvm.repository.MainRepository
import de.mpg.mpdl.mpgcity.mvvm.ui.uistate.MainUiState

class MainViewModel(application: Application,
                    mainRepository: MainRepository,
                    responseErrorListener: IResponseErrorListener
): BaseViewModel<MainRepository, MainUiState>(application,mainRepository,responseErrorListener) {
    private var mainUiState = MutableLiveData<MainUiState>(MainUiState())

    override fun getUiState(): LiveData<MainUiState> = mainUiState
}