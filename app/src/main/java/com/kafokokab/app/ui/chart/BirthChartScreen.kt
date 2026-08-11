/*
نام فایل: BirthChartScreen.kt
مسیر: app/.../ui/chart/
وظیفه: نمایش ساده و زیبای چارت تولد (موقعیت سیارات)
نویسنده: AI Principal Engineer
تاریخ: 2026-08-08
آخرین تغییر: 2026-08-11 - اصلاح import Modifier و فراخوانی loadChart

طراحی:
- تم Dark Galaxy + Glassmorphism
- نمایش برج خورشید، ماه و طلوع در بالای صفحه
- لیست موقعیت تمام سیارات با نام فارسی
- امکان تعویض سیستم غربی / ودیک
*/

package com.kafokokab.app.ui.chart

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kafokokab.core.domain.model.astrology.ChartSystem
import com.kafokokab.core.domain.model.astrology.PlanetPosition
import com.kafokokab.core.ui.theme.DarkGalaxy
import com.kafokokab.core.ui.theme.Gold
import com.kafokokab.core.ui.theme.MysticPurple
import com.kafokokab.core.ui.theme.NeonPink
import com.kafokokab.core.ui.theme.SoftWhite

@Composable
fun BirthChartScreen(
    onBack: () -> Unit,
    viewModel: BirthChartViewModel = hiltViewModel()
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
        ) {
            ChartHeader(
                onBack = onBack,
                onRefresh = { viewModel.loadChart() }
            )

            Spacer(modifier = Modifier.height(12.dp))

            SystemSelector(
                selected = uiState.selectedSystem,
                onSelect = { viewModel.changeSystem(it) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = NeonPink)
                    }
                }

                uiState.errorMessage != null -> {
                    Text(
                        text = uiState.errorMessage ?: "",
                        color = SoftWhite,
                        modifier = Modifier.padding(20.dp),
                        textAlign = TextAlign.Center
                    )
                }

                uiState.chart != null -> {
                    val chart = uiState.chart!!

                    SummaryCards(
                        sun = chart.sun,
                        moon = chart.moon,
                        asc = chart.ascendant
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "موقعیت سیارات",
                        style = MaterialTheme.typography.titleMedium,
                        color = SoftWhite,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    chart.positions.forEach { position ->
                        PlanetRow(position = position)
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "⚠ این چارت با موتور موقت (Stub) محاسبه شده است.\nمحاسبات واقعی نجومی در نسخه‌های بعدی اضافه می‌شود.",
                        style = MaterialTheme.typography.bodySmall,
                        color = SoftWhite.copy(alpha = 0.5f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    )

                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }
}

@Composable
private fun ChartHeader(
    onBack: () -> Unit,
    onRefresh: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "بازگشت",
            tint = SoftWhite,
            modifier = Modifier
                .size(28.dp)
                .clickable(onClick = onBack)
        )

        Text(
            text = "چارت تولد",
            style = MaterialTheme.typography.titleLarge,
            color = SoftWhite,
            fontWeight = FontWeight.Bold
        )

        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "تازه‌سازی",
            tint = SoftWhite.copy(alpha = 0.8f),
            modifier = Modifier
                .size(24.dp)
                .clickable(onClick = onRefresh)
        )
    }
}

@Composable
private fun SystemSelector(
    selected: ChartSystem,
    onSelect: (ChartSystem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        SystemChip(
            title = "غربی",
            selected = selected == ChartSystem.WESTERN,
            modifier = Modifier.weight(1f),
            onClick = { onSelect(ChartSystem.WESTERN) }
        )
        SystemChip(
            title = "ودیک",
            selected = selected == ChartSystem.VEDIC,
            modifier = Modifier.weight(1f),
            onClick = { onSelect(ChartSystem.VEDIC) }
        )
    }
}

@Composable
private fun SystemChip(
    title: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (selected) NeonPink.copy(alpha = 0.25f)
                else SoftWhite.copy(alpha = 0.07f)
            )
            .border(
                width = 1.dp,
                color = if (selected) NeonPink else SoftWhite.copy(alpha = 0.12f),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = if (selected) SoftWhite else SoftWhite.copy(alpha = 0.7f),
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
private fun SummaryCards(
    sun: PlanetPosition?,
    moon: PlanetPosition?,
    asc: PlanetPosition?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        SummaryItem(
            title = "خورشید",
            value = sun?.sign?.persianName ?: "—",
            symbol = sun?.planet?.symbol ?: "☉",
            modifier = Modifier.weight(1f)
        )
        SummaryItem(
            title = "ماه",
            value = moon?.sign?.persianName ?: "—",
            symbol = moon?.planet?.symbol ?: "☽",
            modifier = Modifier.weight(1f)
        )
        SummaryItem(
            title = "طلوع",
            value = asc?.sign?.persianName ?: "—",
            symbol = "Asc",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun SummaryItem(
    title: String,
    value: String,
    symbol: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(SoftWhite.copy(alpha = 0.08f))
            .border(1.dp, SoftWhite.copy(alpha = 0.12f), RoundedCornerShape(16.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = symbol,
            style = MaterialTheme.typography.titleLarge,
            color = Gold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = SoftWhite.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            color = SoftWhite,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun PlanetRow(position: PlanetPosition) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(SoftWhite.copy(alpha = 0.06f))
            .border(1.dp, SoftWhite.copy(alpha = 0.1f), RoundedCornerShape(14.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MysticPurple.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = position.planet.symbol,
                color = Gold,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = position.planet.persianName,
                style = MaterialTheme.typography.titleSmall,
                color = SoftWhite,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = position.toPersianDisplay().substringAfter("در "),
                style = MaterialTheme.typography.bodySmall,
                color = SoftWhite.copy(alpha = 0.7f)
            )
        }

        if (position.isRetrograde) {
            Text(
                text = "رجعی",
                style = MaterialTheme.typography.labelSmall,
                color = NeonPink,
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(NeonPink.copy(alpha = 0.15f))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            )
        }
    }
}
