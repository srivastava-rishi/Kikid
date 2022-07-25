package com.example.otpsender.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.otpsender.data.entity.MessageEntity


@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)

    suspend fun insertNewMessage(savedNewsEntity: MessageEntity)

    @Query("SELECT * FROM messages ORDER BY savedTimestamp DESC")

    fun getAllSavedMessageByOrder() : List<MessageEntity>

}