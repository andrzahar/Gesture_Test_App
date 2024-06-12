package com.andr.zahar2.gesturetestapp.ui

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.andr.zahar2.gesturetestapp.R
import com.andr.zahar2.gesturetestapp.domain.GesturesDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val domain: GesturesDomain) : ViewModel() {

    val isActiveFlow = domain.isActiveFlow

    fun onStartPauseButtonClick(context: Context) {
        if (isActiveFlow.value) {
            domain.onPause()
        } else {
            domain.onStart()
            val launchIntent = context.packageManager
                .getLaunchIntentForPackage(context.getString(R.string.chrome_package))
            if (launchIntent != null) {
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(launchIntent)
            }
        }
    }
}