package com.example.nammamela.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cast_members")
data class CastMember(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val playId: Int,
    val actorName: String,
    val roleName: String,
    val imageUrl: String
)
