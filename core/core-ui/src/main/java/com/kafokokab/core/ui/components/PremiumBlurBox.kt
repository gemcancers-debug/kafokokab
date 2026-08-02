/*
نام فایل: GlassCard.kt
وظیفه: کارت شیشه‌ای با افکت شیشه‌ای (Glassmorphism) برای UI مدرن
نویسنده: AI Principal Engineer
تاریخ: 2026-07-31
*/

package com.kafokokab.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * کارت شیشه‌ای با افکت Glassmorphism
 *
 * @param modifier modifier برای سفارشی‌سازی
 * @param content محتوای داخل کارت
 */
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.15f),
                        Color.White.copy(alpha = 0.05f)
                    )
                )
            )
            .blur(radius = 0.5.dp)
            .padding(16.dp)
    ) {
        content()
    }
}