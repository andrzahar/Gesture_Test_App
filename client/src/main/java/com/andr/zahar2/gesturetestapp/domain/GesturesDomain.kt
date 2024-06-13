package com.andr.zahar2.gesturetestapp.domain

import com.andr.zahar2.api.model.ClientEvent
import com.andr.zahar2.api.model.GestureEvent
import com.andr.zahar2.gesturetestapp.data.Network
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class GesturesDomain(private val network: Network) {

    private val _isActiveFlow = MutableStateFlow(false)
    val isActiveFlow = _isActiveFlow.asStateFlow()

    private val _gesturesFlow = MutableSharedFlow<GestureEvent>()
    val gesturesFlow: SharedFlow<GestureEvent> = _gesturesFlow.asSharedFlow()

    fun onStart() {
        _isActiveFlow.value = true
    }

    fun onChromeIsOpen() {
        if (!isActiveFlow.value) return
        network.start(_gesturesFlow, ClientEvent.CHROME_IS_OPEN)
    }

    fun onGestureCompleted() {
        network.sendEvent(ClientEvent.GESTURE_COMPLETED)
    }

    fun onGestureCancelled() {
        network.sendEvent(ClientEvent.GESTURE_CANCELLED)
    }

    fun onPause() {
        _isActiveFlow.value = false
        network.stop(ClientEvent.PAUSE)
    }
}