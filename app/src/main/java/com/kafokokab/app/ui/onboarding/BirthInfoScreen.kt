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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.kafokokab.core.domain.geo.IranCities
import com.kafokokab.core.ui.theme.DarkGalaxy
import com.kafokokab.core.ui.theme.Gold
import com.kafokokab.core.ui.theme.MysticPurple
import com.kafokokab.core.ui.theme.NeonPink
import com.kafokokab.core.ui.theme.SoftWhite

private val persianMonths = listOf(
    "1" to "فروردین", "2" to "اردیبهشت", "3" to "خرداد", "4" to "تیر",
    "5" to "مرداد", "6" to "شهریور", "7" to "مهر", "8" to "آبان",
    "9" to "آذر", "10" to "دی", "11" to "بهمن", "12" to "اسفند"
)

private fun monthLabel(code: String): String =
    persianMonths.firstOrNull { it.first == code }?.second ?: code

private val dayOptions = (1..31).map { it.toString() }
private val yearOptions = (1340..1405).map { it.toString() }.reversed()
private val hourOptions = (0..23).map { it.toString().padStart(2, '0') }
private val minuteOptions = (0..59).map { it.toString().padStart(2, '0') }

private val provinceOptions = listOf(
    "تهران", "خراسان رضوی", "اصفهان", "فارس", "آذربایجان شرقی",
    "البرز", "خوزستان", "قم", "کرمانشاه", "آذربایجان غربی",
    "گیلان", "سیستان و بلوچستان", "همدان", "کرمان", "یزد",
    "اردبیل", "هرمزگان", "مرکزی", "زنجان", "کردستان",
    "قزوین", "لرستان", "گلستان", "مازندران", "بوشهر",
    "خراسان جنوبی", "ایلام", "کهگیلویه و بویراحمد", "چهارمحال و بختیاری",
    "خراسان شمالی", "سمنان"
)

@Composable
fun BirthInfoScreen(
    viewModel: OnboardingViewModel,
    onBack: () -> Unit = {},
    onContinue: () -> Unit = {}
) {
    val profile by viewModel.profile.collectAsState()

    var day by remember(profile.birthDay) { mutableStateOf(profile.birthDay.ifBlank { "1" }) }
    var month by remember(profile.birthMonth) {
        val raw = profile.birthMonth
        val asCode = persianMonths.firstOrNull { it.second == raw }?.first ?: raw.ifBlank { "1" }
        mutableStateOf(asCode)
    }
    var year by remember(profile.birthYear) { mutableStateOf(profile.birthYear.ifBlank { "1370" }) }
    var gender by remember(profile.gender) { mutableStateOf(profile.gender.ifBlank { "زن" }) }
    var hour by remember(profile.birthHour) { mutableStateOf(profile.birthHour.ifBlank { "12" }) }
    var minute by remember(profile.birthMinute) { mutableStateOf(profile.birthMinute.ifBlank { "00" }) }
    var unknownTime by remember(profile.isBirthTimeUnknown) { mutableStateOf(profile.isBirthTimeUnknown) }
    var province by remember(profile.birthProvince) { mutableStateOf(profile.birthProvince.ifBlank { "تهران" }) }
    var city by remember(profile.birthCity) { mutableStateOf(profile.birthCity.ifBlank { "تهران" }) }
    var picker by remember { mutableStateOf<PickerType?>(null) }

    fun persistDate() = viewModel.updateBirthDate(day, month, year)
    fun persistTime() = viewModel.updateBirthTime(hour, minute, unknownTime)
    fun persistLocation() = viewModel.updateBirthLocation("ایران", province, city)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF1A0533), DarkGalaxy, Color(0xFF0A001A))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(bottom = 100.dp)
        ) {
            OnboardingHeader(currentStep = 1, totalSteps = 4, title = "اطلاعات تولد شما", onBack = onBack)
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "روی هر خانه بزن و مقدار را انتخاب کن",
                style = MaterialTheme.typography.bodyMedium,
                color = SoftWhite.copy(alpha = 0.7f),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            SectionCard(title = "تاریخ تولد (شمسی)", icon = Icons.Default.CalendarMonth) {
                Row(Modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    SelectorBox(label = "سال", value = year, modifier = Modifier.weight(1.2f), onClick = { picker = PickerType.Year })
                    SelectorBox(label = "ماه", value = monthLabel(month), modifier = Modifier.weight(1.3f), onClick = { picker = PickerType.Month })
                    SelectorBox(label = "روز", value = day, modifier = Modifier.weight(1f), onClick = { picker = PickerType.Day })
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            SectionCard(title = "جنسیت", icon = Icons.Default.Person) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    GenderChip(text = "زن", selected = gender == "زن", modifier = Modifier.weight(1f), onClick = { gender = "زن"; viewModel.updateGender("زن") })
                    GenderChip(text = "مرد", selected = gender == "مرد", modifier = Modifier.weight(1f), onClick = { gender = "مرد"; viewModel.updateGender("مرد") })
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            SectionCard(title = "زمان تولد", icon = Icons.Default.Schedule) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clickable { unknownTime = !unknownTime; persistTime() }) {
                    Checkbox(checked = unknownTime, onCheckedChange = { unknownTime = it; persistTime() }, colors = CheckboxDefaults.colors(checkedColor = NeonPink, uncheckedColor = SoftWhite.copy(alpha = 0.5f)))
                    Text(text = "ساعت تولدم را نمی‌دانم", style = MaterialTheme.typography.bodyMedium, color = SoftWhite)
                }
                if (!unknownTime) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        SelectorBox(label = "ساعت", value = hour, modifier = Modifier.weight(1f), onClick = { picker = PickerType.Hour })
                        SelectorBox(label = "دقیقه", value = minute, modifier = Modifier.weight(1f), onClick = { picker = PickerType.Minute })
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            SectionCard(title = "محل تولد", icon = Icons.Default.LocationOn) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    SelectorBox(label = "استان", value = province, modifier = Modifier.weight(1f), onClick = { picker = PickerType.Province })
                    SelectorBox(label = "شهر", value = city, modifier = Modifier.weight(1f), onClick = { picker = PickerType.City })
                }
            }
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().background(Color(0xFF0A001A).copy(alpha = 0.92f)).padding(20.dp)) {
            ContinueButton(text = "ادامه", onClick = {
                persistDate(); persistTime(); persistLocation(); viewModel.updateGender(gender); onContinue()
            })
        }
    }

    when (val type = picker) {
        null -> Unit
        else -> {
            val (title, options) = when (type) {
                PickerType.Day -> "انتخاب روز" to dayOptions
                PickerType.Month -> "انتخاب ماه" to persianMonths.map { "${it.first} — ${it.second}" }
                PickerType.Year -> "انتخاب سال" to yearOptions
                PickerType.Hour -> "انتخاب ساعت" to hourOptions
                PickerType.Minute -> "انتخاب دقیقه" to minuteOptions
                PickerType.Province -> "انتخاب استان" to provinceOptions
                PickerType.City -> "انتخاب شهر" to listOf(
                    "تهران", "مشهد", "اصفهان", "شیراز", "تبریز", "کرج", "اهواز", "قم", "کرمانشاه",
                    "ارومیه", "رشت", "زاهدان", "همدان", "کرمان", "یزد", "اردبیل", "بندرعباس",
                    "اراک", "زنجان", "سنندج", "قزوین", "خرم‌آباد", "گرگان", "ساری", "بوشهر",
                    "بیرجند", "ایلام", "یاسوج", "شهرکرد", "بجنورد", "سمنان", "کاشان", "ساوه"
                )
            }
            OptionPickerDialog(title = title, options = options, onDismiss = { picker = null }, onSelect = { selected ->
                when (type) {
                    PickerType.Day -> { day = selected; persistDate() }
                    PickerType.Month -> {
                        month = selected.substringBefore(" ").trim().ifBlank { selected.filter { it.isDigit() } }
                        persistDate()
                    }
                    PickerType.Year -> { year = selected; persistDate() }
                    PickerType.Hour -> { hour = selected; persistTime() }
                    PickerType.Minute -> { minute = selected; persistTime() }
                    PickerType.Province -> { province = selected; persistLocation() }
                    PickerType.City -> {
                        val loc = IranCities.resolve(selected, province)
                        city = loc.name
                        persistLocation()
                    }
                }
                picker = null
            })
        }
    }
}

private enum class PickerType { Day, Month, Year, Hour, Minute, Province, City }

@Composable
private fun OptionPickerDialog(title: String, options: List<String>, onDismiss: () -> Unit, onSelect: (String) -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title, color = SoftWhite, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start) },
        text = {
            LazyColumn(modifier = Modifier.fillMaxWidth().heightIn(max = 360.dp)) {
                items(options) { option ->
                    Text(
                        text = option, color = SoftWhite, style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth().clickable { onSelect(option) }.padding(vertical = 14.dp, horizontal = 8.dp),
                        textAlign = TextAlign.Start
                    )
                }
            }
        },
        confirmButton = { TextButton(onClick = onDismiss) { Text("بستن", color = NeonPink) } },
        containerColor = Color(0xFF1A0A2E),
        titleContentColor = SoftWhite,
        textContentColor = SoftWhite
    )
}

@Composable
fun OnboardingHeader(currentStep: Int, totalSteps: Int, title: String, onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "بازگشت", tint = SoftWhite)
            }
            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                repeat(totalSteps) { index ->
                    val stepNumber = index + 1
                    val isActive = stepNumber <= currentStep
                    Box(
                        modifier = Modifier.size(if (isActive) 28.dp else 22.dp).clip(RoundedCornerShape(50))
                            .background(if (isActive) NeonPink else SoftWhite.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "$stepNumber", color = if (isActive) SoftWhite else SoftWhite.copy(alpha = 0.6f), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    if (index < totalSteps - 1) {
                        Box(modifier = Modifier.width(16.dp).height(2.dp).background(if (stepNumber < currentStep) NeonPink else SoftWhite.copy(alpha = 0.2f)))
                    }
                }
            }
            Spacer(modifier = Modifier.width(48.dp))
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = title, style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold), color = SoftWhite, textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun SectionCard(title: String, icon: ImageVector, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(18.dp)).background(SoftWhite.copy(alpha = 0.06f))
            .border(1.dp, SoftWhite.copy(alpha = 0.12f), RoundedCornerShape(18.dp)).padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Icon(imageVector = icon, contentDescription = null, tint = Gold, modifier = Modifier.size(22.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = title, style = MaterialTheme.typography.titleMedium, color = SoftWhite, fontWeight = FontWeight.SemiBold)
        }
        Spacer(modifier = Modifier.height(14.dp))
        content()
    }
}

@Composable
fun SelectorBox(label: String, value: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(modifier = modifier) {
        Text(text = label, style = MaterialTheme.typography.labelMedium, color = SoftWhite.copy(alpha = 0.65f), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier.fillMaxWidth().height(52.dp).clip(RoundedCornerShape(14.dp))
                .background(SoftWhite.copy(alpha = 0.08f)).border(1.dp, NeonPink.copy(alpha = 0.35f), RoundedCornerShape(14.dp))
                .clickable(onClick = onClick).padding(horizontal = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(text = value, style = MaterialTheme.typography.bodyLarge, color = SoftWhite, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun GenderChip(text: String, selected: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier.height(48.dp).clip(RoundedCornerShape(14.dp))
            .background(if (selected) NeonPink.copy(alpha = 0.3f) else SoftWhite.copy(alpha = 0.07f))
            .border(1.dp, if (selected) NeonPink else SoftWhite.copy(alpha = 0.12f), RoundedCornerShape(14.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = SoftWhite, fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal)
    }
}

@Composable
fun ContinueButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth().height(56.dp).clip(RoundedCornerShape(16.dp))
            .background(brush = Brush.horizontalGradient(colors = listOf(NeonPink, MysticPurple)))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, style = MaterialTheme.typography.titleMedium, color = SoftWhite, fontWeight = FontWeight.Bold)
    }
}
