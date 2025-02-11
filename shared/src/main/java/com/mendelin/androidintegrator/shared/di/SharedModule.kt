package com.mendelin.androidintegrator.shared.di

import com.chuckerteam.chucker.api.*
import com.mendelin.androidintegrator.shared.KoinConstants
import com.mendelin.androidintegrator.shared.KoinConstants.IO_DISPATCHER
import kotlinx.coroutines.*
import okhttp3.Interceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val sharedModule = module {
    single<Interceptor>(named(KoinConstants.CHUCKER_INTERCEPTOR)) {
        val chuckerCollector = ChuckerCollector(
            context = androidContext(),
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

        ChuckerInterceptor.Builder(androidContext())
            .collector(chuckerCollector)
            .maxContentLength(250_000L)
            .alwaysReadResponseBody(true)
            .build()
    }

    single<CoroutineDispatcher>(named(IO_DISPATCHER)) { Dispatchers.IO }
}
