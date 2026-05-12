package com.example.nammamela.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plays")
data class Play(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val duration: String,
    val description: String,
    val posterUrl: String,
    val date: String = "TBD",
    val time: String = "7:00 PM",
    val venue: String = "Main Auditorium"
)
