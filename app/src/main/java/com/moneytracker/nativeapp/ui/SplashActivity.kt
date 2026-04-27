package com.moneytracker.nativeapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.moneytracker.nativeapp.R
import com.nphlab.sdk.ads.NphAds

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Wait for SDK to fully initialize before showing splash ad
        // SDK needs ~1.5s to init + load App Open ad
        val handler = android.os.Handler(mainLooper)
        val timeoutRunnable = Runnable { navigateToMain() }

        // Timeout safety: navigate after 4s max regardless
        handler.postDelayed(timeoutRunnable, 4000)

        // Try to show splash ad
        handler.postDelayed({
            try {
                NphAds.showSplash(this) {
                    handler.removeCallbacks(timeoutRunnable)
                    navigateToMain()
                }
            } catch (e: Exception) {
                handler.removeCallbacks(timeoutRunnable)
                navigateToMain()
            }
        }, 1500) // Wait 1.5s for SDK to be ready
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
