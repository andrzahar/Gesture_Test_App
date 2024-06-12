package com.andr.zahar2.gesture_server.domain

import com.andr.zahar2.api.model.GestureEvent
import com.andr.zahar2.api.model.Point
import com.andr.zahar2.gesture_server.server.ClientsConnectionsManager
import com.andr.zahar2.gesture_server.server.ServerManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class GestureDomain(
    private val serverManager: ServerManager,
    private val clientsConnectionsManager: ClientsConnectionsManager
) {

    private val repositoryScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        repositoryScope.launch {
            clientsConnectionsManager.clientEventFlow.collect {
                clientsConnectionsManager.broadcast(
                    GestureEvent(Point(500f, 1000f), Point(500f, 1500f), 3000L)
                )
            }
        }
    }

    fun start() {
        serverManager.startServer()
    }

    fun onGestureEvent(gestureEvent: GestureEvent) {
        clientsConnectionsManager.broadcast(gestureEvent)
    }

    fun stop() {
        serverManager.stopServer()
    }

    fun clear() {
        repositoryScope.cancel()
    }
}