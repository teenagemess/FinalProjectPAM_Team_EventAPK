package com.example.finalprojectpam.repositori

import android.content.Context
import com.example.finalprojectpam.database.DatabaseEvent

interface ContainerApp{
    val repositoriEvent : RepositoriEvent
}
class ContainerDataApp(private val context: Context): ContainerApp{
    override val repositoriEvent: RepositoriEvent by lazy {
        OfflineRepositoriEvent(DatabaseEvent.getDatabase(context).EventDAO()) }
    }