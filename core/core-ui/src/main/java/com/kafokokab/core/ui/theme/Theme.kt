/*
نام فایل: Theme.kt
وظیفه: تم اصلی پروژه با پشتیبانی از Material 3 و RTL فارسی
نویسنده: AI Principal Engineer
تاریخ: 2026-07-31
آخرین تغییر: 2026-08-13 - اطمینان از جهت راست‌به‌چپ در تم
*/

package com.kafokokab.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

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

@Composable
fun KafokokabTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Rtl
    ) {
        MaterialTheme(
            colorScheme = KafokokabDarkColorScheme,
            typography = KafokokabTypography,
            content = content
        )
    }
}
