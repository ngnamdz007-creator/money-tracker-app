package com.moneytracker.nativeapp.ui

import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.moneytracker.nativeapp.R
import com.nphlab.sdk.ads.NphAds
import com.moneytracker.nativeapp.data.MoneyTrackerRepository
import com.moneytracker.nativeapp.data.PeriodType
import com.moneytracker.nativeapp.data.Transaction
import com.moneytracker.nativeapp.data.TransactionType
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.text.NumberFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var rvTransactions: RecyclerView
    private lateinit var tvIncome: TextView
    private lateinit var tvExpense: TextView
    private lateinit var tvBalance: TextView
    private lateinit var tvGreeting: TextView
    private lateinit var tvCurrentPeriod: TextView
    private lateinit var btnAdd: Button
    private lateinit var dropdownPeriod: AutoCompleteTextView
    private lateinit var btnSelectDate: MaterialButton
    
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repo = MoneyTrackerRepository(requireContext())
        viewModel = ViewModelProvider(this, HomeViewModel.Factory(repo))[HomeViewModel::class.java]
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        tvIncome = view.findViewById(R.id.tvIncome)
        tvExpense = view.findViewById(R.id.tvExpense)
        tvBalance = view.findViewById(R.id.tvBalance)
        tvGreeting = view.findViewById(R.id.tvGreeting)
        tvCurrentPeriod = view.findViewById(R.id.tvCurrentPeriod)
        rvTransactions = view.findViewById(R.id.rvTransactions)
        btnAdd = view.findViewById(R.id.btnAdd)
        dropdownPeriod = view.findViewById(R.id.dropdownPeriod)
        btnSelectDate = view.findViewById(R.id.btnSelectDate)
        
        rvTransactions.layoutManager = LinearLayoutManager(requireContext())
        
        btnAdd.setOnClickListener {
            (activity as? MainActivity)?.navigateToAddTransaction()
        }
        
        // Load banner ad
        val bannerContainer = view.findViewById<android.widget.FrameLayout>(R.id.bannerAdContainer)
        bannerContainer?.let {
            android.util.Log.d("HomeFragment", "Loading banner ad...")
            NphAds.loadBannerInto(it, "nsp_bn_home_bottom")
        } ?: android.util.Log.e("HomeFragment", "Banner container not found!")
        
        setupPeriodSelector()
        setupDatePicker()
        observeData()
    }
    
    override fun onResume() {
        super.onResume()
        // Force refresh data when returning to this fragment
        viewModel.refreshData()
        updateGreeting()
    }
    
    private fun updateGreeting() {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val greetingRes = when (hour) {
            in 0..11 -> R.string.good_morning
            in 12..17 -> R.string.good_afternoon
            else -> R.string.good_evening
        }
        tvGreeting.text = getString(greetingRes)
    }
    
    private fun setupPeriodSelector() {
        val periods = listOf(
            getString(R.string.day) to PeriodType.DAY,
            getString(R.string.month) to PeriodType.MONTH,
            getString(R.string.year) to PeriodType.YEAR
        )
        
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, periods.map { it.first })
        dropdownPeriod.setAdapter(adapter)
        
        dropdownPeriod.setOnItemClickListener { _, _, position, _ ->
            val selectedPeriod = periods[position].second
            viewModel.setPeriod(selectedPeriod)
        }
        
        // Set default selection
        dropdownPeriod.setText(getString(R.string.month), false)
    }
    
    private fun setupDatePicker() {
        btnSelectDate.setOnClickListener {
            val periodType = viewModel.selectedPeriod.value
            
            when (periodType) {
                PeriodType.DAY -> showDatePicker()
                PeriodType.MONTH -> showMonthPicker()
                PeriodType.YEAR -> showYearPicker()
            }
        }
    }
    
    private fun showDatePicker() {
        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.select_date))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
        
        picker.addOnPositiveButtonClickListener { selection ->
            val date = Instant.ofEpochMilli(selection).atZone(ZoneId.systemDefault()).toLocalDate()
            viewModel.setDate(date)
        }
        
        picker.show(parentFragmentManager, "DATE_PICKER")
    }
    
    private fun showMonthPicker() {
        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.select_month))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
        
        picker.addOnPositiveButtonClickListener { selection ->
            val date = Instant.ofEpochMilli(selection).atZone(ZoneId.systemDefault()).toLocalDate()
            viewModel.setDate(date.withDayOfMonth(1))
        }
        
        picker.show(parentFragmentManager, "MONTH_PICKER")
    }
    
    private fun showYearPicker() {
        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.select_year))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
        
        picker.addOnPositiveButtonClickListener { selection ->
            val date = Instant.ofEpochMilli(selection).atZone(ZoneId.systemDefault()).toLocalDate()
            viewModel.setDate(date.withDayOfYear(1))
        }
        
        picker.show(parentFragmentManager, "YEAR_PICKER")
    }
    
    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.monthlyStats.collect { stats ->
                        tvIncome.text = formatAmount(stats.income)
                        tvExpense.text = formatAmount(stats.expense)
                        tvBalance.text = formatAmount(stats.balance)
                        tvBalance.setTextColor(
                            if (stats.balance >= BigDecimal.ZERO) 
                                androidx.core.content.ContextCompat.getColor(requireContext(), R.color.white)
                            else 
                                androidx.core.content.ContextCompat.getColor(requireContext(), R.color.pink_400)
                        )
                    }
                }
                
                launch {
                    viewModel.recentTransactions.collect { transactions ->
                        rvTransactions.adapter = TransactionAdapter(transactions)
                    }
                }
                
                launch {
                    viewModel.selectedPeriod.collect { period ->
                        updatePeriodDisplay()
                    }
                }
                
                launch {
                    viewModel.selectedDate.collect { date ->
                        updatePeriodDisplay()
                    }
                }
            }
        }
    }
    
    private fun updatePeriodDisplay() {
        val period = viewModel.selectedPeriod.value
        val date = viewModel.selectedDate.value
        
        val periodText = when (period) {
            PeriodType.DAY -> date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
            PeriodType.MONTH -> date.format(DateTimeFormatter.ofPattern("MMMM yyyy"))
            PeriodType.YEAR -> date.format(DateTimeFormatter.ofPattern("yyyy"))
        }
        tvCurrentPeriod.text = periodText
    }
    
    private fun formatAmount(amount: BigDecimal): String {
        return currencyFormat.format(amount)
    }
}

class TransactionAdapter(private val transactions: List<Transaction>) : 
    RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {
    
    private val dateFormat = DateTimeFormatter.ofPattern("MMM dd")
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
    
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCategory: TextView = view.findViewById(R.id.tvCategory)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
        val tvAmount: TextView = view.findViewById(R.id.tvAmount)
        val ivIcon: android.widget.ImageView = view.findViewById(R.id.ivIcon)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return ViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.tvCategory.text = transaction.category
        holder.tvTitle.text = transaction.title
        holder.tvDate.text = transaction.date.format(dateFormat)
        holder.tvAmount.text = currencyFormat.format(transaction.amount)
        
        val (amountColor, amountBg) = if (transaction.type == TransactionType.INCOME) {
            Pair(R.color.teal_500, R.drawable.bg_income_tag)
        } else {
            Pair(R.color.coral_500, R.drawable.bg_expense_tag)
        }
        holder.tvAmount.setTextColor(androidx.core.content.ContextCompat.getColor(holder.itemView.context, amountColor))
        
        // Update amount tag background - get parent view of tvAmount
        val amountContainer = holder.tvAmount.parent as? android.widget.LinearLayout
        amountContainer?.background = androidx.core.content.ContextCompat.getDrawable(holder.itemView.context, amountBg)
        
        // Set icon based on category
        holder.ivIcon.setImageResource(getCategoryIcon(transaction.category))
        
        // Set category-specific background and tint
        val (bgRes, tintRes) = getCategoryStyle(transaction.category)
        holder.ivIcon.background = androidx.core.content.ContextCompat.getDrawable(holder.itemView.context, bgRes)
        holder.ivIcon.setColorFilter(androidx.core.content.ContextCompat.getColor(holder.itemView.context, tintRes), android.graphics.PorterDuff.Mode.SRC_IN)
    }
    
    override fun getItemCount() = transactions.size.coerceAtMost(10)
    
    private fun getCategoryIcon(category: String): Int {
        return when (category) {
            "food" -> R.drawable.ic_restaurant
            "transport" -> R.drawable.ic_directions_car
            "shopping" -> R.drawable.ic_shopping_bag
            "entertainment" -> R.drawable.ic_movie
            "bills" -> R.drawable.ic_receipt
            "health" -> R.drawable.ic_local_hospital
            "education" -> R.drawable.ic_school
            "salary" -> R.drawable.ic_work
            "freelance" -> R.drawable.ic_computer
            "investment" -> R.drawable.ic_trending_up
            "gift" -> R.drawable.ic_card_giftcard
            else -> R.drawable.ic_more
        }
    }
    
    private fun getCategoryStyle(category: String): Pair<Int, Int> {
        return when (category) {
            "food" -> Pair(R.drawable.bg_circle_food, R.color.cat_food)
            "transport" -> Pair(R.drawable.bg_circle_transport, R.color.cat_transport)
            "shopping" -> Pair(R.drawable.bg_circle_shopping, R.color.cat_shopping)
            "entertainment" -> Pair(R.drawable.bg_circle_entertainment, R.color.cat_entertainment)
            "bills" -> Pair(R.drawable.bg_circle_food, R.color.cat_bills)
            "health" -> Pair(R.drawable.bg_circle_entertainment, R.color.cat_health)
            "education" -> Pair(R.drawable.bg_circle_transport, R.color.cat_education)
            "salary" -> Pair(R.drawable.bg_circle_salary, R.color.cat_salary)
            else -> Pair(R.drawable.bg_circle_other, R.color.cat_other)
        }
    }
}
