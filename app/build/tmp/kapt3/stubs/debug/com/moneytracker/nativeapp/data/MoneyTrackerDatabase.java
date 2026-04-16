package com.moneytracker.nativeapp.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&\u00a8\u0006\f"}, d2 = {"Lcom/moneytracker/nativeapp/data/MoneyTrackerDatabase;", "Landroidx/room/RoomDatabase;", "()V", "budgetDao", "Lcom/moneytracker/nativeapp/data/BudgetDao;", "categoryDao", "Lcom/moneytracker/nativeapp/data/CategoryDao;", "transactionDao", "Lcom/moneytracker/nativeapp/data/TransactionDao;", "userSettingsDao", "Lcom/moneytracker/nativeapp/data/UserSettingsDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.moneytracker.nativeapp.data.Transaction.class, com.moneytracker.nativeapp.data.Category.class, com.moneytracker.nativeapp.data.Budget.class, com.moneytracker.nativeapp.data.UserSettings.class}, version = 1, exportSchema = true)
@androidx.room.TypeConverters(value = {com.moneytracker.nativeapp.data.Converters.class})
public abstract class MoneyTrackerDatabase extends androidx.room.RoomDatabase {
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.moneytracker.nativeapp.data.MoneyTrackerDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull()
    public static final com.moneytracker.nativeapp.data.MoneyTrackerDatabase.Companion Companion = null;
    
    public MoneyTrackerDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.moneytracker.nativeapp.data.TransactionDao transactionDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.moneytracker.nativeapp.data.CategoryDao categoryDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.moneytracker.nativeapp.data.BudgetDao budgetDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.moneytracker.nativeapp.data.UserSettingsDao userSettingsDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/moneytracker/nativeapp/data/MoneyTrackerDatabase$Companion;", "", "()V", "INSTANCE", "Lcom/moneytracker/nativeapp/data/MoneyTrackerDatabase;", "getDatabase", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.moneytracker.nativeapp.data.MoneyTrackerDatabase getDatabase(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}