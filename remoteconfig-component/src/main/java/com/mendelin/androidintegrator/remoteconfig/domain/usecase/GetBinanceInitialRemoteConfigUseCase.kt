package com.mendelin.androidintegrator.remoteconfig.domain.usecase

import com.mendelin.androidintegrator.remoteconfig.domain.repository.RemoteConfigRepository
import com.mendelin.androidintegrator.shared.AiResult

internal class GetBinanceInitialRemoteConfigUseCase(
    private val remoteConfigRepository: RemoteConfigRepository
) : GetBinanceInitialRemoteConfig {
    override suspend fun invoke(): AiResult<String, Unit> {
       return remoteConfigRepository.fetchRemoteConfiguration().reduce(
            success = {
                AiResult.Success(remoteConfigRepository.getBinanceStablecoinReference())
            },
            failure = {
                AiResult.Failure(Unit)
            }
        )
    }
}
