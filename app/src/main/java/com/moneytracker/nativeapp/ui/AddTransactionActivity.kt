package com.moneytracker.nativeapp.ui

import android.content.Context
import android.os.Bundle
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import com.moneytracker.nativeapp.R
import com.moneytracker.nativeapp.data.Category
import com.nphlab.sdk.ads.NphAds
import com.nphlab.sdk.ads.listener.NphAdListener
import com.nphlab.sdk.ads.AdError
import com.moneytracker.nativeapp.data.MoneyTrackerRepository
import com.moneytracker.nativeapp.data.Transaction
import com.moneytracker.nativeapp.data.TransactionType
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDate
import java.util.Locale

class AddTransactionActivity : AppCompatActivity() {
    
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
    
    private lateinit var radioType: RadioGroup
    private lateinit var etAmount: TextInputEditText
    private lateinit var etTitle: TextInputEditText
    private lateinit var etDescription: TextInputEditText
    private lateinit var chipGroup: ChipGroup
    private lateinit var btnSave: MaterialButton
    
    private var selectedType = TransactionType.EXPENSE
    private var selectedCategory = ""
    private var currentCategories = listOf<Category>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)
        
        repository = MoneyTrackerRepository(this)
        
        findViewById<MaterialToolbar>(R.id.toolbar).apply {
            setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
        
        // Register back button interstitial ad with timeout fallback
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            private var isHandling = false
            override fun handleOnBackPressed() {
                if (isHandling) return
                isHandling = true
                val handler = android.os.Handler(mainLooper)
                val fallback = Runnable { if (!isFinishing) finish() }
                handler.postDelayed(fallback, 3000)
                NphAds.showInterstitial(
                    activity = this@AddTransactionActivity,
                    nameSpace = "nsp_inter_add_transaction",
                    listener = object : NphAdListener() {
                        override fun onAdDismissed() {
                            handler.removeCallbacks(fallback)
                            finish()
                        }
                        override fun onAdFailed(error: AdError) {
                            handler.removeCallbacks(fallback)
                            finish()
                        }
                    }
                )
            }
        })
        
        radioType = findViewById(R.id.radioType)
        etAmount = findViewById(R.id.etAmount)
        etTitle = findViewById(R.id.etTitle)
        etDescription = findViewById(R.id.etDescription)
        chipGroup = findViewById(R.id.chipGroup)
        btnSave = findViewById(R.id.btnSave)
        
        radioType.setOnCheckedChangeListener { _, checkedId ->
            selectedType = when (checkedId) {
                R.id.radioIncome -> TransactionType.INCOME
                else -> TransactionType.EXPENSE
            }
            updateCategories()
        }
        
        btnSave.setOnClickListener {
            saveTransaction()
        }
        
        updateCategories()
    }
    
    private fun updateCategories() {
        val categoriesLiveData = if (selectedType == TransactionType.EXPENSE) {
            repository.getExpenseCategories()
        } else {
            repository.getIncomeCategories()
        }
        
        categoriesLiveData.observe(this) { categories ->
            chipGroup.removeAllViews()
            currentCategories = categories ?: emptyList()
            
            if (currentCategories.isEmpty()) {
                // Fallback to default categories if database is empty
                val defaultCategories = if (selectedType == TransactionType.EXPENSE) {
                    listOf("food" to "Food", "transport" to "Transport", "shopping" to "Shopping", 
                           "entertainment" to "Entertainment", "bills" to "Bills", 
                           "health" to "Health", "education" to "Education", "other" to "Other")
                } else {
                    listOf("salary" to "Salary", "freelance" to "Freelance", 
                           "investment" to "Investment", "gift" to "Gifts", "other" to "Other")
                }
                
                defaultCategories.forEachIndexed { index, (id, name) ->
                    val chip = com.google.android.material.chip.Chip(this).apply {
                        text = name
                        isCheckable = true
                        isChecked = index == 0
                        setOnCheckedChangeListener { _, isChecked ->
                            if (isChecked) {
                                selectedCategory = id
                            }
                        }
                    }
                    chipGroup.addView(chip)
                }
                
                if (defaultCategories.isNotEmpty()) {
                    selectedCategory = defaultCategories[0].first
                }
            } else {
                currentCategories.forEachIndexed { index, category ->
                    val chip = com.google.android.material.chip.Chip(this).apply {
                        text = category.name
                        isCheckable = true
                        isChecked = index == 0
                        chipIcon = resources.getDrawable(getIconResource(category.icon), null)
                        setOnCheckedChangeListener { _, isChecked ->
                            if (isChecked) {
                                selectedCategory = category.id
                            }
                        }
                    }
                    chipGroup.addView(chip)
                }
                
                if (currentCategories.isNotEmpty()) {
                    selectedCategory = currentCategories[0].id
                }
            }
        }
    }
    
    private fun getIconResource(iconName: String): Int {
        return resources.getIdentifier(iconName, "drawable", packageName)
            .takeIf { it != 0 } ?: R.drawable.ic_more
    }
    
    private fun saveTransaction() {
        val amountText = etAmount.text.toString()
        val title = etTitle.text.toString().trim()
        val description = etDescription.text.toString().trim()
        
        if (amountText.isEmpty() || title.isEmpty()) {
            Toast.makeText(this, getString(R.string.please_fill_amount_title), Toast.LENGTH_SHORT).show()
            return
        }
        
        val amount = amountText.toBigDecimalOrNull()
        if (amount == null || amount <= BigDecimal.ZERO) {
            Toast.makeText(this, getString(R.string.please_enter_valid_amount), Toast.LENGTH_SHORT).show()
            return
        }
        
        val transaction = Transaction(
            amount = amount,
            type = selectedType,
            category = selectedCategory,
            title = title,
            description = description,
            date = LocalDate.now()
        )
        
        lifecycleScope.launch {
            repository.addTransaction(transaction)
            Toast.makeText(this@AddTransactionActivity, getString(R.string.transaction_saved), Toast.LENGTH_SHORT).show()
            
            // Show interstitial ad
            NphAds.showInterstitial(
                activity = this@AddTransactionActivity,
                nameSpace = "nsp_inter_add_transaction",
                listener = object : NphAdListener() {
                    override fun onAdDismissed() {}
                    override fun onAdFailed(error: AdError) {}
                }
            )
            
            finish()
        }
    }
}
