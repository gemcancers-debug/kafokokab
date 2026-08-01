/*
نام فایل: LoginScreen.kt
وظیفه: صفحه ورود اصلی اپلیکیشن (ورود با گوگل + ورود با شماره تلفن)
طراحی: بر اساس فایل UI آپلود شده توسط کاربر
نویسنده: AI Principal Engineer
تاریخ: 2026-08-01

نکته مهم برای ویرایش بعدی:
- تمام بخش‌های بصری به کامپوننت‌های کوچک جدا شده‌اند
- رنگ‌ها و فاصله‌ها از Theme گرفته می‌شوند تا بازطراحی آسان باشد
- منطق واقعی احراز هویت هنوز پیاده نشده (فقط UI)
*/

package com.kafokokab.app.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier.modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kafokokab.core.ui.theme.DarkGalaxy
import com.kafokokab.core.ui.theme.Gold
import com.kafokokab.core.ui.theme.MysticPurple
import com.kafokokab.core.ui.theme.NeonPink
import com.kafokokab.core.ui.theme.SoftWhite

/**
 * صفحه ورود اصلی.
 *
 * @param onGoogleClick کلیک روی دکمه ورود با گوگل
 * @param onPhoneClick کلیک روی دکمه ورود با شماره تلفن
 */
@Composable
fun LoginScreen(
    onGoogleClick: () -> Unit = {},
    onPhoneClick: () -> Unit = {}
) {
    // پس‌زمینه کهکشانی تاریک
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A0533),
                        DarkGalaxy,
                        Color(0xFF0A001A)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // بخش بالایی: لوگو و عنوان
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 80.dp)
            ) {
                // عنوان اصلی
                Text(
                    text = "کف و کوکب",
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp
                    ),
                    color = SoftWhite,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                // شعار
                Text(
                    text = "آسترولوژی، زبان نمادین آسمان است.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = SoftWhite.copy(alpha = 0.75f),
                    textAlign = TextAlign.Center
                )
            }

            // بخش میانی: نماد ماه و دایره بروج (فعلاً ساده)
            // بعداً می‌توانید این بخش را با تصویر یا Canvas زیباتر کنید
            ZodiacMoonPlaceholder()

            // بخش پایینی: دکمه‌های ورود
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 48.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // دکمه ورود با گوگل
                LoginButton(
                    text = "ورود با جیمیل",
                    trailingContent = {
                        // حرف G رنگی به جای آیکون واقعی گوگل
                        Text(
                            text = "G",
                            color = Color(0xFF4285F4),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    },
                    onClick = onGoogleClick
                )

                // دکمه ورود با شماره تلفن
                LoginButton(
                    text = "ورود با شماره تلفن",
                    trailingContent = {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null,
                            tint = SoftWhite,
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    onClick = onPhoneClick
                )
            }
        }
    }
}

/**
 * دکمه ورود قابل استفاده مجدد.
 * برای تغییر ظاهر دکمه‌ها فقط این کامپوننت را ویرایش کنید.
 */
@Composable
private fun LoginButton(
    text: String,
    trailingContent: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        MysticPurple.copy(alpha = 0.35f),
                        NeonPink.copy(alpha = 0.25f)
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        SoftWhite.copy(alpha = 0.25f),
                        Color.Transparent
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        // متن دکمه
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = SoftWhite,
            modifier = Modifier.align(Alignment.Center)
        )

        // آیکون سمت راست (در RTL سمت چپ دیده می‌شود)
        Box(
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            trailingContent()
        }
    }
}

/**
 * نماد ماه و دایره بروج (نسخه ساده).
 * بعداً می‌توانید این را با تصویر واقعی یا Canvas جایگزین کنید.
 */
@Composable
private fun ZodiacMoonPlaceholder() {
    Box(
        modifier = Modifier
            .size(220.dp)
            .clip(RoundedCornerShape(50))
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        MysticPurple.copy(alpha = 0.4f),
                        Color.Transparent
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // ماه ساده با رنگ طلایی
        Text(
            text = "☽",
            fontSize = 96.sp,
            color = Gold.copy(alpha = 0.9f)
        )
    }
}
