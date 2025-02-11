package com.mendelin.androidintegrator.analytics.data.api

interface AnalyticsClient {
    fun track(event: String, params: Map<String, Any>)
}
