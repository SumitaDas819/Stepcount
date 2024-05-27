package com.remedio.stepcount.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "step_count")
data class StepCount(
 @PrimaryKey(autoGenerate = true)
 val id: Long = 0,
 @ColumnInfo(name = "date")
 val date: String,
 @ColumnInfo(name = "count")
 val count: Int
)
