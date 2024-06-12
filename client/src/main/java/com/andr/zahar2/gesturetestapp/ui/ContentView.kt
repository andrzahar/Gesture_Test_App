package com.andr.zahar2.gesturetestapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.andr.zahar2.gesturetestapp.R

@Composable
fun ContentView(
    modifier: Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    var openDialog by remember { mutableStateOf(false) }
    val isActive by viewModel.isActiveFlow.collectAsState(initial = false)

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { openDialog = true }) {
            Text(text = stringResource(id = R.string.config_button))
        }
        Button(onClick = { viewModel.onStartPauseButtonClick(context) }) {
            Text(
                text = stringResource(
                    id = if (isActive) R.string.start_pause_button_pause
                    else R.string.start_pause_button_start
                )
            )
        }
    }

    if (openDialog) {
        ConfigDialog {
            openDialog = false
        }
    }
}

@Preview
@Composable
fun ContentViewPreview() {
    ContentView(modifier = Modifier)
}