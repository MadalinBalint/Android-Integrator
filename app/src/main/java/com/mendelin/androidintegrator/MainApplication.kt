package com.mendelin.androidintegrator

import android.app.Application
import com.mendelin.androidintegrator.analytics.di.analyticsModule
import com.mendelin.androidintegrator.binance.di.*
import com.mendelin.androidintegrator.di.appModule
import com.mendelin.androidintegrator.exchangerate.di.*
import com.mendelin.androidintegrator.home.di.homeUiModule
import com.mendelin.androidintegrator.remoteconfig.di.remoteConfigClientModule
import com.mendelin.androidintegrator.rickandmorty.di.*
import com.mendelin.androidintegrator.shared.di.sharedModule
import com.mendelin.androidintegrator.tmdb.di.*
import org.koin.android.ext.koin.*
import org.koin.core.context.startKoin
import timber.log.Timber

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)

            modules(
                appModule,

                sharedModule,
                remoteConfigClientModule,
                analyticsModule,

                homeUiModule,

                rickAndMortyComponentModule,
                rickAndMortyUiModule,

                tmdbComponentModule,
                tmdbUiModule,

                exchangeRateComponentModule,
                exchangeRateUiModule,

                binanceComponentModule,
                binanceUiModule
            )
        }
    }
}
