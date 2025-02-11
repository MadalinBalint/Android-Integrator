package com.mendelin.androidintegrator.binance.di

import com.mendelin.androidintegrator.binance.data.api.*
import com.mendelin.androidintegrator.binance.data.mapper.*
import com.mendelin.androidintegrator.binance.data.repository.DefaultBinanceRepository
import com.mendelin.androidintegrator.binance.domain.repository.BinanceRepository
import com.mendelin.androidintegrator.binance.domain.usecase.*
import com.mendelin.androidintegrator.shared.KoinConstants
import com.mendelin.androidintegrator.shared.KoinConstants.IO_DISPATCHER
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.websocket.WebSockets
import org.koin.core.qualifier.named
import org.koin.dsl.module

val binanceComponentModule = module {
    single(named(KoinConstants.WEBSOCKETS_HTTP_CLIENT)) {
        HttpClient(OkHttp) {
            engine {
                config {
                    followRedirects(true)
                }
                addInterceptor(get(named(KoinConstants.CHUCKER_INTERCEPTOR)))
            }
            install(WebSockets)
        }
    }

    single<BinanceApi> { DefaultBinanceApi(get(named(KoinConstants.WEBSOCKETS_HTTP_CLIENT))) }

    single<BinanceMapper> { DefaultBinanceMapper() }

    single<BinanceRepository> { DefaultBinanceRepository(get(), get(), get(named(IO_DISPATCHER))) }

    single<GetAveragePrice> { GetAveragePriceUseCase(get()) }
}
