package com.moneytracker.nativeapp

import android.app.Application
import com.moneytracker.nativeapp.data.MoneyTrackerRepository
import com.nphlab.sdk.ads.NphSdk
import com.nphlab.sdk.config.ConfigSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MoneyTrackerApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()

        // Init NPH SDK
        NphSdk.init(
            context = this,
            apiKey = "nph_zQPZWBcm23CZIBYQF2Isb_N7KuMKfJOK",
            configSource = ConfigSource.FIREBASE,
            enableDebug = BuildConfig.DEBUG
        )

        // Initialize database with default data on first run
        applicationScope.launch {
            val repository = MoneyTrackerRepository(this@MoneyTrackerApplication)

            // Ensure user settings exist
            repository.getOrCreateSettings()

            // Initialize default categories
            repository.initializeDefaultCategories()
        }
    }
}
