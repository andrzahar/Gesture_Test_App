package com.andr.zahar2.gesture_server.data.server

import com.andr.zahar2.api.model.ClientEvent
import com.andr.zahar2.api.model.GestureEvent
import io.ktor.server.websocket.DefaultWebSocketServerSession
import io.ktor.server.websocket.sendSerialized
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.Collections

class ClientsConnectionsManager {

    private val connections =
        Collections.synchronizedSet<DefaultWebSocketServerSession>(LinkedHashSet())

    private val _clientEventFlow = MutableSharedFlow<ClientEvent>()
    val clientEventFlow = _clientEventFlow.asSharedFlow()

    fun addConnection(session: DefaultWebSocketServerSession) {
        connections += session
    }

    fun broadcast(gestureEvent: GestureEvent) {
        connections.forEach {
            CoroutineScope(Dispatchers.IO).launch {
                it.sendSerialized(gestureEvent)
            }
        }
    }

    suspend fun onClientEvent(session: DefaultWebSocketServerSession, clientEvent: ClientEvent) {
        _clientEventFlow.emit(clientEvent)
    }

    fun removingConnection(session: DefaultWebSocketServerSession) {
        connections.remove(session)
    }
}