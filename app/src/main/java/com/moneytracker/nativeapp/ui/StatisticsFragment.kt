package com.moneytracker.nativeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.moneytracker.nativeapp.R
import com.moneytracker.nativeapp.data.MoneyTrackerRepository
import kotlinx.coroutines.launch

class StatisticsFragment : Fragment() {
    private lateinit var viewModel: StatisticsViewModel
    private lateinit var pieChart: PieChart
    private lateinit var barChart: BarChart
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repo = MoneyTrackerRepository(requireContext())
        viewModel = ViewModelProvider(this, StatisticsViewModel.Factory(repo))[StatisticsViewModel::class.java]
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_statistics, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        pieChart = view.findViewById(R.id.pieChart)
        barChart = view.findViewById(R.id.barChart)
        
        setupCharts()
        observeData()
    }
    
    private fun setupCharts() {
        pieChart.apply {
            description.isEnabled = false
            isDrawHoleEnabled = true
            holeRadius = 40f
            setHoleColor(android.R.color.transparent)
            legend.isEnabled = true
        }
        
        barChart.apply {
            description.isEnabled = false
            legend.isEnabled = false
            xAxis.apply {
                setDrawGridLines(false)
            }
            axisLeft.apply {
                setDrawGridLines(true)
            }
            axisRight.isEnabled = false
        }
    }
    
    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.categoryStats.collect { stats ->
                        updatePieChart(stats)
                    }
                }
                
                launch {
                    viewModel.monthlyComparison.collect { data ->
                        updateBarChart(data)
                    }
                }
            }
        }
    }
    
    private fun updatePieChart(stats: List<com.moneytracker.nativeapp.data.CategoryStats>) {
        val entries = stats.map {
            PieEntry(it.percentage, it.category)
        }
        
        val colors = stats.map { 
            android.graphics.Color.parseColor(it.color) 
        }
        
        val dataSet = PieDataSet(entries, "Categories").apply {
            this.colors = colors
            setDrawValues(true)
        }
        
        pieChart.data = PieData(dataSet)
        pieChart.invalidate()
    }
    
    private fun updateBarChart(data: List<Pair<String, Pair<java.math.BigDecimal, java.math.BigDecimal>>>) {
        val incomeEntries = data.mapIndexed { index, item ->
            BarEntry(index.toFloat(), item.second.first.toFloat())
        }
        
        val expenseEntries = data.mapIndexed { index, item ->
            BarEntry(index.toFloat(), item.second.second.toFloat())
        }
        
        val incomeDataSet = BarDataSet(incomeEntries, "Income").apply {
            color = androidx.core.content.ContextCompat.getColor(requireContext(), R.color.green_500)
        }
        
        val expenseDataSet = BarDataSet(expenseEntries, "Expense").apply {
            color = androidx.core.content.ContextCompat.getColor(requireContext(), R.color.red_500)
        }
        
        barChart.data = BarData(incomeDataSet, expenseDataSet)
        barChart.invalidate()
    }
}
