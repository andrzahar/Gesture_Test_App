package com.andr.zahar2.gesturetestapp.data

import com.andr.zahar2.api.model.ClientEvent
import com.andr.zahar2.api.model.GestureEvent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.receiveDeserialized
import io.ktor.client.plugins.websocket.sendSerialized
import io.ktor.client.plugins.websocket.webSocket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Network(private val client: HttpClient) {

    private var socketSession: DefaultClientWebSocketSession? = null

    fun gestureListener(): Flow<GestureEvent> = flow {
        client.webSocket(host = "", port = 1106, path = "/gestures") {
            socketSession = this
            try {
                while (true) {
                    val gesture = receiveDeserialized<GestureEvent>()
                    emit(gesture)
                }
            } catch (e: Exception) {

            } finally {
                socketSession = null
            }
        }
    }

    fun sendEvent(event: ClientEvent): Flow<Any> = flow {
        socketSession?.sendSerialized(event)
    }
}