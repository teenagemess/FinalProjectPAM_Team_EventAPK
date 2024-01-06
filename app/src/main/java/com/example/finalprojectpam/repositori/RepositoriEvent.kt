package com.example.finalprojectpam.repositori

import com.example.finalprojectpam.database.Event
import kotlinx.coroutines.flow.Flow

interface RepositoriEvent {
    fun getAllEventStream(): Flow<List<Event>>

    fun getEventStream(id: Int): Flow<Event?>

    suspend fun insertEvent(event: Event)

    suspend fun deleteEvent(event: Event)

    suspend fun updateEvent(event: Event)
}