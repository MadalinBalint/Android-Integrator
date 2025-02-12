package com.mendelin.androidintegrator.remoteconfig.domain.usecase

import com.mendelin.androidintegrator.remoteconfig.domain.repository.RemoteConfigRepository
import kotlinx.coroutines.flow.Flow

internal class ObserveBinanceRemoteConfigUseCase(
    private val remoteConfigRepository: RemoteConfigRepository
) : ObserveBinanceRemoteConfig {
    override fun invoke(): Flow<String> {
       return remoteConfigRepository.observeStablecoin()
    }
}
