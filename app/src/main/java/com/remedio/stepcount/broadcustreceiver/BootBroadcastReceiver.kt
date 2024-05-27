package com.remedio.stepcount.broadcustreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.remedio.stepcount.services.StepCountServices

class BootBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            val serviceIntent = Intent(context, StepCountServices::class.java)
            ContextCompat.startForegroundService(context, serviceIntent)
        }
    }
}