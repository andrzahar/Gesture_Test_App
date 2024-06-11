package com.andr.zahar2.gesture_server.server

import android.util.Log
import com.andr.zahar2.api.model.ClientEvent
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.websocket.WebSockets
import io.ktor.serialization.kotlinx.*
import io.ktor.server.routing.routing
import io.ktor.server.websocket.receiveDeserialized
import io.ktor.server.websocket.webSocket
import kotlinx.serialization.json.*

fun Application.configureWebSockets(connectionsManager: ClientsConnectionsManager) {
    install(WebSockets) {
        contentConverter = KotlinxWebsocketSerializationConverter(Json)
    }

    routing {
        webSocket("/gestures") {
            connectionsManager.addConnection(this)
            Log.d("ttt", "newConnection")
            try {
                while (true) {
                    val clientEvent = receiveDeserialized<ClientEvent>()
                    connectionsManager.onClientEvent(clientEvent)
                }
            } catch (e: Exception) {
                Log.d("ttt", e.message ?: "no message")
            } finally {
                connectionsManager.removingConnection(this)
                Log.d("ttt", "removed connection")
            }
        }
    }
}