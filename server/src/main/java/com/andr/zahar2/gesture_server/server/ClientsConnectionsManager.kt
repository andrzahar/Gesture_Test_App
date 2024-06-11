package com.andr.zahar2.gesture_server.server

import com.andr.zahar2.api.model.ClientEvent
import com.andr.zahar2.api.model.GestureEvent
import com.andr.zahar2.api.model.Point
import io.ktor.server.websocket.DefaultWebSocketServerSession
import io.ktor.server.websocket.sendSerialized
import java.util.Collections

class ClientsConnectionsManager {

    private val connections =
        Collections.synchronizedSet<DefaultWebSocketServerSession>(LinkedHashSet())

    fun addConnection(session: DefaultWebSocketServerSession) {
        connections += session
    }

    suspend fun broadcast(gestureEvent: GestureEvent) {
        connections.forEach {
            it.sendSerialized(gestureEvent)
        }
    }

    suspend fun onClientEvent(clientEvent: ClientEvent) {
        broadcast(GestureEvent(Point(0f, 0f), Point(1f, 1f), 3L))
    }

    fun removingConnection(session: DefaultWebSocketServerSession) {
        connections.remove(session)
    }
}