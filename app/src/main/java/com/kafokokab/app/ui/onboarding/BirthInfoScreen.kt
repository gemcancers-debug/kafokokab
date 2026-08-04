/*
نام فایل: BirthInfoScreen.kt
وظیفه: مرحله ۱ از ۴ آنبوردینگ – دریافت اطلاعات تولد کاربر
طراحی: بر اساس UI آپلود شده توسط کاربر
نویسنده: AI Principal Engineer
تاریخ: 2026-08-01

نکته برای ویرایش بعدی:
- همه بخش‌ها به کامپوننت‌های کوچک جدا شده‌اند
- لیست ماه‌های شمسی و استان‌ها به راحتی قابل تغییر هستند
- رنگ‌ها و فاصله‌ها از Theme می‌آیند
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
 * صفحه اطلاعات تولد (مرحله ۱ از ۴).
 *
 * @param onBack کلیک روی دکمه بازگشت
 * @param onContinue کلیک روی دکمه ادامه (با داده‌های وارد شده)
 */
@Composable
fun BirthInfoScreen(
    onBack: () -> Unit = {},
    onContinue: () -> Unit = {}
) {
    // وضعیت‌های فرم (بعداً به ViewModel منتقل می‌شوند)
    var selectedDay by remember { mutableStateOf("۱۵") }
    var selectedMonth by remember { mutableStateOf("اردیبهشت") }
    var selectedYear by remember { mutableStateOf("۱۳۷۰") }
    var selectedGender by remember { mutableStateOf("مرد") } // مرد یا زن
    var birthHour by remember { mutableStateOf("۱۴") }
    var birthMinute by remember { mutableStateOf("۳۰") }
    var unknownTime by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf("ایران") }
    var selectedProvince by remember { mutableStateOf("تهران") }
    var selectedCity by remember { mutableStateOf("تهران") }

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
            // هدر با دکمه بازگشت و نوار پیشرفت
            OnboardingHeader(
                currentStep = 1,
                totalSteps = 4,
                title = "اطلاعات تولد شما",
                onBack = onBack
            )

            Spacer(modifier = Modifier.height(24.dp))

            // بخش تاریخ تولد
            SectionCard(
                title = "تاریخ تولد",
                icon = Icons.Default.CalendarMonth
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // روز
                    SelectorBox(
                        label = "روز",
                        value = selectedDay,
                        modifier = Modifier.weight(1f),
                        onClick = { /* بعداً DatePicker */ }
                    )
                    // ماه شمسی
                    SelectorBox(
                        label = "ماه",
                        value = selectedMonth,
                        modifier = Modifier.weight(1.3f),
                        onClick = { /* بعداً لیست ماه‌ها */ }
                    )
                    // سال
                    SelectorBox(
                        label = "سال",
                        value = selectedYear,
                        modifier = Modifier.weight(1.2f),
                        onClick = { /* بعداً سال‌ها */ }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // بخش جنسیت
            SectionCard(
                title = "جنسیت",
                icon = Icons.Default.Person
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GenderChip(
                        text = "زن",
                        selected = selectedGender == "زن",
                        modifier = Modifier.weight(1f),
                        onClick = { selectedGender = "زن" }
                    )
                    GenderChip(
                        text = "مرد",
                        selected = selectedGender == "مرد",
                        modifier = Modifier.weight(1f),
                        onClick = { selectedGender = "مرد" }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // بخش زمان تولد
            SectionCard(
                title = "زمان تولد",
                icon = Icons.Default.Schedule
            ) {
                // چک‌باکس «ساعت تولدم را نمی‌دانم»
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { unknownTime = !unknownTime }
                ) {
                    Checkbox(
                        checked = unknownTime,
                        onCheckedChange = { unknownTime = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = NeonPink,
                            uncheckedColor = SoftWhite.copy(alpha = 0.5f)
                        )
                    )
                    Text(
                        text = "ساعت تولدم را نمی‌دانم",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SoftWhite
                    )
                }

                if (!unknownTime) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        SelectorBox(
                            label = "ساعت",
                            value = birthHour,
                            modifier = Modifier.weight(1f),
                            onClick = { }
                        )
                        SelectorBox(
                            label = "دقیقه",
                            value = birthMinute,
                            modifier = Modifier.weight(1f),
                            onClick = { }
                        )
                    }
                } else {
                    // پیشنهاد تخمین ساعت (Premium)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "می‌توانید بعداً با روش‌های پیشرفته ساعت تقریبی را تخمین بزنید (ویژه)",
                        style = MaterialTheme.typography.bodySmall,
                        color = Gold.copy(alpha = 0.85f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // بخش مکان تولد
            SectionCard(
                title = "مکان تولد",
                icon = Icons.Default.LocationOn
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SelectorBox(
                        label = "کشور",
                        value = selectedCountry,
                        modifier = Modifier.weight(1f),
                        onClick = { }
                    )
                    SelectorBox(
                        label = "استان",
                        value = selectedProvince,
                        modifier = Modifier.weight(1f),
                        onClick = { }
                    )
                    SelectorBox(
                        label = "شهر",
                        value = selectedCity,
                        modifier = Modifier.weight(1f),
                        onClick = { }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // دکمه ادامه
            ContinueButton(
                text = "ادامه",
                onClick = onContinue
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

// ============================================================
// کامپوننت‌های قابل استفاده مجدد برای آنبوردینگ
// این کامپوننت‌ها را می‌توانید در صفحات بعدی هم استفاده کنید
// ============================================================

/**
 * هدر مشترک صفحات آنبوردینگ (عنوان + نوار پیشرفت + بازگشت)
 */
@Composable
fun OnboardingHeader(
    currentStep: Int,
    totalSteps: Int,
    title: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        // ردیف بازگشت و نوار پیشرفت
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "بازگشت",
                    tint = SoftWhite
                )
            }

            // نوار پیشرفت دایره‌ای
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(totalSteps) { index ->
                    val stepNumber = index + 1
                    val isActive = stepNumber <= currentStep

                    Box(
                        modifier = Modifier
                            .size(if (isActive) 28.dp else 22.dp)
                            .clip(RoundedCornerShape(50))
                            .background(
                                if (isActive) NeonPink else SoftWhite.copy(alpha = 0.2f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$stepNumber",
                            color = if (isActive) SoftWhite else SoftWhite.copy(alpha = 0.6f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    if (index < totalSteps - 1) {
                        Box(
                            modifier = Modifier
                                .width(16.dp)
                                .height(2.dp)
                                .background(
                                    if (stepNumber < currentStep) NeonPink
                                    else SoftWhite.copy(alpha = 0.2f)
                                )
                        )
                    }
                }
            }

            // فضای خالی برای تعادل با دکمه بازگشت
            Spacer(modifier = Modifier.width(48.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = SoftWhite,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/**
 * کارت بخش با عنوان و آیکون (گلاسمورفیسم ساده)
 */
@Composable
fun SectionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
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
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 14.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Gold,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = SoftWhite
            )
        }
        content()
    }
}

/**
 * جعبه انتخاب (برای روز، ماه، سال، شهر و ...)
 */
@Composable
fun SelectorBox(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = SoftWhite.copy(alpha = 0.6f),
            modifier = Modifier.padding(bottom = 6.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(DarkGalaxy.copy(alpha = 0.6f))
                .border(
                    width = 1.dp,
                    color = SoftWhite.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                color = SoftWhite,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

/**
 * چیپ انتخاب جنسیت
 */
@Composable
fun GenderChip(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (selected) NeonPink.copy(alpha = 0.35f)
                else SoftWhite.copy(alpha = 0.08f)
            )
            .border(
                width = 1.dp,
                color = if (selected) NeonPink else SoftWhite.copy(alpha = 0.15f),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = SoftWhite,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

/**
 * دکمه ادامه پایین صفحه
 */
@Composable
fun ContinueButton(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(NeonPink, MysticPurple)
                )
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = SoftWhite,
            fontWeight = FontWeight.Bold
        )
    }
}
