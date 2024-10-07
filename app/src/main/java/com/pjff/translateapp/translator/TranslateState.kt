package com.pjff.translateapp.translator

//Vid 305
data class TranslateState(
    val textToTranslate: String = "",
    val translateText : String = "",
    val isDownloading : Boolean = false
)
