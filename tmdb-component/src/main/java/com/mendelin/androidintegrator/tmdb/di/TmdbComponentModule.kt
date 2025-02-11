package com.mendelin.androidintegrator.tmdb.di

import com.mendelin.androidintegrator.shared.KoinConstants
import com.mendelin.androidintegrator.shared.KoinConstants.IO_DISPATCHER
import com.mendelin.androidintegrator.tmdb.component.BuildConfig
import com.mendelin.androidintegrator.tmdb.data.api.*
import com.mendelin.androidintegrator.tmdb.data.mapper.*
import com.mendelin.androidintegrator.tmdb.data.repository.DefaultTmdbRepository
import com.mendelin.androidintegrator.tmdb.domain.repository.TmdbRepository
import com.mendelin.androidintegrator.tmdb.domain.usecase.*
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

val tmdbComponentModule = module {
    single(named(KoinConstants.TMDB_HTTP_CLIENT)) {
        HttpClient(OkHttp) {
            expectSuccess = true

            engine {
                config {
                    followRedirects(true)
                }
                addInterceptor(get(named(KoinConstants.CHUCKER_INTERCEPTOR)))
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    },
                )
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
                level = LogLevel.ALL
            }

            defaultRequest {
                headers {
                    header("Accept", "application/json")
                    header("Authorization", "Bearer ${BuildConfig.TMDB_API_KEY}")
                }
            }
        }
    }

    single<TmdbApi> { DefaultTmdbApi(get(named(KoinConstants.TMDB_HTTP_CLIENT))) }

    single<TmdbMapper> { DefaultTmdbMapper() }

    single<TmdbRepository> { DefaultTmdbRepository(get(), get(), get(named(IO_DISPATCHER))) }

    single<GetMovieGenres> { GetMovieGenresUseCase(get()) }

    single<GetMoviesNowPlaying> { GetMoviesNowPlayingUseCase(get()) }
}
