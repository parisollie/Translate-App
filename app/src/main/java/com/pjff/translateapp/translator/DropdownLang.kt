package com.pjff.translateapp.translator

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Vid 208,drowpdown generico
@Composable
fun DropdownLang(
    itemSelection: List<String>,
    selectedIndex: Int,
    expand: Boolean,
    //Para abrir la parte del dropdown
    onClickExpanded: () -> Unit,
    onClickDismiss: () -> Unit,
    onClickItem: (index:Int) -> Unit,
){
    Box(){
      OutlinedButton(onClick = { onClickExpanded() }) {
          Text(text = itemSelection[selectedIndex])
          Icon(Icons.Default.ArrowDropDown, contentDescription = "")
      }

        DropdownMenu(expanded = expand, onDismissRequest = { onClickDismiss() }) {
            itemSelection.forEachIndexed { index, item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = { onClickItem(index) })
            }
        }


    }
}