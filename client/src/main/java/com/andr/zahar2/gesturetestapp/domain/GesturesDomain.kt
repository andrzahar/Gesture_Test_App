package com.andr.zahar2.gesturetestapp.domain

import com.andr.zahar2.api.model.ClientEvent
import com.andr.zahar2.api.model.GestureEvent
import com.andr.zahar2.gesturetestapp.data.Network
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class GesturesDomain(private val network: Network) {

    private val _gesturesFlow = MutableSharedFlow<GestureEvent>()
    val gesturesFlow: SharedFlow<GestureEvent> = _gesturesFlow.asSharedFlow()

    fun onChromeIsOpen() {
        network.start(_gesturesFlow, ClientEvent.CHROME_IS_OPEN)
    }

    fun onGestureCompleted() {
        network.sendEvent(ClientEvent.GESTURE_COMPLETED)
    }

    fun onGestureCancelled() {
        network.sendEvent(ClientEvent.GESTURE_CANCELLED)
    }

    fun onPause() {
        network.sendEvent(ClientEvent.PAUSE)
        network.stop()
    }
}