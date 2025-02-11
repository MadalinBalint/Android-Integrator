package com.mendelin.androidintegrator.remoteconfig.domain.usecase

import com.mendelin.androidintegrator.remoteconfig.domain.repository.RemoteConfigRepository

class GetBinanceRemoteConfigUseCase(
    private val remoteConfigRepository: RemoteConfigRepository
) : GetBinanceRemoteConfig {
    override suspend fun invoke(): String {
        return remoteConfigRepository.getBinanceStablecoinReference()
    }
}
