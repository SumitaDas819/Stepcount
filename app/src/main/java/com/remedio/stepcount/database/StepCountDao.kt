package com.remedio.stepcount.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StepCountDao {
    @Query("SELECT * FROM step_count")
    fun getRecentStepCounts(): List<StepCount>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStepCount(stepCount: StepCount)

    @Query("DELETE FROM step_count WHERE id = (SELECT id FROM step_count ORDER BY id ASC LIMIT 1)")
    fun deleteFirstStepCount()


}