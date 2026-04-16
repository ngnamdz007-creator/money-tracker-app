package com.moneytracker.nativeapp.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0014J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0013H\u0002J\u0012\u0010\u001d\u001a\u00020\u00172\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0014J\b\u0010 \u001a\u00020\u0017H\u0002J\b\u0010!\u001a\u00020\u0017H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\""}, d2 = {"Lcom/moneytracker/nativeapp/ui/AddTransactionActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "btnSave", "Lcom/google/android/material/button/MaterialButton;", "chipGroup", "Lcom/google/android/material/chip/ChipGroup;", "currentCategories", "", "Lcom/moneytracker/nativeapp/data/Category;", "etAmount", "Lcom/google/android/material/textfield/TextInputEditText;", "etDescription", "etTitle", "radioType", "Landroid/widget/RadioGroup;", "repository", "Lcom/moneytracker/nativeapp/data/MoneyTrackerRepository;", "selectedCategory", "", "selectedType", "Lcom/moneytracker/nativeapp/data/TransactionType;", "attachBaseContext", "", "newBase", "Landroid/content/Context;", "getIconResource", "", "iconName", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "saveTransaction", "updateCategories", "app_debug"})
public final class AddTransactionActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.moneytracker.nativeapp.data.MoneyTrackerRepository repository;
    private android.widget.RadioGroup radioType;
    private com.google.android.material.textfield.TextInputEditText etAmount;
    private com.google.android.material.textfield.TextInputEditText etTitle;
    private com.google.android.material.textfield.TextInputEditText etDescription;
    private com.google.android.material.chip.ChipGroup chipGroup;
    private com.google.android.material.button.MaterialButton btnSave;
    @org.jetbrains.annotations.NotNull()
    private com.moneytracker.nativeapp.data.TransactionType selectedType = com.moneytracker.nativeapp.data.TransactionType.EXPENSE;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String selectedCategory = "";
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.moneytracker.nativeapp.data.Category> currentCategories;
    
    public AddTransactionActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void attachBaseContext(@org.jetbrains.annotations.NotNull()
    android.content.Context newBase) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void updateCategories() {
    }
    
    private final int getIconResource(java.lang.String iconName) {
        return 0;
    }
    
    private final void saveTransaction() {
    }
}