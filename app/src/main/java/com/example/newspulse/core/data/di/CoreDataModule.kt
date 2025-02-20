package com.example.newspulse.core.data.di

import com.example.newspulse.core.data.network.HttpClientFactory
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory().build()
    }
}