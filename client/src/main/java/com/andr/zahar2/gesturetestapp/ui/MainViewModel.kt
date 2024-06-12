package com.andr.zahar2.gesturetestapp.ui

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.andr.zahar2.gesturetestapp.R
import com.andr.zahar2.gesturetestapp.domain.GesturesDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val domain: GesturesDomain) : ViewModel() {

    private val _isActiveFlow = MutableStateFlow(false)
    val isActiveFlow = _isActiveFlow.asStateFlow()

    fun onStartPauseButtonClick(context: Context) {
        if (isActiveFlow.value) {
            _isActiveFlow.value = false
            domain.onPause()
        } else {
            _isActiveFlow.value = true

            val launchIntent = context.packageManager
                .getLaunchIntentForPackage(context.getString(R.string.chrome_package))
            if (launchIntent != null) {
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(launchIntent)
            }
        }
    }
}