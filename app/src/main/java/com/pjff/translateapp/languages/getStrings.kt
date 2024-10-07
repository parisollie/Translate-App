package com.pjff.translateapp.languages

import androidx.compose.runtime.Composable

@Composable
//Vid 203 ,claves a traducir
fun getStrings(): List<Map<String, String>> {
    val en = mapOf(
        "title" to "Hello World",
        "subtitle" to "The World is yours"
    )

    val es = mapOf(
        "title" to "Hola Mundo",
        "subtitle" to "El Mundo es tuyo"
    )

    //retornamos en la lista
    return listOf(
        en,
        es
    )
}