package com.moneytracker.nativeapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import com.moneytracker.nativeapp.R
import com.nphlab.sdk.ads.NphAds
import com.nphlab.sdk.ads.listener.NphAdListener
import com.nphlab.sdk.ads.AdError
import java.util.Locale

class MainActivity : AppCompatActivity() {
    
    override fun attachBaseContext(newBase: Context) {
        // Apply saved language before activity creation
        val prefs = newBase.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        val languageCode = prefs.getString("language", "en") ?: "en"
        
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        
        val config = newBase.resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        
        val context = newBase.createConfigurationContext(config)
        super.attachBaseContext(context)
    }
    private lateinit var bottomNav: BottomNavigationView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        setupBottomNavigation()
        
        // Show initial fragment
        if (savedInstanceState == null) {
            showFragment(HomeFragment())
        }
        
        // Load banner ad (delay to ensure SDK ready)
        val bannerContainer = findViewById<FrameLayout>(R.id.bannerAdContainer)
        bannerContainer?.postDelayed({
            NphAds.loadBannerInto(bannerContainer, "nsp_bn_home_bottom")
        }, 1500)
    }
    
    private fun setupBottomNavigation() {
        bottomNav = findViewById(R.id.bottomNavigation)
        
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    showFragment(HomeFragment())
                    true
                }
                R.id.nav_statistics -> {
                    showInterstitialAd {
                        showFragment(StatisticsFragment())
                    }
                    true
                }
                R.id.nav_budget -> {
                    showFragment(BudgetFragment())
                    true
                }
                R.id.nav_settings -> {
                    showInterstitialAdSettings {
                        showFragment(SettingsFragment())
                    }
                    true
                }
                else -> false
            }
        }
    }
    
    private fun showFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
    
    fun navigateToAddTransaction() {
        startActivity(Intent(this, AddTransactionActivity::class.java))
    }

    private fun showInterstitialAd(onComplete: () -> Unit) {
        NphAds.showInterstitial(
            activity = this,
            nameSpace = "nsp_inter_main",
            listener = object : NphAdListener() {
                override fun onAdDismissed() {
                    onComplete()
                }
                override fun onAdFailed(error: AdError) {
                    onComplete()
                }
            }
        )
    }
    
    private fun showInterstitialAdSettings(onComplete: () -> Unit) {
        NphAds.showInterstitial(
            activity = this,
            nameSpace = "nsp_inter_settings",
            listener = object : NphAdListener() {
                override fun onAdDismissed() {
                    onComplete()
                }
                override fun onAdFailed(error: AdError) {
                    onComplete()
                }
            }
        )
    }

    override fun onDestroy() {
        NphAds.destroy(this)
        super.onDestroy()
    }
}
