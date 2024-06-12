package com.andr.zahar2.gesturetestapp.data

import com.andr.zahar2.api.model.ClientEvent
import com.andr.zahar2.api.model.GestureEvent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.receiveDeserialized
import io.ktor.client.plugins.websocket.sendSerialized
import io.ktor.client.plugins.websocket.webSocket
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow

class Network(private val client: HttpClient) {

    private var socketSession: DefaultClientWebSocketSession? = null

    private val _isRunning = MutableStateFlow(false)
    val isRunning = _isRunning.asStateFlow()

    suspend fun start() {
        client.webSocket(host = "192.168.101.15", port = 1106, path = "/gestures") {
            socketSession = this
            _isRunning.emit(true)
        }
    }

    val gesturesFlow: Flow<GestureEvent> = flow {
        try {
            while (isRunning.value) {
                val gesture = socketSession?.receiveDeserialized<GestureEvent>()
                gesture?.let { emit(it) }
            }
        } catch (_: Exception) {

        } finally {
            stop()
        }
    }

    suspend fun sendEvent(event: ClientEvent) {
        socketSession?.sendSerialized(event)
    }

    suspend fun stop() {
        _isRunning.emit(false)
        socketSession?.cancel()
        socketSession = null
    }
}