package com.moneytracker.nativeapp.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\fH\u0002J\b\u0010\u0010\u001a\u00020\u000eH\u0002J\u0012\u0010\u0011\u001a\u00020\u000e2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0014J\b\u0010\u0014\u001a\u00020\u000eH\u0002J\b\u0010\u0015\u001a\u00020\u000eH\u0002J\b\u0010\u0016\u001a\u00020\u000eH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/moneytracker/nativeapp/ui/LanguageActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "btnSave", "Lcom/google/android/material/button/MaterialButton;", "currentSettings", "Lcom/moneytracker/nativeapp/data/UserSettings;", "radioGroup", "Landroid/widget/RadioGroup;", "repository", "Lcom/moneytracker/nativeapp/data/MoneyTrackerRepository;", "selectedLanguage", "", "applyLanguage", "", "languageCode", "loadCurrentLanguage", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "restartApp", "saveLanguage", "setupLanguageOptions", "app_debug"})
public final class LanguageActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.moneytracker.nativeapp.data.MoneyTrackerRepository repository;
    private android.widget.RadioGroup radioGroup;
    private com.google.android.material.button.MaterialButton btnSave;
    @org.jetbrains.annotations.Nullable()
    private com.moneytracker.nativeapp.data.UserSettings currentSettings;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String selectedLanguage = "en";
    
    public LanguageActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupLanguageOptions() {
    }
    
    private final void loadCurrentLanguage() {
    }
    
    private final void saveLanguage() {
    }
    
    private final void applyLanguage(java.lang.String languageCode) {
    }
    
    private final void restartApp() {
    }
}