package com.mendelin.androidintegrator.remoteconfig.data.repository

import com.mendelin.androidintegrator.remoteconfig.data.api.RemoteConfigClient
import com.mendelin.androidintegrator.remoteconfig.domain.repository.RemoteConfigRepository
import com.mendelin.androidintegrator.shared.AiResult
import kotlinx.coroutines.flow.*

internal class DefaultRemoteConfigRepository(
    private val remoteConfigClient: RemoteConfigClient
) : RemoteConfigRepository {
    override suspend fun fetchRemoteConfiguration(): AiResult<Unit, Unit> {
        return try {
            remoteConfigClient.fetchAndActivateConfig()
            AiResult.Success(Unit)
        } catch (t: Exception) {
            AiResult.Failure(Unit)
        }
    }

    override fun getBinanceStablecoinReference(): String {
        return remoteConfigClient.getString(BINANCE_STABLECOIN_REFERENCE)
    }

    override fun observeStablecoin(): Flow<String> {
        return remoteConfigClient.observeConfigUpdates()
            .filter {
                it.contains(BINANCE_STABLECOIN_REFERENCE)
            }.map {
                getBinanceStablecoinReference()
            }
    }

    companion object {
        const val BINANCE_STABLECOIN_REFERENCE = "binance_stablecoin"
    }
}
