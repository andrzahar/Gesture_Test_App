package com.andr.zahar2.api.model

import kotlinx.serialization.Serializable

@Serializable
enum class ClientEvent {
    CHROME_IS_OPEN, GESTURE_COMPLETED, GESTURE_CANCELLED, PAUSE
}