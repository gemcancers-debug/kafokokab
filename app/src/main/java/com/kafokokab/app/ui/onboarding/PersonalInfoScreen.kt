/*
نام فایل: PersonalInfoScreen.kt
وظیفه: مرحله ۲ از ۴ آنبوردینگ – دریافت نام و تصاویر کف دست
طراحی: بر اساس UI آپلود شده توسط کاربر
نویسنده: AI Principal Engineer
تاریخ: 2026-08-01

نکته برای ویرایش بعدی:
- فیلدهای نام به راحتی قابل گسترش هستند
- جای عکس کف دست فعلاً placeholder است (منطق دوربین بعداً اضافه می‌شود)
- از کامپوننت‌های مشترک OnboardingHeader و ContinueButton استفاده می‌کند
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
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
 * صفحه اطلاعات شخصی (مرحله ۲ از ۴).
 *
 * شامل:
 * - نام، نام خانوادگی، نام مادر
 * - جای عکس کف دست چپ و راست
 * - یادآوری Premium برای تحلیل کامل کف دست
 *
 * @param onBack بازگشت به مرحله قبل
 * @param onContinue رفتن به مرحله بعد
 * @param onSkipPalm رد کردن موقت عکس کف دست
 */
@Composable
fun PersonalInfoScreen(
    onBack: () -> Unit = {},
    onContinue: () -> Unit = {},
    onSkipPalm: () -> Unit = {}
) {
    // وضعیت فیلدهای متنی (بعداً به ViewModel منتقل می‌شود)
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var motherName by remember { mutableStateOf("") }

    // وضعیت عکس کف دست (فعلاً فقط UI)
    var hasLeftPalm by remember { mutableStateOf(false) }
    var hasRightPalm by remember { mutableStateOf(false) }

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
            // هدر مشترک آنبوردینگ
            OnboardingHeader(
                currentStep = 2,
                totalSteps = 4,
                title = "اطلاعات تکمیلی شما",
                onBack = onBack
            )

            Spacer(modifier = Modifier.height(24.dp))

            // بخش نام‌ها
            SectionCard(
                title = "اطلاعات فردی",
                icon = Icons.Default.Person
            ) {
                // نام
                OnboardingTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = "نام",
                    placeholder = "نام خود را وارد کنید"
                )

                Spacer(modifier = Modifier.height(12.dp))

                // نام خانوادگی
                OnboardingTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = "نام خانوادگی",
                    placeholder = "نام خانوادگی خود را وارد کنید"
                )

                Spacer(modifier = Modifier.height(12.dp))

                // نام مادر (برای محاسبات ابجد و طالع‌بینی سنتی)
                OnboardingTextField(
                    value = motherName,
                    onValueChange = { motherName = it },
                    label = "نام مادر",
                    placeholder = "نام مادر خود را وارد کنید"
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // بخش عکس کف دست
            SectionCard(
                title = "تصویر کف دست",
                icon = Icons.Default.CameraAlt
            ) {
                Text(
                    text = "برای تحلیل دقیق‌تر کف دست، از هر دو دست عکس بگیرید.",
                    style = MaterialTheme.typography.bodySmall,
                    color = SoftWhite.copy(alpha = 0.7f),
                    modifier = Modifier.padding(bottom = 14.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // کف دست چپ
                    PalmPhotoBox(
                        label = "کف دست چپ",
                        hasPhoto = hasLeftPalm,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            // فعلاً فقط وضعیت را تغییر می‌دهیم
                            // منطق واقعی دوربین بعداً اضافه می‌شود
                            hasLeftPalm = !hasLeftPalm
                        }
                    )

                    // کف دست راست
                    PalmPhotoBox(
                        label = "کف دست راست",
                        hasPhoto = hasRightPalm,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            hasRightPalm = !hasRightPalm
                        }
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // یادآوری Premium
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Gold.copy(alpha = 0.1f))
                        .padding(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = Gold,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "تحلیل کامل کف دست فقط برای کاربران ویژه فعال است.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Gold.copy(alpha = 0.9f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // گزینه رد کردن موقت
                Text(
                    text = "بعداً وارد می‌کنم",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SoftWhite.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onSkipPalm)
                        .padding(8.dp)
                )
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

/**
 * فیلد متنی استاندارد برای صفحات آنبوردینگ.
 * ظاهر آن با تم کهکشانی هماهنگ است.
 */
@Composable
fun OnboardingTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = SoftWhite.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 6.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = SoftWhite.copy(alpha = 0.4f)
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = SoftWhite,
                unfocusedTextColor = SoftWhite,
                focusedBorderColor = NeonPink,
                unfocusedBorderColor = SoftWhite.copy(alpha = 0.2f),
                cursorColor = NeonPink,
                focusedContainerColor = DarkGalaxy.copy(alpha = 0.5f),
                unfocusedContainerColor = DarkGalaxy.copy(alpha = 0.5f)
            )
        )
    }
}

/**
 * جعبه انتخاب عکس کف دست.
 * فعلاً فقط UI است و با کلیک وضعیت را تغییر می‌دهد.
 * بعداً به دوربین یا گالری متصل می‌شود.
 */
@Composable
fun PalmPhotoBox(
    label: String,
    hasPhoto: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    if (hasPhoto) NeonPink.copy(alpha = 0.15f)
                    else SoftWhite.copy(alpha = 0.06f)
                )
                .border(
                    width = 1.dp,
                    color = if (hasPhoto) NeonPink else SoftWhite.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // نماد ساده دست
                Text(
                    text = "✋",
                    fontSize = 36.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "گرفتن عکس",
                    tint = if (hasPhoto) NeonPink else SoftWhite.copy(alpha = 0.6f),
                    modifier = Modifier.size(22.dp)
                )
                if (hasPhoto) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "ثبت شد",
                        style = MaterialTheme.typography.labelSmall,
                        color = NeonPink
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = SoftWhite.copy(alpha = 0.8f),
            textAlign = TextAlign.Center
        )
    }
}
