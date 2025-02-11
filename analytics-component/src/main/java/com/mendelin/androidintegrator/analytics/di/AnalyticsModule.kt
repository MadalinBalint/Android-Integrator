package com.mendelin.androidintegrator.analytics.di

import com.mendelin.androidintegrator.analytics.data.api.*
import org.koin.dsl.module

val analyticsModule = module {
    single<AnalyticsClient> { FirebaseTracker() }
}
