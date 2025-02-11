package com.mendelin.androidintegrator.remoteconfig.domain.usecase

import com.mendelin.androidintegrator.shared.AiResult
import kotlinx.coroutines.flow.Flow

fun interface GetBinanceInitialRemoteConfig {
    suspend operator fun invoke(): AiResult<String, Unit>
}

fun interface GetBinanceRemoteConfig {
    suspend operator fun invoke(): String
}

fun interface ObserveBinanceRemoteConfig {
    operator fun invoke(): Flow<String>
}

