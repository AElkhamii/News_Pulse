package com.example.newspulse.core.data.network.di

import com.example.newspulse.core.data.network.HttpClientFactory
import org.koin.dsl.module

val ktorModule = module {
    single {
        HttpClientFactory().build()
    }
}