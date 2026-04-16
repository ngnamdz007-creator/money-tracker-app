package com.moneytracker.nativeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.moneytracker.nativeapp.R
import com.moneytracker.nativeapp.data.MoneyTrackerRepository
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

class BudgetFragment : Fragment() {
    private lateinit var viewModel: BudgetViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var tvSpent: TextView
    private lateinit var tvBudget: TextView
    private lateinit var tvRemaining: TextView
    private lateinit var etBudget: TextInputEditText
    private lateinit var btnSetBudget: MaterialButton
    
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repo = MoneyTrackerRepository(requireContext())
        viewModel = ViewModelProvider(this, BudgetViewModel.Factory(repo))[BudgetViewModel::class.java]
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_budget, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        progressBar = view.findViewById(R.id.progressBar)
        tvSpent = view.findViewById(R.id.tvSpent)
        tvBudget = view.findViewById(R.id.tvBudget)
        tvRemaining = view.findViewById(R.id.tvRemaining)
        etBudget = view.findViewById(R.id.etBudget)
        btnSetBudget = view.findViewById(R.id.btnSetBudget)
        
        btnSetBudget.setOnClickListener {
            val amount = etBudget.text.toString().toBigDecimalOrNull() ?: BigDecimal.ZERO
            if (amount > BigDecimal.ZERO) {
                viewModel.setBudget(amount)
            }
        }
        
        observeData()
    }
    
    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.budgetProgress.collect { (spent, budget) ->
                    updateBudgetUI(spent, budget)
                }
            }
        }
    }
    
    private fun updateBudgetUI(spent: BigDecimal, budget: BigDecimal) {
        tvSpent.text = getString(R.string.spent_label, currencyFormat.format(spent))
        tvBudget.text = getString(R.string.budget_label, currencyFormat.format(budget))
        
        val remaining = budget.subtract(spent)
        tvRemaining.text = getString(R.string.remaining_label, currencyFormat.format(remaining))
        tvRemaining.setTextColor(
            if (remaining >= BigDecimal.ZERO)
                androidx.core.content.ContextCompat.getColor(requireContext(), R.color.green_500)
            else
                androidx.core.content.ContextCompat.getColor(requireContext(), R.color.red_500)
        )
        
        val progress = if (budget > BigDecimal.ZERO) {
            (spent.divide(budget, 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal(100))).toInt()
        } else 0
        
        progressBar.progress = progress.coerceIn(0, 100)
    }
}
