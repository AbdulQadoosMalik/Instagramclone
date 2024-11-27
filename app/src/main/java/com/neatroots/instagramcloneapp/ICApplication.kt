package com.neatroots.instagramcloneapp

import android.app.Application
import com.neatroots.instagramcloneapp.data.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class ICApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger() // Log Koin events
            androidContext(this@ICApplication) // Provide Android context
            modules(appModule) // Load your modules
        }
    }
}


