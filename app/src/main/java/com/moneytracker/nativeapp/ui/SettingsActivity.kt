package com.moneytracker.nativeapp.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.moneytracker.nativeapp.MoneyTrackerApplication
import com.moneytracker.nativeapp.R
import com.moneytracker.nativeapp.data.MoneyTrackerRepository
import com.moneytracker.nativeapp.data.UserSettings
import com.nphlab.sdk.ads.NphAds
import com.nphlab.sdk.ads.AdError
import com.nphlab.sdk.ads.listener.NphAdListener
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.Locale

class SettingsActivity : AppCompatActivity() {
    
    override fun attachBaseContext(newBase: Context) {
        val prefs = newBase.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        val languageCode = prefs.getString("language", "en") ?: "en"
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = newBase.resources.configuration
        config.setLocale(locale)
        val context = newBase.createConfigurationContext(config)
        super.attachBaseContext(context)
    }
    private lateinit var repository: MoneyTrackerRepository
    
    private lateinit var etUserName: TextInputEditText
    private lateinit var etMonthlyBudget: TextInputEditText
    private lateinit var btnSave: MaterialButton
    
    private var currentSettings: UserSettings? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        
        repository = MoneyTrackerRepository(this)
        
        findViewById<MaterialToolbar>(R.id.toolbar).apply {
            setNavigationOnClickListener {
                Log.d("SettingsActivity", "=== Toolbar navigation clicked ===")
                onBackPressedDispatcher.onBackPressed()
            }
            title = getString(R.string.profile)
        }
        
        // Register back button interstitial ad
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("SettingsActivity", "=== Back pressed, showing interstitial ad ===")
                if (!MoneyTrackerApplication.isSdkReady) {
                    Log.w("NphAds", "=== SDK not ready, skipping interstitial ===")
                    finish()
                    return
                }
                NphAds.showInterstitial(
                    activity = this@SettingsActivity,
                    nameSpace = "nsp_inter_settings",
                    listener = object : NphAdListener() {
                        override fun onAdDismissed() {
                            Log.d("NphAds", "=== Settings interstitial dismissed, finishing ===")
                            finish()
                        }
                        override fun onAdFailed(error: AdError) {
                            Log.e("NphAds", "=== Settings interstitial failed: ${error.message}, finishing ===")
                            finish()
                        }
                    }
                )
            }
        })
        
        etUserName = findViewById(R.id.etUserName)
        etMonthlyBudget = findViewById(R.id.etMonthlyBudget)
        btnSave = findViewById(R.id.btnSave)
        
        btnSave.setOnClickListener {
            saveSettings()
        }
        
        loadSettings()
    }
    
    private fun loadSettings() {
        lifecycleScope.launch {
            currentSettings = repository.getOrCreateSettings()
            currentSettings?.let { settings ->
                etUserName.setText(settings.userName)
                etMonthlyBudget.setText(settings.monthlyBudget.toString())
            }
        }
    }
    
    private fun saveSettings() {
        val userName = etUserName.text.toString().trim()
        val monthlyBudget = etMonthlyBudget.text.toString().toBigDecimalOrNull() ?: BigDecimal.ZERO
        
        val settings = UserSettings(
            userName = userName,
            monthlyBudget = monthlyBudget,
            currency = currentSettings?.currency ?: "USD",
            language = currentSettings?.language ?: "en"
        )
        
        lifecycleScope.launch {
            repository.updateSettings(settings)
            
            // Also update budget
            if (monthlyBudget > BigDecimal.ZERO) {
                repository.setBudget(monthlyBudget)
            }
            
            Toast.makeText(this@SettingsActivity, getString(R.string.settings_saved), Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
