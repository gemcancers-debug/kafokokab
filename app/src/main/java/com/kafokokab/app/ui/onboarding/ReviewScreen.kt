/*
نام فایل: ReviewScreen.kt
وظیفه: مرحله ۴ از ۴ آنبوردینگ – بررسی و تأیید نهایی اطلاعات کاربر
طراحی: بر اساس UI آپلود شده توسط کاربر
نویسنده: AI Principal Engineer
تاریخ: 2026-08-01

نکته برای ویرایش بعدی:
- داده‌ها فعلاً به صورت نمونه (hardcoded) نمایش داده می‌شوند
- بعداً از ViewModel و State واقعی خوانده می‌شوند
- دکمه «تغییر» هر بخش را به صفحه مربوطه برمی‌گرداند
*/

package com.kafokokab.app.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kafokokab.core.ui.theme.DarkGalaxy
import com.kafokokab.core.ui.theme.Gold
import com.kafokokab.core.ui.theme.MysticPurple
import com.kafokokab.core.ui.theme.NeonPink
import com.kafokokab.core.ui.theme.SoftWhite

/**
 * صفحه بررسی نهایی (مرحله ۴ از ۴).
 *
 * کاربر تمام اطلاعات وارد شده را می‌بیند و می‌تواند قبل از ورود نهایی آن‌ها را ویرایش کند.
 *
 * @param onBack بازگشت
 * @param onConfirm تأیید نهایی و ورود به برنامه
 * @param onEditBirth ویرایش اطلاعات تولد
 * @param onEditPersonal ویرایش اطلاعات فردی
 * @param onEditPhotos ویرایش تصاویر و تحلیل‌ها
 * @param onEditOptional ویرایش موارد اختیاری
 */
@Composable
fun ReviewScreen(
    onBack: () -> Unit = {},
    onConfirm: () -> Unit = {},
    onEditBirth: () -> Unit = {},
    onEditPersonal: () -> Unit = {},
    onEditPhotos: () -> Unit = {},
    onEditOptional: () -> Unit = {}
) {
    // داده‌های نمونه – بعداً از ViewModel می‌آیند
    val sampleBirthDate = "۱۵ اردیبهشت ۱۳۷۰"
    val sampleBirthTime = "۱۴:۳۰"
    val sampleLocation = "تهران، ایران"
    val sampleGender = "مرد"
    val sampleFirstName = "محمدرضا"
    val sampleLastName = "احمدی"
    val sampleMotherName = "فاطمه"

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
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            OnboardingHeader(
                currentStep = 4,
                totalSteps = 4,
                title = "اطلاعات تکمیلی شما",
                onBack = onBack
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "بررسی و تأیید نهایی",
                style = MaterialTheme.typography.titleMedium,
                color = Gold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "لطفاً اطلاعات وارد شده را بررسی کنید و در صورت صحیح بودن، تأیید نهایی کنید.",
                style = MaterialTheme.typography.bodySmall,
                color = SoftWhite.copy(alpha = 0.65f),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp, bottom = 20.dp)
            )

            // ---------- بخش اطلاعات تولد ----------
            ReviewSection(
                title = "اطلاعات تولد",
                icon = Icons.Default.CalendarMonth,
                onEdit = onEditBirth
            ) {
                ReviewItem(label = "تاریخ تولد", value = sampleBirthDate)
                ReviewItem(label = "ساعت تولد", value = sampleBirthTime)
                ReviewItem(label = "محل تولد", value = sampleLocation)
                ReviewItem(label = "جنسیت", value = sampleGender)
            }

            Spacer(modifier = Modifier.height(14.dp))

            // ---------- بخش اطلاعات فردی ----------
            ReviewSection(
                title = "اطلاعات فردی",
                icon = Icons.Default.Person,
                onEdit = onEditPersonal
            ) {
                ReviewItem(label = "نام", value = sampleFirstName)
                ReviewItem(label = "نام خانوادگی", value = sampleLastName)
                ReviewItem(label = "نام مادر", value = sampleMotherName)
            }

            Spacer(modifier = Modifier.height(14.dp))

            // ---------- بخش تصاویر و تحلیل‌ها ----------
            ReviewSection(
                title = "تصاویر و تحلیل‌ها",
                icon = Icons.Default.CameraAlt,
                onEdit = onEditPhotos
            ) {
                ReviewStatusItem(label = "کف دست", status = "ثبت نشده")
                ReviewStatusItem(label = "چهره‌شناسی", status = "ثبت نشده")
                ReviewStatusItem(label = "خال‌شناسی", status = "ثبت شده")
            }

            Spacer(modifier = Modifier.height(14.dp))

            // ---------- بخش موارد اختیاری ----------
            ReviewSection(
                title = "موارد تکمیلی (اختیاری)",
                icon = Icons.Default.Star,
                onEdit = onEditOptional
            ) {
                ReviewItem(label = "رنگ مو", value = "مشکی")
                ReviewItem(label = "گروه خونی", value = "O+")
                ReviewItem(label = "رنگ چشم", value = "قهوه‌ای")
                ReviewItem(label = "قد", value = "۱۷۵ سانتی‌متر")
            }

            Spacer(modifier = Modifier.height(20.dp))

            // یادداشت حریم خصوصی
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(SoftWhite.copy(alpha = 0.06f))
                    .padding(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = SoftWhite.copy(alpha = 0.6f),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "اطلاعات شما به صورت محرمانه ذخیره می‌شود و فقط برای محاسبات و تحلیل‌های شخصی‌سازی‌شده استفاده خواهد شد.",
                    style = MaterialTheme.typography.bodySmall,
                    color = SoftWhite.copy(alpha = 0.65f)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // دکمه تأیید نهایی
            ContinueButton(
                text = "تأیید نهایی و ورود به برنامه",
                onClick = onConfirm
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

/**
 * بخش قابل ویرایش در صفحه بررسی نهایی.
 */
@Composable
fun ReviewSection(
    title: String,
    icon: ImageVector,
    onEdit: () -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(SoftWhite.copy(alpha = 0.07f))
            .border(
                width = 1.dp,
                color = SoftWhite.copy(alpha = 0.12f),
                shape = RoundedCornerShape(18.dp)
            )
            .padding(16.dp)
    ) {
        // هدر بخش با دکمه تغییر
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Gold,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = SoftWhite,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // دکمه تغییر
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(onClick = onEdit)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "تغییر",
                    tint = NeonPink,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "تغییر",
                    style = MaterialTheme.typography.labelMedium,
                    color = NeonPink
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        content()
    }
}

/**
 * یک ردیف اطلاعات ساده (برچسب + مقدار)
 */
@Composable
fun ReviewItem(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = SoftWhite.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = SoftWhite,
            fontWeight = FontWeight.Medium
        )
    }
}

/**
 * ردیف وضعیت (مثلاً ثبت شده / ثبت نشده)
 */
@Composable
fun ReviewStatusItem(
    label: String,
    status: String
) {
    val isDone = status.contains("ثبت شده")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = SoftWhite.copy(alpha = 0.6f)
        )
        Text(
            text = status,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isDone) Gold else SoftWhite.copy(alpha = 0.5f),
            fontWeight = FontWeight.Medium
        )
    }
}
