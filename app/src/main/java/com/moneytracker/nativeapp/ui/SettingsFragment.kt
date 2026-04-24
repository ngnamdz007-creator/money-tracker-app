package com.moneytracker.nativeapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.moneytracker.nativeapp.R
import com.moneytracker.nativeapp.data.MoneyTrackerRepository
import com.nphlab.sdk.ads.NphAds
import android.widget.FrameLayout
import com.moneytracker.nativeapp.data.UserSettings
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {
    private lateinit var repository: MoneyTrackerRepository
    private lateinit var tvCurrencyValue: TextView
    private lateinit var tvLanguageValue: TextView
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        repository = MoneyTrackerRepository(requireContext())
        
        // Find value text views
        tvCurrencyValue = view.findViewById(R.id.tvCurrencyValue)
        tvLanguageValue = view.findViewById(R.id.tvLanguageValue)
        
        view.findViewById<View>(R.id.cardProfile).setOnClickListener {
            startActivity(Intent(requireContext(), SettingsActivity::class.java))
        }
        
        view.findViewById<View>(R.id.cardCategories).setOnClickListener {
            startActivity(Intent(requireContext(), CategoriesActivity::class.java))
        }
        
        view.findViewById<View>(R.id.cardCurrency).setOnClickListener {
            startActivity(Intent(requireContext(), CurrencyActivity::class.java))
        }
        
        view.findViewById<View>(R.id.cardLanguage).setOnClickListener {
            startActivity(Intent(requireContext(), LanguageActivity::class.java))
        }
        
        view.findViewById<View>(R.id.cardExport).setOnClickListener {
            // Export data
        }
        
        loadSettings()

        // Load native ad
        val nativeAdContainer = view.findViewById<FrameLayout>(R.id.nativeAdContainer)
        nativeAdContainer?.let {
            NphAds.loadNativeInto(it, "nsp_native_settings")
        }
    }

    override fun onResume() {
        super.onResume()
        loadSettings()
    }
    
    private fun loadSettings() {
        lifecycleScope.launch {
            val settings = repository.getOrCreateSettings()
            updateUI(settings)
        }
    }
    
    private fun updateUI(settings: UserSettings) {
        // Update currency display
        val currencyInfo = UserSettings.SUPPORTED_CURRENCIES.find { it.first == settings.currency }
        tvCurrencyValue.text = currencyInfo?.let { "${it.first} (${it.second})" } ?: settings.currency
        
        // Update language display
        val languageInfo = UserSettings.SUPPORTED_LANGUAGES.find { it.first == settings.language }
        tvLanguageValue.text = languageInfo?.second ?: settings.language
    }
}
