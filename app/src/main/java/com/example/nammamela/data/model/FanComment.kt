package com.example.nammamela.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fan_comments")
data class FanComment(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val playId: Int,
    val userName: String,
    val comment: String,
    val timestamp: Long = System.currentTimeMillis()
)
