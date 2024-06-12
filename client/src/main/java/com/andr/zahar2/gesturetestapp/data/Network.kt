package com.andr.zahar2.gesturetestapp.data

import com.andr.zahar2.api.model.ClientEvent
import com.andr.zahar2.api.model.GestureEvent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.receiveDeserialized
import io.ktor.client.plugins.websocket.sendSerialized
import io.ktor.client.plugins.websocket.webSocket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class Network(private val client: HttpClient) {

    private var socketSession: DefaultClientWebSocketSession? = null

    private val _isRunning = MutableStateFlow(false)
    val isRunning = _isRunning.asStateFlow()

    val gestureListener: Flow<GestureEvent> = flow {
        client.webSocket(host = "192.168.101.15", port = 1106, path = "/gestures") {
            socketSession = this
            _isRunning.tryEmit(true)
            try {
                while (true) {
                    val gesture = receiveDeserialized<GestureEvent>()
                    emit(gesture)
                }
            } catch (_: Exception) {

            } finally {
                stop()
            }
        }
    }.flowOn(Dispatchers.IO)

    fun sendEvent(event: ClientEvent): Flow<Nothing> = flow<Nothing> {
        socketSession?.sendSerialized(event)
    }.flowOn(Dispatchers.IO)

    fun stop() {
        _isRunning.tryEmit(false)
        socketSession?.cancel()
        socketSession = null
    }
}