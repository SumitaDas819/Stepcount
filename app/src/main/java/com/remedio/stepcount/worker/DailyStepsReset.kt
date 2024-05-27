package com.remedio.stepcount.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import android.content.SharedPreferences
import android.util.Log
import com.remedio.stepcount.Constants
import com.remedio.stepcount.database.StepCount
import com.remedio.stepcount.database.StepCountDao
import com.remedio.stepcount.database.StepCountDatabase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DailyStepsReset(val context: Context, workParmes: WorkerParameters) :
    Worker(context, workParmes) {


    lateinit var stepCountDatabase: StepCountDatabase
    lateinit var stepConteDao: StepCountDao
    lateinit var sharedPreferences: SharedPreferences
    override fun doWork(): Result {
        Log.d("STEPCOUNTERTAG","do work function call")
        resetDailySteps()
        return Result.success()
    }

    private fun resetDailySteps() {
        sharedPreferences = context.getSharedPreferences(Constants.STEPCOUNT_PREF, Context.MODE_PRIVATE)
        stepCountDatabase= StepCountDatabase.getDatabase(context)
        stepConteDao=stepCountDatabase.stepCountDao()
        val stepsList=stepConteDao.getRecentStepCounts()
        Log.d("STEPCOUNTERTAG","steplist size is--${stepsList.size}")
        Log.d("STEPCOUNTERTAG","steplist is--$stepsList")
        if (stepsList.size>=7){
           stepConteDao.deleteFirstStepCount()

        }else{
            val todaysSteps=sharedPreferences.getInt(Constants.DAILY_STEPS,0)
            val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val hours=Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            val minutes=Calendar.getInstance().get(Calendar.MINUTE)
            val time="$currentDate : $hours : $minutes"

            val stepCount = StepCount(date = time, count = todaysSteps)
            stepConteDao.insertStepCount(stepCount)
        }
//        sharedPreferences.edit().apply {
//            putInt(Constants.DAILY_STEPS,0)
//            apply()
//            Log.d("STEPCOUNTERTAG","shared preferance updated")
//        }


    }
}