package com.karatas.koinmvvmappkotlin


import android.app.Application
import com.karatas.koinmvvmappkotlin.di.anotherModule
import com.karatas.koinmvvmappkotlin.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(appModule, anotherModule)
        }
    }
}