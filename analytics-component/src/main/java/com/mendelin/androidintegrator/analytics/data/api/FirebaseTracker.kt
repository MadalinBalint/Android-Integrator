package com.mendelin.androidintegrator.analytics.data.api

import androidx.core.os.bundleOf
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import timber.log.Timber

class FirebaseTracker : AnalyticsClient {
    private val firebaseAnalytics = Firebase.analytics

    override fun track(event: String, params: Map<String, Any>) {
        val bundle = bundleOf()
        params.forEach { entry ->
            bundle.putString(entry.key, entry.value.toString())
        }
        firebaseAnalytics.logEvent(event, bundle)
        Timber.d("Tracking $event -> $bundle")
    }
}
