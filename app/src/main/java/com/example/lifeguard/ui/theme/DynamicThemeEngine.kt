package com.lifeguard.app.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import java.util.Calendar

data class TimePalette(
    val gradientTop: Color,
    val gradientBottom: Color,
    val primary: Color,
    val onPrimary: Color,
    val surface: Color,
    val onSurface: Color,
    val isDark: Boolean,
    val label: String
)

object DynamicThemeEngine {

    fun paletteForHour(hour: Int): TimePalette = when (hour) {
        in 6..17 -> TimePalette(
            gradientTop = DaySkyTop,
            gradientBottom = DaySkyBottom,
            primary = DayPrimary,
            onPrimary = Color.White,
            surface = Color.White,
            onSurface = DayOnSurface,
            isDark = false,
            label = "Day"
        )
        in 18..19 -> TimePalette(
            gradientTop = SunsetTop,
            gradientBottom = SunsetBottom,
            primary = SunsetPrimary,
            onPrimary = Color.Black,
            surface = SunsetSurface,
            onSurface = SunsetOnSurface,
            isDark = true,
            label = "Sunset"
        )
        else -> TimePalette(
            gradientTop = NightTop,
            gradientBottom = NightBottom,
            primary = NightPrimary,
            onPrimary = Color.Black,
            surface = NightSurface,
            onSurface = NightOnSurface,
            isDark = true,
            label = "Night"
        )
    }

    fun currentHour(): Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

    fun currentPalette(): TimePalette = paletteForHour(currentHour())
}

/** CompositionLocal so any screen can read the active gradient/palette. */
val LocalTimePalette = staticCompositionLocalOf { DynamicThemeEngine.currentPalette() }