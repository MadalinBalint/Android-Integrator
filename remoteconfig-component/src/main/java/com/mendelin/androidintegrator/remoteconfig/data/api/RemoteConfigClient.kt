package com.mendelin.androidintegrator.remoteconfig.data.api

import kotlinx.coroutines.flow.Flow

internal interface RemoteConfigClient {
    fun getBoolean(key: String): Boolean
    fun getDouble(key: String): Double
    fun getLong(key: String): Long
    fun getString(key: String): String

    suspend fun fetchAndActivateConfig()
    fun observeConfigUpdates(): Flow<Set<String>>
}
