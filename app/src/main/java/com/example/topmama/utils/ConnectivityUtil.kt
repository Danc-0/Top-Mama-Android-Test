package com.example.topmama.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object ConnectivityUtil {

    fun isConnected(context: Context?): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}
