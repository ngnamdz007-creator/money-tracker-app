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

        val handler = android.os.Handler(mainLooper)
        val navigateRunnable = Runnable { navigateToMain() }

        // Wait 1.5s for SDK to fully initialize, then show splash
        handler.postDelayed({
            try {
                NphAds.showSplash(this) {
                    // Ad finished or failed — navigate immediately
                    handler.removeCallbacks(navigateRunnable)
                    navigateToMain()
                }
            } catch (e: Exception) {
                navigateToMain()
            }
        }, 1500)

        // Safety timeout: max 8 seconds on splash screen
        handler.postDelayed(navigateRunnable, 8000)
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
