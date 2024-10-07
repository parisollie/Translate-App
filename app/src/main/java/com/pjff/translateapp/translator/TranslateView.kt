package com.pjff.translateapp.translator

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.pjff.translateapp.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class,
    ExperimentalPermissionsApi::class
)

//Vid 209
@Composable
fun TranslateView(viewModel: TranslateViewModel) {

    val state = viewModel.state
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val languageOptions = viewModel.languageOptions
    val itemsSelection = viewModel.itemSelection
    //Vid 248
    val itemVoice = viewModel.itemsVoice

    var indexSource by remember { mutableStateOf(0) }
    var indexTarget by remember { mutableStateOf(1) }

    var expandSource by remember { mutableStateOf(false) }
    var expandTarget by remember { mutableStateOf(false) }

    //vid 246
    var selectedSourceLang by remember { mutableStateOf(languageOptions[0]) }
    var selectedTargetLang by remember { mutableStateOf(languageOptions[1]) }

    //Vid 248
    var selectedTargetVoice by remember { mutableStateOf(itemVoice[1]) }

    val permissionState = rememberPermissionState(permission = Manifest.permission.RECORD_AUDIO)

    SideEffect {
        permissionState.launchPermissionRequest()
    }

    val speechRecognitionLauncher = rememberLauncherForActivityResult(
        contract = SpeechRecognizerContract(),
        //traemos el resultado
        onResult = {
            viewModel.onValue(it.toString().replace("[","").replace("]","").trimStart())
        })


    //Vid 209
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            DropdownLang(
                itemSelection = itemsSelection,
                selectedIndex = indexSource,
                expand = expandSource,
                onClickExpanded = { expandSource = true },
                onClickDismiss = { expandSource = false },
                onClickItem = { index ->
                    indexSource = index
                    selectedSourceLang = languageOptions[index]
                    expandSource = false
                }
            )

            Icon(
                Icons.Default.ArrowForward, contentDescription = "",
                modifier = Modifier.padding(start = 15.dp, end = 15.dp)
            )

            //Vid 210
            DropdownLang(
                itemSelection = itemsSelection,
                selectedIndex = indexTarget,
                expand = expandTarget,
                onClickExpanded = { expandTarget = true },
                onClickDismiss = { expandTarget = false },
                onClickItem = { index ->
                    indexTarget = index
                    selectedTargetLang = languageOptions[index]
                    //vid 249
                    selectedTargetVoice = itemVoice[index]
                    expandTarget = false
                }
            )
        }

        //Vid 211
        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = state.textToTranslate,
            onValueChange = { viewModel.onValue(it) },
            label = { Text(text = "Escribe...") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    viewModel.onTranslate(
                        state.textToTranslate,
                        context,
                        selectedSourceLang,
                        selectedTargetLang
                    )
                }
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth()
        )

        //Vid 224

        Row(verticalAlignment = Alignment.CenterVertically) {
            MainIconButton(icon = R.drawable.mic) {
                if(permissionState.status.isGranted){
                    speechRecognitionLauncher.launch(Unit)
                }else{
                    permissionState.launchPermissionRequest()
                }
            }
            MainIconButton(icon = R.drawable.translate) {
                viewModel.onTranslate(
                    state.textToTranslate,
                    context,
                    selectedSourceLang,
                    selectedTargetLang
                )
            }
            MainIconButton(icon = R.drawable.speak) {
                //Vid 249, selectedTargetVoice
                viewModel.textToSpeech(context, selectedTargetVoice)
            }
            MainIconButton(icon = R.drawable.delete) {
                viewModel.clean()
            }
        }



        if (state.isDownloading){
            CircularProgressIndicator()
            Text(text = "Descargando modelo, espere un momento...")
        }else{
            OutlinedTextField(
                value = state.translateText,
                onValueChange = {},
                label = { Text(text = "Tu texto traducido...") },
                readOnly = false,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
                )
        }

    }

}