package com.example.libraryreg.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = Color.White,
    primaryContainer = DarkNavy,
    onPrimaryContainer = Color.White,
    secondary = DarkNavy,
    onSecondary = Color.White,
    background = AppBackground,
    onBackground = TextPrimary,
    surface = AppSurface,
    onSurface = TextPrimary,
    error = AppError,
    onError = Color.White,
    outline = InputBorder
)

@Composable
fun LibraryRegTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
