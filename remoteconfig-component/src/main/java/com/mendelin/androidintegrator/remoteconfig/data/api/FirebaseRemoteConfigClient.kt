package com.mendelin.androidintegrator.remoteconfig.data.api

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.*
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class FirebaseRemoteConfigClient : RemoteConfigClient {
    private val remoteConfig: FirebaseRemoteConfig
        get() = Firebase.remoteConfig
    private val remoteConfigUpdates = MutableStateFlow(emptySet<String>())

    init {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = FETCH_INTERVAL
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                Log.d("Firebase", "Updated keys: " + configUpdate.updatedKeys)
                remoteConfig.activate().addOnCompleteListener {
                    remoteConfigUpdates.update { configUpdate.updatedKeys + System.currentTimeMillis().toString() }
                }
            }

            override fun onError(error: FirebaseRemoteConfigException) {
                Log.d("Firebase", error.localizedMessage ?: "Unknown Firebase exception")
            }
        })
    }

    override fun getBoolean(key: String): Boolean = remoteConfig.getBoolean(key)

    override fun getDouble(key: String): Double = remoteConfig.getDouble(key)

    override fun getLong(key: String): Long = remoteConfig.getLong(key)

    override fun getString(key: String): String = remoteConfig.getString(key)

    override suspend fun fetchAndActivateConfig() {
        return suspendCancellableCoroutine { continuation ->
            remoteConfig.fetchAndActivate().addOnCompleteListener {
                if (it.isSuccessful) {
                    continuation.resume(Unit)
                } else {
                    Log.d(
                        "Firebase",
                        it.exception?.localizedMessage ?: "Unknown fetchAndActivate exception"
                    )
                    continuation.cancel(it.exception)
                }
            }
        }
    }

    override fun observeConfigUpdates(): Flow<Set<String>> = remoteConfigUpdates.asStateFlow()

    private companion object {
        const val FETCH_INTERVAL = 3600L
    }
}
