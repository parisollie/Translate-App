package com.pjff.translateapp.languages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.launch


@Composable
fun LanguagesView() {
    //Vid 204
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = LanguagesStore(context)

    val indexLang by dataStore.getIndexLang.collectAsState(initial = 0)

    //Vid 202
    val items = listOf("English", "Spanish")
    //Vid 203.
    val languages = getStrings()
    //Vid 204,guardamos el indice de la posicion languages[indexLang]
    var currentLanguages by remember { mutableStateOf(languages[indexLang]) }

    //El remember se carga mas rapido , con eso lo solucionamos
    LaunchedEffect(indexLang){
        currentLanguages = languages[indexLang]
    }

    var expanded by remember { mutableStateOf(false) }
    //Vid 202
    var selectIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = items[selectIndex])
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.ArrowDropDown, contentDescription = "")
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                //Para obtener todos los datos de nuestra lista.
                items.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = { Text(text = item)},
                        onClick = {
                            selectIndex = index
                            //Vid 203.
                            currentLanguages = languages[index]
                            //Vid 204
                            scope.launch {
                                dataStore.saveIndexLang(index)
                            }
                            expanded = false
                        })
                }
            }
        }

        //Vid 203.
        Text(text = currentLanguages["title"].toString(), fontWeight = FontWeight.Bold)
        Text(text = currentLanguages["subtitle"].toString())

    }

}