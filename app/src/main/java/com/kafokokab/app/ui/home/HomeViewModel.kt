/*
نام فایل: HomeViewModel.kt
مسیر: app/.../ui/home/
وظیفه: مدیریت داده صفحه اصلی (نام کاربر، خلاصه پروفایل و طالع امروز)
نویسنده: AI Principal Engineer
تاریخ: 2026-08-10
آخرین تغییر: 2026-08-13 - اضافه شدن طالع نمادین روزانه

این ViewModel از ProfileRepository می‌خواند و یک پیام نمادین روزانه
بر اساس روز سال تولید می‌کند (بدون ادعای پیش‌بینی قطعی).
*/

package com.kafokokab.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kafokokab.core.domain.model.UserProfile
import com.kafokokab.core.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar
import javax.inject.Inject

data class HomeUiState(
    val userName: String = "کاربر",
    val hasBirthInfo: Boolean = false,
    val birthSummary: String = "",
    val dailyInsightTitle: String = "طالع امروز",
    val dailyInsight: String = ""
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    profileRepository: ProfileRepository
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = profileRepository.profileFlow
        .map { profile ->
            val insight = buildDailyInsight()
            HomeUiState(
                userName = profile.fullName.ifBlank { "کاربر" },
                hasBirthInfo = profile.hasBasicBirthInfo,
                birthSummary = buildBirthSummary(profile),
                dailyInsightTitle = insight.first,
                dailyInsight = insight.second
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState(
                dailyInsight = buildDailyInsight().second
            )
        )

    private fun buildBirthSummary(profile: UserProfile): String {
        if (!profile.hasBasicBirthInfo) return ""
        return listOfNotNull(
            profile.birthDay.takeIf { it.isNotBlank() },
            profile.birthMonth.takeIf { it.isNotBlank() },
            profile.birthYear.takeIf { it.isNotBlank() }
        ).joinToString("/") + if (profile.birthCity.isNotBlank()) " - ${profile.birthCity}" else ""
    }

    /**
     * تولید یک پیام نمادین و آموزشی بر اساس روز سال.
     * این پیام‌ها جنبه سرگرمی و خودشناسی دارند و ادعای پیش‌بینی قطعی ندارند.
     */
    private fun buildDailyInsight(): Pair<String, String> {
        val dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        val messages = listOf(
            "امروز انرژی ماه در بخش ارتباط است. به حرف‌های قلبت گوش بده و با نرمی حرف بزن.",
            "روز مناسبی برای شروع یک عادت کوچک است. حتی یک قدم کوتاه هم مسیر را تغییر می‌دهد.",
            "سیارات از صبر و مشاهده حمایت می‌کنند. قبل از تصمیم بزرگ، کمی در سکوت بنشین.",
            "انرژی امروز به خلاقیت نزدیک است. یک ایده ساده را روی کاغذ بنویس.",
            "زمان خوبی برای قدردانی از بدن و احساساتت است. به خودت مهربان باش.",
            "امروز می‌توانی مرزهای سالمی تعیین کنی. گفتن «نه» گاهی مهربان‌ترین کار است.",
            "نور درونی‌ات امروز روشن‌تر دیده می‌شود. به شهودت اعتماد کن، حتی اگر منطقی به نظر نرسد.",
            "روز برای جمع‌وجور کردن افکار مناسب است. یک لیست کوتاه از چیزهایی که مهم هستند بنویس.",
            "انرژی آب و احساس غالب است. اگر احساساتت بالا آمد، آن‌ها را انکار نکن؛ فقط مشاهده‌شان کن.",
            "امروز فرصت کوچکی برای بخشش وجود دارد — یا بخشیدن خودت، یا دیگری.",
            "ستاره راهنمایت امروز «تعادل» است. بین کار و استراحت، بین حرف زدن و شنیدن.",
            "یک نشانه کوچک در روزمرگی‌ات پنهان است. چشم‌هایت را باز نگه دار."
        )
        val index = (dayOfYear - 1) % messages.size
        return "طالع امروز" to messages[index]
    }
}
