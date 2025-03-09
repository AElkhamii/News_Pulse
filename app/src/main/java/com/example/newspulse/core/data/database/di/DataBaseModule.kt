package com.example.newspulse.core.data.database.di

import androidx.room.Room
import com.example.newspulse.core.data.database.BreakingNewsDataBase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataBaseModule = module {
    single {
        Room.databaseBuilder(
            context = androidApplication(),
            klass = BreakingNewsDataBase::class.java,
            name = "news_pulse_dp",
        ).build()
    }

    single { get<BreakingNewsDataBase>().breakingNewsListDao }
}