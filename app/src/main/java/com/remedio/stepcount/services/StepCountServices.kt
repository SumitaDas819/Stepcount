package com.remedio.stepcount.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.remedio.stepcount.Constants

class StepCountServices() :Service() ,SensorEventListener{


    private lateinit var sensorManager: SensorManager
    private var stepCounterSensor: Sensor? = null
    private var steps: Int = 0
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val CHANNEL_ID = "StepCounterChannel"
        private const val FOREGROUND_SERVICE_ID = 101
    }


    override fun onCreate() {
        super.onCreate()
        Log.d("STEPCOUNTERTAG","Step counter created")
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        stepCounterSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
        sharedPreferences = getSharedPreferences(Constants.STEPCOUNT_PREF, Context.MODE_PRIVATE)
<<<<<<< HEAD
        steps=sharedPreferences.getInt(Constants.DAILY_STEPS,0)
=======
>>>>>>> ddf9448657a2a66e3bc989b7009b4c8eff791fa0
        createNotificationChannel()
        startForegroundService()

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("STEPCOUNTERTAG","Notification channel created")
            val name = "Step Counter Channel"
            val descriptionText = "Channel for Step Counter Service"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name,importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("ForegroundServiceType")
    private fun startForegroundService() {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Step Counter Service")
            .setContentText("Running")
            .setOngoing(true)
            .build()
        Log.d("STEPCOUNTERTAG","start foreground service callled")

        startForeground(FOREGROUND_SERVICE_ID, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {

            if (it.sensor.type == Sensor.TYPE_STEP_COUNTER) {
                Log.d("STEPCOUNTERTAG", "sensor motion detected")
                val detectedSteps = it.values[0].toInt()
                steps += detectedSteps
                saveDailySteps(steps)
            }
        }



    }
    override fun onDestroy() {
        super.onDestroy()
        Log.e("STEPCOUNTERTAG", "Service are destroyed")
        sensorManager.unregisterListener(this)
    }

    private fun saveDailySteps(steps: Int) {
        sharedPreferences.edit().apply {

            Log.d("STEPCOUNTERTAG","Save daily steps called on service class---$steps")


            putInt(Constants.DAILY_STEPS,steps)
            apply()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}