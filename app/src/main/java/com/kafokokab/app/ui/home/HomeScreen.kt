/*
نام فایل: HomeScreen.kt
وظیفه: صفحه اصلی (داشبورد) اپلیکیشن – مطابق طراحی آپلود شده
نویسنده: AI Principal Engineer
تاریخ: 2026-08-01
آخرین تغییر: 2026-08-13 - اضافه شدن کارت طالع نمادین امروز (DailyInsightCard)
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kafokokab.core.ui.theme.DarkGalaxy
import com.kafokokab.core.ui.theme.Gold
import com.kafokokab.core.ui.theme.MysticPurple
import com.kafokokab.core.ui.theme.NeonPink
import com.kafokokab.core.ui.theme.SoftWhite

/**
 * صفحه اصلی داشبورد.
 * نام کاربر از ProfileRepository خوانده می‌شود.
 */
@Composable
fun HomeScreen(
    onNavigateToChart: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

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
                .padding(bottom = 80.dp)
        ) {
            HomeHeader(
                userName = uiState.userName,
                onNotificationClick = {},
                onSettingsClick = {}
            )

            Spacer(modifier = Modifier.height(16.dp))

            BirthChartCard(
                onGetChartClick = onNavigateToChart,
                hasBirthInfo = uiState.hasBirthInfo,
                birthSummary = uiState.birthSummary
            )

            Spacer(modifier = Modifier.height(16.dp))

            DailyInsightCard(
                title = uiState.dailyInsightTitle,
                message = uiState.dailyInsight
            )

            Spacer(modifier = Modifier.height(20.dp))

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
                    onClick = onNavigateToChart
                )
                MainCategoryCard(
                    title = "هوروسکوپ شرقی",
                    subtitle = "سیدریال",
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateToChart
                )
                MainCategoryCard(
                    title = "هوروسکوپ ودیک",
                    subtitle = "جیوتیش",
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateToChart
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

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

            SectionTitle(title = "ابزارهای پیشرفته")

            Spacer(modifier = Modifier.height(12.dp))

            AdvancedToolsGrid()

            Spacer(modifier = Modifier.height(20.dp))

            PremiumBanner(onClick = {})

            Spacer(modifier = Modifier.height(24.dp))
        }

        BottomNavigationBar(
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun HomeHeader(
    userName: String,
    onNotificationClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "سلام، $userName",
                style = MaterialTheme.typography.titleLarge,
                color = SoftWhite,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "آسمان امروز چه می‌گوید؟",
                style = MaterialTheme.typography.bodyMedium,
                color = SoftWhite.copy(alpha = 0.7f)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(SoftWhite.copy(alpha = 0.1f))
                    .clickable(onClick = onNotificationClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint = SoftWhite,
                    modifier = Modifier.size(22.dp)
                )
            }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(SoftWhite.copy(alpha = 0.1f))
                    .clickable(onClick = onSettingsClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    tint = SoftWhite,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}

@Composable
fun BirthChartCard(
    onGetChartClick: () -> Unit,
    hasBirthInfo: Boolean = false,
    birthSummary: String = ""
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

            if (hasBirthInfo && birthSummary.isNotBlank()) {
                Text(
                    text = birthSummary,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Gold
                )
                Spacer(modifier = Modifier.height(8.dp))
            } else {
                Text(
                    text = "در یک نگاه از سرنوشت خود آگاه شوید",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SoftWhite.copy(alpha = 0.75f)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(NeonPink)
                    .clickable(onClick = onGetChartClick)
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Text(
                    text = if (hasBirthInfo) "مشاهده چارت" else "به‌دست آوردن هوروسکوپ",
                    style = MaterialTheme.typography.labelLarge,
                    color = SoftWhite,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * کارت طالع نمادین امروز.
 * پیام‌ها جنبه آموزشی و خودشناسی دارند و ادعای پیش‌بینی قطعی آینده ندارند.
 */
@Composable
fun DailyInsightCard(
    title: String,
    message: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2A1458).copy(alpha = 0.7f),
                        MysticPurple.copy(alpha = 0.35f)
                    )
                )
            )
            .border(
                width = 1.dp,
                color = Gold.copy(alpha = 0.35f),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(20.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "☽",
                    fontSize = 22.sp,
                    color = Gold
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Gold,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = SoftWhite.copy(alpha = 0.9f),
                lineHeight = 26.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "برای سرگرمی و خودشناسی • قطعی نیست",
                style = MaterialTheme.typography.labelSmall,
                color = SoftWhite.copy(alpha = 0.45f)
            )
        }
    }
}

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
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = SoftWhite,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.labelSmall,
            color = SoftWhite.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )
    }
}

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
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isPremium) Gold else SoftWhite,
                    modifier = Modifier.size(28.dp)
                )
                if (isPremium) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = NeonPink,
                        modifier = Modifier
                            .size(12.dp)
                            .align(Alignment.TopEnd)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = SoftWhite,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
            if (isPremium) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Premium",
                    style = MaterialTheme.typography.labelSmall,
                    color = Gold.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
fun AdvancedToolsGrid() {
    val tools = listOf(
        "طالع بینی ماهانه" to Icons.Default.CalendarMonth,
        "سازگاری زوج" to Icons.Default.Favorite,
        "تحلیل خواب" to Icons.Default.WbTwilight,
        "فال حافظ" to Icons.Default.AutoAwesome
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        tools.chunked(2).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                row.forEach { (title, icon) ->
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(14.dp))
                            .background(SoftWhite.copy(alpha = 0.07f))
                            .border(1.dp, SoftWhite.copy(alpha = 0.1f), RoundedCornerShape(14.dp))
                            .clickable { }
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = Gold,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelLarge,
                            color = SoftWhite
                        )
                    }
                }
                if (row.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun PremiumBanner(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Gold.copy(alpha = 0.25f),
                        NeonPink.copy(alpha = 0.2f)
                    )
                )
            )
            .border(1.dp, Gold.copy(alpha = 0.4f), RoundedCornerShape(18.dp))
            .clickable(onClick = onClick)
            .padding(18.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "کف و کوکب Premium",
                    style = MaterialTheme.typography.titleMedium,
                    color = Gold,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "تحلیل عمیق، گزارش کامل و تاریخچه",
                    style = MaterialTheme.typography.bodySmall,
                    color = SoftWhite.copy(alpha = 0.8f)
                )
            }
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Gold,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF12001F).copy(alpha = 0.95f))
            .padding(vertical = 10.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItem(icon = Icons.Default.Home, label = "خانه", selected = true)
        BottomNavItem(icon = Icons.Default.AutoAwesome, label = "چارت", selected = false)
        BottomNavItem(icon = Icons.Default.Star, label = "ابزار", selected = false)
        BottomNavItem(icon = Icons.Default.Person, label = "پروفایل", selected = false)
    }
}

@Composable
private fun BottomNavItem(
    icon: ImageVector,
    label: String,
    selected: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (selected) NeonPink else SoftWhite.copy(alpha = 0.5f),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
            color = if (selected) NeonPink else SoftWhite.copy(alpha = 0.5f)
        )
    }
}
