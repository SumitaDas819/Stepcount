package com.remedio.stepcount.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.remedio.stepcount.R
import com.remedio.stepcount.databinding.ActivityMainBinding
import com.remedio.stepcount.services.StepCountServices
import com.remedio.stepcount.worker.DailyStepsReset
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = Intent(this, StepCountServices::class.java)
        startForegroundService(intent)
        Log.d("STEPCOUNTERTAG","Service intent call")
        scheculeWorker()
        binding.btn.setOnClickListener {
            Intent(this, ShowDailyStepsActivity::class.java).also {
                startActivity(it)
            }
        }


    }

    private fun scheculeWorker() {
        val workRequest = PeriodicWorkRequestBuilder<DailyStepsReset>(2, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }


}