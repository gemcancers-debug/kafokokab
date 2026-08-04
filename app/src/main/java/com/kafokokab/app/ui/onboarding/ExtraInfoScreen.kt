/*
نام فایل: ExtraInfoScreen.kt
وظیفه: مرحله ۳ از ۴ آنبوردینگ – چهره‌شناسی + خال‌شناسی بدون عکس
طراحی: بر اساس UI آپلود شده + تصمیم حریم خصوصی کاربر
نویسنده: AI Principal Engineer
تاریخ: 2026-08-01

نکته بسیار مهم:
- هیچ عکسی از بدن برای خال‌شناسی گرفته نمی‌شود
- فقط انتخاب موقعیت خال از منوی شیشه‌ای
- قسمت‌های حساس (نشیمنگاه، آلت جنسی و ...) حذف شده‌اند
- لحن «قفسه سینه» خنثی و مردانه‌تر است
*/

package com.kafokokab.app.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
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
 * لیست موقعیت‌های بدن برای خال‌شناسی.
 * این لیست را می‌توانید بعداً به راحتی ویرایش یا گسترش دهید.
 * هیچ قسمت حساسی در آن وجود ندارد.
 */
object MoleBodyParts {
    val face = listOf(
        "پیشانی",
        "ابروی چپ",
        "ابروی راست",
        "گونه چپ",
        "گونه راست",
        "بینی",
        "لب",
        "چانه",
        "شقیقه چپ",
        "شقیقه راست"
    )

    val neck = listOf(
        "جلوی گردن",
        "پشت گردن",
        "سمت چپ گردن",
        "سمت راست گردن"
    )

    val upperBody = listOf(
        "شانه چپ",
        "شانه راست",
        "قفسه سینه",           // لحن خنثی و مردانه‌تر
        "پشت (بالا)",
        "پشت (وسط)",
        "پهلوی چپ",
        "پهلوی راست"
    )

    val arms = listOf(
        "بازو چپ",
        "بازو راست",
        "ساعد چپ",
        "ساعد راست",
        "مچ دست چپ",
        "مچ دست راست"
    )

    val lowerBody = listOf(
        "شکم",
        "کمر",
        "ران چپ",
        "ران راست",
        "زانوی چپ",
        "زانوی راست",
        "ساق پای چپ",
        "ساق پای راست",
        "قوزک پای چپ",
        "قوزک پای راست",
        "روی پای چپ",
        "روی پای راست",
        "پاشنه پای چپ",
        "پاشنه پای راست",
        "کف پای چپ",
        "کف پای راست"
    )

    /** همه موقعیت‌ها به صورت یک لیست تخت */
    val all: List<String> = face + neck + upperBody + arms + lowerBody
}

/**
 * صفحه اطلاعات تکمیلی (مرحله ۳ از ۴).
 *
 * شامل:
 * - جای اسکن چهره (اختیاری)
 * - منوی شیشه‌ای انتخاب موقعیت خال‌ها (بدون هیچ عکسی)
 * - موارد اختیاری دیگر
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExtraInfoScreen(
    onBack: () -> Unit = {},
    onContinue: () -> Unit = {},
    onSkip: () -> Unit = {}
) {
    // وضعیت انتخاب‌شده‌های خال
    var selectedMoles by remember { mutableStateOf(setOf<String>()) }

    // وضعیت اسکن چهره (فعلاً فقط UI)
    var hasFaceScan by remember { mutableStateOf(false) }

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
                currentStep = 3,
                totalSteps = 4,
                title = "اطلاعات تکمیلی شما",
                onBack = onBack
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ---------- بخش چهره‌شناسی ----------
            SectionCard(
                title = "چهره‌شناسی",
                icon = Icons.Default.Face
            ) {
                Text(
                    text = "برای تحلیل ویژگی‌های چهره و شخصیت شما",
                    style = MaterialTheme.typography.bodySmall,
                    color = SoftWhite.copy(alpha = 0.7f),
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // جای عکس چهره
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            if (hasFaceScan) NeonPink.copy(alpha = 0.12f)
                            else SoftWhite.copy(alpha = 0.06f)
                        )
                        .border(
                            width = 1.dp,
                            color = if (hasFaceScan) NeonPink else SoftWhite.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable { hasFaceScan = !hasFaceScan },
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "عکس چهره",
                            tint = if (hasFaceScan) NeonPink else SoftWhite.copy(alpha = 0.6f),
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (hasFaceScan) "عکس ثبت شد" else "عکس چهره",
                            style = MaterialTheme.typography.bodyMedium,
                            color = SoftWhite.copy(alpha = 0.85f)
                        )
                        Text(
                            text = "در نور مناسب و بدون فیلتر",
                            style = MaterialTheme.typography.labelSmall,
                            color = SoftWhite.copy(alpha = 0.5f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "بعداً وارد می‌کنم",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SoftWhite.copy(alpha = 0.55f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onSkip)
                        .padding(6.dp)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // ---------- بخش خال‌شناسی (بدون عکس) ----------
            SectionCard(
                title = "خال‌شناسی",
                icon = Icons.Default.Lock
            ) {
                // توضیح کوتاه و شفاف
                Text(
                    text = "موقعیت خال‌های بدن خود را انتخاب کنید. نیازی به گرفتن عکس نیست.",
                    style = MaterialTheme.typography.bodySmall,
                    color = SoftWhite.copy(alpha = 0.75f),
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                Text(
                    text = "این اطلاعات فقط برای تحلیل نمادین استفاده می‌شود و کاملاً محرمانه می‌ماند.",
                    style = MaterialTheme.typography.labelSmall,
                    color = SoftWhite.copy(alpha = 0.5f),
                    modifier = Modifier.padding(bottom = 14.dp)
                )

                // منوی موقعیت‌ها به صورت دسته‌بندی شده
                MoleCategorySection(
                    title = "صورت و سر",
                    items = MoleBodyParts.face,
                    selected = selectedMoles,
                    onToggle = { part ->
                        selectedMoles = if (part in selectedMoles) selectedMoles - part
                        else selectedMoles + part
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                MoleCategorySection(
                    title = "گردن",
                    items = MoleBodyParts.neck,
                    selected = selectedMoles,
                    onToggle = { part ->
                        selectedMoles = if (part in selectedMoles) selectedMoles - part
                        else selectedMoles + part
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                MoleCategorySection(
                    title = "بالاتنه",
                    items = MoleBodyParts.upperBody,
                    selected = selectedMoles,
                    onToggle = { part ->
                        selectedMoles = if (part in selectedMoles) selectedMoles - part
                        else selectedMoles + part
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                MoleCategorySection(
                    title = "دست‌ها",
                    items = MoleBodyParts.arms,
                    selected = selectedMoles,
                    onToggle = { part ->
                        selectedMoles = if (part in selectedMoles) selectedMoles - part
                        else selectedMoles + part
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                MoleCategorySection(
                    title = "پایین تنه و پاها",
                    items = MoleBodyParts.lowerBody,
                    selected = selectedMoles,
                    onToggle = { part ->
                        selectedMoles = if (part in selectedMoles) selectedMoles - part
                        else selectedMoles + part
                    }
                )

                // نمایش تعداد انتخاب‌شده
                if (selectedMoles.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "${selectedMoles.size} موقعیت انتخاب شده",
                        style = MaterialTheme.typography.labelMedium,
                        color = Gold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            ContinueButton(
                text = "ادامه",
                onClick = onContinue
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

/**
 * یک دسته از موقعیت‌های بدن با عنوان و چیپ‌های قابل انتخاب.
 * ظاهر شیشه‌ای و هماهنگ با تم پروژه.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MoleCategorySection(
    title: String,
    items: List<String>,
    selected: Set<String>,
    onToggle: (String) -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = SoftWhite.copy(alpha = 0.85f),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items.forEach { part ->
                val isSelected = part in selected
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            if (isSelected) NeonPink.copy(alpha = 0.3f)
                            else SoftWhite.copy(alpha = 0.08f)
                        )
                        .border(
                            width = 1.dp,
                            color = if (isSelected) NeonPink else SoftWhite.copy(alpha = 0.18f),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .clickable { onToggle(part) }
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = part,
                        style = MaterialTheme.typography.bodySmall,
                        color = SoftWhite,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}
