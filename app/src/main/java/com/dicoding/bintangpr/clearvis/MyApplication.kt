package com.dicoding.bintangpr.clearvis

import android.app.Application
import com.dicoding.bintangpr.clearvis.di.dataStoreModule
import com.dicoding.bintangpr.clearvis.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    dataStoreModule,
                    viewModelModule
                )
            )
        }
    }
}