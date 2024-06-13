package com.andr.zahar2.gesture_server.ui.log

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.andr.zahar2.gesture_server.data.model.LogEvent

@Composable
fun LogDialog(
    events: List<LogEvent>,
    onDismissRequest: () -> Unit
) {
    
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            LazyColumn(Modifier.fillMaxSize()) {
                items(events) {
                    LogItem(logEvent = it)
                }
            }
        }
    }
}