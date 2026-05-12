package com.example.nammamela.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "seats")
data class Seat(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val playId: Int,           // which event this seat belongs to
    val seatNumber: String,
    val reserved: Boolean = false
)
