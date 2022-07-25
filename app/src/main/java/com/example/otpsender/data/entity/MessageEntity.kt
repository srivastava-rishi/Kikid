package com.example.otpsender.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int ,
    val name: String,
    val message:String,
    val image :String,
    val savedTimestamp: Long
)
