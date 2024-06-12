package com.andr.zahar2.gesturetestapp.domain

import com.andr.zahar2.api.model.ClientEvent
import com.andr.zahar2.gesturetestapp.data.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GesturesDomain(private val network: Network) {

    val isRunning = network.isRunning

    val gesturesFlow = network.gesturesFlow.flowOn(Dispatchers.IO)

    fun onChromeIsOpen(): Flow<Nothing> = flow<Nothing> {
        network.start()
        network.sendEvent(ClientEvent.CHROME_IS_OPEN)
    }.flowOn(Dispatchers.IO)

    fun onGestureCompletes(): Flow<Nothing> = flow<Nothing> {
        network.sendEvent(ClientEvent.GESTURE_COMPLETED)
    }.flowOn(Dispatchers.IO)

    fun onGestureCancelled(): Flow<Nothing> = flow<Nothing> {
        network.sendEvent(ClientEvent.GESTURE_CANCELLED)
    }.flowOn(Dispatchers.IO)

    fun onPause(): Flow<Nothing> = flow<Nothing> {
        network.sendEvent(ClientEvent.PAUSE)
        network.stop()
    }.flowOn(Dispatchers.IO)
}