package com.moneytracker.nativeapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.moneytracker.nativeapp.data.CategoryStats
import com.moneytracker.nativeapp.data.MoneyTrackerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal

class StatisticsViewModel(private val repository: MoneyTrackerRepository) : ViewModel() {
    
    private val _categoryStats = MutableStateFlow<List<CategoryStats>>(emptyList())
    val categoryStats: StateFlow<List<CategoryStats>> = _categoryStats
    
    private val _monthlyComparison = MutableStateFlow<List<Pair<String, Pair<BigDecimal, BigDecimal>>>>(emptyList())
    val monthlyComparison: StateFlow<List<Pair<String, Pair<BigDecimal, BigDecimal>>>> = _monthlyComparison
    
    init {
        loadStats()
    }
    
    private fun loadStats() {
        viewModelScope.launch {
            _categoryStats.value = repository.getCategoryStats()
            _monthlyComparison.value = repository.getMonthlyComparison(6)
        }
    }
    
    class Factory(private val repository: MoneyTrackerRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return StatisticsViewModel(repository) as T
        }
    }
}
