package com.jop.testvascomm

import android.app.Application
import com.jop.testvascomm.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TestVascomm : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TestVascomm)
            androidLogger()
            modules(appModule())
        }
    }
}