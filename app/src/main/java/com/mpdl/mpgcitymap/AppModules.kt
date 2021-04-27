package com.mpdl.mpgcitymap

import com.fyt.mvvm.globalsetting.IGlobalConfig
import com.fyt.mvvm.globalsetting.IGlobalHttpInterceptor
import com.fyt.mvvm.globalsetting.IResponseErrorListener
import com.mpdl.mpgcitymap.mvvm.repository.MainRepository
import com.mpdl.mpgcitymap.mvvm.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val mAppModule = module {

    single<IGlobalConfig>{ GlobalConfig() }

    single<IResponseErrorListener> { GlobalResponseErrorListener(androidContext()) }

    single<IGlobalHttpInterceptor> { GlobalHttpInterceptor() }

}


val mViewModelModule = module {
    viewModel { MainViewModel(get(), MainRepository(get()), get()) }
}