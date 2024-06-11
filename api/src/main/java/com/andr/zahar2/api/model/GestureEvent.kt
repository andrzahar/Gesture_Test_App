package com.andr.zahar2.api.model

import kotlinx.serialization.Serializable


@Serializable
data class GestureEvent(
    val startPoint: Point,
    val endPoint: Point,
    val duration: Long
)