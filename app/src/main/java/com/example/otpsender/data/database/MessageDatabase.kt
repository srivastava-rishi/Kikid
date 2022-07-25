package com.example.otpsender.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.otpsender.data.dao.MessageDao
import com.example.otpsender.data.entity.MessageEntity


@Database(entities = [MessageEntity::class], version = 5)
abstract class MessageDatabase: RoomDatabase() {

    abstract fun savedMessageDao(): MessageDao

}