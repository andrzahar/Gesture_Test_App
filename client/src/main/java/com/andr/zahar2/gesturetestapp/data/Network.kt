package com.andr.zahar2.gesturetestapp.data

import android.content.Context
import com.andr.zahar2.api.model.ClientEvent
import com.andr.zahar2.api.model.Constants
import com.andr.zahar2.api.model.GestureEvent
import com.andr.zahar2.gesturetestapp.preferences.Preferences.ipFlow
import com.andr.zahar2.gesturetestapp.preferences.Preferences.portFlow
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.receiveDeserialized
import io.ktor.client.plugins.websocket.sendSerialized
import io.ktor.client.plugins.websocket.webSocket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class Network(private val context: Context, private val client: HttpClient) {

    private var socketSession: DefaultClientWebSocketSession? = null

    private var webSocketJob: Job? = null

    fun start(gesturesFlow: MutableSharedFlow<GestureEvent>, eventOnStart: ClientEvent) {
        webSocketJob = CoroutineScope(Dispatchers.IO).launch {
            supervisorScope {
                while (isActive) {
                    try {
                        connectAndReceiveMessages(gesturesFlow, eventOnStart)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        delay(5000)
                    }
                }
            }
        }
    }

    private suspend fun connectAndReceiveMessages(
        gesturesFlow: MutableSharedFlow<GestureEvent>,
        eventOnStart: ClientEvent
    ) {
        val host = context.ipFlow.first()
        val port = context.portFlow.first()
        client.webSocket(host = host, port = port, path = Constants.PATH) {
            socketSession = this
            sendEvent(eventOnStart)
            try {
                receiveMessages(gesturesFlow)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                stop()
            }
        }
    }

    private suspend fun DefaultClientWebSocketSession.receiveMessages(
        gesturesFlow: MutableSharedFlow<GestureEvent>
    ) {
        while (isActive) {
            val gesture = receiveDeserialized<GestureEvent>()
            gesturesFlow.emit(gesture)
        }
    }

    fun sendEvent(event: ClientEvent) {
        CoroutineScope(Dispatchers.IO).launch {
            socketSession?.sendSerialized(event)
        }
    }

    fun stop() {
        socketSession?.cancel()
        socketSession = null

        webSocketJob?.cancel()
        webSocketJob = null
    }
}