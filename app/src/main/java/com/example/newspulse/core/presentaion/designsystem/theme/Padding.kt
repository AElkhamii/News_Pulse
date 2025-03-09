package com.example.newspulse.core.presentaion.designsystem.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


data class Padding(
    val zeroPadding: Dp = 0.dp,
    val tinyPadding : Dp = 2.dp,
    val verySmallPadding: Dp = 4.dp,
    val smallPadding: Dp = 8.dp,
    val mediumPadding: Dp = 16.dp,
    val largePadding: Dp = 32.dp,
    val topBarPadding:Dp = 26.dp
)

val LocalPadding = compositionLocalOf { Padding() }