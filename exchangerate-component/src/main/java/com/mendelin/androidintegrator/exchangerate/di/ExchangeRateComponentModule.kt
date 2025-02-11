package com.mendelin.androidintegrator.exchangerate.di

import com.mendelin.androidintegrator.exchangerate.data.api.*
import com.mendelin.androidintegrator.exchangerate.data.mapper.*
import com.mendelin.androidintegrator.exchangerate.data.repository.DefaultExchangeRateRepository
import com.mendelin.androidintegrator.exchangerate.domain.repository.ExchangeRateRepository
import com.mendelin.androidintegrator.exchangerate.domain.usescase.*
import com.mendelin.androidintegrator.shared.KoinConstants
import com.mendelin.androidintegrator.shared.KoinConstants.IO_DISPATCHER
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.xml.xml
import org.koin.core.qualifier.named
import org.koin.dsl.module

val exchangeRateComponentModule = module {
    single(named(KoinConstants.SOAP_HTTP_CLIENT)) {
        HttpClient(OkHttp) {
            expectSuccess = true

            engine {
                config {
                    followRedirects(true)
                }
                addInterceptor(get(named(KoinConstants.CHUCKER_INTERCEPTOR)))
            }

            install(ContentNegotiation) {
                xml()
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
                level = LogLevel.ALL
            }
        }
    }

    single<ExchangeRateApi> { DefaultExchangeRateApi(get((named(KoinConstants.SOAP_HTTP_CLIENT)))) }

    single<ExchangeRateMapper> { DefaultExchangeRateMapper() }

    single<ExchangeRateRepository> {
        DefaultExchangeRateRepository(
            get(),
            get(),
            get(named(IO_DISPATCHER))
        )
    }

    single<GetLatestValue> { GetLatestValueUseCase(get()) }
}
