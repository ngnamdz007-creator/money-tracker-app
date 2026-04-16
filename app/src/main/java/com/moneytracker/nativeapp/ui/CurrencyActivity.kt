package com.moneytracker.nativeapp.ui

import android.content.Context
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.moneytracker.nativeapp.R
import com.moneytracker.nativeapp.data.MoneyTrackerRepository
import com.moneytracker.nativeapp.data.UserSettings
import kotlinx.coroutines.launch
import java.util.Locale

class CurrencyActivity : AppCompatActivity() {
    
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
    private lateinit var radioGroup: RadioGroup
    private lateinit var btnSave: MaterialButton
    
    private var currentSettings: UserSettings? = null
    private var selectedCurrency: String = "USD"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)
        
        repository = MoneyTrackerRepository(this)
        
        findViewById<MaterialToolbar>(R.id.toolbar).apply {
            setNavigationOnClickListener { finish() }
            title = getString(R.string.currency)
        }
        
        radioGroup = findViewById(R.id.radioGroupCurrency)
        btnSave = findViewById(R.id.btnSave)
        
        setupCurrencyOptions()
        
        btnSave.setOnClickListener {
            saveCurrency()
        }
        
        loadCurrentCurrency()
    }
    
    private fun setupCurrencyOptions() {
        UserSettings.SUPPORTED_CURRENCIES.forEach { (code, symbol) ->
            val radioButton = RadioButton(this).apply {
                id = code.hashCode()
                text = "$code ($symbol)"
                textSize = 16f
                setPadding(16, 24, 16, 24)
            }
            radioGroup.addView(radioButton)
        }
        
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selected = UserSettings.SUPPORTED_CURRENCIES.find { it.first.hashCode() == checkedId }
            selected?.let { selectedCurrency = it.first }
        }
    }
    
    private fun loadCurrentCurrency() {
        lifecycleScope.launch {
            currentSettings = repository.getOrCreateSettings()
            currentSettings?.let { settings ->
                selectedCurrency = settings.currency
                val radioId = settings.currency.hashCode()
                radioGroup.check(radioId)
            }
        }
    }
    
    private fun saveCurrency() {
        lifecycleScope.launch {
            val settings = currentSettings?.copy(currency = selectedCurrency)
                ?: UserSettings(currency = selectedCurrency)
            
            repository.updateSettings(settings)
            
            Toast.makeText(this@CurrencyActivity, getString(R.string.settings_saved), Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
