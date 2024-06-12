package com.andr.zahar2.gesturetestapp.ui

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
import com.andr.zahar2.gesturetestapp.R
import com.andr.zahar2.gesturetestapp.preferences.Preferences.ipFlow
import com.andr.zahar2.gesturetestapp.preferences.Preferences.portFlow
import com.andr.zahar2.gesturetestapp.preferences.Preferences.setIp
import com.andr.zahar2.gesturetestapp.preferences.Preferences.setPort

@Composable
fun ConfigDialog(onDismissRequest: () -> Unit) {

    val context = LocalContext.current
    val ipState by context.ipFlow.collectAsState(initial = "")
    val portState by context.portFlow.collectAsState(initial = 0)

    ConfigDialogBase(
        ip = ipState ?: "",
        port = portState?.toString() ?: "0",
        onDismissRequest = onDismissRequest
    ) { ip, port ->
        context.setIp(ip)
        context.setPort(port.toIntOrNull() ?: 0)
        onDismissRequest()
    }
}

@Composable
fun ConfigDialogBase(
    ip: String,
    port: String,
    onDismissRequest: () -> Unit,
    onSave: (String, String) -> Unit
) {

    var ipText by remember { mutableStateOf(ip) }
    var portText by remember { mutableStateOf(port) }

    LaunchedEffect(ip) {
        if (ip != ipText) {
            ipText = ip
        }
    }

    LaunchedEffect(port) {
        if (port != portText) {
            portText = port
        }
    }

    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            TextField(
                value = ipText,
                onValueChange = { ipText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                label = { Text(stringResource(id = R.string.ip_field)) }
            )
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
                onClick = { onSave(ipText, portText) },
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