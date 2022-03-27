package com.example.topmama.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.work.*
import com.example.topmama.R
import com.example.topmama.utils.notify.NotifyWork
import com.example.topmama.utils.notify.work.NotifyWork.Companion.NOTIFICATION_WORK
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.make
import dagger.hilt.android.AndroidEntryPoint
import java.lang.System.currentTimeMillis
import java.util.concurrent.TimeUnit
import kotlin.time.ExperimentalTime

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FIRST_SYSTEM_WINDOW)
        window.statusBarColor = this.resources.getColor(android.R.color.darker_gray)

        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        if (!isConnected) {
            val main = findViewById<ConstraintLayout>(R.id.main)
            showSnackBar(
                main,
                "Check your internet connection to access latest weather information"
            )
        }
    }

    private fun showSnackBar(view: View, message: String) {
        val snackBar = make(view, message, Snackbar.LENGTH_INDEFINITE)
            .setAction("Okay") {

            }
            .setActionTextColor(ContextCompat.getColor(this, android.R.color.white))
        val snackBarView = snackBar.view
        val txt =
            snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.black))
        txt.setTextColor(ContextCompat.getColor(this, android.R.color.white))

        snackBar.show()
    }

    companion object {
        const val NOTIFICATION_ID = "appName_notification_id"
    }
}


