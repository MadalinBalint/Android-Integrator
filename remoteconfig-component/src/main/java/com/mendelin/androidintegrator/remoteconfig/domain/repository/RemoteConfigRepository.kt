package com.mendelin.androidintegrator.remoteconfig.domain.repository

import com.mendelin.androidintegrator.shared.AiResult
import kotlinx.coroutines.flow.Flow

interface RemoteConfigRepository {
    suspend fun fetchRemoteConfiguration(): AiResult<Unit, Unit>
    fun getBinanceStablecoinReference(): String
    fun observeStablecoin(): Flow<String>
}
