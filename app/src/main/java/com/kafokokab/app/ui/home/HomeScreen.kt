/*
نام فایل: HomeScreen.kt
وظیفه: صفحه اصلی (داشبورد) اپلیکیشن – مطابق طراحی آپلود شده
نویسنده: AI Principal Engineer
تاریخ: 2026-08-01

نکته برای ویرایش بعدی:
- تمام بخش‌ها به کامپوننت‌های کوچک جدا شده‌اند
- دکمه‌ها و کارت‌ها به راحتی قابل تغییر هستند
- داده‌ها فعلاً نمونه هستند و بعداً از ViewModel می‌آیند
*/

package com.kafokokab.app.ui.home

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier.modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
 * صفحه اصلی داشبورد.
 * ساختار کلی مطابق طراحی آپلود شده کاربر است.
 */
@Composable
fun HomeScreen() {
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
                .padding(bottom = 80.dp) // فضا برای Bottom Navigation
        ) {
            // ---------- هدر خوش‌آمدگویی ----------
            HomeHeader(
                userName = "نگین جان",
                onNotificationClick = {},
                onSettingsClick = {}
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ---------- کارت چارت تولد ----------
            BirthChartCard(
                onGetChartClick = {}
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ---------- دسته‌بندی اصلی ----------
            SectionTitle(title = "دسته‌بندی اصلی")

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MainCategoryCard(
                    title = "هوروسکوپ غربی",
                    subtitle = "تروپیکال",
                    modifier = Modifier.weight(1f),
                    onClick = {}
                )
                MainCategoryCard(
                    title = "هوروسکوپ شرقی",
                    subtitle = "سیدریال",
                    modifier = Modifier.weight(1f),
                    onClick = {}
                )
                MainCategoryCard(
                    title = "هوروسکوپ ودیک",
                    subtitle = "جیوتیش",
                    modifier = Modifier.weight(1f),
                    onClick = {}
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ---------- ابزارهای تحلیل ----------
            SectionTitle(title = "تحلیل‌های ویژه")

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AnalysisToolCard(
                    title = "تحلیل کف‌بینی",
                    icon = Icons.Default.Favorite,
                    isPremium = true,
                    modifier = Modifier.weight(1f),
                    onClick = {}
                )
                AnalysisToolCard(
                    title = "چهره‌شناسی",
                    icon = Icons.Default.Face,
                    isPremium = true,
                    modifier = Modifier.weight(1f),
                    onClick = {}
                )
                AnalysisToolCard(
                    title = "خال‌شناسی",
                    icon = Icons.Default.Star,
                    isPremium = true,
                    modifier = Modifier.weight(1f),
                    onClick = {}
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ---------- ابزارهای پیشرفته ----------
            SectionTitle(title = "ابزارهای پیشرفته")

            Spacer(modifier = Modifier.height(12.dp))

            AdvancedToolsGrid()

            Spacer(modifier = Modifier.height(20.dp))

            // ---------- بنر Premium ----------
            PremiumBanner(onClick = {})

            Spacer(modifier = Modifier.height(24.dp))
        }

        // ---------- نوار پایین ----------
        BottomNavigationBar(
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

// ============================================================
// کامپوننت‌های صفحه اصلی
// ============================================================

/**
 * هدر صفحه اصلی با نام کاربر و دکمه‌های اعلان و تنظیمات
 */
@Composable
fun HomeHeader(
    userName: String,
    onNotificationClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // آواتار ساده
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(MysticPurple.copy(alpha = 0.4f))
                    .border(1.dp, SoftWhite.copy(alpha = 0.2f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = SoftWhite,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "سلام، $userName",
                    style = MaterialTheme.typography.titleMedium,
                    color = SoftWhite,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "به دنیای خوش آمدی ✨",
                    style = MaterialTheme.typography.bodySmall,
                    color = SoftWhite.copy(alpha = 0.6f)
                )
            }
        }

        Row {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "اعلان‌ها",
                tint = SoftWhite.copy(alpha = 0.8f),
                modifier = Modifier
                    .size(24.dp)
                    .clickable(onClick = onNotificationClick)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "تنظیمات",
                tint = SoftWhite.copy(alpha = 0.8f),
                modifier = Modifier
                    .size(24.dp)
                    .clickable(onClick = onSettingsClick)
            )
        }
    }
}

/**
 * کارت اصلی چارت تولد
 */
@Composable
fun BirthChartCard(
    onGetChartClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        MysticPurple.copy(alpha = 0.35f),
                        NeonPink.copy(alpha = 0.2f)
                    )
                )
            )
            .border(
                width = 1.dp,
                color = SoftWhite.copy(alpha = 0.15f),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(20.dp)
    ) {
        Column {
            Text(
                text = "چارت تولد شما",
                style = MaterialTheme.typography.titleLarge,
                color = SoftWhite,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "در یک نگاه از سرنوشت خود آگاه شوید",
                style = MaterialTheme.typography.bodyMedium,
                color = SoftWhite.copy(alpha = 0.75f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(NeonPink)
                    .clickable(onClick = onGetChartClick)
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Text(
                    text = "به‌دست آوردن هوروسکوپ",
                    style = MaterialTheme.typography.labelLarge,
                    color = SoftWhite,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * عنوان بخش
 */
@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = SoftWhite,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(horizontal = 20.dp)
    )
}

/**
 * کارت دسته‌بندی اصلی (غربی / شرقی / ودیک)
 */
@Composable
fun MainCategoryCard(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(SoftWhite.copy(alpha = 0.07f))
            .border(1.dp, SoftWhite.copy(alpha = 0.12f), RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MysticPurple.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.WbTwilight,
                contentDescription = null,
                tint = Gold,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = SoftWhite,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.labelSmall,
            color = SoftWhite.copy(alpha = 0.5f),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "ورود",
            style = MaterialTheme.typography.labelSmall,
            color = NeonPink
        )
    }
}

/**
 * کارت ابزار تحلیل (کف‌بینی، چهره، خال) با قفل Premium
 */
@Composable
fun AnalysisToolCard(
    title: String,
    icon: ImageVector,
    isPremium: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(SoftWhite.copy(alpha = 0.07f))
            .border(1.dp, SoftWhite.copy(alpha = 0.12f), RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = SoftWhite,
                    modifier = Modifier.size(28.dp)
                )
                if (isPremium) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "ویژه",
                        tint = Gold,
                        modifier = Modifier
                            .size(14.dp)
                            .align(Alignment.TopEnd)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = SoftWhite,
                textAlign = TextAlign.Center
            )
            if (isPremium) {
                Text(
                    text = "Premium",
                    style = MaterialTheme.typography.labelSmall,
                    color = Gold
                )
            }
        }
    }
}

/**
 * شبکه ابزارهای پیشرفته
 */
@Composable
fun AdvancedToolsGrid() {
    val tools = listOf(
        "فال روزانه" to Icons.Default.AutoAwesome,
        "طالع بینی ماهانه" to Icons.Default.CalendarMonth,
        "سازگاری زوجین" to Icons.Default.Favorite,
        "بازگشت مشتری" to Icons.Default.WbTwilight,
        "انتخاب زمان مناسب" to Icons.Default.Star,
        "اعداد و ابجد" to Icons.Default.Star,
        "سنگ‌شناسی" to Icons.Default.Star,
        "رنگ‌شناسی" to Icons.Default.Star,
        "شخصیت‌شناسی" to Icons.Default.Person,
        "مدیتیشن و انرژی" to Icons.Default.AutoAwesome
    )

    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        tools.chunked(5).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItems.forEach { (title, icon) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(SoftWhite.copy(alpha = 0.06f))
                            .clickable { }
                            .padding(vertical = 10.dp, horizontal = 4.dp)
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = SoftWhite.copy(alpha = 0.85f),
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            color = SoftWhite.copy(alpha = 0.75f),
                            textAlign = TextAlign.Center,
                            maxLines = 2
                        )
                    }
                }
                // پر کردن فضای خالی اگر ردیف کامل نباشد
                repeat(5 - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

/**
 * بنر دعوت به نسخه Premium
 */
@Composable
fun PremiumBanner(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Gold.copy(alpha = 0.25f),
                        MysticPurple.copy(alpha = 0.3f)
                    )
                )
            )
            .border(1.dp, Gold.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "نسخه پریمیوم",
                    style = MaterialTheme.typography.titleMedium,
                    color = SoftWhite,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "دسترسی به تمام تحلیل‌های پیشرفته بدون محدودیت",
                    style = MaterialTheme.typography.bodySmall,
                    color = SoftWhite.copy(alpha = 0.75f)
                )
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Gold)
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "مشاهده و خرید",
                    style = MaterialTheme.typography.labelMedium,
                    color = DarkGalaxy,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * نوار پایین صفحه (Bottom Navigation)
 */
@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(DarkGalaxy.copy(alpha = 0.95f))
            .border(
                width = 1.dp,
                color = SoftWhite.copy(alpha = 0.08f)
            )
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItem(icon = Icons.Default.Settings, label = "تنظیمات", selected = false)
        BottomNavItem(icon = Icons.Default.Favorite, label = "علاقه‌مندی‌ها", selected = false)
        BottomNavItem(icon = Icons.Default.Home, label = "خانه", selected = true)
        BottomNavItem(icon = Icons.Default.Star, label = "گزارش‌ها", selected = false)
        BottomNavItem(icon = Icons.Default.Person, label = "پروفایل", selected = false)
    }
}

@Composable
fun BottomNavItem(
    icon: ImageVector,
    label: String,
    selected: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (selected) NeonPink else SoftWhite.copy(alpha = 0.5f),
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
            color = if (selected) NeonPink else SoftWhite.copy(alpha = 0.5f)
        )
    }
}
