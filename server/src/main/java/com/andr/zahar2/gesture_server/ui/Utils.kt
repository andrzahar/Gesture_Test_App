package com.andr.zahar2.gesture_server.ui

import androidx.compose.ui.geometry.Offset
import com.andr.zahar2.api.model.Point

fun Offset.toPoint(): Point = Point(x, y)