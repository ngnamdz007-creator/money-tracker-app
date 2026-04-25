package com.moneytracker.nativeapp

import android.app.Application
import com.moneytracker.nativeapp.data.MoneyTrackerRepository
import com.nphlab.sdk.ads.NphAds
import com.nphlab.sdk.ads.NphSdk
import com.nphlab.sdk.config.ConfigSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MoneyTrackerApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    
    companion object {
        var isSdkReady = false
    }
    
    override fun onCreate() {
        super.onCreate()
        
        // Init NPH SDK - Load config from Firebase (fallback to assets/ads_config.json)
        // Fallback chain: Firebase → Cache → Local assets → Default
        try {
            NphSdk.init(
                context = this,
                apiKey = "nph_zQPZWBcm23CZIBYQF2Isb_N7KuMKfJOK",
                configSource = ConfigSource.FIREBASE,
                enableDebug = true
            )
            isSdkReady = true
        } catch (e: Exception) {
            // SDK init failed - ads will be skipped
        }
        
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
