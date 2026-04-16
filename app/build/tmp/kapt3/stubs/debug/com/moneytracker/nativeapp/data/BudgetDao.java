package com.moneytracker.nativeapp.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\bH\'J\u0018\u0010\n\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\rJ\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0013"}, d2 = {"Lcom/moneytracker/nativeapp/data/BudgetDao;", "", "delete", "", "budget", "Lcom/moneytracker/nativeapp/data/Budget;", "(Lcom/moneytracker/nativeapp/data/Budget;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllBudgets", "Landroidx/lifecycle/LiveData;", "", "getBudgetByCategory", "category", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getOverallBudget", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insert", "", "update", "app_debug"})
@androidx.room.Dao()
public abstract interface BudgetDao {
    
    @androidx.room.Query(value = "SELECT * FROM budgets ORDER BY startDate DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.moneytracker.nativeapp.data.Budget>> getAllBudgets();
    
    @androidx.room.Query(value = "SELECT * FROM budgets WHERE category IS NULL LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getOverallBudget(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.moneytracker.nativeapp.data.Budget> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM budgets WHERE category = :category LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getBudgetByCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String category, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.moneytracker.nativeapp.data.Budget> $completion);
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.moneytracker.nativeapp.data.Budget budget, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.moneytracker.nativeapp.data.Budget budget, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.moneytracker.nativeapp.data.Budget budget, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}