/*
نام فایل: SignInterpretation.kt
مسیر: core/core-domain/.../model/astrology/
وظیفه: تفسیرهای کوتاه و نمادین برای خورشید، ماه و طلوع
نویسنده: AI Principal Engineer
تاریخ: 2026-08-13

قوانین محتوا:
- لحن آموزشی، نمادین، سرگرمی و خودشناسی
- هیچ ادعای پیش‌بینی قطعی آینده یا تشخیص پزشکی ندارد
- برای استفاده offline و بدون وابستگی به شبکه طراحی شده
*/

package com.kafokokab.core.domain.model.astrology

/**
 * یک تفسیر کوتاه برای یک نقطه مهم چارت.
 */
data class ChartPointInterpretation(
    val title: String,          // مثلاً «خورشید در سرطان»
    val symbol: String,         // نماد برج
    val body: String,           // متن کوتاه آموزشی
    val elementHint: String     // اشاره به عنصر
)

/**
 * ارائه‌دهنده تفسیرهای ثابت و آموزشی برای ۱۲ برج.
 * این کلاس pure domain است و هیچ وابستگی Android ندارد.
 */
object SignInterpretation {

    /**
     * تفسیر خورشید (هویت و جهت اصلی انرژی).
     */
    fun forSun(sign: ZodiacSign): ChartPointInterpretation {
        val body = when (sign) {
            ZodiacSign.ARIES -> "انرژی پیشرو و شروع‌کننده. تمایل به اقدام سریع و جسارت در مسیرهای جدید."
            ZodiacSign.TAURUS -> "پایداری، لذت از زیبایی و امنیت. رشد آهسته اما محکم در ارزش‌های شخصی."
            ZodiacSign.GEMINI -> "کنجکاوی ذهنی و ارتباط. نیاز به تنوع و یادگیری مداوم."
            ZodiacSign.CANCER -> "حساسیت عاطفی و مراقبت. خانه و پیوندهای نزدیک منبع قدرت درونی است."
            ZodiacSign.LEO -> "ابراز خلاق و قلب گرم. نیاز به دیده شدن و خلق چیزی معنادار."
            ZodiacSign.VIRGO -> "دقت، خدمت و بهبود مداوم. قدرت در جزئیات و نظم روزمره."
            ZodiacSign.LIBRA -> "تعادل، زیبایی و رابطه. جست‌وجوی هماهنگی در تصمیم‌ها و محیط."
            ZodiacSign.SCORPIO -> "عمق، تحول و صداقت درونی. توانایی دیدن لایه‌های پنهان."
            ZodiacSign.SAGITTARIUS -> "گسترش افق و معنا. علاقه به سفر، فلسفه و حقیقت بزرگ‌تر."
            ZodiacSign.CAPRICORN -> "مسئولیت، ساختار و هدف بلندمدت. صبر برای ساختن چیزی ماندگار."
            ZodiacSign.AQUARIUS -> "نگاه نو و استقلال فکری. علاقه به جمع و ایده‌های آینده‌نگر."
            ZodiacSign.PISCES -> "تخیل، همدلی و جریان احساسی. اتصال به دنیای نمادین و درونی."
        }
        return ChartPointInterpretation(
            title = "خورشید در ${sign.persianName}",
            symbol = sign.symbol,
            body = body,
            elementHint = "عنصر ${sign.element.persianName}"
        )
    }

    /**
     * تفسیر ماه (نیازهای عاطفی و واکنش‌های درونی).
     */
    fun forMoon(sign: ZodiacSign): ChartPointInterpretation {
        val body = when (sign) {
            ZodiacSign.ARIES -> "واکنش سریع عاطفی. نیاز به فضای حرکت و استقلال در احساس."
            ZodiacSign.TAURUS -> "آرامش از طریق ثبات و لذت‌های حسی. امنیت عاطفی مهم است."
            ZodiacSign.GEMINI -> "احساسات از مسیر ذهن و گفت‌وگو پردازش می‌شوند. نیاز به تنوع ذهنی."
            ZodiacSign.CANCER -> "عمق عاطفی بالا. مراقبت و تعلق، هسته احساس امنیت است."
            ZodiacSign.LEO -> "نیاز به تأیید گرم و ابراز قلب. وفاداری عاطفی قوی."
            ZodiacSign.VIRGO -> "احساسات از راه خدمت و نظم آرام می‌شوند. دقت به نیازهای کوچک."
            ZodiacSign.LIBRA -> "تعادل رابطه‌ای مهم است. زیبایی و هماهنگی حال را بهتر می‌کند."
            ZodiacSign.SCORPIO -> "احساسات عمیق و متمرکز. نیاز به اعتماد واقعی و فضای امن."
            ZodiacSign.SAGITTARIUS -> "آزادی عاطفی و امید. نیاز به معنا و فضای باز برای احساس."
            ZodiacSign.CAPRICORN -> "احساسات با مسئولیت و ساختار آرام می‌گیرند. صبر در ابراز."
            ZodiacSign.AQUARIUS -> "فاصله سالم و دوستی در عاطفه. نیاز به فضای ذهنی آزاد."
            ZodiacSign.PISCES -> "جریان قوی همدلی و خیال. مرزهای احساسی گاهی محو می‌شوند."
        }
        return ChartPointInterpretation(
            title = "ماه در ${sign.persianName}",
            symbol = sign.symbol,
            body = body,
            elementHint = "عنصر ${sign.element.persianName}"
        )
    }

    /**
     * تفسیر طلوع / Ascendant (ماسک اجتماعی و سبک ورود به دنیا).
     */
    fun forRising(sign: ZodiacSign): ChartPointInterpretation {
        val body = when (sign) {
            ZodiacSign.ARIES -> "ورود با انرژی مستقیم و سریع. دیگران اغلب جسارت و حرکت را در شما می‌بینند."
            ZodiacSign.TAURUS -> "حضور آرام و پایدار. ظاهر و محیط فیزیکی برای شما اهمیت دارد."
            ZodiacSign.GEMINI -> "ارتباط آسان و کنجکاو. سبک ورود اغلب از راه حرف و ایده است."
            ZodiacSign.CANCER -> "حفاظت و مراقبت در برخورد اول. فضای امن برای باز شدن لازم است."
            ZodiacSign.LEO -> "حضور گرم و قابل‌توجه. تمایل به ابراز و مرکز توجه ملایم."
            ZodiacSign.VIRGO -> "دقت و مفید بودن در نگاه اول. جزئیات محیط را زود می‌بینید."
            ZodiacSign.LIBRA -> "ظرافت و تعادل اجتماعی. جست‌وجوی هماهنگی در روابط اولیه."
            ZodiacSign.SCORPIO -> "نگاه عمیق و متمرکز. دیگران شدت و رازگونگی را حس می‌کنند."
            ZodiacSign.SAGITTARIUS -> "ورود باز و خوش‌بین. علاقه به افق‌های جدید زود نمایان می‌شود."
            ZodiacSign.CAPRICORN -> "جدی و قابل‌اعتماد در ظاهر. ساختار و هدف‌مندی دیده می‌شود."
            ZodiacSign.AQUARIUS -> "متفاوت و مستقل. سبک ورود اغلب غیرمنتظره یا نوآورانه است."
            ZodiacSign.PISCES -> "نرم و همدل. مرزها در برخورد اول گاهی نامشخص به نظر می‌رسند."
        }
        return ChartPointInterpretation(
            title = "طلوع در ${sign.persianName}",
            symbol = sign.symbol,
            body = body,
            elementHint = "عنصر ${sign.element.persianName}"
        )
    }

    /**
     * ساخت لیست تفسیرهای اصلی چارت (خورشید، ماه، طلوع).
     * اگر نقطه‌ای موجود نباشد، نادیده گرفته می‌شود.
     */
    fun forChart(chart: BirthChart): List<ChartPointInterpretation> {
        val list = mutableListOf<ChartPointInterpretation>()
        chart.sunSign?.let { list.add(forSun(it)) }
        chart.moonSign?.let { list.add(forMoon(it)) }
        chart.risingSign?.let { list.add(forRising(it)) }
        return list
    }
}
