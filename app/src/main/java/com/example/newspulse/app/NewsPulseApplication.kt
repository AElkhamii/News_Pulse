package com.example.newspulse.app

import android.app.Application
import com.example.newspulse.BuildConfig
import com.example.newspulse.app.di.appModule
import com.example.newspulse.breakingnews.data.di.breakingNewsDataModule
import com.example.newspulse.breakingnews.domain.di.breakingNewsDomainModule
import com.example.newspulse.breakingnews.presentation.di.breakingNewsPresentationModule
import com.example.newspulse.core.data.di.coreDataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class NewsPulseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        // Timber
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree()) // to enable use timber for logging
        }

        // Koin
        startKoin {
            androidLogger()
            androidContext(this@NewsPulseApplication)
            modules(
                appModule,
                coreDataModule,
                breakingNewsPresentationModule,
                breakingNewsDataModule,
                breakingNewsDomainModule
            )
        }
    }

}