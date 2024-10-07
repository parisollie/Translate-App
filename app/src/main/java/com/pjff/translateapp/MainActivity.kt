package com.pjff.translateapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.pjff.translateapp.languages.LanguagesView
import com.pjff.translateapp.translator.TranslateView
import com.pjff.translateapp.translator.TranslateViewModel
import com.pjff.translateapp.ui.theme.TranslateAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Vid 305
        val viewModel : TranslateViewModel by viewModels()
        setContent {
            TranslateAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Vid 205
                    TranslateView(viewModel)
                    //Vid 202 dropdown
                   // LanguagesView()
                   // MyView()
                }
            }
        }
    }
}
//vid 201
@Composable
fun MyView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Vid 201, vamos a res-> values y ahoi esta la carpeta de strings
        Text(text = stringResource(id = R.string.title), fontWeight = FontWeight.Bold)
        Text(text = stringResource(id = R.string.subtitle))
    }
}