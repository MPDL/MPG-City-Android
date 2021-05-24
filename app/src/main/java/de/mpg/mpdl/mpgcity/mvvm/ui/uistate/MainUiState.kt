package de.mpg.mpdl.mpgcity.mvvm.ui.uistate

import de.fyt.mvvm.base.BaseUiState


data class MainUiState(var showLoading: Boolean = false,
                       var toastMsg: String? = null) : BaseUiState(showLoading,toastMsg)