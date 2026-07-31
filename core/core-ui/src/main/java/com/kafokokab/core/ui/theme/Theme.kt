/*
نام فایل: Theme.kt
وظیفه: تم اصلی پروژه با پشتیبانی از Material 3 و رنگ‌های اختصاصی
نویسنده: AI Principal Engineer
تاریخ: 2026-07-31
*/

package com.kafokokab.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val KafokokabDarkColorScheme = darkColorScheme(
    primary = NeonPink,
    onPrimary = SoftWhite,
    secondary = MysticPurple,
    onSecondary = SoftWhite,
    tertiary = Gold,
    background = DarkGalaxy,
    onBackground = SoftWhite,
    surface = DarkGalaxy,
    onSurface = SoftWhite,
    surfaceVariant = Color(0xFF1A0A2E),
    onSurfaceVariant = MutedLavender
)

/**
 * تم اصلی اپلیکیشن کف و کوکب.
 * فعلاً فقط حالت تاریک (Dark Galaxy) پشتیبانی می‌شود چون با هویت برند هماهنگ است.
 */
@Composable
fun KafokokabTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = KafokokabDarkColorScheme,
        content = content
    )
}
