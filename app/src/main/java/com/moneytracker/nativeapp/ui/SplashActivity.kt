package com.moneytracker.nativeapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.moneytracker.nativeapp.R
import com.nphlab.sdk.ads.NphAds

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Log.d("SplashActivity", "=== onCreate - About to call showSplash ===")

        // Show App Open ad and navigate to MainActivity when done
        val startTime = System.currentTimeMillis()
        try {
            Log.d("NphAds", "=== Calling showSplash() ===")
            NphAds.showSplash(this) {
                val elapsed = System.currentTimeMillis() - startTime
                Log.d("NphAds", "=== Splash callback — elapsed: ${elapsed}ms ===")
                navigateToMain()
            }
        } catch (e: Exception) {
            Log.e("NphAds", "=== showSplash FAILED: ${e.message}", e)
            navigateToMain()
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
