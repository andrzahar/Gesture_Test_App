package com.andr.zahar2.gesture_server.server

import com.andr.zahar2.api.model.ClientEvent
import com.andr.zahar2.api.model.Constants
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.routing
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.receiveDeserialized
import io.ktor.server.websocket.webSocket
import kotlinx.serialization.json.Json

fun Application.configureWebSockets(connectionsManager: ClientsConnectionsManager) {
    install(WebSockets) {
        contentConverter = KotlinxWebsocketSerializationConverter(Json)
    }

    routing {
        webSocket(Constants.PATH) {
            connectionsManager.addConnection(this)
            try {
                while (true) {
                    val clientEvent = receiveDeserialized<ClientEvent>()
                    connectionsManager.onClientEvent(this, clientEvent)
                }
            } catch (_: Exception) {
            } finally {
                connectionsManager.removingConnection(this)
            }
        }
    }
}