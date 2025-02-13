package com.mendelin.androidintegrator.remoteconfig.domain.usecase

import com.mendelin.androidintegrator.remoteconfig.domain.repository.RemoteConfigRepository

internal class GetBinanceRemoteConfigUseCase(
    private val remoteConfigRepository: RemoteConfigRepository
) : GetBinanceRemoteConfig {
    override suspend fun invoke(): String {
        return remoteConfigRepository.getBinanceStablecoinReference()
    }
}
