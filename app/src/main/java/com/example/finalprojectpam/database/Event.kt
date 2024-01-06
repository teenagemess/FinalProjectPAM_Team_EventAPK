package com.example.finalprojectpam.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblEvent")
data class Event(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val namaevent : String,
    val alamatevent : String,
    val penyelenggaraevent : String,
    val deskripsievent : String,
    val waktuevent : String,
    val tanggalevent :  String,
)