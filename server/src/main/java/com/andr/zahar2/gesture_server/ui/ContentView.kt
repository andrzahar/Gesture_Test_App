package com.andr.zahar2.gesture_server.ui

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.andr.zahar2.gesture_server.R
import com.andr.zahar2.gesture_server.ui.log.LogDialog

@Composable
fun ContentView(
    modifier: Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {

    var openDialog by remember { mutableStateOf(false) }
    var openLog by remember { mutableStateOf(false) }

    val logEvents by viewModel.logEvents.collectAsState()

    Box(
        modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = viewModel::onDrugEnd,
                    onDrag = viewModel::onDrag
                )
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { openDialog = true }) {
                    Text(text = stringResource(id = R.string.config_button))
                }
                Button(onClick = { openLog = true }) {
                    Text(text = stringResource(R.string.logs_button))
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = viewModel::onStartClick) {
                    Text(text = stringResource(id = R.string.start_button))
                }
                Button(onClick = viewModel::onStopClick) {
                    Text(text = stringResource(R.string.stop_button))
                }
            }
        }
    }

    if (openDialog) {
        ConfigDialog {
            openDialog = false
        }
    }

    if (openLog) {
        LogDialog(events = logEvents) {
            openLog = false
        }
    }
}

@Preview
@Composable
fun ContentViewPreview() {
    ContentView(modifier = Modifier)
}