package com.example.newspulse.core.presentaion.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.newspulse.core.presentaion.designsystem.theme.NewsPulseTheme


@Composable
fun NewsPulseBackground(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit

) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
    ){
        Column {
            content()
        }
    }
    

}

@Preview

@Composable

private fun NewsPulseBackgroundPreview() {

     NewsPulseTheme {
         NewsPulseBackground(){
             
         }
     }

}