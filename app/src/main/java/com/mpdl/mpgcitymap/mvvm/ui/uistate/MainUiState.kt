package com.mpdl.mpgcitymap.mvvm.ui.uistate

import com.fyt.mvvm.base.BaseUiState

data class MainUiState(var showLoading: Boolean = false,
                       var toastMsg: String? = null) : BaseUiState(showLoading,toastMsg)