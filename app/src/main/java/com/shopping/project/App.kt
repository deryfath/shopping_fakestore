package com.shopping.project

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.shopping.project.data.DataRepository
import com.shopping.project.dependency.provideApiDataSource
import com.shopping.project.dependency.provideGson
import com.shopping.project.dependency.provideLoggingInterceptor
import com.shopping.project.dependency.provideOkHttpClient
import com.shopping.project.dependency.provideRequestInterceptor
import com.shopping.project.module.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class App : Application(){

    override fun onCreate() {
        super.onCreate()

        val appModule: Module = module {
            single { DataRepository(get()) }
            single<SharedPreferences> {
                androidContext().getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)
            }
            viewModel { MainViewModel(get()) }
            single { provideRequestInterceptor() }
            single { provideGson() }
            single { provideLoggingInterceptor() }
            single { provideOkHttpClient(get(), get()) }
            single { provideApiDataSource(get(), get()) }

        }

        startKoin {
            androidContext(this@App)
            modules(listOf(appModule))
        }
    }
}