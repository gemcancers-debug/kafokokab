/*
نام فایل: PremiumBlurBox.kt
وظیفه: کامپوننت حیاتی برای قفل کردن محتوای Premium با افکت بلور + قفل طلایی
نویسنده: AI Principal Engineer
تاریخ: 2026-07-31

این کامپوننت باید در تمام قابلیت‌های پولی استفاده شود.
*/

package com.kafokokab.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kafokokab.core.ui.theme.Gold
import com.kafokokab.core.ui.theme.SoftWhite

/**
 * جعبه قفل Premium.
 * محتوای داخل آن تار (blur) می‌شود و یک قفل طلایی + متن دعوت به ارتقا نمایش داده می‌شود.
 *
 * @param isPremium آیا کاربر اشتراک دارد؟ اگر true باشد محتوا بدون قفل نمایش داده می‌شود.
 * @param onUnlockClick کلیک روی قفل (معمولاً باز کردن صفحه خرید اشتراک)
 * @param content محتوای اصلی که در صورت نداشتن اشتراک تار می‌شود
 */
@Composable
fun PremiumBlurBox(
    isPremium: Boolean,
    onUnlockClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        // محتوای اصلی
        Box(
            modifier = Modifier
                .fillMaxSize()
                .then(
                    if (!isPremium) Modifier.blur(radius = 12.dp) else Modifier
                )
        ) {
            content()
        }

        // لایه قفل (فقط وقتی کاربر Premium نیست)
        if (!isPremium) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.35f),
                                Color.Black.copy(alpha = 0.55f)
                            )
                        )
                    )
                    .clickable(onClick = onUnlockClick),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    // دایره طلایی پشت قفل
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(Gold.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "قفل ویژه",
                            tint = Gold,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "محتوای ویژه",
                        style = MaterialTheme.typography.titleMedium,
                        color = SoftWhite,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "با اشتراک ویژه باز کنید",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Gold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
