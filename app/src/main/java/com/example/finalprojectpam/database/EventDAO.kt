package com.example.finalprojectpam.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(event: Event)

    @Update
    suspend fun update(event: Event)

    @Delete
    suspend fun delete(event: Event)

    @Query("SELECT * from tblEvent WHERE id = :id")
    fun getEvent(id: Int): Flow<Event>

    @Query("SELECT * from tblEvent ORDER BY namaevent ASC")
    fun getAllEvent(): Flow<List<Event>>
}