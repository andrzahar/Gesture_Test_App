package com.andr.zahar2.gesture_server.ui.log

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andr.zahar2.api.model.ClientEvent
import com.andr.zahar2.gesture_server.data.model.LogEvent
import java.util.Date

@Composable
fun LogItem(logEvent: LogEvent) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            Text(text = logEvent.address)
            Text(text = logEvent.event.name)
        }
        Text(text = Date(logEvent.timestamp).toLocaleString())
        HorizontalDivider()
    }
}

@Preview
@Composable
fun LogItemPreview() {
    LogItem(
        logEvent = LogEvent(
            timestamp = 1718243080473,
            address = "192.168.101.6",
            event = ClientEvent.CHROME_IS_OPEN
        )
    )
}