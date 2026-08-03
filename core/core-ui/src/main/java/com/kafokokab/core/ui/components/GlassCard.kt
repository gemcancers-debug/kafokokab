/*
نام فایل: GlassCard.kt
وظیفه: کارت گلاسمورفیسم (شیشه‌ای مات) برای استفاده در سراسر اپلیکیشن
نویسنده: AI Principal Engineer
تاریخ: 2026-08-03
*/

package com.kafokokab.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kafokokab.core.ui.theme.GlassBorder
import com.kafokokab.core.ui.theme.GlassWhite
import com.kafokokab.core.ui.theme.MysticPurple

/**
 * کارت شیشه‌ای مات (Glassmorphism)
 * برای نمایش محتوا با ظاهر لوکس و مدرن استفاده می‌شود.
 *
 * @param modifier Modifier خارجی
 * @param cornerRadius شعاع گوشه‌ها
 * @param content محتوای داخل کارت
 */
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 20.dp,
    content: @Composable BoxScope.() -> Unit
) {
    val shape = RoundedCornerShape(cornerRadius)

    Box(
        modifier = modifier
            .clip(shape)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        GlassWhite.copy(alpha = 0.18f),
                        MysticPurple.copy(alpha = 0.08f)
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        GlassBorder,
                        Color.Transparent
                    )
                ),
                shape = shape
            )
            .padding(16.dp),
        content = content
    )
}
