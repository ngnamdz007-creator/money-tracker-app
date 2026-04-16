package com.moneytracker.nativeapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.moneytracker.nativeapp.data.MonthlyStats
import com.moneytracker.nativeapp.data.MoneyTrackerRepository
import com.moneytracker.nativeapp.data.PeriodType
import com.moneytracker.nativeapp.data.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDate

class HomeViewModel(private val repository: MoneyTrackerRepository) : ViewModel() {
    
    private val _monthlyStats = MutableStateFlow(MonthlyStats("", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO))
    val monthlyStats: StateFlow<MonthlyStats> = _monthlyStats
    
    private val _recentTransactions = MutableStateFlow<List<Transaction>>(emptyList())
    val recentTransactions: StateFlow<List<Transaction>> = _recentTransactions
    
    private val _selectedPeriod = MutableStateFlow(PeriodType.MONTH)
    val selectedPeriod: StateFlow<PeriodType> = _selectedPeriod
    
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate
    
    init {
        loadData()
    }
    
    private fun loadData() {
        viewModelScope.launch {
            refreshData()
        }
    }
    
    fun refreshData() {
        viewModelScope.launch {
            val period = _selectedPeriod.value
            val date = _selectedDate.value
            _monthlyStats.value = repository.getStatsForPeriod(period, date)
            _recentTransactions.value = repository.getTransactionsForPeriod(period, date)
        }
    }
    
    fun setPeriod(periodType: PeriodType) {
        _selectedPeriod.value = periodType
        refreshData()
    }
    
    fun setDate(date: LocalDate) {
        _selectedDate.value = date
        refreshData()
    }
    
    class Factory(private val repository: MoneyTrackerRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(repository) as T
        }
    }
}
