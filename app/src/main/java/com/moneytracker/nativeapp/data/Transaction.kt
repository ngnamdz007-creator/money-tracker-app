package com.moneytracker.nativeapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amount: BigDecimal,
    val type: TransactionType, // INCOME, EXPENSE
    val category: String, // Food, Transport, Shopping, Salary, etc.
    val title: String,
    val description: String = "",
    val date: LocalDate,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val isRecurring: Boolean = false,
    val recurringPeriod: String? = null // daily, weekly, monthly, yearly
)

enum class TransactionType {
    INCOME, EXPENSE
}

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey
    val id: String,
    val name: String,
    val type: TransactionType,
    val icon: String,
    val color: String,
    val isDefault: Boolean = false
)

@Entity(tableName = "budgets")
data class Budget(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val category: String?, // null means overall budget
    val amount: BigDecimal,
    val period: BudgetPeriod, // MONTHLY, WEEKLY, YEARLY
    val startDate: LocalDate,
    val endDate: LocalDate? = null
)

enum class BudgetPeriod {
    WEEKLY, MONTHLY, YEARLY
}

enum class PeriodType {
    DAY, MONTH, YEAR
}

@Entity(tableName = "user_settings")
data class UserSettings(
    @PrimaryKey
    val id: Int = 1,
    val userName: String = "User",
    val currency: String = "USD",
    val language: String = "en",
    val monthlyBudget: BigDecimal = BigDecimal.ZERO,
    val notificationsEnabled: Boolean = true,
    val darkModeEnabled: Boolean = false
) {
    companion object {
        val SUPPORTED_CURRENCIES = listOf(
            "USD" to "$",
            "EUR" to "€",
            "GBP" to "£",
            "JPY" to "¥",
            "CNY" to "¥",
            "KRW" to "₩",
            "VND" to "₫",
            "SGD" to "S$",
            "AUD" to "A$",
            "CAD" to "C$",
            "CHF" to "Fr",
            "INR" to "₹",
            "RUB" to "₽",
            "BRL" to "R$",
            "MXN" to "$",
            "THB" to "฿",
            "MYR" to "RM",
            "IDR" to "Rp",
            "PHP" to "₱",
            "HKD" to "HK$"
        )
        
        val SUPPORTED_LANGUAGES = listOf(
            "en" to "English",
            "vi" to "Tiếng Việt",
            "es" to "Español",
            "fr" to "Français",
            "de" to "Deutsch",
            "it" to "Italiano",
            "pt" to "Português",
            "ru" to "Русский",
            "ja" to "日本語",
            "ko" to "한국어",
            "zh" to "中文",
            "th" to "ไทย",
            "id" to "Bahasa Indonesia",
            "hi" to "हिन्दी",
            "ar" to "العربية"
        )
    }
}

data class MonthlyStats(
    val month: String,
    val income: BigDecimal,
    val expense: BigDecimal,
    val balance: BigDecimal
)

data class CategoryStats(
    val category: String,
    val amount: BigDecimal,
    val percentage: Float,
    val color: String
)
