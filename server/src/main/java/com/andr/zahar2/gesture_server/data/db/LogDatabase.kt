package com.andr.zahar2.gesture_server.data.db

import androidx.room.Database
import com.andr.zahar2.gesture_server.data.model.LogEvent

@Database(entities = [LogEvent::class], version = 1)
abstract class LogDatabase {
    abstract fun logDao(): LogDao
}