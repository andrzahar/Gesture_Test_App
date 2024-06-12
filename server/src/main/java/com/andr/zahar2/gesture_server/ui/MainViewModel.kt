package com.andr.zahar2.gesture_server.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.lifecycle.ViewModel
import com.andr.zahar2.api.model.GestureEvent
import com.andr.zahar2.gesture_server.domain.GestureDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val domain: GestureDomain): ViewModel() {

    private var startPointer: PointerInputChange? = null
    private var endPointer: PointerInputChange? = null

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