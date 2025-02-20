package com.example.newspulse.core.presentaion.designsystem.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val smallSpace: Dp = 8.dp,
    val mediumSpace: Dp = 16.dp,
    val largeSpace: Dp = 32.dp,
    val imageSizeHeight: Dp = 90.dp,
    val imageSizeWidth: Dp = 160.dp,
    val imageRadius: Dp = 16.dp,
    val favouriteImageSize: Dp = 40.dp,
    val dividerThickness: Dp = 1.dp,
    val topBarHeight: Dp = 70.dp,
    val tinyBoarderThickness: Dp = 1.dp,
    val itemHeight:Dp = 130.dp,
    val progressBarSize:Dp = 100.dp
)

val LocalDimensions = compositionLocalOf { Dimensions() }