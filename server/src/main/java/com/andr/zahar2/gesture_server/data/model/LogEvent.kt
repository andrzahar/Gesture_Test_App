package com.andr.zahar2.gesture_server.data.model

import androidx.room.Entity
import com.andr.zahar2.api.model.ClientEvent

@Entity(primaryKeys = ["timestamp", "address"])
data class LogEvent(
    val timestamp: Long,
    val address: String,
    val event: ClientEvent
)
