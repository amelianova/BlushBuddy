package com.example.blushbuddy.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val BlushColorScheme = lightColorScheme(
    primary = RoseGold,
    onPrimary = Color.White,
    primaryContainer = BlushPink,
    onPrimaryContainer = RoseGoldDark,
    secondary = BlushPinkDark,
    onSecondary = Color.White,
    secondaryContainer = LightPink,
    onSecondaryContainer = WarmBrown,
    tertiary = WarmBrown,
    onTertiary = Color.White,
    background = Cream,
    onBackground = WarmBrown,
    surface = Color.White,
    onSurface = WarmBrown,
    surfaceVariant = SoftBeige,
    onSurfaceVariant = WarmBrown,
)

@Composable
fun BlushBuddyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = BlushColorScheme,
        typography = Typography,
        content = content
    )
}
