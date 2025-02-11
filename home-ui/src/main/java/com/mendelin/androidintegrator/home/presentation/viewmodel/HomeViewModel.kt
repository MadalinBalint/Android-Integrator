package com.mendelin.androidintegrator.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.mendelin.androidintegrator.analytics.data.api.AnalyticsClient

class HomeViewModel(
    private val analytics: AnalyticsClient
) : ViewModel(),
    AnalyticsClient by analytics
