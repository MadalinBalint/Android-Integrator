package com.mendelin.androidintegrator.remoteconfig.di

import com.mendelin.androidintegrator.remoteconfig.data.api.*
import com.mendelin.androidintegrator.remoteconfig.data.repository.DefaultRemoteConfigRepository
import com.mendelin.androidintegrator.remoteconfig.domain.repository.RemoteConfigRepository
import com.mendelin.androidintegrator.remoteconfig.domain.usecase.*
import org.koin.dsl.module

val remoteConfigClientModule = module {
    single<RemoteConfigClient> { FirebaseRemoteConfigClient() }

    single<RemoteConfigRepository> { DefaultRemoteConfigRepository(get()) }

    single<GetBinanceInitialRemoteConfig> { GetBinanceInitialRemoteConfigUseCase(get()) }

    single<GetBinanceRemoteConfig> { GetBinanceRemoteConfigUseCase(get()) }

    single<ObserveBinanceRemoteConfig> { ObserveBinanceRemoteConfigUseCase(get()) }
}
