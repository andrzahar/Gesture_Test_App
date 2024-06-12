package com.andr.zahar2.gesture_server.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.andr.zahar2.gesture_server.R
import com.andr.zahar2.gesture_server.preferences.Preferences.portFlow
import com.andr.zahar2.gesture_server.preferences.Preferences.setPort

@Composable
fun ConfigDialog(onDismissRequest: () -> Unit) {

    val context = LocalContext.current
    val portState by context.portFlow.collectAsState(initial = 0)

    ConfigDialogBase(
        port = portState?.toString() ?: "0",
        onDismissRequest = onDismissRequest
    ) { port ->
        context.setPort(port.toIntOrNull() ?: 0)
        onDismissRequest()
    }
}

@Composable
fun ConfigDialogBase(
    port: String,
    onDismissRequest: () -> Unit,
    onSave: (String) -> Unit
) {

    var portText by remember { mutableStateOf(port) }

    LaunchedEffect(port) {
        if (port != portText) {
            portText = port
        }
    }

    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(125.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            TextField(
                value = portText,
                onValueChange = { portText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                label = { Text(stringResource(id = R.string.port_field)) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            TextButton(
                onClick = { onSave(portText) },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.BottomEnd)
            ) {
                Text(text = stringResource(id = R.string.save_button))
            }
        }
    }
}

@Preview
@Composable
fun ConfigDialogPreview() {
    ConfigDialog {

    }
}