/*
نام فایل: PersonalInfoScreen.kt
وظیفه: مرحله ۲ از ۴ آنبوردینگ – دریافت نام و تصاویر کف دست
طراحی: بر اساس UI آپلود شده توسط کاربر
نویسنده: AI Principal Engineer
تاریخ: 2026-08-01
آخرین تغییر: 2026-08-05 - اتصال به OnboardingViewModel
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kafokokab.core.ui.theme.DarkGalaxy
import com.kafokokab.core.ui.theme.Gold
import com.kafokokab.core.ui.theme.NeonPink
import com.kafokokab.core.ui.theme.SoftWhite

/**
 * صفحه اطلاعات شخصی (مرحله ۲ از ۴).
 */
@Composable
fun PersonalInfoScreen(
    viewModel: OnboardingViewModel,
    onBack: () -> Unit = {},
    onContinue: () -> Unit = {},
    onSkipPalm: () -> Unit = {}
) {
    val profile by viewModel.profile.collectAsState()

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
                currentStep = 2,
                totalSteps = 4,
                title = "اطلاعات تکمیلی شما",
                onBack = onBack
            )

            Spacer(modifier = Modifier.height(24.dp))

            SectionCard(
                title = "اطلاعات فردی",
                icon = Icons.Default.Person
            ) {
                OnboardingTextField(
                    value = profile.firstName,
                    onValueChange = {
                        viewModel.updatePersonalInfo(it, profile.lastName, profile.motherName)
                    },
                    label = "نام",
                    placeholder = "نام خود را وارد کنید"
                )

                Spacer(modifier = Modifier.height(12.dp))

                OnboardingTextField(
                    value = profile.lastName,
                    onValueChange = {
                        viewModel.updatePersonalInfo(profile.firstName, it, profile.motherName)
                    },
                    label = "نام خانوادگی",
                    placeholder = "نام خانوادگی خود را وارد کنید"
                )

                Spacer(modifier = Modifier.height(12.dp))

                OnboardingTextField(
                    value = profile.motherName,
                    onValueChange = {
                        viewModel.updatePersonalInfo(profile.firstName, profile.lastName, it)
                    },
                    label = "نام مادر",
                    placeholder = "نام مادر خود را وارد کنید"
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

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
                    PalmPhotoBox(
                        label = "کف دست چپ",
                        hasPhoto = profile.hasLeftPalmPhoto,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            viewModel.updatePalmPhotos(
                                !profile.hasLeftPalmPhoto,
                                profile.hasRightPalmPhoto
                            )
                        }
                    )

                    PalmPhotoBox(
                        label = "کف دست راست",
                        hasPhoto = profile.hasRightPalmPhoto,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            viewModel.updatePalmPhotos(
                                profile.hasLeftPalmPhoto,
                                !profile.hasRightPalmPhoto
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

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

            ContinueButton(
                text = "ادامه",
                onClick = onContinue
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

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
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "✋", fontSize = 36.sp)
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
