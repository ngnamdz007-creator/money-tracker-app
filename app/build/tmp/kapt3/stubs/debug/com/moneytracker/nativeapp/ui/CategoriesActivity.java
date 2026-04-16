package com.moneytracker.nativeapp.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0014J\u0010\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0005H\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0011H\u0002J\u0012\u0010\u0019\u001a\u00020\u00112\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0014J\u0012\u0010\u001c\u001a\u00020\u00112\b\u0010\u0015\u001a\u0004\u0018\u00010\u0005H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lcom/moneytracker/nativeapp/ui/CategoriesActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "categories", "", "Lcom/moneytracker/nativeapp/data/Category;", "currentType", "Lcom/moneytracker/nativeapp/data/TransactionType;", "fabAdd", "Lcom/google/android/material/floatingactionbutton/FloatingActionButton;", "repository", "Lcom/moneytracker/nativeapp/data/MoneyTrackerRepository;", "rvCategories", "Landroidx/recyclerview/widget/RecyclerView;", "tabLayout", "Lcom/google/android/material/tabs/TabLayout;", "attachBaseContext", "", "newBase", "Landroid/content/Context;", "deleteCategory", "category", "getDefaultIcon", "", "loadCategories", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "showCategoryDialog", "app_debug"})
public final class CategoriesActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.moneytracker.nativeapp.data.MoneyTrackerRepository repository;
    private androidx.recyclerview.widget.RecyclerView rvCategories;
    private com.google.android.material.tabs.TabLayout tabLayout;
    private com.google.android.material.floatingactionbutton.FloatingActionButton fabAdd;
    @org.jetbrains.annotations.NotNull()
    private com.moneytracker.nativeapp.data.TransactionType currentType = com.moneytracker.nativeapp.data.TransactionType.EXPENSE;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.moneytracker.nativeapp.data.Category> categories;
    
    public CategoriesActivity() {
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
    
    private final void loadCategories() {
    }
    
    private final void showCategoryDialog(com.moneytracker.nativeapp.data.Category category) {
    }
    
    private final void deleteCategory(com.moneytracker.nativeapp.data.Category category) {
    }
    
    private final java.lang.String getDefaultIcon() {
        return null;
    }
}