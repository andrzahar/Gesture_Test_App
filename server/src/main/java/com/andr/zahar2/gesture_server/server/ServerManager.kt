package com.andr.zahar2.gesture_server.server

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine

class ServerManager {

    private var server: NettyApplicationEngine? = null

    fun startServer() {
        stopServer()
        server = embeddedServer(Netty, port = 1106) {
            configureWebSockets(ClientsConnectionsManager())
        }
        server?.start()
    }

    fun stopServer() {
        server?.stop()
        server = null
    }
}