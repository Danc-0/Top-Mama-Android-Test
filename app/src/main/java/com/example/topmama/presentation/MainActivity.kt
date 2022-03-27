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
        val data = Data.Builder()
            .putInt(NOTIFICATION_ID, 0)
            .build()
        val delay = 5000L
        scheduleNotification(delay, data)
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

    private fun scheduleNotification(delay: Long, data: Data) {
        val instanceWorkManager = WorkManager.getInstance(this)

        val notificationConstraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val periodicNotificationWork = PeriodicWorkRequestBuilder<NotifyWork>(10, TimeUnit.SECONDS)
            .setConstraints(notificationConstraints)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()

        instanceWorkManager.enqueue(periodicNotificationWork)
    }

    companion object {
        const val NOTIFICATION_ID = "appName_notification_id"
    }

    @ExperimentalTime
    private fun userInterface(view: View) {
        val currentTime = currentTimeMillis()
        val customTime = 1648388160997
        val data = Data.Builder().putInt(NOTIFICATION_ID, 0).build()
        val delay = customTime - currentTime
//        scheduleNotification(delay, data)

        val titleNotificationSchedule = getString(R.string.weather_reminder)
        val patternNotificationSchedule = getString(R.string.notification_subtitle)

        make(
            view, "Hello",
            LENGTH_INDEFINITE
        )
    }
}


