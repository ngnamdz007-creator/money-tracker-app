package com.moneytracker.nativeapp.data

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class MoneyTrackerRepository(context: Context) {
    private val db = MoneyTrackerDatabase.getDatabase(context)
    private val transactionDao = db.transactionDao()
    private val categoryDao = db.categoryDao()
    private val budgetDao = db.budgetDao()
    private val userSettingsDao = db.userSettingsDao()

    // Transactions
    fun getAllTransactions(): LiveData<List<Transaction>> = transactionDao.getAllTransactions()

    fun getTransactionsForCurrentMonth(): kotlinx.coroutines.flow.Flow<List<Transaction>> {
        val now = LocalDate.now()
        val start = now.withDayOfMonth(1)
        val end = now.withDayOfMonth(now.lengthOfMonth())
        return transactionDao.getTransactionsByDateRangeFlow(start, end)
    }
    
    suspend fun getTransactionsForCurrentMonthOnce(): List<Transaction> = withContext(Dispatchers.IO) {
        val now = LocalDate.now()
        val start = now.withDayOfMonth(1)
        val end = now.withDayOfMonth(now.lengthOfMonth())
        transactionDao.getTransactionsByDateRangeSync(start, end)
    }

    fun getTransactionsByDateRange(start: LocalDate, end: LocalDate): LiveData<List<Transaction>> {
        return transactionDao.getTransactionsByDateRange(start, end)
    }

    suspend fun addTransaction(transaction: Transaction): Long = withContext(Dispatchers.IO) {
        transactionDao.insert(transaction)
    }

    suspend fun updateTransaction(transaction: Transaction) = withContext(Dispatchers.IO) {
        transactionDao.update(transaction)
    }

    suspend fun deleteTransaction(transaction: Transaction) = withContext(Dispatchers.IO) {
        transactionDao.delete(transaction)
    }

    suspend fun deleteTransactionById(id: Long) = withContext(Dispatchers.IO) {
        transactionDao.deleteById(id)
    }

    suspend fun getTransactionById(id: Long): Transaction? = withContext(Dispatchers.IO) {
        transactionDao.getTransactionById(id)
    }

    // Statistics
    suspend fun getCurrentMonthStats(): MonthlyStats = withContext(Dispatchers.IO) {
        val now = LocalDate.now()
        val start = now.withDayOfMonth(1)
        val end = now.withDayOfMonth(now.lengthOfMonth())

        val result = transactionDao.getIncomeExpenseForPeriod(start, end)
        val income = result?.totalIncome ?: BigDecimal.ZERO
        val expense = result?.totalExpense ?: BigDecimal.ZERO

        MonthlyStats(
            month = now.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
            income = income,
            expense = expense,
            balance = income.subtract(expense)
        )
    }

    suspend fun getCategoryStats(): List<CategoryStats> = withContext(Dispatchers.IO) {
        val now = LocalDate.now()
        val start = now.withDayOfMonth(1)
        val end = now.withDayOfMonth(now.lengthOfMonth())

        val totals = transactionDao.getExpenseByCategory(start, end)
        val totalExpense = totals.sumOf { it.total }

        if (totalExpense == BigDecimal.ZERO) {
            return@withContext emptyList()
        }

        totals.map { catTotal ->
            CategoryStats(
                category = catTotal.category,
                amount = catTotal.total,
                percentage = catTotal.total.divide(totalExpense, 4, BigDecimal.ROUND_HALF_UP)
                    .multiply(BigDecimal(100)).toFloat(),
                color = getCategoryColor(catTotal.category)
            )
        }
    }

    suspend fun getMonthlyComparison(months: Int = 6): List<Pair<String, Pair<BigDecimal, BigDecimal>>> = withContext(Dispatchers.IO) {
        val result = mutableListOf<Pair<String, Pair<BigDecimal, BigDecimal>>>()
        val now = YearMonth.now()

        for (i in months - 1 downTo 0) {
            val month = now.minusMonths(i.toLong())
            val start = month.atDay(1)
            val end = month.atEndOfMonth()
            val stats = transactionDao.getIncomeExpenseForPeriod(start, end)

            result.add(Pair(
                month.format(DateTimeFormatter.ofPattern("MMM")),
                Pair(
                    stats?.totalIncome ?: BigDecimal.ZERO,
                    stats?.totalExpense ?: BigDecimal.ZERO
                )
            ))
        }

        result
    }

    // Categories
    fun getAllCategories(): LiveData<List<Category>> = categoryDao.getAllCategories()

    fun getExpenseCategories(): LiveData<List<Category>> = categoryDao.getCategoriesByType(TransactionType.EXPENSE)

    fun getIncomeCategories(): LiveData<List<Category>> = categoryDao.getCategoriesByType(TransactionType.INCOME)
    
    suspend fun addCategory(category: Category) = withContext(Dispatchers.IO) {
        categoryDao.insert(category)
    }
    
    suspend fun updateCategory(category: Category) = withContext(Dispatchers.IO) {
        categoryDao.update(category)
    }
    
    suspend fun deleteCategory(category: Category) = withContext(Dispatchers.IO) {
        categoryDao.delete(category)
    }

    suspend fun initializeDefaultCategories() = withContext(Dispatchers.IO) {
        val defaultCategories = listOf(
            // Expense categories
            Category("food", "Food & Dining", TransactionType.EXPENSE, "ic_restaurant", "#FF6B6B", true),
            Category("transport", "Transport", TransactionType.EXPENSE, "ic_directions_car", "#4ECDC4", true),
            Category("shopping", "Shopping", TransactionType.EXPENSE, "ic_shopping_bag", "#45B7D1", true),
            Category("entertainment", "Entertainment", TransactionType.EXPENSE, "ic_movie", "#96CEB4", true),
            Category("bills", "Bills & Utilities", TransactionType.EXPENSE, "ic_receipt", "#FFEAA7", true),
            Category("health", "Health", TransactionType.EXPENSE, "ic_local_hospital", "#DDA0DD", true),
            Category("education", "Education", TransactionType.EXPENSE, "ic_school", "#98D8C8", true),
            Category("other_expense", "Other", TransactionType.EXPENSE, "ic_more", "#95A5A6", true),

            // Income categories
            Category("salary", "Salary", TransactionType.INCOME, "ic_work", "#2ECC71", true),
            Category("freelance", "Freelance", TransactionType.INCOME, "ic_computer", "#3498DB", true),
            Category("investment", "Investment", TransactionType.INCOME, "ic_trending_up", "#9B59B6", true),
            Category("gift", "Gifts", TransactionType.INCOME, "ic_card_giftcard", "#E74C3C", true),
            Category("other_income", "Other", TransactionType.INCOME, "ic_add", "#95A5A6", true)
        )
        categoryDao.insertAll(defaultCategories)
    }

    // Budget
    fun getAllBudgets(): LiveData<List<Budget>> = budgetDao.getAllBudgets()

    suspend fun setBudget(amount: BigDecimal, period: BudgetPeriod = BudgetPeriod.MONTHLY) = withContext(Dispatchers.IO) {
        val existing = budgetDao.getOverallBudget()
        val now = LocalDate.now()
        val budget = Budget(
            id = existing?.id ?: 0,
            category = null,
            amount = amount,
            period = period,
            startDate = now.withDayOfMonth(1)
        )
        if (existing != null) {
            budgetDao.update(budget)
        } else {
            budgetDao.insert(budget)
        }
    }

    suspend fun getBudgetProgress(): Pair<BigDecimal, BigDecimal> = withContext(Dispatchers.IO) {
        val budget = budgetDao.getOverallBudget()
        val budgetAmount = budget?.amount ?: BigDecimal.ZERO

        val now = LocalDate.now()
        val start = now.withDayOfMonth(1)
        val end = now.withDayOfMonth(now.lengthOfMonth())
        val spent = transactionDao.getTotalByType(TransactionType.EXPENSE, start, end) ?: BigDecimal.ZERO

        Pair(spent, budgetAmount)
    }

    // Period-based queries
    suspend fun getStatsForPeriod(periodType: PeriodType, date: LocalDate): MonthlyStats = withContext(Dispatchers.IO) {
        val (start, end, periodLabel) = when (periodType) {
            PeriodType.DAY -> Triple(
                date,
                date,
                date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
            )
            PeriodType.MONTH -> Triple(
                date.withDayOfMonth(1),
                date.withDayOfMonth(date.lengthOfMonth()),
                date.format(DateTimeFormatter.ofPattern("MMMM yyyy"))
            )
            PeriodType.YEAR -> Triple(
                date.withDayOfYear(1),
                date.withDayOfYear(date.lengthOfYear()),
                date.format(DateTimeFormatter.ofPattern("yyyy"))
            )
        }

        val result = transactionDao.getIncomeExpenseForPeriod(start, end)
        val income = result?.totalIncome ?: BigDecimal.ZERO
        val expense = result?.totalExpense ?: BigDecimal.ZERO

        MonthlyStats(
            month = periodLabel,
            income = income,
            expense = expense,
            balance = income.subtract(expense)
        )
    }

    suspend fun getTransactionsForPeriod(periodType: PeriodType, date: LocalDate): List<Transaction> = withContext(Dispatchers.IO) {
        val (start, end) = when (periodType) {
            PeriodType.DAY -> Pair(date, date)
            PeriodType.MONTH -> Pair(date.withDayOfMonth(1), date.withDayOfMonth(date.lengthOfMonth()))
            PeriodType.YEAR -> Pair(date.withDayOfYear(1), date.withDayOfYear(date.lengthOfYear()))
        }
        transactionDao.getTransactionsByDateRangeSync(start, end)
    }

    // User Settings
    fun getUserSettings(): LiveData<UserSettings?> = userSettingsDao.getSettings()

    suspend fun updateSettings(settings: UserSettings) = withContext(Dispatchers.IO) {
        userSettingsDao.update(settings)
    }

    suspend fun getOrCreateSettings(): UserSettings = withContext(Dispatchers.IO) {
        userSettingsDao.getSettingsSync() ?: UserSettings().also {
            userSettingsDao.insert(it)
        }
    }

    private fun getCategoryColor(category: String): String {
        return when (category) {
            "food" -> "#FF6B6B"
            "transport" -> "#4ECDC4"
            "shopping" -> "#45B7D1"
            "entertainment" -> "#96CEB4"
            "bills" -> "#FFEAA7"
            "health" -> "#DDA0DD"
            "education" -> "#98D8C8"
            else -> "#95A5A6"
        }
    }
}
