package com.remedio.stepcount.activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.remedio.stepcount.Constants
import com.remedio.stepcount.R
import com.remedio.stepcount.database.StepCountDao
import com.remedio.stepcount.database.StepCountDatabase
import com.remedio.stepcount.databinding.ActivityShowDailyStepsBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ShowDailyStepsActivity : AppCompatActivity() {
    lateinit var stepCountDatabase: StepCountDatabase
    lateinit var stepConteDao: StepCountDao
    lateinit var sharedPreferences: SharedPreferences
    lateinit var binding:ActivityShowDailyStepsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityShowDailyStepsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(Constants.STEPCOUNT_PREF, Context.MODE_PRIVATE)
        stepCountDatabase= StepCountDatabase.getDatabase(this)
        stepConteDao=stepCountDatabase.stepCountDao()
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
            Date()
        )
        val steps=sharedPreferences.getInt(Constants.DAILY_STEPS,0)
        Log.d("STEPCOUNTERTAG","Save daily steps called on show daily steps class---$steps")
        binding.date.text=currentDate
        binding.step.text=steps.toString()
        val stepRecords=stepConteDao.getRecentStepCounts()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, stepRecords)
        binding.stepsListView.adapter = adapter



    }
}