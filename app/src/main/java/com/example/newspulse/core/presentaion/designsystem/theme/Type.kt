package com.example.newspulse.core.presentaion.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.newspulse.R

val robot = FontFamily(
    Font(
        resId = R.font.roboto_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.roboto_light,
        weight = FontWeight.Light
    ),
    Font(
        resId = R.font.roboto_medium,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.roboto_semi_bold,
        weight = FontWeight.SemiBold
    ),
    Font(
        resId = R.font.roboto_bold,
        weight = FontWeight.Bold
    ),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodySmall = TextStyle(
        fontFamily = robot,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        color = NewsPulseBlack
    ),
    bodyMedium = TextStyle(
        fontFamily = robot,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp,
        color = NewsPulseBlack
    ),
    bodyLarge = TextStyle(
        fontFamily = robot,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        color = NewsPulseBlack
    ),
    headlineMedium = TextStyle(
        fontFamily = robot,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = NewsPulseBlack
    )
)