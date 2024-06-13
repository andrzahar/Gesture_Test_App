package com.andr.zahar2.gesturetestapp.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.view.accessibility.AccessibilityEvent
import com.andr.zahar2.api.model.GestureEvent
import com.andr.zahar2.gesturetestapp.R
import com.andr.zahar2.gesturetestapp.domain.GesturesDomain
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GesturesAccessibilityService : AccessibilityService() {

    private val scope: CoroutineScope = MainScope()

    private var chromeOpenCheck = false

    @Inject
    lateinit var domain: GesturesDomain

    override fun onServiceConnected() {
        super.onServiceConnected()

        scope.launch {
            domain.gesturesFlow.collect {
                makeGesture(it)
            }
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

        if (event?.source?.packageName == getString(R.string.chrome_package)) {
            if (!chromeOpenCheck) {
                chromeOpenCheck = true
                onChromeIsOpen()
            }
        } else {
            chromeOpenCheck = false
        }
    }

    private fun onChromeIsOpen() {
        scope.launch {
            domain.onChromeIsOpen()
        }
    }

    private fun makeGesture(gestureEvent: GestureEvent) {
        if (!domain.isActiveFlow.value) return
        dispatchGesture(
            buildSwipe(gestureEvent),
            object : GestureResultCallback() {
                override fun onCompleted(gestureDescription: GestureDescription?) {
                    super.onCompleted(gestureDescription)
                    scope.launch {
                        domain.onGestureCompleted()
                    }
                }

                override fun onCancelled(gestureDescription: GestureDescription?) {
                    super.onCancelled(gestureDescription)
                    scope.launch {
                        domain.onGestureCancelled()
                    }
                }
            },
            null
        )
    }

    private fun buildSwipe(gestureEvent: GestureEvent): GestureDescription {
        val swipePath = Path()
        swipePath.moveTo(gestureEvent.startPoint.x, gestureEvent.startPoint.y)
        swipePath.lineTo(gestureEvent.endPoint.x, gestureEvent.endPoint.y)
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

    private fun cancelScope() {
        scope.cancel()
    }

    override fun onInterrupt() {
        cancelScope()
    }
}