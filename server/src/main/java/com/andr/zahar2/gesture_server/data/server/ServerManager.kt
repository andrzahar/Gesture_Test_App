package com.andr.zahar2.gesture_server.data.server

import android.content.Context
import com.andr.zahar2.gesture_server.preferences.Preferences.portFlow
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ServerManager(
    private val context: Context,
    private val clientsConnectionsManager: ClientsConnectionsManager
) {

    private var server: NettyApplicationEngine? = null

    fun startServer() {
        CoroutineScope(Dispatchers.IO).launch {
            stopServer()
            val port = context.portFlow.first() ?: 80
            server = embeddedServer(Netty, port = port) {
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