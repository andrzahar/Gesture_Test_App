package com.andr.zahar2.gesturetestapp.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.andr.zahar2.api.model.ClientEvent
import com.andr.zahar2.api.model.GestureEvent
import com.andr.zahar2.gesturetestapp.data.Network
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class GesturesAccessibilityService : AccessibilityService() {

    private val scope = MainScope()

    @Inject
    lateinit var network: Network

    override fun onServiceConnected() {
        super.onServiceConnected()

        network.gestureListener().onEach {
            makeGesture(it)
        }.launchIn(scope)

        Log.d("ttt", "onServiceConnected")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

        if (event?.source?.packageName == "com.android.chrome") {
            Log.d("ttt", "chrome")
        } else {
            Log.d("ttt", "not chrome")
        }

        //network.sendEvent(ClientEvent.CHROME_IS_OPEN).launchIn(scope)
    }

    private fun makeGesture(gestureEvent: GestureEvent) {
        dispatchGesture(
            buildSwipe(gestureEvent),
            object : GestureResultCallback() {
                override fun onCompleted(gestureDescription: GestureDescription?) {
                    super.onCompleted(gestureDescription)
                    Log.d("ttt", "onCompleted")
                }

                override fun onCancelled(gestureDescription: GestureDescription?) {
                    super.onCancelled(gestureDescription)
                    Log.d("ttt", "onCancelled")
                }
            },
            null
        )
    }

    private fun buildSwipe(gestureEvent: GestureEvent): GestureDescription {
        val swipePath = Path()
        swipePath.moveTo(gestureEvent.startPoint.x, gestureEvent.startPoint.y)
        swipePath.lineTo(gestureEvent.endPoint.x, gestureEvent.endPoint.x)
        val swipeBuilder = GestureDescription.Builder()
        swipeBuilder.addStroke(
            GestureDescription.StrokeDescription(
                swipePath,
                0,
                gestureEvent.duration
            )
        )
        return swipeBuilder.build()
    }

    override fun onInterrupt() {
        scope.cancel()
    }
}