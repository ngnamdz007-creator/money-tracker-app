package com.moneytracker.nativeapp

import android.app.Application
import android.util.Log
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
        
        Log.d("MoneyTrackerApplication", "=== APPLICATION onCreate START ===")
        
        // Init NPH SDK - Load config from Firebase (fallback to assets/ads_config.json)
        // NOTE: Firebase Remote Config cần có key 'nph_ads_config' được setup bởi mentor
        // Nếu Firebase chưa setup, SDK sẽ fallback về assets/ads_config.json
        // Fallback chain: Firebase → Cache → Local assets → Default
        try {
            // Verify local config exists (used as Firebase fallback)
            val configJson = assets.open("ads_config.json").bufferedReader().use { it.readText() }
            Log.d("MoneyTrackerApplication", "=== Local ads_config.json loaded: ${configJson.length} chars ===")
            
            NphSdk.init(
                context = this,
                apiKey = "nph_zQPZWBcm23CZlBYQF2Isb_N7KuMKfJOK",
                configSource = ConfigSource.FIREBASE,
                enableDebug = true  // Force debug mode - logs all NPH SDK events
            )
            Log.d("MoneyTrackerApplication", "=== NphSdk.init() called ===")
            Log.d("MoneyTrackerApplication", "=== SDK will try: Firebase → Cache → Local assets ===")
        } catch (e: Exception) {
            Log.e("MoneyTrackerApplication", "=== NphSdk.init() FAILED: ${e.message}", e)
            Log.e("MoneyTrackerApplication", "=== Possible causes: License key mismatch, Signature mismatch, or Network error ===")
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
