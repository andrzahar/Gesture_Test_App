package com.andr.zahar2.gesture_server.ui

import androidx.lifecycle.ViewModel
import com.andr.zahar2.gesture_server.domain.GestureDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val domain: GestureDomain): ViewModel() {

    fun onStartClick() {
        domain.start()
    }

    fun onStopClick() {
        domain.stop()
    }

    override fun onCleared() {
        super.onCleared()
        domain.clear()
    }
}