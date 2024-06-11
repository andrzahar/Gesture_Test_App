package com.andr.zahar2.api.model

import kotlinx.serialization.Serializable

@Serializable
enum class ClientEvent {
    CHROME_IS_OPEN, PAUSE
}