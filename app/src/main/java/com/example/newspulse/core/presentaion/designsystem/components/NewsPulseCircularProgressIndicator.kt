package com.example.newspulse.core.presentaion.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.newspulse.core.presentaion.designsystem.theme.Dimensions
import com.example.newspulse.core.presentaion.designsystem.theme.Padding

@Composable
fun NewsPulseCircularProgressIndicator(
    modifier: Modifier = Modifier,
    dimensions: Dimensions,
    padding: Padding
){
    Column(
        modifier = modifier.fillMaxSize().background(Color.Transparent),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(dimensions.progressBarSize),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = padding.tinyPadding
        )
        Spacer(Modifier.height(dimensions.largeSpace))
        Text(
            text = "Loading...",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}