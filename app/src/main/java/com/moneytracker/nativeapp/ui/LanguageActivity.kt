package com.moneytracker.nativeapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.moneytracker.nativeapp.R
import com.moneytracker.nativeapp.data.MoneyTrackerRepository
import com.moneytracker.nativeapp.data.UserSettings
import com.nphlab.sdk.ads.NphAds
import com.nphlab.sdk.ads.AdError
import com.nphlab.sdk.ads.listener.NphAdListener
import kotlinx.coroutines.launch
import java.util.Locale

class LanguageActivity : AppCompatActivity() {
    private lateinit var repository: MoneyTrackerRepository
    private lateinit var radioGroup: RadioGroup
    private lateinit var btnSave: MaterialButton
    
    private var currentSettings: UserSettings? = null
    private var selectedLanguage: String = "en"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language)
        
        repository = MoneyTrackerRepository(this)
        
        findViewById<MaterialToolbar>(R.id.toolbar).apply {
            setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            title = getString(R.string.language)
        }
        
        // Register back button interstitial ad
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                NphAds.showInterstitial(
                    activity = this@LanguageActivity,
                    nameSpace = "nsp_inter_language",
                    listener = object : NphAdListener() {
                        override fun onAdDismissed() {
                            finish()
                        }
                        override fun onAdFailed(error: AdError) {
                            finish()
                        }
                    }
                )
            }
        })
        
        radioGroup = findViewById(R.id.radioGroupLanguage)
        btnSave = findViewById(R.id.btnSave)
        
        setupLanguageOptions()
        
        btnSave.setOnClickListener {
            saveLanguage()
        }
        
        loadCurrentLanguage()
    }
    
    private fun setupLanguageOptions() {
        UserSettings.SUPPORTED_LANGUAGES.forEach { (code, name) ->
            val radioButton = RadioButton(this).apply {
                id = code.hashCode()
                text = name
                textSize = 16f
                setPadding(16, 24, 16, 24)
            }
            radioGroup.addView(radioButton)
        }
        
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selected = UserSettings.SUPPORTED_LANGUAGES.find { it.first.hashCode() == checkedId }
            selected?.let { selectedLanguage = it.first }
        }
    }
    
    private fun loadCurrentLanguage() {
        lifecycleScope.launch {
            currentSettings = repository.getOrCreateSettings()
            currentSettings?.let { settings ->
                selectedLanguage = settings.language
                val radioId = settings.language.hashCode()
                radioGroup.check(radioId)
            }
        }
    }
    
    private fun saveLanguage() {
        lifecycleScope.launch {
            val settings = currentSettings?.copy(language = selectedLanguage)
                ?: UserSettings(language = selectedLanguage)
            
            repository.updateSettings(settings)
            
            // Apply language immediately
            applyLanguage(selectedLanguage)
            
            Toast.makeText(this@LanguageActivity, getString(R.string.settings_saved), Toast.LENGTH_SHORT).show()
            
            // Restart app to apply language changes everywhere
            restartApp()
        }
    }
    
    private fun applyLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        
        val config = resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        
        resources.updateConfiguration(config, resources.displayMetrics)
        
        // Save to SharedPreferences for persistence across app restarts
        getSharedPreferences("app_settings", Context.MODE_PRIVATE)
            .edit()
            .putString("language", languageCode)
            .apply()
    }
    
    private fun restartApp() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}
