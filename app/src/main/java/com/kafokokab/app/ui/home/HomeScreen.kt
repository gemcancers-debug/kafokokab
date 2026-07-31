/*
نام فایل: HomeScreen.kt
وظیفه: صفحه اصلی موقت اپلیکیشن (placeholder)
نویسنده: AI Principal Engineer
تاریخ: 2026-07-31
*/

package com.kafokokab.app.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier.modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kafokokab.core.ui.components.GlassCard
import com.kafokokab.core.ui.theme.DarkGalaxy
import com.kafokokab.core.ui.theme.Gold
import com.kafokokab.core.ui.theme.SoftWhite

/**
 * صفحه اصلی موقت.
 * بعداً با داشبورد واقعی شامل چارت، هوروسکوپ روزانه و دسترسی سریع به ماژول‌ها جایگزین می‌شود.
 */
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGalaxy)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "کف و کوکب",
            style = MaterialTheme.typography.displayMedium,
            color = SoftWhite,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "ستاره‌ها روایتگر تو هستند",
            style = MaterialTheme.typography.bodyLarge,
            color = Gold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        GlassCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "خوش آمدید",
                    style = MaterialTheme.typography.headlineSmall,
                    color = SoftWhite
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "فاز ۱ با موفقیت در حال تکمیل است.\nبه زودی چارت تولد و امکانات بیشتر اضافه می‌شود.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SoftWhite.copy(alpha = 0.85f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
