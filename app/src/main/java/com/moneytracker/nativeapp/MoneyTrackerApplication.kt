package com.moneytracker.nativeapp

import android.app.Application
import com.moneytracker.nativeapp.data.MoneyTrackerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MoneyTrackerApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    
    override fun onCreate() {
        super.onCreate()
        
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
