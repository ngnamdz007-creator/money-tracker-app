package com.moneytracker.nativeapp.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0014J\b\u0010\u0010\u001a\u00020\rH\u0002J\u0012\u0010\u0011\u001a\u00020\r2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0014J\b\u0010\u0014\u001a\u00020\rH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/moneytracker/nativeapp/ui/SettingsActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "btnSave", "Lcom/google/android/material/button/MaterialButton;", "currentSettings", "Lcom/moneytracker/nativeapp/data/UserSettings;", "etMonthlyBudget", "Lcom/google/android/material/textfield/TextInputEditText;", "etUserName", "repository", "Lcom/moneytracker/nativeapp/data/MoneyTrackerRepository;", "attachBaseContext", "", "newBase", "Landroid/content/Context;", "loadSettings", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "saveSettings", "app_debug"})
public final class SettingsActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.moneytracker.nativeapp.data.MoneyTrackerRepository repository;
    private com.google.android.material.textfield.TextInputEditText etUserName;
    private com.google.android.material.textfield.TextInputEditText etMonthlyBudget;
    private com.google.android.material.button.MaterialButton btnSave;
    @org.jetbrains.annotations.Nullable()
    private com.moneytracker.nativeapp.data.UserSettings currentSettings;
    
    public SettingsActivity() {
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
    
    private final void loadSettings() {
    }
    
    private final void saveSettings() {
    }
}