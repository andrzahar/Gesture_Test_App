package com.andr.zahar2.gesture_server.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andr.zahar2.api.model.GestureEvent
import com.andr.zahar2.gesture_server.data.model.LogEvent
import com.andr.zahar2.gesture_server.domain.GestureDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val domain: GestureDomain): ViewModel() {

    private val _logEvents = MutableStateFlow(emptyList<LogEvent>())
    val logEvents = _logEvents.asStateFlow()

    private var startPointer: PointerInputChange? = null
    private var endPointer: PointerInputChange? = null

    init {
        viewModelScope.launch {
            domain.getLogEvents().collect {
                _logEvents.emit(it)
            }
        }
    }

    fun onStartClick() {
        domain.start()
    }

    fun onStopClick() {
        domain.stop()
    }

    fun onDrag(change: PointerInputChange, dragAmount: Offset) {
        if (startPointer == null) {
            startPointer = change
        } else {
            endPointer = change
        }
    }

    fun onDrugEnd() {
        if (startPointer != null && endPointer != null) {
            val gestureEvent = GestureEvent(
                startPoint = startPointer!!.position.toPoint(),
                endPoint = endPointer!!.position.toPoint(),
                duration = endPointer!!.uptimeMillis - startPointer!!.uptimeMillis
            )
            domain.onGestureEvent(gestureEvent)
        }
        startPointer = null
        endPointer = null
    }

    override fun onCleared() {
        super.onCleared()
        domain.clear()
    }
}