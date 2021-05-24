package de.mpg.mpdl.mpgcity

import de.fyt.mvvm.globalsetting.IGlobalConfig
import de.fyt.mvvm.globalsetting.IGlobalHttpInterceptor
import de.fyt.mvvm.globalsetting.IResponseErrorListener
import de.mpg.mpdl.mpgcity.mvvm.repository.MainRepository
import de.mpg.mpdl.mpgcity.mvvm.viewmodel.MainViewModel
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