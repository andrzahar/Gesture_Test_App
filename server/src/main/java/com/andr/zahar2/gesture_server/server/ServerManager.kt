package com.andr.zahar2.gesture_server.server

import android.content.Context
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ServerManager(
    private val context: Context,
    private val clientsConnectionsManager: ClientsConnectionsManager
) {

    private var server: NettyApplicationEngine? = null

    fun startServer() {
        CoroutineScope(Dispatchers.IO).launch {
            stopServer()
            server = embeddedServer(Netty, port = 1106) {
                configureWebSockets(clientsConnectionsManager)
            }
            server?.start()
        }
    }

    fun stopServer() {
        CoroutineScope(Dispatchers.IO).launch {
            server?.stop()
            server = null
        }
    }
}