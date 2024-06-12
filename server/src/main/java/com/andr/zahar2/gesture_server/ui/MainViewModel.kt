package com.andr.zahar2.gesture_server.ui

import com.andr.zahar2.gesture_server.domain.GestureDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val domain: GestureDomain) {

    fun onStartClick() {
        domain.start()
    }

    fun onStopClick() {
        domain.stop()
    }
}