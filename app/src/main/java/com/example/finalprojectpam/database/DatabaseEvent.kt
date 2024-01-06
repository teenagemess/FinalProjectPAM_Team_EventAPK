package com.example.finalprojectpam.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Event::class], version = 1, exportSchema = false)
abstract class DatabaseEvent : RoomDatabase() {
    abstract fun EventDAO() : EventDAO

    companion object {
        @Volatile
        private var Instance: DatabaseEvent? = null

        fun getDatabase(context: Context): DatabaseEvent {
            return (Instance?: synchronized(this){
                Room.databaseBuilder(context,
                    DatabaseEvent::class.java,
                    "event_database")
                    .build().also { Instance=it }
            })
        }
    }
}