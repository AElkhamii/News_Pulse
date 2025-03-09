package com.example.newspulse.core.presentaion.designsystem.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = NewsPulsePrimaryLight,
    secondary = NewsPulseSecondaryLight,
    tertiary = NewsPulseTertiaryLight,
    onBackground = NewsPulseOnBackgroundLight
)

private val LightColorScheme = lightColorScheme(
    primary = NewsPulsePrimaryDark,
    secondary = NewsPulseSecondaryDark,
    tertiary = NewsPulseTertiaryDark,
    onBackground = NewsPulseOnBackgroundDark
)

@Composable
fun NewsPulseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}