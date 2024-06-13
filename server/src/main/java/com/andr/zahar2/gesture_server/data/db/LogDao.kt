package com.andr.zahar2.gesture_server.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andr.zahar2.gesture_server.data.model.LogEvent
import kotlinx.coroutines.flow.Flow

@Dao
interface LogDao {

    @Query("SELECT * FROM logevent")
    fun getAll(): Flow<List<LogEvent>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg logEvents: LogEvent)

    @Query("DELETE FROM logevent")
    suspend fun deleteAll()
}