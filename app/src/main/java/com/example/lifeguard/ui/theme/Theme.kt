package com.lifeguard.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

@Composable
fun LifeGuardTheme(
    hour: Int = remember { DynamicThemeEngine.currentHour() },
    content: @Composable () -> Unit
) {
    val palette = remember(hour) { DynamicThemeEngine.paletteForHour(hour) }

    val scheme = if (palette.isDark) {
        darkColorScheme(
            primary = palette.primary,
            onPrimary = palette.onPrimary,
            background = palette.gradientTop,
            onBackground = palette.onSurface,
            surface = palette.surface,
            onSurface = palette.onSurface,
            surfaceVariant = palette.surface,
            onSurfaceVariant = palette.onSurface.copy(alpha = 0.75f),
            outline = palette.onSurface.copy(alpha = 0.35f),
            error = LifeGuardRed,
            tertiary = LifeGuardGreen
        )
    } else {
        lightColorScheme(
            primary = palette.primary,
            onPrimary = palette.onPrimary,
            background = palette.gradientTop,
            onBackground = palette.onSurface,
            surface = palette.surface,
            onSurface = palette.onSurface,
            surfaceVariant = Color(0xFFF1F6F7),
            onSurfaceVariant = palette.onSurface.copy(alpha = 0.75f),
            outline = palette.onSurface.copy(alpha = 0.3f),
            error = LifeGuardRed,
            tertiary = LifeGuardGreen
        )
    }

    CompositionLocalProvider(LocalTimePalette provides palette) {
        MaterialTheme(
            colorScheme = scheme,
            typography = LifeGuardTypography,
            content = content
        )
    }
}