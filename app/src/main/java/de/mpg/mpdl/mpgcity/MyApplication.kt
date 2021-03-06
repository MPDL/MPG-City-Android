package de.mpg.mpdl.mpgcity

import android.app.Application
import android.content.ComponentCallbacks2
import com.bumptech.glide.Glide
import com.squareup.leakcanary.LeakCanary
import com.tencent.bugly.crashreport.CrashReport
import de.fyt.mvvm.common.Preference
import de.fyt.mvvm.di.mSingleModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            if (BuildConfig.DEBUG){
                androidLogger()
            }
            // use the Android context given there
            androidContext(this@MyApplication)
            modules(listOf(mAppModule,mSingleModule,mViewModelModule))
        }

        if (BuildConfig.DEBUG) {
            LeakCanary.install(this)
            Timber.plant(Timber.DebugTree())
        }
        Preference.setContext(applicationContext)

        CrashReport.initCrashReport(applicationContext, "766677e0eb", BuildConfig.DEBUG)

    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        // clear Glide cache
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory()
        }
        // trim memory
        Glide.get(this).trimMemory(level)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        // low memory clear Glide cache
        Glide.get(this).clearMemory()
    }
}