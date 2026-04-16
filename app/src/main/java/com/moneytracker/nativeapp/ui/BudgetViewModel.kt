package com.moneytracker.nativeapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.moneytracker.nativeapp.data.BudgetPeriod
import com.moneytracker.nativeapp.data.MoneyTrackerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal

class BudgetViewModel(private val repository: MoneyTrackerRepository) : ViewModel() {
    
    private val _budgetProgress = MutableStateFlow<Pair<BigDecimal, BigDecimal>>(Pair(BigDecimal.ZERO, BigDecimal.ZERO))
    val budgetProgress: StateFlow<Pair<BigDecimal, BigDecimal>> = _budgetProgress
    
    init {
        loadBudget()
    }
    
    private fun loadBudget() {
        viewModelScope.launch {
            _budgetProgress.value = repository.getBudgetProgress()
        }
    }
    
    fun setBudget(amount: BigDecimal, period: BudgetPeriod = BudgetPeriod.MONTHLY) {
        viewModelScope.launch {
            repository.setBudget(amount, period)
            loadBudget()
        }
    }
    
    class Factory(private val repository: MoneyTrackerRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BudgetViewModel(repository) as T
        }
    }
}
