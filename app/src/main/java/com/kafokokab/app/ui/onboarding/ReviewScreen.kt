/*
نام فایل: ReviewScreen.kt
وظیفه: مرحله ۴ از ۴ آنبوردینگ – بررسی و تأیید نهایی اطلاعات کاربر
طراحی: بر اساس UI آپلود شده توسط کاربر
نویسنده: AI Principal Engineer
تاریخ: 2026-08-01
آخرین تغییر: 2026-08-05 - نمایش داده واقعی از OnboardingViewModel
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.kafokokab.core.ui.theme.NeonPink
import com.kafokokab.core.ui.theme.SoftWhite

@Composable
fun ReviewScreen(
    viewModel: OnboardingViewModel,
    onBack: () -> Unit = {},
    onConfirm: () -> Unit = {},
    onEditBirth: () -> Unit = {},
    onEditPersonal: () -> Unit = {},
    onEditPhotos: () -> Unit = {},
    onEditOptional: () -> Unit = {}
) {
    val profile by viewModel.profile.collectAsState()

    val birthDateText = listOf(profile.birthDay, profile.birthMonth, profile.birthYear)
        .filter { it.isNotBlank() }
        .joinToString(" ")
        .ifBlank { "—" }

    val birthTimeText = when {
        profile.isBirthTimeUnknown -> "نامشخص"
        profile.birthHour.isNotBlank() || profile.birthMinute.isNotBlank() ->
            "${profile.birthHour.ifBlank { "--" }}:${profile.birthMinute.ifBlank { "--" }}"
        else -> "—"
    }

    val locationText = listOf(profile.birthCity, profile.birthProvince, profile.birthCountry)
        .filter { it.isNotBlank() }
        .joinToString("، ")
        .ifBlank { "—" }

    val palmStatus = when {
        profile.hasLeftPalmPhoto && profile.hasRightPalmPhoto -> "ثبت شده (هر دو دست)"
        profile.hasLeftPalmPhoto || profile.hasRightPalmPhoto -> "ثبت شده (یک دست)"
        else -> "ثبت نشده"
    }

    val faceStatus = if (profile.hasFacePhoto) "ثبت شده" else "ثبت نشده"
    val moleStatus = if (profile.selectedMolePositions.isNotEmpty()) {
        "ثبت شده (${profile.selectedMolePositions.size} موقعیت)"
    } else {
        "ثبت نشده"
    }

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

            ReviewSection(
                title = "اطلاعات تولد",
                icon = Icons.Default.CalendarMonth,
                onEdit = onEditBirth
            ) {
                ReviewItem(label = "تاریخ تولد", value = birthDateText)
                ReviewItem(label = "ساعت تولد", value = birthTimeText)
                ReviewItem(label = "محل تولد", value = locationText)
                ReviewItem(label = "جنسیت", value = profile.gender.ifBlank { "—" })
            }

            Spacer(modifier = Modifier.height(14.dp))

            ReviewSection(
                title = "اطلاعات فردی",
                icon = Icons.Default.Person,
                onEdit = onEditPersonal
            ) {
                ReviewItem(label = "نام", value = profile.firstName.ifBlank { "—" })
                ReviewItem(label = "نام خانوادگی", value = profile.lastName.ifBlank { "—" })
                ReviewItem(label = "نام مادر", value = profile.motherName.ifBlank { "—" })
            }

            Spacer(modifier = Modifier.height(14.dp))

            ReviewSection(
                title = "تصاویر و تحلیل‌ها",
                icon = Icons.Default.CameraAlt,
                onEdit = onEditPhotos
            ) {
                ReviewStatusItem(label = "کف دست", status = palmStatus)
                ReviewStatusItem(label = "چهره‌شناسی", status = faceStatus)
                ReviewStatusItem(label = "خال‌شناسی", status = moleStatus)
            }

            Spacer(modifier = Modifier.height(14.dp))

            ReviewSection(
                title = "موارد تکمیلی (اختیاری)",
                icon = Icons.Default.Star,
                onEdit = onEditOptional
            ) {
                ReviewItem(label = "رنگ مو", value = profile.hairColor.ifBlank { "—" })
                ReviewItem(label = "گروه خونی", value = profile.bloodType.ifBlank { "—" })
                ReviewItem(label = "رنگ چشم", value = profile.eyeColor.ifBlank { "—" })
                ReviewItem(
                    label = "قد",
                    value = if (profile.heightCm.isNotBlank()) "${profile.heightCm} سانتی‌متر" else "—"
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

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

            ContinueButton(
                text = "تأیید نهایی و ورود به برنامه",
                onClick = onConfirm
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

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

@Composable
fun ReviewItem(label: String, value: String) {
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

@Composable
fun ReviewStatusItem(label: String, status: String) {
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
