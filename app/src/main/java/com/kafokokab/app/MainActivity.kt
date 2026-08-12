package com.h2signals.app

import android.os.Bundle
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.material3.LocalTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.h2signals.app.data.database.SignalEntity
import com.h2signals.app.data.database.WatchlistItem
import com.h2signals.app.data.database.TradeEntity
import com.h2signals.app.data.repository.GoldenBasketRegistry
import com.h2signals.app.data.repository.GoldenAsset
import com.h2signals.app.data.api.providers.ApiProvider
import com.h2signals.app.ui.theme.*
import com.h2signals.app.ui.viewmodel.H2signalsViewModel
import com.h2signals.app.utils.PersianDateTimeUtils
import com.h2signals.app.utils.AppSettings
import com.h2signals.app.utils.ExchangeRateCache
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import com.h2signals.app.notifications.SignalNotificationManager
import com.h2signals.app.notifications.SignalAlarmScheduler
import android.Manifest
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import androidx.compose.ui.draw.scale
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.ImeAction
import kotlinx.coroutines.launch


data class CoinVisual(val emoji: String, val color: Color, val name: String, val persianName: String = "")

fun getCoinVisual(symbol: String): CoinVisual {
    return when {
        symbol.contains("BTC") -> CoinVisual("₿", Color(0xFFFF9900), "Bitcoin", "بیت‌کوین")
        symbol.contains("ETH") -> CoinVisual("Ξ", Color(0xFF627EEA), "Ethereum", "اتریوم")
        symbol.contains("SOL") -> CoinVisual("◎", Color(0xFF9945FF), "Solana", "سولانا")
        symbol.contains("BNB") -> CoinVisual("⬡", Color(0xFFF3BA2F), "BNB", "بایننس کوین")
        symbol.contains("USDT") || symbol.contains("USDC") -> CoinVisual("₮", Color(0xFF26A17B), "Stable", "تتر")
        symbol.contains("XRP") -> CoinVisual("✕", Color(0xFF346AA9), "Ripple", "ریپل")
        symbol.contains("ADA") -> CoinVisual("₳", Color(0xFF0033AD), "Cardano", "کاردانو")
        symbol.contains("DOGE") -> CoinVisual("Ð", Color(0xFFC2A633), "Dogecoin", "دوج‌کوین")
        symbol.contains("AVAX") -> CoinVisual("▲", Color(0xFFE84142), "Avalanche", "آوالانچ")
        symbol.contains("MATIC") -> CoinVisual("⬡", Color(0xFF8247E5), "Polygon", "پالیگان")
        symbol.contains("LINK") -> CoinVisual("⬡", Color(0xFF2A5ADA), "Chainlink", "چین‌لینک")
        symbol.contains("LTC") -> CoinVisual("Ł", Color(0xFFBFBFBF), "Litecoin", "لایت‌کوین")
        symbol.contains("TRX") -> CoinVisual("◈", Color(0xFFEB0029), "TRON", "ترون")
        symbol.contains("XLM") -> CoinVisual("✦", Color(0xFF14B6E7), "Stellar", "استلار")
        symbol.contains("RENDER") -> CoinVisual("⬡", Color(0xFF1A1AFF), "Render", "رندر")
        symbol.contains("TIA") -> CoinVisual("◉", Color(0xFF7B2FBE), "Celestia", "سلستیا")
        symbol.contains("APT") -> CoinVisual("◆", Color(0xFF00BFFF), "Aptos", "آپتوس")
        symbol.contains("ARB") -> CoinVisual("◈", Color(0xFF28A0F0), "Arbitrum", "آربیتروم")
        symbol.contains("SUI") -> CoinVisual("◎", Color(0xFF6FBCF0), "Sui", "سویی")
        symbol.contains("INJ") -> CoinVisual("◆", Color(0xFF00C0FF), "Injective", "اینجکتیو")
        symbol.contains("ICP") -> CoinVisual("∞", Color(0xFF29ABE2), "ICP", "اینترنت کامپیوتر")
        symbol.contains("NEAR") -> CoinVisual("◎", Color(0xFF00EC97), "NEAR", "نیر")
        symbol.contains("GOLD") || symbol.contains("XAU") -> CoinVisual("★", Color(0xFFFFD700), "Gold", "طلا")
        symbol.contains("TOMAN") || symbol.contains("IRT") -> CoinVisual("﷼", Color(0xFF4CAF50), "Toman", "تومان")
        symbol.contains("NAT_GAS") -> CoinVisual("⛽", Color(0xFF42A5F5), "Gas", "گاز طبیعی")
        symbol.contains("OIL") || symbol.contains("CRU") -> CoinVisual("🛢", Color(0xFF795548), "Oil", "نفت")
        symbol.contains("COFFEE") -> CoinVisual("☕", Color(0xFF795548), "Coffee", "قهوه")
        symbol.contains("COPPER") -> CoinVisual("⬡", Color(0xFFB87333), "Copper", "مس")
        symbol.contains("COAL") -> CoinVisual("◆", Color(0xFF424242), "Coal", "ذغال‌سنگ")
        symbol.contains("SPX") || symbol.contains("US30") -> CoinVisual("📈", Color(0xFF1565C0), "Index", "شاخص")
        symbol.contains("GBP") -> CoinVisual("£", Color(0xFF1565C0), "GBP", "پوند")
        symbol.contains("EUR") -> CoinVisual("€", Color(0xFF1A237E), "EUR", "یورو")
        symbol.contains("IRON") -> CoinVisual("⬡", Color(0xFF78909C), "Iron", "آهن")
        symbol.contains("LDO") -> CoinVisual("◈", Color(0xFFF09242), "Lido", "لیدو")
        else -> CoinVisual(symbol.take(1), Color(0xFF607D8B), symbol, symbol)
    }
}

fun getOpportunityColor(score: Int): Color {
    return when {
        score >= 75 -> Color(0xFF00FF7B)
        score >= 55 -> Color(0xFFFFD54A)
        score >= 35 -> Color(0xFFFF9800)
        else -> Color(0xFFFF3B4E)
    }
}

// ═════════════════════════════════════════════════════════════════════════
// ═══ LOGIN SCREEN — دقیقاً مثل عکسی که کاربر فرستاد ═══
// ═════════════════════════════════════════════════════════════════════════
private const val H2_APP_PASSWORD = "H2signals29466468"

@Composable
fun LoginScreen(onSuccess: () -> Unit) {
    var passwordInput by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val shakeOffset = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    // انیمیشن درخشش دکمه LOGIN
    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    fun attemptLogin() {
        if (passwordInput == H2_APP_PASSWORD) {
            errorMessage = null
            onSuccess()
        } else {
            errorMessage = "رمز عبور اشتباه است. دوباره تلاش کنید."
            scope.launch {
                shakeOffset.snapTo(0f)
                listOf(-18f, 18f, -14f, 14f, -8f, 8f, 0f).forEach { target ->
                    shakeOffset.animateTo(target, animationSpec = tween(durationMillis = 45))
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // ─── ۱. پس‌زمینه: عکس تریدر + چارت ───
        Image(
            painter = painterResource(id = R.drawable.login_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // گرادیان تیره از بالا و پایین برای خوانایی
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.90f),
                            Color.Black.copy(alpha = 0.30f),
                            Color.Black.copy(alpha = 0.85f)
                        )
                    )
                )
        )

        // ─── ۲. محتوای صفحه ───
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // ─── لوگو H2 SIGNALS ───
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // H2 بزرگ سفید/نقره‌ای با افکت درخشش
                Text(
                    text = "H2",
                    fontSize = 80.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    style = androidx.compose.ui.text.TextStyle(
                        shadow = androidx.compose.ui.graphics.Shadow(
                            color = NeonGreen.copy(alpha = 0.5f),
                            offset = androidx.compose.ui.geometry.Offset(0f, 4f),
                            blurRadius = 24f
                        )
                    )
                )
                // SIGNALS سبز نئونی
                Text(
                    text = "SIGNALS",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeonGreen,
                    letterSpacing = 8.sp,
                    style = androidx.compose.ui.text.TextStyle(
                        shadow = androidx.compose.ui.graphics.Shadow(
                            color = NeonGreen.copy(alpha = 0.8f),
                            offset = androidx.compose.ui.geometry.Offset(0f, 0f),
                            blurRadius = 20f
                        )
                    )
                )
            }

            Spacer(modifier = Modifier.height(80.dp))

            // ─── باکس شیشه‌ای ورود ───
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF0D1F0D).copy(alpha = 0.88f),
                                Color(0xFF0A150A).copy(alpha = 0.95f)
                            )
                        )
                    )
                    .border(
                        width = 1.5.dp,
                        brush = Brush.verticalGradient(
                            listOf(
                                NeonGreen.copy(alpha = 0.6f),
                                NeonGreen.copy(alpha = 0.2f)
                            )
                        ),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(24.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // عنوان WELCOME BACK
                    Text(
                        text = "WELCOME BACK",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = 4.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Please sign in to continue",
                        fontSize = 12.sp,
                        color = MutedSteel
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // ─── فیلد پسورد ───
                    val fieldBorderColor = if (errorMessage != null) NeonRed else NeonGreen.copy(alpha = 0.5f)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(x = shakeOffset.value.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(ObsidianDark)
                            .border(1.5.dp, fieldBorderColor, RoundedCornerShape(16.dp))
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Lock,
                            contentDescription = null,
                            tint = NeonGreen,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        BasicTextField(
                            value = passwordInput,
                            onValueChange = {
                                passwordInput = it
                                errorMessage = null
                            },
                            singleLine = true,
                            visualTransformation = if (passwordVisible)
                                VisualTransformation.None
                            else
                                PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                            ),
                            textStyle = LocalTextStyle.current.copy(
                                color = IceWhite,
                                fontSize = 15.sp,
                                textAlign = TextAlign.End
                            ),
                            cursorBrush = SolidColor(NeonGreen),
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 14.dp),
                            decorationBox = { innerTextField ->
                                Box {
                                    if (passwordInput.isEmpty()) {
                                        Text(
                                            "Enter your password",
                                            color = MutedSteel.copy(alpha = 0.7f),
                                            fontSize = 14.sp
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        )
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = if (passwordVisible) "مخفی کردن" else "نمایش",
                                tint = MutedSteel,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    if (errorMessage != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = errorMessage ?: "",
                            color = NeonRed,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // ─── دکمه LOGIN سبز نئونی ───
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        NeonGreen.copy(alpha = glowAlpha),
                                        Color(0xFF00CC66).copy(alpha = glowAlpha)
                                    )
                                )
                            )
                            .border(
                                width = 1.dp,
                                color = NeonGreen.copy(alpha = 0.6f),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clickable { attemptLogin() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "LOGIN",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.Black,
                            letterSpacing = 3.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

class MainActivity : ComponentActivity() {
    private val viewModel: H2signalsViewModel by viewModels()

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.getStringExtra("open_symbol")?.let { symbol ->
            viewModel.selectSymbol(symbol)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppSettings.init(this)
        com.h2signals.app.utils.ExchangeRateCache.startAutoRefresh()
        SignalNotificationManager.ensureChannel(this)
        SignalAlarmScheduler.scheduleRepeatingReminder(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        intent?.getStringExtra("open_symbol")?.let { symbol ->
            viewModel.selectSymbol(symbol)
        }

        enableEdgeToEdge()
        setContent {
            H2SignalTheme {
                val userFontSize by AppSettings.fontSizeScale
                val isBold by AppSettings.fontBold
                val fontScaleFactor = (userFontSize / 12f).coerceIn(0.75f, 1.6f)
                val baseDensity = LocalDensity.current
                val scaledDensity = Density(baseDensity.density, baseDensity.fontScale * fontScaleFactor)

                val boldTypography = if (isBold) {
                    val base = MaterialTheme.typography
                    base.copy(
                        displayLarge = base.displayLarge.copy(fontWeight = FontWeight.Black),
                        displayMedium = base.displayMedium.copy(fontWeight = FontWeight.Black),
                        displaySmall = base.displaySmall.copy(fontWeight = FontWeight.Black),
                        headlineLarge = base.headlineLarge.copy(fontWeight = FontWeight.ExtraBold),
                        headlineMedium = base.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
                        headlineSmall = base.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
                        titleLarge = base.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                        titleMedium = base.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                        titleSmall = base.titleSmall.copy(fontWeight = FontWeight.Bold),
                        bodyLarge = base.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        bodyMedium = base.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        bodySmall = base.bodySmall.copy(fontWeight = FontWeight.Bold),
                        labelLarge = base.labelLarge.copy(fontWeight = FontWeight.Bold),
                        labelMedium = base.labelMedium.copy(fontWeight = FontWeight.Bold),
                        labelSmall = base.labelSmall.copy(fontWeight = FontWeight.Bold)
                    )
                } else {
                    MaterialTheme.typography
                }

                CompositionLocalProvider(
                    LocalLayoutDirection provides LayoutDirection.Rtl,
                    LocalDensity provides scaledDensity,
                    LocalTextStyle provides boldTypography.bodyMedium
                ) {
                    var isAuthenticated by remember { mutableStateOf(false) }
                    if (!isAuthenticated) {
                        LoginScreen(onSuccess = { isAuthenticated = true })
                    } else {
                        var activeTab by remember { mutableStateOf(0) }
                        Scaffold(
                            modifier = Modifier.fillMaxSize().testTag("main_scaffold"),
                            containerColor = ObsidianDark,
                            bottomBar = {
                                H2BottomNavBar(
                                    activeTab = activeTab,
                                    onTabSelected = { activeTab = it }
                                )
                            }
                        ) { innerPadding ->
                            DashboardScreen(
                                viewModel = viewModel,
                                activeTab = activeTab,
                                onTabChanged = { activeTab = it },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun DashboardScreen(
    viewModel: H2signalsViewModel,
    activeTab: Int,
    onTabChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedCoin by viewModel.selectedSymbol.collectAsState()
    val watchlist by viewModel.watchlist.collectAsState()
    val activeSignal by viewModel.activeSignalForSelectedSymbol.collectAsState()
    val activeSignals by viewModel.activeSignals.collectAsState()
    val backtestResult by viewModel.backtestResultForSelectedSymbol.collectAsState()
    val allBacktestResults by viewModel.backtestResults.collectAsState()
    val providerHealthList by viewModel.providerHealthList.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val allTrades by viewModel.allTrades.collectAsState()
    val allSignalsHistory by viewModel.allSignals.collectAsState()
    val weights by viewModel.engineWeights.collectAsState()
    val activeSessions by viewModel.activeSessionsCount.collectAsState()
    val goldenBasketSuperSignals by viewModel.goldenBasketSuperSignals.collectAsState()

    var tehranNow by remember { mutableStateOf(PersianDateTimeUtils.getTehranNow()) }

    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(1000)
            tehranNow = PersianDateTimeUtils.getTehranNow()
        }
    }

    val dashboardScrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    // هر بار که کاربر تب پایین صفحه را عوض می‌کند، اسکرول به بالای همان محتوای تب جدید
    // برمی‌گردد؛ در غیر این صورت کاربر گمان می‌کند کارت بالای صفحه (WAIL AI) هنوز
    // بخشی از تب تنظیمات یا بکتست است، چون موقعیت اسکرول قبلی حفظ می‌شود.
    LaunchedEffect(activeTab) {
        dashboardScrollState.animateScrollTo(0)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(dashboardScrollState)
            .background(ObsidianDark)
            .padding(16.dp)
    ) {
        // --- REAL-TIME TEHRAN CLOCK & DATE COMPACT INLINE HEADER ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .testTag("tehran_clock_card"),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Right: Brand & active indicators
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Glassmorphism Icon
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.05f))
                        .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.OfflineBolt,
                        contentDescription = null,
                        tint = NeonCyan,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Column {
                    // نام برند "H2 Signals" همیشه باید چپ‌به‌راست (LTR) نمایش داده شود،
                    // چون یک اسم انگلیسی ثابت است. قبلاً چون کل صفحه RTL بود، ترتیب
                    // نمایش این دو کلمه معکوس می‌شد و «Signals H2» دیده می‌شد.
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "H2",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    fontSize = 22.sp,
                                    letterSpacing = 1.sp
                                ),
                                color = IceWhite
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Signals",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    fontSize = 22.sp,
                                    letterSpacing = 1.sp
                                ),
                                color = NeonGreen
                            )
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(5.dp)
                                .clip(RoundedCornerShape(2.5.dp))
                                .background(NeonGreen)
                        )
                        Text(
                            text = "سیستم فعال • نهنگ: ${PersianDateTimeUtils.formatLong(activeSessions.toLong())}",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                            color = MutedSteel
                        )
                    }
                }
            }

            // Left: Real-time Tehran clock & Shamsi Date
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = PersianDateTimeUtils.formatTehranTime(tehranNow),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        fontFamily = FontFamily.Monospace
                    ),
                    color = IceWhite
                )
                Text(
                    text = PersianDateTimeUtils.getPersianDateString(tehranNow),
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 15.sp, fontWeight = FontWeight.Medium),
                    color = IceWhite
                )
            }
        }

        // --- محتوای اختصاصی هر تب پایین صفحه ---
        // نکته‌ی مهم معماری: کارت WAIL AI، بنر سیگنال، و ردیف کوین‌ها فقط در تب
        // "سیگنال‌ها/تاریخچه" (activeTab == 0) نمایش داده می‌شوند. قبلاً این المان‌ها
        // برای همه‌ی تب‌ها (تنظیمات، بکتست، معاملات، سلامت) هم نمایش داده می‌شدند چون
        // بخشی از همان Column اسکرول‌شونده‌ی مشترک بودند؛ در نتیجه با رفتن به تب تنظیمات
        // کاربر هنوز کارت سیگنال را بالای صفحه می‌دید و گمان می‌کرد بخشی از تنظیمات است.
        when (activeTab) {
            0 -> {
                // --- ACTIVE SIGNAL BANNER STRIP ---
                val bannerColor = when (activeSignal?.direction) {
                    "BUY" -> NeonGreen
                    "SELL" -> NeonRed
                    "EXIT" -> Color(0xFFFF6D00)
                    "HOLDING" -> NeonCyan
                    else -> BrightGold
                }
                val bannerText = when (activeSignal?.direction) {
                    "BUY" -> "🟢  سیگنال خرید فعال"
                    "SELL" -> "🔴  سیگنال فروش فعال"
                    "EXIT" -> "🟠  پیشنهاد خروج از پوزیشن"
                    "HOLDING" -> "🔷  پوزیشن شما باز است"
                    else -> "⏳  در انتظار سیگنال"
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(bannerColor.copy(alpha = 0.12f))
                        .border(1.dp, bannerColor.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = bannerText, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = bannerColor)
                    if (activeSignal != null) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(bannerColor.copy(alpha = 0.25f))
                                    .border(1.dp, bannerColor.copy(alpha = 0.6f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = selectedCoin.take(1), fontSize = 13.sp, fontWeight = FontWeight.Black, color = IceWhite)
                            }
                            Text(text = selectedCoin, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = IceWhite, fontFamily = FontFamily.Monospace)
                        }
                    }
                }

                // --- PRIMARY AI DECISION CARD ---
                AITradeDecisionCard(
                    symbol = selectedCoin,
                    signal = activeSignal,
                    watchlist = watchlist,
                    isRefreshing = isRefreshing,
                    backtestResults = allBacktestResults,
                    weights = weights,
                    onMockTrade = { dir, entry ->
                        val lastAtr = entry * 0.02
                        val sl = if (dir == "BUY") entry - (lastAtr * 1.8) else entry + (lastAtr * 1.8)
                        val tp1 = if (dir == "BUY") entry + (lastAtr * 2.0) else entry - (lastAtr * 2.0)
                        viewModel.executeTrade(dir, entry, sl, tp1)
                    },
                    onCloseAllTrades = { viewModel.closeAllTrades() },
                    onManualScan = { viewModel.refreshData() },
                    onBuy = { symbol, tomanPrice -> registerManualBuyInToman(viewModel, symbol, tomanPrice) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // --- باکس اختصاصی «سبد طلایی» — ۸ دارایی استراتژیک، موتور جدا ---
                GoldenBasketBox(
                    watchlist = watchlist,
                    superSignals = goldenBasketSuperSignals,
                    allTrades = allTrades,
                    onSelectCoin = { sym ->
                        viewModel.selectSymbol(sym)
                        coroutineScope.launch { dashboardScrollState.animateScrollTo(0) }
                    },
                    onBuy = { symbol, tomanPrice -> registerManualBuyInToman(viewModel, symbol, tomanPrice) },
                    onExecuteTrade = { symbol ->
                        viewModel.selectSymbol(symbol)
                        onTabChanged(1)
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // --- تاریخچه‌ی سیگنال‌ها ادغام‌شده با کارت‌های کوین ---
                // اولویت نمایش نمادها: آخرین سیگنال BUY/SELL/EXIT بالای لیست (نه صرفاً WAIT)
                SignalHistoryTab(
                    allSignals = allSignalsHistory,
                    watchlist = watchlist,
                    onSelectCoin = { sym ->
                        viewModel.selectSymbol(sym)
                        coroutineScope.launch { dashboardScrollState.animateScrollTo(0) }
                    }
                )
            }
            1 -> TradeHistoryTab(
                allTrades = allTrades,
                watchlist = watchlist,
                backtestResults = allBacktestResults,
                onWipe = { viewModel.clearDatabase() },
                onRegisterTrade = { symbol, tomanPrice -> registerManualBuyInToman(viewModel, symbol, tomanPrice) },
                onDeleteTrade = { tradeId -> viewModel.deleteTrade(tradeId) },
                onSell = { symbol ->
                    viewModel.selectSymbol(symbol)
                    onTabChanged(0)
                }
            )
            2 -> MultiProviderHealthTab(
                providers = providerHealthList,
                goldenBasketActive = goldenBasketSuperSignals.isNotEmpty(),
                whaleEngineActive = activeSignals.isNotEmpty(),
                newsParserOnline = com.h2signals.app.data.repository.NewsSentimentEngine.lastFetchSucceeded
            )
            3 -> SettingsTab()
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}
// کاربر همیشه قیمت خرید واقعی خود را به تومان وارد می‌کند (چون صرافی‌های ایرانی
// قیمت را به تومان نشان می‌دهند)، اما دیتابیس داخلی اپ و PositionTracker بر مبنای
// همان واحدی کار می‌کنند که در سراسر اپ برای livePrice/formatPriceText استفاده شده
// (دلار). بنابراین قبل از ثبت، با نرخ زنده‌ی ExchangeRateCache به دلار تبدیل می‌شود
// تا محاسبه‌ی حد ضرر/سود و مقایسه با قیمت لحظه‌ای همچنان درست بماند.
fun registerManualBuyInToman(viewModel: H2signalsViewModel, symbol: String, tomanPrice: Double) {
    val usdToTomanRate = com.h2signals.app.utils.ExchangeRateCache.usdToToman.value
    if (usdToTomanRate <= 0.0 || tomanPrice <= 0.0) return
    val entryPriceUsd = tomanPrice / usdToTomanRate
    val lastAtr = entryPriceUsd * 0.02
    val sl = entryPriceUsd - (lastAtr * 1.8)
    val tp1 = entryPriceUsd + (lastAtr * 2.0)
    viewModel.selectSymbol(symbol)
    viewModel.executeTrade("BUY", entryPriceUsd, sl, tp1)
}

@Composable
fun CoinSelectorCard(
    item: WatchlistItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    signal: SignalEntity? = null
) {
    val visual = getCoinVisual(item.symbol)
    // طبق درخواست کاربر: رنگ فرصت (Opportunity Color) دیگر بر اساس opportunityScore
    // (که صرفاً یک امتیاز عمومی نوسان/حجم است و ربط مستقیمی به اطمینان موتور سیگنال
    // ندارد) محاسبه نمی‌شود، بلکه بر اساس confidenceScore همان سیگنال فعلی این نماد
    // است — عددی که واقعاً نشان می‌دهد موتور تحلیلی چقدر به تصمیمش مطمئن است. اگر
    // سیگنالی هنوز برای این نماد صادر نشده، به opportunityScore به‌عنوان جایگزین
    // بازمی‌گردیم.
    val effectiveScore = signal?.confidenceScore ?: item.opportunityScore
    val oppColor = getOpportunityColor(effectiveScore)
    val activeColor = if (isSelected) NeonCyan else oppColor
    val bgGradient = Brush.verticalGradient(
        listOf(CarbonGray, activeColor.copy(alpha = if (isSelected) 0.12f else 0.06f))
    )

    Box(
        modifier = Modifier
            .width(130.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(bgGradient)
            .border(1.5.dp, if (isSelected) NeonCyan else Color.White.copy(alpha = 0.06f), RoundedCornerShape(18.dp))
            .clickable { onClick() }
            .padding(12.dp)
            .testTag("coin_selector_${item.symbol}")
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Big coin icon
            // نقطه اولویت بالای کارت
            Box(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(oppColor).align(Alignment.TopEnd))
            }
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Brush.radialGradient(listOf(visual.color.copy(alpha = 0.3f), ObsidianDark)))
                    .border(2.dp, visual.color.copy(alpha = if (isSelected) 0.9f else 0.5f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = visual.emoji, fontSize = 22.sp, fontWeight = FontWeight.Black, color = visual.color)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.symbol,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                color = IceWhite,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = formatPriceText(item.symbol, item.livePrice),
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = MutedSteel,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            val changeColor = if (item.change24h >= 0) NeonGreen else NeonRed
            Text(
                text = "${if (item.change24h >= 0) "+" else ""}${PersianDateTimeUtils.formatDouble(item.change24h, 1)}%",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = changeColor,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(6.dp))
            LinearProgressIndicator(
                progress = effectiveScore / 100f,
                modifier = Modifier.fillMaxWidth().height(3.dp).clip(RoundedCornerShape(2.dp)),
                color = if (isSelected) NeonCyan else MutedSteel.copy(alpha = 0.5f),
                trackColor = Color.White.copy(alpha = 0.05f)
            )
        }
    }
}
@Composable
fun AITradeDecisionCard(
    symbol: String,
    signal: SignalEntity?,
    watchlist: List<WatchlistItem>,
    isRefreshing: Boolean,
    backtestResults: List<com.h2signals.app.data.database.BacktestResultEntity> = emptyList(),
    weights: List<com.h2signals.app.data.database.EngineWeightEntity> = emptyList(),
    onMockTrade: (String, Double) -> Unit,
    onCloseAllTrades: () -> Unit,
    onManualScan: () -> Unit,
    onBuy: (String, Double) -> Unit = { _, _ -> }
) {
    // نگه‌داری اینکه کدام اصطلاح فنی الان توضیحش باز است (فقط یکی در هر لحظه)؛
    // مقدار null یعنی هیچ Tooltip ای باز نیست.
    var activeTooltip by remember { mutableStateOf<String?>(null) }
    // سه پاپ‌آپ مستقل کارت بزرگ اصلی: معرفی کوین (کلیک روی دایره‌ی آیکون)،
    // بک‌تست واقعی (دکمه‌ی بک‌تست)، و فرم خرید (دکمه‌ی خرید) — کاملاً از هم جدا.
    var showCoinInfoDialog by remember { mutableStateOf(false) }
    var showBacktestDialog by remember { mutableStateOf(false) }
    var showBuyDialog by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "borderGlow")
    val alphaGlow by infiniteTransition.animateFloat(
        initialValue = 0.4f, targetValue = 1.0f,
        animationSpec = infiniteRepeatable(animation = tween(1200, easing = LinearEasing), repeatMode = RepeatMode.Reverse),
        label = "alphaGlow"
    )

    val decisionColor = when (signal?.direction) {
        "BUY" -> NeonGreen
        "SELL" -> NeonRed
        "EXIT" -> Color(0xFFFF6D00) // نارنجی مشخص - جدا از سبز/قرمز/طلایی برای اعلام واضح خروج از پوزیشن
        "HOLDING" -> NeonCyan // آبی روشن مشخص - برای تمایز از سیگنال فعال BUY (که یعنی "اکنون بخر")
        else -> BrightGold
    }

    val currentItem = watchlist.firstOrNull { it.symbol == symbol }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(CarbonGray)
            .border(1.5.dp, decisionColor.copy(alpha = alphaGlow), RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        Column {
            // Header row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(modifier = Modifier.size(36.dp), contentAlignment = Alignment.Center) {
                        WhaleRadarAnimation(modifier = Modifier.fillMaxSize(), color = decisionColor)
                        Text(text = "🐋", fontSize = 15.sp, modifier = Modifier.align(Alignment.Center))
                    }
                    Text(
                        text = "تصمیم معاملاتی هوشمند WAIL AI",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                        color = NeonGreen
                    )
                }
                if (signal?.wailVerified == true) {
                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(NeonCyan.copy(alpha = 0.15f)).border(1.dp, NeonCyan, RoundedCornerShape(6.dp)).padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(text = "WAIL VERIFIED", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = NeonCyan, letterSpacing = 1.sp)
                    }
                }
            }

            Text(text = "رژیم بازار: ${signal?.marketRegime ?: "RANGE"}", fontSize = 12.sp, color = MutedSteel, modifier = Modifier.padding(top = 4.dp, start = 44.dp))

            Spacer(modifier = Modifier.height(16.dp))
            // Main signal row - single unified layout (no duplicate coin info)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left: BIG direction signal
                Column {
                    val bigLabel = when (signal?.direction) {
                        "HOLDING" -> "در حال نگهداری"
                        null -> "WAIT"
                        else -> signal.direction
                    }
                    Text(
                        text = bigLabel,
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Monospace,
                            fontSize = if (bigLabel == "در حال نگهداری") 26.sp else 46.sp
                        ),
                        color = decisionColor
                    )
                    Text(text = "ضریب اطمینان: ${PersianDateTimeUtils.formatLong((signal?.confidenceScore ?: 50).toLong())}%", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = decisionColor)
                    Spacer(modifier = Modifier.height(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                when (signal?.riskLevel) { "LOW" -> NeonGreen.copy(alpha = 0.15f); "MEDIUM" -> BrightGold.copy(alpha = 0.15f); else -> NeonRed.copy(alpha = 0.15f) }
                            )
                            .border(1.dp,
                                when (signal?.riskLevel) { "LOW" -> NeonGreen; "MEDIUM" -> BrightGold; else -> NeonRed },
                                RoundedCornerShape(8.dp)
                            )
                            .clickable(onClick = { activeTooltip = if (activeTooltip == "risk") null else "risk" })
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "ℹ️ ریسک: ${signal?.riskLevel ?: "LOW"}",
                            fontSize = 13.sp, fontWeight = FontWeight.Bold,
                            color = when (signal?.riskLevel) { "LOW" -> NeonGreen; "MEDIUM" -> BrightGold; else -> NeonRed }
                        )
                    }
                    if (activeTooltip == "risk") {
                        TooltipExplanationBox(termKey = "risk", onDismiss = { activeTooltip = null })
                    }
                }

                // Right: coin icon + price (single, clean)
                val signalVisual = getCoinVisual(symbol)
                Column(horizontalAlignment = Alignment.End) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(Brush.radialGradient(listOf(signalVisual.color.copy(alpha = 0.35f), ObsidianDark)))
                            .border(2.dp, signalVisual.color.copy(alpha = 0.8f), CircleShape)
                            .clickable { showCoinInfoDialog = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = signalVisual.emoji, fontSize = 26.sp, fontWeight = FontWeight.Black, color = signalVisual.color)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = symbol, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MutedSteel, fontFamily = FontFamily.Monospace)
                    if (signalVisual.persianName.isNotBlank()) {
                        Text(text = signalVisual.persianName, fontSize = 12.sp, color = MutedSteel.copy(alpha = 0.8f))
                    }
                    Text(text = formatPriceText(symbol, signal?.price ?: currentItem?.livePrice ?: 0.0), fontSize = 15.sp, fontWeight = FontWeight.Bold, color = IceWhite, fontFamily = FontFamily.Monospace)
                    val change = currentItem?.change24h ?: 0.0
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                        Box(modifier = Modifier.size(5.dp).clip(CircleShape).background(if (change >= 0) NeonGreen else NeonRed))
                        Text(text = "${if (change >= 0) "+" else ""}${PersianDateTimeUtils.formatDouble(change, 1)}%", fontSize = 12.sp, color = if (change >= 0) NeonGreen else NeonRed)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            // TP/SL grid or WAIT message
            if (signal != null && signal.direction != "WAIT") {
                Row(
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(ObsidianDark).padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Column {
                            Text(text = "ورود (Entry)", fontSize = 12.sp, color = MutedSteel)
                            Text(text = formatPriceText(symbol, signal.price), fontSize = 15.sp, fontWeight = FontWeight.Bold, color = IceWhite, fontFamily = FontFamily.Monospace)
                        }
                        Column(
                            modifier = Modifier
                                .clickable(onClick = { activeTooltip = if (activeTooltip == "stop_loss") null else "stop_loss" })
                                .padding(vertical = 2.dp)
                        ) {
                            Text(text = "ℹ️ حد ضرر (Stop Loss)", fontSize = 12.sp, color = MutedSteel)
                            Text(text = formatPriceText(symbol, signal.stopLoss), fontSize = 15.sp, fontWeight = FontWeight.Bold, color = NeonRed, fontFamily = FontFamily.Monospace)
                        }
                    }
                    Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Column(
                            horizontalAlignment = Alignment.End,
                            modifier = Modifier
                                .clickable(onClick = { activeTooltip = if (activeTooltip == "take_profit_1") null else "take_profit_1" })
                                .padding(vertical = 2.dp)
                        ) {
                            Text(text = "ℹ️ هدف سود ۱ (TP 1)", fontSize = 12.sp, color = MutedSteel)
                            Text(text = formatPriceText(symbol, signal.takeProfit1), fontSize = 15.sp, fontWeight = FontWeight.Bold, color = NeonGreen, fontFamily = FontFamily.Monospace)
                        }
                        Column(
                            horizontalAlignment = Alignment.End,
                            modifier = Modifier
                                .clickable(onClick = { activeTooltip = if (activeTooltip == "take_profit_2") null else "take_profit_2" })
                                .padding(vertical = 2.dp)
                        ) {
                            Text(text = "ℹ️ هدف سود ۲ (TP 2)", fontSize = 12.sp, color = MutedSteel)
                            Text(text = formatPriceText(symbol, signal.takeProfit2), fontSize = 15.sp, fontWeight = FontWeight.Bold, color = NeonGreen, fontFamily = FontFamily.Monospace)
                        }
                    }
                }
                if (activeTooltip == "stop_loss" || activeTooltip == "take_profit_1" || activeTooltip == "take_profit_2") {
                    TooltipExplanationBox(termKey = activeTooltip!!, onDismiss = { activeTooltip = null })
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(ObsidianDark).padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "پیشنهاد صبوری و رصد نوسانات (وضعیت انتظار WAIT). در این حالت معامله نکنید.",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 13.sp),
                        color = BrightGold,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Agreement + reasons
            if (signal != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(NeonCyan.copy(alpha = 0.08f))
                        .border(1.dp, NeonCyan.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
                        .clickable(onClick = { activeTooltip = if (activeTooltip == "agreement") null else "agreement" })
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "ℹ️ همگرایی و همسویی تحلیل (Wale AI Agreement):", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = NeonCyan, modifier = Modifier.weight(1f))
                    Text(text = "${PersianDateTimeUtils.formatLong(signal.agreement.toLong())}%", fontSize = 14.sp, fontWeight = FontWeight.Black, color = NeonCyan, fontFamily = FontFamily.Monospace)
                }
                if (activeTooltip == "agreement") {
                    TooltipExplanationBox(termKey = "agreement", onDismiss = { activeTooltip = null })
                }

                if (signal.reasons.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(ObsidianDark).padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "ℹ️ دلایل تحلیل هوشمند:",
                            fontSize = 13.sp, fontWeight = FontWeight.Bold, color = NeonCyan,
                            modifier = Modifier
                                .clickable(onClick = { activeTooltip = if (activeTooltip == "smart_reasons") null else "smart_reasons" })
                                .padding(vertical = 4.dp)
                        )
                        signal.reasons.split(",").forEachIndexed { reasonIndex, reason ->
                            if (reason.isNotBlank()) {
                                val trimmedReason = reason.trim()
                                val reasonTooltipKey = findReasonTooltipKey(trimmedReason)
                                val reasonTooltipId = "reason_$reasonIndex"
                                Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(14.dp).padding(top = 1.dp))
                                    Text(text = trimmedReason, fontSize = 12.sp, color = IceWhite, lineHeight = 14.sp, modifier = Modifier.weight(1f))
                                    if (reasonTooltipKey != null) {
                                        Icon(
                                            imageVector = Icons.Outlined.Info,
                                            contentDescription = "توضیح",
                                            tint = MutedSteel,
                                            modifier = Modifier
                                                .size(13.dp)
                                                .clickable { activeTooltip = if (activeTooltip == reasonTooltipId) null else reasonTooltipId }
                                        )
                                    }
                                }
                                if (activeTooltip == reasonTooltipId && reasonTooltipKey != null) {
                                    TooltipExplanationBox(termKey = reasonTooltipKey, onDismiss = { activeTooltip = null })
                                }
                            }
                        }
                        if (activeTooltip == "smart_reasons") {
                            TooltipExplanationBox(termKey = "smart_reasons", onDismiss = { activeTooltip = null })
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // چهار دکمه‌ی گرد و هم‌اندازه، کنار هم: بروزرسانی سیگنال، بک‌تست واقعی،
            // خرید دستی (با ثبت قیمت به تومان)، و بستن همه‌ی معاملات باز.
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Top
            ) {
                RoundActionButton(
                    icon = Icons.Default.Refresh,
                    label = "بروزرسانی",
                    circleColor = NeonGreen,
                    isLoading = isRefreshing,
                    onClick = onManualScan,
                    testTag = "wail_ai_signal_btn"
                )
                RoundActionButton(
                    icon = Icons.Default.AutoGraph,
                    label = "بک‌تست",
                    circleColor = NeonBlue,
                    onClick = { showBacktestDialog = true }
                )
                RoundActionButton(
                    icon = Icons.Default.AddShoppingCart,
                    label = "خرید",
                    circleColor = BrightGold,
                    onClick = { showBuyDialog = true }
                )
                RoundActionButton(
                    icon = Icons.Default.Cancel,
                    label = "بستن معاملات",
                    circleColor = NeonRed,
                    onClick = onCloseAllTrades
                )
            }

            if (showCoinInfoDialog) {
                CoinInfoDialog(symbol = symbol, onDismiss = { showCoinInfoDialog = false })
            }
            if (showBacktestDialog) {
                CoinBacktestInfoDialog(
                    symbol = symbol,
                    backtest = backtestResults.firstOrNull { it.symbol == symbol },
                    weights = weights,
                    onDismiss = { showBacktestDialog = false }
                )
            }
            if (showBuyDialog) {
                CoinBuyDialog(
                    symbol = symbol,
                    watchItem = currentItem,
                    onDismiss = { showBuyDialog = false },
                    onBuy = onBuy
                )
            }

            // ساختار بازار اسمارت مانی - ادغام شده داخل همین کارت
            Spacer(modifier = Modifier.height(18.dp))
            // نوار جداکننده‌ی نئونی سبز شیشه‌ای به‌جای خط ساده‌ی قبلی
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                NeonGreen.copy(alpha = 0f),
                                NeonGreen.copy(alpha = 0.7f),
                                NeonGreen.copy(alpha = 0f)
                            )
                        )
                    )
            )
            Spacer(modifier = Modifier.height(18.dp))
            SMCTechnicalsContent(symbol = symbol, signal = signal)
        }
    }
}

@Composable
fun RoundActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    circleColor: Color,
    isLoading: Boolean = false,
    onClick: () -> Unit,
    testTag: String? = null
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(58.dp)
                // درخشش نئونی بیرونی - یک هاله‌ی محو پشت دایره‌ی شیشه‌ای
                .shadow(elevation = 14.dp, shape = CircleShape, ambientColor = circleColor, spotColor = circleColor)
                .clip(CircleShape)
                // جلوه‌ی شیشه‌ای: گرادیان مورب از رنگ روشن به تیره به‌علاوه‌ی یک لایه‌ی
                // نیمه‌شفاف سفید در بالا برای شبیه‌سازی انعکاس نور روی شیشه
                .background(
                    Brush.verticalGradient(
                        listOf(
                            circleColor.copy(alpha = 0.32f),
                            circleColor.copy(alpha = 0.10f)
                        )
                    )
                )
                .border(1.5.dp, circleColor.copy(alpha = 0.85f), CircleShape)
                .clickable { onClick() }
                .then(if (testTag != null) Modifier.testTag(testTag) else Modifier),
            contentAlignment = Alignment.Center
        ) {
            // لایه‌ی انعکاس شیشه‌ای بالای دایره
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 4.dp, start = 10.dp, end = 10.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.White.copy(alpha = 0.14f), Color.Transparent),
                            endY = 40f
                        )
                    )
            )
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = circleColor, strokeWidth = 2.dp)
            } else {
                Icon(imageVector = icon, contentDescription = label, tint = circleColor, modifier = Modifier.size(23.dp))
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = label, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = circleColor, textAlign = TextAlign.Center)
    }
}
@Composable
fun WhaleRadarAnimation(
    modifier: Modifier = Modifier,
    color: Color = NeonGreen
) {
    val transition = rememberInfiniteTransition(label = "RadarSweep")
    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )
    val pulses = listOf(
        transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "pulse1"
        ),
        transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, delayMillis = 1000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "pulse2"
        )
    )

    Canvas(modifier = modifier) {
        val center = androidx.compose.ui.geometry.Offset(size.width / 2, size.height / 2)
        val radius = size.minDimension / 2

        // Draw pulses
        pulses.forEach { pulse ->
            drawCircle(
                color = color,
                radius = radius * pulse.value,
                center = center,
                style = Stroke(width = 1.5.dp.toPx()),
                alpha = (1f - pulse.value) * 0.3f
            )
        }

        // Draw static grid circles
        drawCircle(
            color = color,
            radius = radius * 0.5f,
            center = center,
            style = Stroke(width = 0.5.dp.toPx(), pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(floatArrayOf(4f, 4f), 0f)),
            alpha = 0.15f
        )
        drawCircle(
            color = color,
            radius = radius,
            center = center,
            style = Stroke(width = 1.dp.toPx()),
            alpha = 0.25f
        )

        // Draw sweep line
        val angleRad = Math.toRadians(rotation.toDouble())
        val targetX = center.x + radius * Math.cos(angleRad).toFloat()
        val targetY = center.y + radius * Math.sin(angleRad).toFloat()
        drawLine(
            color = color,
            start = center,
            end = androidx.compose.ui.geometry.Offset(targetX, targetY),
            strokeWidth = 1.5.dp.toPx(),
            alpha = 0.6f
        )
    }
}
@Composable
fun H2BottomNavBar(
    activeTab: Int,
    onTabSelected: (Int) -> Unit
) {
    // نوار پایین کلاً از کامپوننت پیش‌فرض متریال (که استایل خودش را داشت و از تم
    // شیشه‌ای/نئونی بقیه‌ی اپ جدا به‌نظر می‌رسید) خارج شد. الان یک نوار شیشه‌ای سبز
    // دست‌ساز است با همان جلوه‌ی دکمه‌های گرد شیشه‌ای (RoundActionButton) که در
    // کارت اصلی استفاده شده — تا کل اپ یک زبان بصری یکپارچه داشته باشد.
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 12.dp, shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp), ambientColor = NeonGreen, spotColor = NeonGreen)
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .background(
                Brush.verticalGradient(
                    listOf(NeonGreen.copy(alpha = 0.16f), CarbonGray.copy(alpha = 0.97f))
                )
            )
            .border(
                border = BorderStroke(1.dp, NeonGreen.copy(alpha = 0.35f)),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            )
            .navigationBarsPadding()
            .padding(vertical = 10.dp, horizontal = 6.dp)
    ) {
        val items = listOf(
            Triple(0, "بازار", Icons.Default.History),
            Triple(1, "معاملات", Icons.Default.AccountBalanceWallet),
            Triple(2, "سلامت", Icons.Default.Wifi),
            Triple(3, "تنظیمات", Icons.Default.Tune)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { (index, label, icon) ->
                val isSelected = activeTab == index
                val tint = if (isSelected) NeonGreen else MutedSteel
                val circleSize = if (isSelected) 54.dp else 46.dp
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(circleSize)
                            .let {
                                if (isSelected) it.shadow(elevation = 10.dp, shape = CircleShape, ambientColor = NeonGreen, spotColor = NeonGreen)
                                else it
                            }
                            .clip(CircleShape)
                            .background(
                                Brush.verticalGradient(
                                    listOf(tint.copy(alpha = if (isSelected) 0.30f else 0.10f), tint.copy(alpha = if (isSelected) 0.10f else 0.04f))
                                )
                            )
                            .border(1.5.dp, tint.copy(alpha = if (isSelected) 0.9f else 0.4f), CircleShape)
                            .clickable { onTabSelected(index) },
                        contentAlignment = Alignment.Center
                    ) {
                        // لایه‌ی انعکاس نور شیشه‌ای
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 4.dp, start = 9.dp, end = 9.dp)
                                .clip(CircleShape)
                                .background(Brush.verticalGradient(listOf(Color.White.copy(alpha = 0.12f), Color.Transparent), endY = 36f))
                        )
                        Icon(
                            imageVector = icon,
                            contentDescription = label,
                            tint = tint,
                            modifier = Modifier.size(if (isSelected) 24.dp else 21.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = label,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = tint
                    )
                }
            }
        }
    }
}
@Composable
fun SMCTechnicalsContent(symbol: String, signal: SignalEntity?) {
    Column {
            Text(
                text = "ساختار بازار اسمارت مانی ($symbol)",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 15.sp),
                color = NeonCyan
            )
            Spacer(modifier = Modifier.height(12.dp))

            val direction = signal?.direction ?: "WAIT"

            val chochValue = when (direction) {
                "BUY" -> "صعودی (Bullish CHOCH)"
                "SELL" -> "نزولی (Bearish CHOCH)"
                else -> "غیرفعال (No CHOCH)"
            }
            val chochColor = when (direction) {
                "BUY" -> NeonGreen
                "SELL" -> NeonRed
                else -> MutedSteel
            }

            val bosValue = when (direction) {
                "BUY" -> "تایید صعودی (Bullish BOS)"
                "SELL" -> "تایید نزولی (Bearish BOS)"
                else -> "فاقد ساختار (No BOS)"
            }
            val bosColor = when (direction) {
                "BUY" -> NeonCyan
                "SELL" -> NeonRed
                else -> MutedSteel
            }

            val obValue = when (direction) {
                "BUY" -> "بلاک تقاضا فعال"
                "SELL" -> "بلاک عرضه فعال"
                else -> "فاقد بلاک فعال"
            }
            val obColor = when (direction) {
                "BUY" -> NeonGreen
                "SELL" -> NeonRed
                else -> MutedSteel
            }

            val fvgValue = when (direction) {
                "BUY" -> "جذب نقدینگی صعودی (FVG)"
                "SELL" -> "تخلیه نقدینگی نزولی (FVG)"
                else -> "فاقد گپ فعال نوسانی"
            }
            val fvgColor = when (direction) {
                "BUY" -> NeonCyan
                "SELL" -> NeonRed
                else -> MutedSteel
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SMCItem(modifier = Modifier.weight(1f), title = "تغییر ساختار CHOCH", value = chochValue, color = chochColor, tooltipKey = "چوچ")
                SMCItem(modifier = Modifier.weight(1f), title = "شکست ساختار BOS", value = bosValue, color = bosColor, tooltipKey = "بوس")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SMCItem(modifier = Modifier.weight(1f), title = "بلاک‌های سفارش (OB)", value = obValue, color = obColor, tooltipKey = "اردربلاک")
                SMCItem(modifier = Modifier.weight(1f), title = "شکاف ارزش منصفانه FVG", value = fvgValue, color = fvgColor, tooltipKey = "فوجی")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color.White.copy(alpha = 0.05f))
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "تاییدیه‌ی اندیکاتورهای کمکی",
                fontSize = 14.sp,
                fontWeight = FontWeight.Black,
                color = NeonGreen
            )
            Spacer(modifier = Modifier.height(8.dp))

            val emaStatus = when (direction) {
                "BUY" -> "روند صعودی همسو (Bullish Trend)"
                "SELL" -> "روند نزولی همسو (Bearish Trend)"
                else -> "بی‌روند و نوسانی (No Trend - Range)"
            }
            val emaColor = when (direction) { "BUY" -> NeonGreen; "SELL" -> NeonRed; else -> BrightGold }
            val emaStatusTooltipKey = if (direction == "WAIT") "status_no_trend" else null

            val rsiStatus = when (direction) {
                "BUY" -> "اشباع فروش و برگشت صعودی"
                "SELL" -> "اشباع خرید و شکست نزولی"
                else -> "خنثی و بدون نوسان (Neutral)"
            }
            val rsiColor = when (direction) { "BUY" -> NeonGreen; "SELL" -> NeonRed; else -> MutedSteel }
            val rsiStatusTooltipKey = if (direction == "WAIT") "status_neutral_rsi" else null

            val macdStatus = when (direction) {
                "BUY" -> "تقاطع صعودی معتبر (Bullish Crossover)"
                "SELL" -> "تقاطع نزولی معتبر (Bearish Crossover)"
                else -> "فاقد تلاقی حرکتی (Flat)"
            }
            val macdColor = when (direction) { "BUY" -> NeonGreen; "SELL" -> NeonRed; else -> MutedSteel }
            val macdStatusTooltipKey = if (direction == "WAIT") "status_flat_macd" else null

            val cmfStatus = when (direction) {
                "BUY" -> "انباشت نهنگ خرید فعال"
                "SELL" -> "توزیع نهنگ فروش فعال"
                else -> "جریان پول خنثی و کم حجم"
            }
            val cmfColor = when (direction) { "BUY" -> NeonGreen; "SELL" -> NeonRed; else -> MutedSteel }
            val cmfStatusTooltipKey = if (direction == "WAIT") "status_low_volume_flow" else null

            IndicatorRow(name = "میانگین متحرک نمایی EMA 50 / 200", status = emaStatus, color = emaColor, tooltipKey = "اما", statusTooltipKey = emaStatusTooltipKey)
            IndicatorRow(name = "شاخص قدرت نسبی RSI (14)", status = rsiStatus, color = rsiColor, tooltipKey = "آرسیای", statusTooltipKey = rsiStatusTooltipKey)
            IndicatorRow(name = "واگرایی مکدی MACD Cross", status = macdStatus, color = macdColor, tooltipKey = "مکدی", statusTooltipKey = macdStatusTooltipKey)
            IndicatorRow(name = "جریان پول چایکین CMF", status = cmfStatus, color = cmfColor, tooltipKey = "سیامف", statusTooltipKey = cmfStatusTooltipKey)
    }
}
@Composable
fun SMCItem(modifier: Modifier = Modifier, title: String, value: String, color: Color, tooltipKey: String? = null) {
    var showTooltip by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(ObsidianDark)
                .let { if (tooltipKey != null) it.clickable(onClick = { showTooltip = !showTooltip }) else it }
                .padding(8.dp)
        ) {
            Text(text = if (tooltipKey != null) "ℹ️ $title" else title, fontSize = 12.sp, color = MutedSteel)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = color)
        }
        if (showTooltip && tooltipKey != null) {
            TooltipExplanationBox(termKey = tooltipKey, onDismiss = { showTooltip = false })
        }
    }
}

@Composable
fun IndicatorRow(name: String, status: String, color: Color, tooltipKey: String? = null, statusTooltipKey: String? = null) {
    var showTooltip by remember { mutableStateOf(false) }
    var showStatusTooltip by remember { mutableStateOf(false) }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (tooltipKey != null) "ℹ️ $name" else name,
                fontSize = 13.sp,
                color = MutedSteel,
                modifier = Modifier.weight(1f).let { if (tooltipKey != null) it.clickable(onClick = { showTooltip = !showTooltip }) else it }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (statusTooltipKey != null) "$status ℹ️" else status,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = color,
                textAlign = TextAlign.End,
                modifier = if (statusTooltipKey != null) Modifier.clickable(onClick = { showStatusTooltip = !showStatusTooltip }) else Modifier
            )
        }
        if (showTooltip && tooltipKey != null) {
            TooltipExplanationBox(termKey = tooltipKey, onDismiss = { showTooltip = false })
        }
        if (showStatusTooltip && statusTooltipKey != null) {
            TooltipExplanationBox(termKey = statusTooltipKey, onDismiss = { showStatusTooltip = false })
        }
    }
}
@Composable
fun BacktestAndLearningTab(
    backtest: com.h2signals.app.data.database.BacktestResultEntity?,
    weights: List<com.h2signals.app.data.database.EngineWeightEntity>
) {
    // صداقت داده: اگر تعداد معاملات شبیه‌سازی‌شده صفر باشد (چه به‌خاطر نبود کندل کافی،
    // چه چون شرایط ورود هرگز در بازه‌ی تاریخی برقرار نشد)، دیگر عدد ثابت و گمراه‌کننده
    // نمایش داده نمی‌شود؛ به‌جای آن پیام صریح «داده‌ی کافی نیست» نشان داده می‌شود.
    val hasRealBacktestData = backtest != null && backtest.totalTrades > 0

    Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(CarbonGray).padding(16.dp)) {
        Column {
            Text(text = "آمار شبیه‌ساز تاریخی و وزن‌دهی هوش", style = MaterialTheme.typography.titleLarge.copy(fontSize = 15.sp), color = NeonCyan)
            Spacer(modifier = Modifier.height(12.dp))
            if (!hasRealBacktestData) {
                Box(
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(ObsidianDark).padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "داده‌ی کافی برای بک‌تست این نماد وجود ندارد", fontSize = 13.sp, color = MutedSteel, textAlign = TextAlign.Center)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "پس از جمع‌آوری تاریخچه‌ی بیشتر قیمت، آمار واقعی نمایش داده خواهد شد", fontSize = 11.sp, color = MutedSteel.copy(alpha = 0.7f), textAlign = TextAlign.Center)
                    }
                }
            } else if (backtest != null) {
                // استفاده از یک متغیر محلی غیر-nullable (به‌جای backtest!! تکراری در هر
                // خط) تا کامپایلر Kotlin بتواند بدون ابهام smart-cast را در کل این بلوک
                // تضمین کند.
                val b = backtest
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    BacktestStatMetric(title = "نرخ موفقیت", value = "${PersianDateTimeUtils.formatDouble(b.winRate, 1)}%", color = NeonGreen)
                    BacktestStatMetric(title = "فاکتور سودآوری", value = PersianDateTimeUtils.formatDouble(b.profitFactor, 2), color = IceWhite)
                    BacktestStatMetric(title = "حداکثر افت", value = "${PersianDateTimeUtils.formatDouble(b.drawdown, 1)}%", color = NeonRed)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "بر اساس ${b.totalTrades} معامله‌ی شبیه‌سازی‌شده", fontSize = 11.sp, color = MutedSteel.copy(alpha = 0.6f))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color.White.copy(alpha = 0.05f))
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "وزن موتورهای تصمیم‌گیری (یادگیری تطبیقی):", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MutedSteel)
            Spacer(modifier = Modifier.height(8.dp))
            if (weights.isEmpty()) {
                Text(text = "در حال بارگذاری وزن موتورها...", fontSize = 13.sp, color = MutedSteel)
            } else {
                weights.forEach { weightEntity ->
                    val progress = weightEntity.weight.toFloat() / 0.50f
                    Column(modifier = Modifier.padding(vertical = 4.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(
                                text = when (weightEntity.engineName) {
                                    "TECHNICAL" -> "موتور تکنیکال"
                                    "SMC" -> "هوش اسمارت مانی (Smart Money Concept)"
                                    "MICROSTRUCTURE" -> "میکروساختار بازار"
                                    "TREND" -> "موتور تشخیص روند"
                                    "MOMENTUM" -> "موتور تکانه بازار"
                                    "LIQUIDITY" -> "حجم و نقدینگی"
                                    "VOLUME" -> "حجم نسبی"
                                    "BACKTEST" -> "داده‌های تاریخی"
                                    else -> weightEntity.engineName
                                },
                                fontSize = 12.sp, color = IceWhite, modifier = Modifier.weight(1f)
                            )
                            Text(text = "${PersianDateTimeUtils.formatDouble(weightEntity.weight * 100, 1)}%", fontSize = 12.sp, color = NeonCyan, fontFamily = FontFamily.Monospace)
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        LinearProgressIndicator(progress = Math.min(1.0f, progress), modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)), color = NeonCyan, trackColor = Color.White.copy(alpha = 0.05f))
                    }
                }
            }
        }
    }
}
@Composable
fun BacktestStatMetric(title: String, value: String, color: Color) {
    Column(
        modifier = Modifier
            .width(108.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(ObsidianDark)
            .border(1.dp, Color.White.copy(alpha = 0.03f), RoundedCornerShape(16.dp))
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, fontSize = 11.sp, color = MutedSteel, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = color, fontFamily = FontFamily.Monospace)
    }
}
@Composable
fun MultiProviderHealthTab(
    providers: List<ApiProvider>,
    goldenBasketActive: Boolean = false,
    whaleEngineActive: Boolean = false,
    newsParserOnline: Boolean = false
) {
    Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(CarbonGray).padding(16.dp)) {
        Column {
            Text(text = "پایشگر چند منبعی صرافی‌ها (Multi-Source Health)", style = MaterialTheme.typography.titleLarge.copy(fontSize = 15.sp), color = NeonCyan)
            Spacer(modifier = Modifier.height(12.dp))
            providers.forEach { provider ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp).clip(RoundedCornerShape(8.dp)).background(ObsidianDark).padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(8.dp).clip(RoundedCornerShape(4.dp)).background(if (provider.isOnline) NeonGreen else NeonRed))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = provider.name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = IceWhite)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "پینگ", fontSize = 10.sp, color = MutedSteel)
                            Text(text = "${provider.latencyMs} ms", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = if (provider.latencyMs < 120) NeonGreen else Color.Yellow, fontFamily = FontFamily.Monospace)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "پایداری", fontSize = 10.sp, color = MutedSteel)
                            Text(text = "${(provider.successRate * 100).toInt()}%", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = NeonCyan, fontFamily = FontFamily.Monospace)
                        }
                    }
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // --- وضعیت موتورهای داخلی برنامه — طبق درخواست کاربر ---
    Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(CarbonGray).padding(16.dp)) {
        Column {
            Text(text = "وضعیت موتورهای تحلیلی داخلی", style = MaterialTheme.typography.titleLarge.copy(fontSize = 15.sp), color = NeonCyan)
            Spacer(modifier = Modifier.height(12.dp))

            EngineStatusRow(
                name = "Golden Basket Engine",
                isOnline = goldenBasketActive,
                detail = if (goldenBasketActive) "در حال تولید سیگنال برای دارایی‌های طلایی" else "هنوز سیگنالی تولید نشده"
            )
            EngineStatusRow(
                name = "Wale AI Engine",
                isOnline = whaleEngineActive,
                detail = if (whaleEngineActive) "تحلیل رفتار نهنگ‌ها فعال است" else "هنوز اسکنی انجام نشده"
            )
            EngineStatusRow(
                name = "News Parser",
                isOnline = newsParserOnline,
                detail = if (newsParserOnline) "اتصال به CryptoCompare News API برقرار است" else "آخرین تلاش برای دریافت اخبار ناموفق بود یا هنوز اجرا نشده"
            )
        }
    }
}

// ==========================================
// وضعیت موتورهای داخلی برنامه — Golden Basket / Whale AI / News Parser
// ==========================================
@Composable
fun EngineStatusRow(name: String, isOnline: Boolean, detail: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp).clip(RoundedCornerShape(8.dp)).background(ObsidianDark).padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(8.dp).clip(RoundedCornerShape(4.dp)).background(if (isOnline) NeonGreen else MutedSteel))
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = IceWhite)
                Text(text = detail, fontSize = 10.sp, color = MutedSteel)
            }
        }
        Text(
            text = if (isOnline) "فعال" else "غیرفعال",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = if (isOnline) NeonGreen else MutedSteel
        )
    }
}
// ==========================================
// باکس «سبد طلایی» — ۸ دارایی استراتژیک با موتور تحلیلی اختصاصی SuperEngine
// ==========================================
@Composable
fun GoldenBasketBox(
    watchlist: List<WatchlistItem>,
    superSignals: Map<String, SignalEntity>,
    allTrades: List<TradeEntity>,
    onSelectCoin: (String) -> Unit,
    onBuy: (String, Double) -> Unit,
    onExecuteTrade: (String) -> Unit = {}
) {
    // نرخ موفقیت (Win Rate) — طبق درخواست کاربر «بالای کارت‌ها». صادقانه بر
    // اساس معاملات واقعیِ بسته‌شده‌ی خود کاربر در نمادهای سبد طلایی محاسبه
    // می‌شود، نه یک بک‌تست فرضی روی داده‌ی تاریخی که وجود ندارد.
    val goldenSymbols = GoldenBasketRegistry.tradableCryptoSymbols
    val closedGoldenTrades = allTrades.filter { it.symbol in goldenSymbols && it.status != "ACTIVE" }
    val winRateText = if (closedGoldenTrades.isNotEmpty()) {
        val wins = closedGoldenTrades.count { it.profitLossPercent > 0 }
        "${(wins * 100 / closedGoldenTrades.size)}% (${wins}/${closedGoldenTrades.size} معامله‌ی بسته‌شده)"
    } else {
        "داده‌ی کافی نیست"
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 14.dp, shape = RoundedCornerShape(22.dp), ambientColor = GoldenBase, spotColor = GoldenBase)
            .clip(RoundedCornerShape(22.dp))
            .background(Brush.verticalGradient(listOf(GoldenBase.copy(alpha = 0.16f), CarbonGray)))
            .border(1.5.dp, GoldenBase.copy(alpha = 0.55f), RoundedCornerShape(22.dp))
            .padding(16.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "🏆", fontSize = 18.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "سبد طلایی", fontSize = 15.sp, fontWeight = FontWeight.Black, color = GoldenBase)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "۸ دارایی استراتژیک", fontSize = 12.sp, color = MutedSteel)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "موتور تحلیلی اختصاصی: حجم هوشمند، فیبوناچی پویا، واگرایی چندتایم‌فریمی و اخبار (با تأیید سه‌منبعی قیمت)",
                fontSize = 11.sp,
                color = MutedSteel.copy(alpha = 0.85f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(GoldenBase.copy(alpha = 0.10f))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "نرخ موفقیت واقعی سبد طلایی", fontSize = 11.sp, color = MutedSteel)
                Text(text = winRateText, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GoldenBase)
            }
            Spacer(modifier = Modifier.height(12.dp))

            GoldenBasketRegistry.assets.chunked(4).forEach { rowAssets ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    rowAssets.forEach { golden ->
                        val watchItem = watchlist.firstOrNull { it.symbol == golden.symbol }
                        val superSignal = superSignals[golden.symbol]
                        val isPurchased = allTrades.any { it.symbol == golden.symbol && it.status == "ACTIVE" }
                        GoldenAssetCard(
                            modifier = Modifier.weight(1f),
                            golden = golden,
                            watchItem = watchItem,
                            superSignal = superSignal,
                            isPurchased = isPurchased,
                            onSelect = { onSelectCoin(golden.symbol) },
                            onBuy = onBuy,
                            onExecuteTrade = onExecuteTrade
                        )
                    }
                    // پر کردن جای خالی ردیف آخر اگر تعداد کامل ۴ تا نبود
                    if (rowAssets.size < 4) {
                        repeat(4 - rowAssets.size) { Spacer(modifier = Modifier.weight(1f)) }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun GoldenAssetCard(
    modifier: Modifier = Modifier,
    golden: GoldenAsset,
    watchItem: WatchlistItem?,
    superSignal: SignalEntity?,
    isPurchased: Boolean,
    onSelect: () -> Unit,
    onBuy: (String, Double) -> Unit,
    onExecuteTrade: (String) -> Unit = {}
) {
    var showBuyDialog by remember { mutableStateOf(false) }
    // بازطراحی «شیشه‌ای» (Glassmorphism) طبق درخواست کاربر: به‌جای کارت مربعی،
    // هر دارایی یک دایره‌ی بزرگ (حداقل ۸۰dp) با پس‌زمینه‌ی نیمه‌شفاف است. حلقه‌ی
    // دور دایره رنگ وضعیت سیگنال را نشان می‌دهد: سبز نئون برای BUY، خاکستری/نقره‌ای
    // برای WAIT، قرمز برای SELL. چون minSdk این پروژه ۲۶ است و افکت Blur واقعی
    // (RenderEffect) فقط از API ۳۱ به بعد در دسترس است، افکت شیشه‌ای با لایه‌های
    // نیمه‌شفاف و گرادیان (نه یک Blur واقعی) شبیه‌سازی می‌شود تا روی همه‌ی گوشی‌ها
    // (از جمله اندروید قدیمی‌تر) بدون کرش کار کند.
    val ringColor = when (superSignal?.direction) {
        "BUY" -> NeonGreen
        "SELL" -> NeonRed
        else -> Color(0xFFB0BEC5) // نقره‌ای/خاکستری برای WAIT
    }
    val glowAlpha = if (superSignal?.direction == "BUY") 0.9f else 0.55f

    Column(
        modifier = modifier.padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center) {
            // دایره‌ی شیشه‌ای اصلی
            Box(
                modifier = Modifier
                    .size(84.dp)
                    .shadow(elevation = if (superSignal?.direction == "BUY") 10.dp else 2.dp, shape = CircleShape, ambientColor = ringColor, spotColor = ringColor)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(
                                Color.White.copy(alpha = 0.20f),
                                Color(0xFF3A3F45).copy(alpha = 0.20f),
                                ObsidianDark.copy(alpha = 0.55f)
                            )
                        )
                    )
                    .border(2.5.dp, ringColor.copy(alpha = glowAlpha), CircleShape)
                    .clickable { onSelect() },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = golden.emoji, fontSize = 22.sp)
                    Text(
                        text = golden.persianName,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = IceWhite,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                    if (watchItem != null) {
                        Text(
                            text = formatPriceText(golden.symbol, watchItem.livePrice),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }
                }
            }
            // نشان «خریداری‌شده» — گوشه‌ی بالا-راست دایره
            if (isPurchased) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(NeonGreen)
                        .border(1.5.dp, ObsidianDark, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "خریداری‌شده", tint = Color.Black, modifier = Modifier.size(12.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // دکمه‌ی گرد وضعیت به‌جای متن — طبق درخواست: آیکون به‌جای نوشتن «خرید»/«صبر»
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(
                    when (superSignal?.direction) {
                        "BUY" -> NeonGreen
                        "SELL" -> NeonRed
                        else -> Color(0xFF616E7A)
                    }
                )
                .clickable {
                    if (isPurchased) {
                        // خریداری‌شده: کلیک روی نماد می‌برد
                        onSelect()
                    } else if (superSignal?.direction == "BUY") {
                        onExecuteTrade(golden.symbol)
                    } else {
                        showBuyDialog = true
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            when {
                isPurchased -> Icon(Icons.Default.Check, contentDescription = "خریداری‌شده", tint = Color.Black, modifier = Modifier.size(16.dp))
                superSignal?.direction == "BUY" -> Icon(Icons.Default.ArrowUpward, contentDescription = "خرید", tint = Color.Black, modifier = Modifier.size(16.dp))
                superSignal?.direction == "SELL" -> Icon(Icons.Default.ArrowDownward, contentDescription = "فروش", tint = Color.Black, modifier = Modifier.size(16.dp))
                else -> Icon(Icons.Default.HourglassEmpty, contentDescription = "صبر", tint = Color.Black, modifier = Modifier.size(14.dp))
            }
        }
    }

    if (showBuyDialog) {
        CoinBuyDialog(
            symbol = golden.symbol,
            watchItem = watchItem,
            onDismiss = { showBuyDialog = false },
            onBuy = onBuy
        )
    }
}

@Composable
fun SignalHistoryTab(
    allSignals: List<SignalEntity>,
    watchlist: List<WatchlistItem>,
    onSelectCoin: (String) -> Unit
) {
    // گروه‌بندی سیگنال‌ها بر اساس نماد و ادغام آن با اطلاعات لحظه‌ای watchlist (قیمت، تغییر ۲۴ساعته).
    // اولویت نمایش: نمادهایی که آخرین سیگنالشان BUY/SELL/EXIT واقعی است (نه صرفاً WAIT)
    // بالای لیست قرار می‌گیرند تا وقتی کاربر وارد اپ می‌شود، اولین چیزی که می‌بیند
    // مهم‌ترین و قابل‌اقدام‌ترین سیگنال باشد.
    // منبع اصلی لیست، کل watchlist است (همه‌ی کوین‌های تحت پایش)، نه فقط آن‌هایی که
    // تا این لحظه سیگنالی برایشان ثبت شده. قبلاً کوین‌هایی که هنوز موتور تحلیلشان
    // نکرده بود اصلاً در این صفحه دیده نمی‌شدند؛ الان همه با وضعیت مناسب (سیگنال یا
    // «در انتظار تحلیل») نمایش داده می‌شوند.
    val groupedSignals = remember(allSignals) {
        allSignals.groupBy { it.symbol }
    }
    val allSymbolsSorted = remember(watchlist, groupedSignals) {
        watchlist.map { it.symbol }.distinct().sortedWith(
            compareByDescending<String> { symbol ->
                val list = groupedSignals[symbol]
                val latestDirection = list?.maxByOrNull { it.timestamp }?.direction
                if (latestDirection == "BUY" || latestDirection == "SELL" || latestDirection == "EXIT") 1 else 0
            }.thenByDescending { symbol -> groupedSignals[symbol]?.maxOfOrNull { it.timestamp } ?: 0L }
        )
    }

    Column {
        Text(
            text = "کوین‌ها و تاریخچه سیگنال‌ها",
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 14.sp, fontWeight = FontWeight.Bold),
            color = NeonCyan
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "روی نام کوین بزنید تا اطلاعات کامل، بک‌تست واقعی و گزینه‌ی خرید در کارت بالای صفحه باز شود؛ روی فلش بزنید تا تاریخچه‌ی سیگنال‌ها باز شود",
            fontSize = 12.sp,
            color = MutedSteel
        )
        Spacer(modifier = Modifier.height(12.dp))

        if (allSymbolsSorted.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(CarbonGray)
                    .padding(vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "در حال بارگذاری بازارها...", fontSize = 14.sp, color = MutedSteel)
            }
        } else {
            allSymbolsSorted.forEach { symbol ->
                val watchItem = watchlist.firstOrNull { it.symbol == symbol }
                val signalsForSymbol = groupedSignals[symbol] ?: emptyList()
                SignalHistoryGroup(
                    symbol = symbol,
                    signals = signalsForSymbol,
                    watchItem = watchItem,
                    onSelectCoin = onSelectCoin
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun SignalHistoryGroup(
    symbol: String,
    signals: List<SignalEntity>,
    watchItem: WatchlistItem?,
    onSelectCoin: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val visual = getCoinVisual(symbol)
    val latest = signals.maxByOrNull { it.timestamp }
    val latestColor = when (latest?.direction) {
        "BUY" -> NeonGreen
        "SELL" -> NeonRed
        "EXIT" -> Color(0xFFFF6D00)
        else -> BrightGold
    }
    // نقطه‌ی رنگی وضعیت بر اساس آخرین سیگنال واقعی، نه صرفاً opportunityScore —
    // تا کاربر بلافاصله بفهمد این کوین سیگنال BUY/SELL دارد یا فقط در انتظار است.
    val statusDotColor = when (latest?.direction) {
        "BUY" -> NeonGreen
        "SELL" -> NeonRed
        "EXIT" -> Color(0xFFFF6D00)
        else -> MutedSteel.copy(alpha = 0.4f)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(CarbonGray)
            .border(1.5.dp, latestColor.copy(alpha = 0.35f), RoundedCornerShape(20.dp))
    ) {
        // ردیف سرِ کارت — بزرگ‌تر، شامل قیمت لحظه‌ای و تغییر ۲۴ساعته، قابل کلیک برای باز/بسته کردن
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onSelectCoin(symbol) }
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(Brush.radialGradient(listOf(visual.color.copy(alpha = 0.3f), ObsidianDark)))
                        .border(2.dp, visual.color.copy(alpha = 0.7f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = visual.emoji, fontSize = 22.sp, color = visual.color)
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(statusDotColor)
                            .align(Alignment.TopEnd)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        Text(text = symbol, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = IceWhite)
                        if (visual.persianName.isNotBlank()) {
                            Text(text = visual.persianName, fontSize = 13.sp, color = MutedSteel)
                        }
                    }
                    if (watchItem != null) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = formatPriceText(symbol, watchItem.livePrice), fontSize = 13.sp, color = MutedSteel, fontFamily = FontFamily.Monospace)
                            Spacer(modifier = Modifier.width(6.dp))
                            val changeColor = if (watchItem.change24h >= 0) NeonGreen else NeonRed
                            Text(
                                text = "${if (watchItem.change24h >= 0) "+" else ""}${PersianDateTimeUtils.formatDouble(watchItem.change24h, 1)}%",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = changeColor
                            )
                        }
                    } else {
                        Text(text = "${signals.size} سیگنال ثبت‌شده", fontSize = 12.sp, color = MutedSteel)
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { expanded = !expanded }
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(latestColor.copy(alpha = 0.15f))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(text = latest?.direction ?: "WAIT", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = latestColor)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = MutedSteel,
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        // بدنه‌ی جمع‌شونده — فقط وقتی expanded=true نمایش داده می‌شود
        if (expanded) {
            Divider(color = Color.White.copy(alpha = 0.06f))
            Column(modifier = Modifier.padding(14.dp)) {
                signals.sortedByDescending { it.timestamp }.take(30).forEach { sig ->
                    val dirColor = when (sig.direction) {
                        "BUY" -> NeonGreen
                        "SELL" -> NeonRed
                        "EXIT" -> Color(0xFFFF6D00)
                        else -> BrightGold
                    }
                    val signalTime = PersianDateTimeUtils.getTehranTime(sig.timestamp)
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(dirColor.copy(alpha = 0.15f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(text = sig.direction, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = dirColor)
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "${PersianDateTimeUtils.getPersianDateString(signalTime)} - ${PersianDateTimeUtils.formatTehranTime(signalTime)}",
                                fontSize = 11.sp,
                                color = MutedSteel
                            )
                        }
                        Text(text = formatPriceText(sig.symbol, sig.price), fontSize = 12.sp, color = MutedSteel, fontFamily = FontFamily.Monospace)
                    }
                }
            }
        }
    }
}

// پاپ‌آپ «خرید» — کاملاً مستقل از بک‌تست. فقط قیمت واقعی خرید را (به تومان) می‌گیرد
// و آن را در سبد خریدها ثبت می‌کند. چون صرافی‌های ایرانی قیمت را به تومان نشان
// می‌دهند، اینجا یک فیلد ورودی عددی تومانی قرار دارد، نه استفاده‌ی خودکار از
// قیمت لحظه‌ای اپ.
@Composable
fun CoinBuyDialog(
    symbol: String,
    watchItem: WatchlistItem?,
    onDismiss: () -> Unit,
    onBuy: (String, Double) -> Unit
) {
    val visual = getCoinVisual(symbol)
    var priceInput by remember { mutableStateOf("") }
    val tomanValue = priceInput.toLongOrNull()

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(22.dp))
                .background(CarbonGray)
                .border(1.5.dp, NeonGreen.copy(alpha = 0.5f), RoundedCornerShape(22.dp))
                .padding(18.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Brush.radialGradient(listOf(visual.color.copy(alpha = 0.3f), ObsidianDark)))
                            .border(1.5.dp, visual.color.copy(alpha = 0.7f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = visual.emoji, fontSize = 16.sp, color = visual.color)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "ثبت خرید $symbol", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = IceWhite)
                        if (watchItem != null) {
                            Text(text = "قیمت لحظه‌ای: ${formatPriceText(symbol, watchItem.livePrice)}", fontSize = 12.sp, color = MutedSteel)
                        }
                    }
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = MutedSteel,
                        modifier = Modifier.size(20.dp).clickable { onDismiss() }
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))
                Divider(color = Color.White.copy(alpha = 0.06f))
                Spacer(modifier = Modifier.height(14.dp))

                Text(text = "قیمتی که واقعاً خریدید (به تومان)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = NeonGreen)
                Spacer(modifier = Modifier.height(8.dp))
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    OutlinedTextField(
                        value = priceInput,
                        onValueChange = { new -> priceInput = new.filter { it.isDigit() } },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        placeholder = { Text(text = "مثلاً 4500000", fontSize = 14.sp, color = MutedSteel.copy(alpha = 0.6f)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        textStyle = androidx.compose.ui.text.TextStyle(color = IceWhite, fontSize = 15.sp),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = ObsidianDark,
                            unfocusedContainerColor = ObsidianDark,
                            focusedBorderColor = NeonGreen.copy(alpha = 0.6f),
                            unfocusedBorderColor = Color.Transparent,
                            cursorColor = NeonGreen
                        )
                    )
                }
                Text(text = "واحد: تومان", fontSize = 11.sp, color = MutedSteel.copy(alpha = 0.6f), modifier = Modifier.padding(top = 4.dp))

                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(if (tomanValue != null && tomanValue > 0) NeonGreen else MutedSteel.copy(alpha = 0.3f))
                        .clickable(enabled = tomanValue != null && tomanValue > 0) {
                            onBuy(symbol, tomanValue!!.toDouble())
                            onDismiss()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "تأیید خرید و افزودن به سبد خریدها", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "پس از ثبت، اعلان‌ها فقط برای زمان مناسبِ فروش همین کوین فعال می‌شوند.",
                    fontSize = 11.sp,
                    color = MutedSteel.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

// پاپ‌آپ «بک‌تست» — فقط اطلاعات، بدون هیچ فرم خریدی. دقیقاً همان محتوایی که در
// تب بک‌تست هست (آمار واقعی + همان سه نوار سبز وزن موتورهای تصمیم‌گیری) اینجا هم
// تکرار می‌شود تا کاربر بدون خروج از کارت اصلی، بک‌تست واقعی نماد انتخابی را ببیند.
@Composable
fun CoinBacktestInfoDialog(
    symbol: String,
    backtest: com.h2signals.app.data.database.BacktestResultEntity?,
    weights: List<com.h2signals.app.data.database.EngineWeightEntity>,
    onDismiss: () -> Unit
) {
    val hasRealBacktestData = backtest != null && backtest.totalTrades > 0
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(22.dp))
                .background(CarbonGray)
                .border(1.5.dp, NeonCyan.copy(alpha = 0.5f), RoundedCornerShape(22.dp))
                .padding(18.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "بک‌تست واقعی $symbol", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = NeonCyan)
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = MutedSteel,
                        modifier = Modifier.size(20.dp).clickable { onDismiss() }
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))
                if (!hasRealBacktestData) {
                    Box(
                        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(ObsidianDark).padding(14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "داده‌ی کافی برای بک‌تست این نماد وجود ندارد", fontSize = 13.sp, color = MutedSteel, textAlign = TextAlign.Center)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "پس از جمع‌آوری تاریخچه‌ی بیشتر قیمت، آمار واقعی نمایش داده خواهد شد", fontSize = 11.sp, color = MutedSteel.copy(alpha = 0.7f), textAlign = TextAlign.Center)
                        }
                    }
                } else {
                    val b = backtest!!
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        BacktestStatMetric(title = "نرخ موفقیت", value = "${PersianDateTimeUtils.formatDouble(b.winRate, 1)}%", color = NeonGreen)
                        BacktestStatMetric(title = "فاکتور سودآوری", value = PersianDateTimeUtils.formatDouble(b.profitFactor, 2), color = IceWhite)
                        BacktestStatMetric(title = "حداکثر افت", value = "${PersianDateTimeUtils.formatDouble(b.drawdown, 1)}%", color = NeonRed)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "بر اساس ${b.totalTrades} معامله‌ی شبیه‌سازی‌شده روی داده‌ی تاریخی واقعی", fontSize = 11.sp, color = MutedSteel.copy(alpha = 0.6f))
                }

                Spacer(modifier = Modifier.height(18.dp))
                Divider(color = Color.White.copy(alpha = 0.06f))
                Spacer(modifier = Modifier.height(14.dp))

                // همان سه نوار سبز «وزن موتورهای تصمیم‌گیری» که در تب بک‌تست هست —
                // این وزن‌ها سراسری‌اند (برای کل سیستم، نه فقط این نماد) پس عیناً از
                // همان‌جا تکرار می‌شود تا کاربر تناقضی بین دو صفحه نبیند.
                Text(text = "وزن موتورهای تصمیم‌گیری (یادگیری تطبیقی):", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MutedSteel)
                Spacer(modifier = Modifier.height(8.dp))
                if (weights.isEmpty()) {
                    Text(text = "در حال بارگذاری وزن موتورها...", fontSize = 13.sp, color = MutedSteel)
                } else {
                    weights.forEach { weightEntity ->
                        val progress = weightEntity.weight.toFloat() / 0.50f
                        Column(modifier = Modifier.padding(vertical = 4.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(
                                    text = when (weightEntity.engineName) {
                                        "TECHNICAL" -> "موتور تکنیکال"
                                        "SMC" -> "هوش اسمارت مانی (Smart Money Concept)"
                                        "MICROSTRUCTURE" -> "میکروساختار بازار"
                                        "TREND" -> "موتور تشخیص روند"
                                        "MOMENTUM" -> "موتور تکانه بازار"
                                        "LIQUIDITY" -> "حجم و نقدینگی"
                                        "VOLUME" -> "حجم نسبی"
                                        "BACKTEST" -> "داده‌های تاریخی"
                                        else -> weightEntity.engineName
                                    },
                                    fontSize = 12.sp, color = IceWhite, modifier = Modifier.weight(1f)
                                )
                                Text(text = "${PersianDateTimeUtils.formatDouble(weightEntity.weight * 100, 1)}%", fontSize = 12.sp, color = NeonCyan, fontFamily = FontFamily.Monospace)
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            LinearProgressIndicator(progress = Math.min(1.0f, progress), modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)), color = NeonCyan, trackColor = Color.White.copy(alpha = 0.05f))
                        }
                    }
                }
            }
        }
    }
}

// معرفی خلاصه به فارسی برای مهم‌ترین کوین‌های واچ‌لیست. اگر برای نمادی معرفی
// نوشته نشده، به‌جای حدس‌زدن یا ساختن اطلاعات نادرست، صادقانه اعلام می‌شود.
val coinIntroductions = mapOf(
    "BTC" to "بیت‌کوین اولین و بزرگ‌ترین ارز دیجیتال جهان است که در سال ۲۰۰۹ توسط فردی با نام مستعار ساتوشی ناکاموتو معرفی شد. عرضه‌ی آن محدود به ۲۱ میلیون واحد است و اغلب به‌عنوان «طلای دیجیتال» و ذخیره‌ی ارزش شناخته می‌شود.",
    "ETH" to "اتریوم دومین ارز دیجیتال بزرگ جهان و بستر اصلی قراردادهای هوشمند و اپلیکیشن‌های غیرمتمرکز (DeFi، NFT و غیره) است. از سال ۲۰۲۲ به مکانیزم اثبات سهام (Proof of Stake) روی آورده که مصرف انرژی آن را به‌شدت کاهش داده است.",
    "SOL" to "سولانا یک بلاک‌چین با سرعت تراکنش بسیار بالا و کارمزد پایین است که برای اپلیکیشن‌های غیرمتمرکز و بازارهای NFT طراحی شده. نقطه‌ی قوت اصلی آن مقیاس‌پذیری است.",
    "BNB" to "بی‌ان‌بی توکن بومی صرافی بایننس، بزرگ‌ترین صرافی ارز دیجیتال جهان، است و در شبکه‌ی BNB Chain برای کارمزد تراکنش و اپلیکیشن‌های غیرمتمرکز استفاده می‌شود.",
    "XRP" to "ریپل (XRP) برای تسویه‌ی سریع و کم‌هزینه‌ی تراکنش‌های بین‌المللی بین بانک‌ها و مؤسسات مالی طراحی شده است.",
    "ADA" to "کاردانو یک بلاک‌چین اثبات سهام است که با رویکردی مبتنی بر پژوهش دانشگاهی و بررسی همتا (peer review) توسعه می‌یابد.",
    "DOGE" to "دوج‌کوین در ابتدا به‌صورت شوخی و بر پایه‌ی یک میم اینترنتی (سگ شیبا اینو) ساخته شد، اما به یکی از شناخته‌شده‌ترین ارزهای دیجیتال با جامعه‌ی بزرگ هواداران تبدیل شده است.",
    "AVAX" to "آوالانچ یک پلتفرم قرارداد هوشمند با تمرکز بر سرعت بالا و تأییدنهایی (finality) در کسری از ثانیه است.",
    "MATIC" to "پالیگان (که اکنون با نام POL نیز شناخته می‌شود) راه‌حل مقیاس‌پذیری لایه‌ی دوم برای اتریوم است که هزینه و زمان تراکنش‌ها را کاهش می‌دهد.",
    "LINK" to "چین‌لینک یک شبکه‌ی اوراکل غیرمتمرکز است که داده‌های دنیای واقعی (مثل قیمت‌ها) را به‌صورت امن به قراردادهای هوشمند متصل می‌کند.",
    "LTC" to "لایت‌کوین یکی از قدیمی‌ترین ارزهای دیجیتال (۲۰۱۱) است که از الگوریتم بیت‌کوین الهام گرفته اما زمان تولید بلاک سریع‌تری دارد.",
    "TRX" to "ترون بستری برای اپلیکیشن‌های غیرمتمرکز با تمرکز ویژه بر صنعت سرگرمی و محتوای دیجیتال است.",
    "XLM" to "استلار برای تسهیل انتقال پول بین ارزهای مختلف با کارمزد بسیار پایین و سرعت بالا طراحی شده، با تمرکز بر شمول مالی.",
    "RENDER" to "رندر یک شبکه‌ی غیرمتمرکز پردازش گرافیکی (GPU) است که قدرت پردازشی رایانه‌های سراسر جهان را برای رندر سه‌بعدی و هوش مصنوعی به اشتراک می‌گذارد.",
    "TIA" to "سلستیا یک شبکه‌ی «لایه‌ی دسترسی داده» (Data Availability) است که به بلاک‌چین‌های دیگر کمک می‌کند مقیاس‌پذیرتر شوند.",
    "APT" to "آپتوس بلاک‌چینی است که با زبان برنامه‌نویسی Move (که ابتدا برای پروژه‌ی Diem متا/فیسبوک ساخته شد) روی امنیت و توان تراکنش بالا تمرکز دارد.",
    "ARB" to "آربیتروم راه‌حل لایه‌ی دوم اتریوم مبتنی بر فناوری Optimistic Rollup است که هزینه‌ی تراکنش‌ها را به‌شدت کاهش می‌دهد.",
    "SUI" to "سویی بلاک‌چینی با زبان برنامه‌نویسی Move است که روی پردازش موازی تراکنش‌ها و سرعت بالا تمرکز دارد.",
    "INJ" to "اینجکتیو بلاک‌چینی اختصاصی برای اپلیکیشن‌های مالی غیرمتمرکز (DeFi) از جمله صرافی‌های مشتقه است.",
    "ICP" to "اینترنت کامپیوتر (ICP) هدفش این است که بلاک‌چین را به بستری برای میزبانی کامل وب‌سایت‌ها و نرم‌افزارها (نه فقط تراکنش‌های مالی) تبدیل کند.",
    "NEAR" to "نییر یک بلاک‌چین مقیاس‌پذیر با تمرکز بر تجربه‌ی کاربری ساده برای توسعه‌دهندگان و کاربران عادی است.",
    "FIL" to "فایل‌کوین شبکه‌ای غیرمتمرکز برای ذخیره‌سازی فایل است که در آن کاربران فضای هارددیسک خالی خود را در ازای دریافت توکن FIL اجاره می‌دهند.",
    "OP" to "اپتیمیزم یکی از بزرگ‌ترین راه‌حل‌های لایه‌ی دوم اتریوم مبتنی بر Optimistic Rollup است که هزینه‌ی تراکنش را کاهش می‌دهد.",
    "FTM" to "فانتوم یک بلاک‌چین لایه‌ی یک با سرعت تأییدنهایی بالا است که برای اپلیکیشن‌های غیرمتمرکز و DeFi استفاده می‌شود.",
    "FET" to "توکن Fetch.ai برای اپلیکیشن‌های مبتنی بر هوش مصنوعی و اتوماسیون غیرمتمرکز (عامل‌های هوشمند) روی بلاک‌چین استفاده می‌شود.",
    "AAVE" to "آوه یکی از بزرگ‌ترین پروتکل‌های وام‌دهی و وام‌گیری غیرمتمرکز (DeFi) است که به کاربران اجازه می‌دهد دارایی دیجیتال خود را قرض بدهند یا با وثیقه قرض بگیرند.",
    "GRT" to "گراف (The Graph) پروتکلی برای ایندکس‌کردن و پرس‌وجوی داده‌های بلاک‌چین است؛ به‌نوعی «گوگل بلاک‌چین» شناخته می‌شود.",
    "LDO" to "لیدو بزرگ‌ترین پروتکل استیکینگ مایع (Liquid Staking) برای اتریوم است که به کاربران اجازه می‌دهد بدون قفل‌کردن کامل دارایی، از استیکینگ سود ببرند."
)

fun getCoinIntroduction(symbol: String): String {
    for ((key, text) in coinIntroductions) {
        if (symbol.contains(key)) return text
    }
    return "این نماد یکی از دارایی‌های دیجیتال رصدشده در H2Signals است. معرفی تفصیلی این کوین هنوز در پایگاه‌داده‌ی اپ ثبت نشده؛ اطلاعات لحظه‌ای قیمت و سیگنال آن همچنان از منابع واقعی بازار دریافت می‌شود."
}

// پاپ‌آپ «معرفی کوین» — با کلیک روی دایره‌ی آیکون کوین در کارت بزرگ اصلی باز
// می‌شود و فقط معرفی/تاریخچه‌ی خلاصه‌ی آن را نشان می‌دهد (نه بک‌تست، نه خرید).
@Composable
fun CoinInfoDialog(symbol: String, onDismiss: () -> Unit) {
    val visual = getCoinVisual(symbol)
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(22.dp))
                .background(CarbonGray)
                .border(1.5.dp, visual.color.copy(alpha = 0.5f), RoundedCornerShape(22.dp))
                .padding(18.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Brush.radialGradient(listOf(visual.color.copy(alpha = 0.35f), ObsidianDark)))
                            .border(2.dp, visual.color.copy(alpha = 0.8f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = visual.emoji, fontSize = 20.sp, color = visual.color)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = symbol, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = IceWhite)
                        if (visual.persianName.isNotBlank()) {
                            Text(text = visual.persianName, fontSize = 13.sp, color = MutedSteel)
                        }
                    }
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = MutedSteel,
                        modifier = Modifier.size(20.dp).clickable { onDismiss() }
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))
                Divider(color = Color.White.copy(alpha = 0.06f))
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = getCoinIntroduction(symbol),
                    fontSize = 14.sp,
                    color = IceWhite,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
fun TradeHistoryTab(
    allTrades: List<TradeEntity>,
    watchlist: List<WatchlistItem>,
    backtestResults: List<com.h2signals.app.data.database.BacktestResultEntity> = emptyList(),
    onWipe: () -> Unit,
    onRegisterTrade: (String, Double) -> Unit,
    onDeleteTrade: (Int) -> Unit = {},
    onSell: (String) -> Unit = {}
) {
    var showAddTrade by remember { mutableStateOf(false) }
    // مرحله‌ی تأیید صریح: کلیک روی یک کوین فقط آن را برای تأیید انتخاب می‌کند و
    // پاپ‌آپ خرید (همان پاپ‌آپ تب «بازار»، شامل بک‌تست واقعی + فیلد قیمت به تومان)
    // باز می‌شود؛ ثبت واقعی معامله فقط بعد از تکمیل آن پاپ‌آپ انجام می‌شود. این از
    // ثبت ناخواسته‌ی معامله با یک کلیک ساده روی کارت جلوگیری می‌کند.
    var pendingSymbol by remember { mutableStateOf<WatchlistItem?>(null) }
    var showBuyDialog by remember { mutableStateOf(false) }

    Column {
        // --- بخش ثبت خرید دستی جدید ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(CarbonGray)
                .border(1.dp, NeonGreen.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                .padding(16.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth().clickable { showAddTrade = !showAddTrade; pendingSymbol = null },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.AddCircle, contentDescription = null, tint = NeonGreen, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "ثبت خرید جدید", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = NeonGreen)
                    }
                    Icon(
                        imageVector = if (showAddTrade) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null, tint = MutedSteel, modifier = Modifier.size(20.dp)
                    )
                }
                if (showAddTrade) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "کوینی که خریده‌اید را انتخاب کنید:", fontSize = 12.sp, color = MutedSteel)
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(watchlist) { item ->
                            val visual = getCoinVisual(item.symbol)
                            val isPending = pendingSymbol?.symbol == item.symbol
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(if (isPending) visual.color.copy(alpha = 0.25f) else ObsidianDark)
                                    .border(1.5.dp, visual.color.copy(alpha = if (isPending) 1f else 0.5f), RoundedCornerShape(14.dp))
                                    .clickable { pendingSymbol = item; showBuyDialog = true }
                                    .padding(horizontal = 14.dp, vertical = 10.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = visual.emoji, fontSize = 14.sp, color = visual.color)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Column {
                                        Text(text = item.symbol, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = IceWhite)
                                        Text(text = formatPriceText(item.symbol, item.livePrice), fontSize = 11.sp, color = MutedSteel)
                                    }
                                }
                            }
                        }
                    }

                    // پاپ‌آپ خرید (فقط ثبت قیمت به تومان) — بک‌تست دیگر اینجا تکرار نمی‌شود،
                    // چون حالا در کارت بزرگ اصلیِ تب «بازار» با دکمه‌ی «بک‌تست» در دسترس است.
                    if (showBuyDialog) {
                        pendingSymbol?.let { selected ->
                            CoinBuyDialog(
                                symbol = selected.symbol,
                                watchItem = selected,
                                onDismiss = {
                                    showBuyDialog = false
                                    pendingSymbol = null
                                },
                                onBuy = { symbol, tomanPrice ->
                                    onRegisterTrade(symbol, tomanPrice)
                                    showAddTrade = false
                                }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // --- لیست معاملات ثبت‌شده: کارت‌های بزرگ‌تر با جستجو، چیدمان دو ستونه ---
        var tradeSearchQuery by remember { mutableStateOf("") }
        val filteredTrades = remember(allTrades, tradeSearchQuery) {
            if (tradeSearchQuery.isBlank()) allTrades
            else allTrades.filter { it.symbol.contains(tradeSearchQuery, ignoreCase = true) }
        }

        Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(CarbonGray).padding(16.dp)) {
        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "دفترچه معاملات ثبت‌شده", style = MaterialTheme.typography.titleLarge.copy(fontSize = 14.sp), color = NeonCyan)
                Text(text = "پاکسازی تاریخچه", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = NeonRed, modifier = Modifier.clickable { onWipe() }.padding(4.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))

            // جعبه‌ی جستجو — از OutlinedTextField استاندارد ماتریال استفاده می‌شود
            // (به‌جای BasicTextField) چون داخل یک اپ کاملاً RTL، BasicTextField گاهی
            // فوکوس کیبورد را به‌درستی نمی‌گرفت و تایپ کردن هیچ واکنشی نداشت.
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                OutlinedTextField(
                    value = tradeSearchQuery,
                    onValueChange = { tradeSearchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text(text = "جستجوی نماد (مثلاً BTC)...", fontSize = 14.sp, color = MutedSteel.copy(alpha = 0.6f)) },
                    leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = MutedSteel, modifier = Modifier.size(18.dp)) },
                    trailingIcon = {
                        if (tradeSearchQuery.isNotEmpty()) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                tint = MutedSteel,
                                modifier = Modifier.size(18.dp).clickable { tradeSearchQuery = "" }
                            )
                        }
                    },
                    textStyle = androidx.compose.ui.text.TextStyle(color = IceWhite, fontSize = 15.sp),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = ObsidianDark,
                        unfocusedContainerColor = ObsidianDark,
                        focusedBorderColor = NeonCyan.copy(alpha = 0.6f),
                        unfocusedBorderColor = Color.Transparent,
                        cursorColor = NeonCyan
                    )
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            if (filteredTrades.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth().padding(vertical = 36.dp), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(NeonCyan.copy(alpha = 0.08f))
                                .border(1.dp, NeonCyan.copy(alpha = 0.25f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.Inbox, contentDescription = null, tint = NeonCyan.copy(alpha = 0.6f), modifier = Modifier.size(28.dp))
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = if (allTrades.isEmpty()) "هنوز هیچ معامله‌ای ثبت نکرده‌اید" else "نمادی یافت نشد",
                            fontSize = 14.sp, fontWeight = FontWeight.Bold, color = IceWhite
                        )
                        if (allTrades.isEmpty()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "از بخش «ثبت خرید جدید» بالا، اولین معامله‌ی خود را ثبت کنید",
                                fontSize = 12.sp, color = MutedSteel, textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            } else {
                // --- بازطراحی کامل: لیست عمودی، هر معامله یک ردیف با ۴ ستون واضح ---
                // قبلاً کارت‌های دوستونه‌ی بزرگ فضای زیادی می‌گرفتند و در گوشی خوانا نبودند؛
                // الان هر معامله یک ردیف فشرده و اسکن‌پذیر است.
                filteredTrades.forEach { trade ->
                    val visual = getCoinVisual(trade.symbol)
                    val watchItem = watchlist.firstOrNull { it.symbol == trade.symbol }
                    val currentPrice = watchItem?.livePrice ?: trade.entryPrice
                    val livePnlPercent = if (trade.status == "ACTIVE") {
                        if (trade.direction == "BUY") ((currentPrice - trade.entryPrice) / trade.entryPrice) * 100
                        else ((trade.entryPrice - currentPrice) / trade.entryPrice) * 100
                    } else {
                        trade.profitLossPercent
                    }
                    val pnlColor = if (livePnlPercent >= 0) NeonGreen else NeonRed
                    var confirmDelete by remember(trade.id) { mutableStateOf(false) }
                    val entryToman = trade.entryPrice * ExchangeRateCache.usdToToman.value

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(ObsidianDark)
                            .border(1.dp, pnlColor.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                            .padding(16.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            // ستون ۱: نماد + نام فارسی (فونت بولد و بزرگ‌تر طبق درخواست خوانایی)
                            Row(modifier = Modifier.weight(1.15f), verticalAlignment = Alignment.CenterVertically) {
                                Text(text = visual.emoji, fontSize = 18.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Column {
                                    Text(text = trade.symbol, fontSize = 14.sp, fontWeight = FontWeight.Black, color = IceWhite)
                                    if (visual.persianName.isNotBlank()) {
                                        Text(text = visual.persianName, fontSize = 11.sp, color = MutedSteel, maxLines = 1)
                                    }
                                }
                            }
                            // ستون ۲: قیمت خرید (تومان و دلار، در یک خط واضح)
                            Column(modifier = Modifier.weight(1.15f), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "قیمت خرید", fontSize = 10.sp, color = MutedSteel)
                                Text(
                                    text = "${PersianDateTimeUtils.formatDouble(entryToman, 0)} ت",
                                    fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White, fontFamily = FontFamily.Monospace, maxLines = 1
                                )
                                Text(text = formatPriceText(trade.symbol, trade.entryPrice), fontSize = 11.sp, color = MutedSteel, fontFamily = FontFamily.Monospace)
                            }
                            // ستون ۳: قیمت فعلی + وضعیت سود/ضرر رنگی (فونت بزرگ‌تر طبق درخواست خوانایی)
                            Column(modifier = Modifier.weight(1.05f), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "فعلی", fontSize = 10.sp, color = MutedSteel)
                                Text(text = formatPriceText(trade.symbol, currentPrice), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White, fontFamily = FontFamily.Monospace)
                                Text(
                                    text = "${if (livePnlPercent >= 0) "+" else ""}${PersianDateTimeUtils.formatDouble(livePnlPercent, 2)}%",
                                    fontSize = 15.sp, fontWeight = FontWeight.Black, color = pnlColor, fontFamily = FontFamily.Monospace
                                )
                            }
                            // ستون ۴: دکمه‌ی دایره‌ای قرمز فروش (فقط آیکون، طبق طراحی درخواستی) + حذف
                            Column(modifier = Modifier.weight(0.95f), horizontalAlignment = Alignment.CenterHorizontally) {
                                if (trade.status == "ACTIVE") {
                                    Box(
                                        modifier = Modifier
                                            .size(38.dp)
                                            .clip(CircleShape)
                                            .background(NeonRed)
                                            .clickable { onSell(trade.symbol) },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Sell,
                                            contentDescription = "فروش",
                                            tint = Color.White,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .size(38.dp)
                                            .clip(CircleShape)
                                            .background(MutedSteel.copy(alpha = 0.18f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "بسته‌شده",
                                            tint = MutedSteel,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "حذف معامله",
                                    tint = MutedSteel.copy(alpha = 0.6f),
                                    modifier = Modifier.size(14.dp).clickable { confirmDelete = true }
                                )
                            }
                        }
                        if (confirmDelete) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(28.dp)
                                        .clip(RoundedCornerShape(9.dp))
                                        .background(NeonRed.copy(alpha = 0.15f))
                                        .border(1.dp, NeonRed.copy(alpha = 0.6f), RoundedCornerShape(9.dp))
                                        .clickable { onDeleteTrade(trade.id) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "حذف قطعی این معامله", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = NeonRed)
                                }
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(28.dp)
                                        .clip(RoundedCornerShape(9.dp))
                                        .border(1.dp, MutedSteel.copy(alpha = 0.4f), RoundedCornerShape(9.dp))
                                        .clickable { confirmDelete = false },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "انصراف", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MutedSteel)
                                }
                            }
                        }
                    }
                }
            }
        }
        }
    }
}
@Composable
fun SettingsTab() {
    var usePersianNums by remember { PersianDateTimeUtils.usePersianNumerals }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(CarbonGray)
            .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(22.dp))
            .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(NeonCyan.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = null,
                        tint = NeonCyan,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Column {
                    Text(
                        text = "تنظیمات سیستم و شخصی‌سازی",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        ),
                        color = NeonGreen
                    )
                    Text(
                        text = "پیکربندی بومی‌سازی و مشخصات پلتفرم",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                        color = MutedSteel
                    )
                }
            }

            Divider(color = Color.White.copy(alpha = 0.04f))

            // Section 1: Localization Controls
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "بومی‌سازی و ترجیحات (Localization)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = IceWhite,
                    letterSpacing = 0.5.sp
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(ObsidianDark)
                        .border(1.dp, Color.White.copy(alpha = 0.03f), RoundedCornerShape(16.dp))
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "نمایش اعداد با فونت فارسی",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeonGreen
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "تبدیل تمام اعداد، درصدها و مبالغ به حروف و ارقام فارسی",
                            fontSize = 11.sp,
                            color = MutedSteel
                        )
                    }
                    Switch(
                        checked = usePersianNums,
                        onCheckedChange = { usePersianNums = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = NeonCyan,
                            checkedTrackColor = NeonCyan.copy(alpha = 0.30f),
                            uncheckedThumbColor = MutedSteel,
                            uncheckedTrackColor = Color.White.copy(alpha = 0.05f),
                            uncheckedBorderColor = Color.White.copy(alpha = 0.1f)
                        )
                    )
                }
            }

            Divider(color = Color.White.copy(alpha = 0.04f))

            // Section: Font Size & Style
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "فونت و نمایش متن‌ها (Font & Display)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = IceWhite,
                    letterSpacing = 0.5.sp
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(ObsidianDark)
                        .border(1.dp, Color.White.copy(alpha = 0.03f), RoundedCornerShape(16.dp))
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Font size slider
                    var fontSizeLocal by remember { mutableStateOf(AppSettings.fontSizeScale.value) }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "اندازه فونت متن‌ها", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = IceWhite)
                        Box(
                            modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(NeonCyan.copy(alpha = 0.15f)).padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(text = "${fontSizeLocal.toInt()} sp", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = NeonCyan, fontFamily = FontFamily.Monospace)
                        }
                    }
                    Slider(
                        value = fontSizeLocal,
                        onValueChange = {
                            fontSizeLocal = it
                            AppSettings.setFontSize(it)
                        },
                        valueRange = 10f..18f,
                        steps = 7,
                        modifier = Modifier.fillMaxWidth(),
                        colors = SliderDefaults.colors(
                            thumbColor = NeonCyan,
                            activeTrackColor = NeonCyan,
                            inactiveTrackColor = MutedSteel.copy(alpha = 0.3f)
                        )
                    )
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "کوچک", fontSize = 11.sp, color = MutedSteel)
                        Text(text = "متوسط", fontSize = 11.sp, color = MutedSteel)
                        Text(text = "بزرگ", fontSize = 11.sp, color = MutedSteel)
                    }

                    Divider(color = Color.White.copy(alpha = 0.03f))

                    // Bold toggle
                    var fontBoldLocal by remember { mutableStateOf(AppSettings.fontBold.value) }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "فونت ضخیم (Bold)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = IceWhite)
                            Text(text = "افزایش خوانایی با فونت درشت‌تر", fontSize = 11.sp, color = MutedSteel)
                        }
                        Switch(
                            checked = fontBoldLocal,
                            onCheckedChange = {
                                fontBoldLocal = it
                                AppSettings.setFontBold(it)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = NeonCyan,
                                checkedTrackColor = NeonCyan.copy(alpha = 0.30f),
                                uncheckedThumbColor = MutedSteel,
                                uncheckedTrackColor = Color.White.copy(alpha = 0.05f)
                            )
                        )
                    }
                }
            }

            Divider(color = Color.White.copy(alpha = 0.04f))

            // Section: Notifications & Alarms
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "اعلان‌ها و هشدار سیگنال (Notifications & Alarms)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = IceWhite,
                    letterSpacing = 0.5.sp
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(ObsidianDark)
                        .border(1.dp, Color.White.copy(alpha = 0.03f), RoundedCornerShape(16.dp))
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    var notifLocal by remember { mutableStateOf(AppSettings.notificationsEnabled.value) }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "اعلان سیگنال جدید", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = IceWhite)
                            Text(text = "دریافت نوتیفیکیشن هنگام صدور سیگنال خرید یا فروش", fontSize = 11.sp, color = MutedSteel)
                        }
                        Switch(
                            checked = notifLocal,
                            onCheckedChange = {
                                notifLocal = it
                                AppSettings.setNotificationsEnabled(it)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = NeonCyan,
                                checkedTrackColor = NeonCyan.copy(alpha = 0.30f),
                                uncheckedThumbColor = MutedSteel,
                                uncheckedTrackColor = Color.White.copy(alpha = 0.05f)
                            )
                        )
                    }

                    Divider(color = Color.White.copy(alpha = 0.03f))

                    var alarmSoundLocal by remember { mutableStateOf(AppSettings.alarmSoundEnabled.value) }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "صدای هشدار (Alarm Sound)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = IceWhite)
                            Text(text = "پخش صدا هنگام رسیدن سیگنال جدید", fontSize = 11.sp, color = MutedSteel)
                        }
                        Switch(
                            checked = alarmSoundLocal,
                            onCheckedChange = {
                                alarmSoundLocal = it
                                AppSettings.setAlarmSoundEnabled(it)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = NeonCyan,
                                checkedTrackColor = NeonCyan.copy(alpha = 0.30f),
                                uncheckedThumbColor = MutedSteel,
                                uncheckedTrackColor = Color.White.copy(alpha = 0.05f)
                            )
                        )
                    }
                }
            }
            // Section 2: Developer Info Card
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "اطلاعات توسعه‌دهنده (Developer & Design)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MutedSteel,
                    letterSpacing = 0.5.sp
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(ObsidianDark)
                        .border(1.dp, Color.White.copy(alpha = 0.03f), RoundedCornerShape(16.dp))
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(text = "👤", fontSize = 14.sp)
                            Text(text = "توسعه‌دهنده سیستم:", fontSize = 13.sp, color = MutedSteel)
                        }
                        Text(text = "حامد فریفته", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = IceWhite)
                    }
                    Divider(color = Color.White.copy(alpha = 0.03f))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(text = "🌐", fontSize = 14.sp)
                            Text(text = "GitHub:", fontSize = 15.sp, color = MutedSteel)
                        }
                        // طبق درخواست کاربر: لینک گیت‌هاب قابل کلیک شد و با ضربه،
                        // مرورگر پیش‌فرض گوشی صفحه‌ی گیت‌هاب را باز می‌کند.
                        val uriHandler = LocalUriHandler.current
                        Text(
                            text = "github.com/gemcancers-debug",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeonCyan,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier.clickable {
                                uriHandler.openUri("https://github.com/gemcancers-debug")
                            }
                        )
                    }
                }
            }

            // Section 3: App Info Card
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "مشخصات و متادیتای سیستم (System Info)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MutedSteel,
                    letterSpacing = 0.5.sp
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(ObsidianDark)
                        .border(1.dp, Color.White.copy(alpha = 0.03f), RoundedCornerShape(16.dp))
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(text = "⚙️", fontSize = 14.sp)
                            Text(text = "موتور پردازشگر اصلی:", fontSize = 13.sp, color = MutedSteel)
                        }
                        Text(text = "Wale AI Engine v1.5", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = BrightGold)
                    }
                    Divider(color = Color.White.copy(alpha = 0.03f))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(text = "🛡️", fontSize = 14.sp)
                            Text(text = "نوع سیستم:", fontSize = 13.sp, color = MutedSteel)
                        }
                        Text(text = "Professional Analytics Protocol", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = IceWhite)
                    }
                }
            }
        }
    }
}
// دیکشنری توضیحات فارسی برای اصطلاحات فنی — وقتی کاربر روی هرکدام کلیک می‌کند،
// این متن در یک باکس Tooltip بزرگ و خوانا نمایش داده می‌شود.
val technicalTermExplanations = mapOf(
    "risk" to "میزان ریسک این معامله بر اساس نوسان قیمت (ATR) و فاصله‌ی حد ضرر تا نقطه‌ی ورود محاسبه می‌شود. ریسک LOW یعنی نوسان کم و امن‌تر، HIGH یعنی این دارایی به‌طور طبیعی پرنوسان است و ممکن است سریع قیمتش تغییر کند — با احتیاط بیشتری وارد شوید.",
    "stop_loss" to "حد ضرر یعنی قیمتی که اگر بازار به آن رسید، بهتر است از معامله خارج شوید تا از ضرر بیشتر جلوگیری شود. این عدد بر اساس نوسان طبیعی بازار محاسبه شده تا هم از ضرر زیاد جلوگیری کند و هم به معامله فضای کافی برای نفس کشیدن بدهد.",
    "take_profit_1" to "هدف سود اول، اولین نقطه‌ای است که می‌توانید بخشی از سود خود را ذخیره کنید. رسیدن قیمت به این نقطه به‌معنای درست بودن جهت تحلیل است.",
    "take_profit_2" to "هدف سود دوم، نقطه‌ی دورتر و بلندپروازانه‌تری است برای زمانی که روند قدرت بیشتری داشته باشد و بخواهید سود بزرگ‌تری کسب کنید.",
    "agreement" to "این عدد نشان می‌دهد موتور تحلیل تکنیکال (RSI، MACD، الگوهای قیمتی) و موتور تحلیل رفتار نهنگ‌ها (سرمایه‌گذاران بزرگ) چقدر با هم هم‌نظر هستند. هرچه این عدد بالاتر باشد، دو تحلیل مستقل به نتیجه‌ی مشابهی رسیده‌اند که اعتماد بیشتری به سیگنال می‌دهد.",
    "smart_reasons" to "این بخش دلایل مشخصی است که موتور هوش مصنوعی بر اساس آن‌ها به این نتیجه رسیده — مثل شکست ساختار قیمتی، تقاطع میانگین‌های متحرک، یا فعالیت غیرعادی حجم معاملات. هرچه دلایل بیشتری هم‌جهت باشند، سیگنال معتبرتر است.",
    "بوس" to "شکست ساختار (Break of Structure) یعنی قیمت از یک سطح مهم قبلی (بالاترین یا پایین‌ترین نقطه‌ی اخیر) عبور کرده — این معمولاً نشانه‌ی ادامه‌ی روند فعلی بازار است.",
    "چوچ" to "تغییر ساختار (Change of Character) یعنی رفتار قیمت برخلاف روند قبلی تغییر کرده — این می‌تواند نشانه‌ی اولیه‌ی برگشت یا معکوس‌شدن روند بازار باشد.",
    "فوجی" to "شکاف ارزش منصفانه (Fair Value Gap) یک ناحیه‌ی خالی در نمودار قیمت است که به‌خاطر حرکت سریع بازار ایجاد شده. قیمت معمولاً تمایل دارد در آینده به این ناحیه برگردد تا آن را «پر» کند.",
    "اردربلاک" to "بلاک سفارش (Order Block) ناحیه‌ای از قیمت است که در آن یک بازیگر بزرگ (نهنگ) حجم زیادی معامله کرده. این نواحی معمولاً به‌عنوان نقاط حمایت یا مقاومت قوی در آینده عمل می‌کنند.",
    "اما" to "میانگین متحرک نمایی (EMA) میانگین قیمت در یک بازه‌ی زمانی است که وزن بیشتری به قیمت‌های اخیر می‌دهد. مقایسه‌ی EMA کوتاه‌مدت (۵۰) و بلندمدت (۲۰۰) نشان می‌دهد روند کلی بازار صعودی است یا نزولی.",
    "آرسیای" to "شاخص قدرت نسبی (RSI) بین صفر تا صد نشان می‌دهد بازار چقدر در وضعیت اشباع خرید (بالای ۷۰) یا اشباع فروش (زیر ۳۰) است. این اندیکاتور به تشخیص نقاط احتمالی برگشت قیمت کمک می‌کند.",
    "مکدی" to "واگرایی همگرایی میانگین متحرک (MACD) قدرت و جهت روند بازار را با مقایسه‌ی دو میانگین متحرک اندازه‌گیری می‌کند. تقاطع خطوط آن معمولاً سیگنال تغییر روند محسوب می‌شود.",
    "سیامف" to "جریان پول چایکین (CMF) نشان می‌دهد فشار خرید یا فروش در حجم معاملات اخیر غالب بوده است. عدد مثبت یعنی ورود پول (خرید)، عدد منفی یعنی خروج پول (فروش).",
    "status_no_trend" to "«بی‌روند و نوسانی» یعنی میانگین‌های متحرک کوتاه‌مدت و بلندمدت (EMA 50 و 200) به‌هم نزدیک‌اند و قیمت جهت مشخصی ندارد — بازار در یک محدوده‌ی رنج در حال نوسان است، نه یک روند صعودی یا نزولی واضح. در این حالت معمولاً بهتر است صبر کرد تا جهت روشن شود.",
    "status_neutral_rsi" to "«خنثی و بدون نوسان» یعنی شاخص قدرت نسبی (RSI) در محدوده‌ی میانی (نه اشباع خرید بالای ۷۰، نه اشباع فروش زیر ۳۰) قرار دارد — یعنی نه فشار خرید غیرعادی هست، نه فشار فروش. این اندیکاتور فعلاً سیگنال برگشتی نمی‌دهد.",
    "status_flat_macd" to "«فاقد تلاقی حرکتی» یعنی دو خط MACD به‌هم نزدیک و تقریباً موازی‌اند و اخیراً تقاطع معناداری رخ نداده — یعنی مومنتوم (شتاب) روند در حال حاضر ضعیف است و اندیکاتور مکدی سیگنال قدرتمندی برای خرید یا فروش نمی‌دهد.",
    "status_low_volume_flow" to "«جریان پول خنثی و کم‌حجم» یعنی بر اساس جریان پول چایکین (CMF)، نه فشار خرید قابل‌توجهی در جریان است و نه فشار فروش — حجم معاملات اخیر برای تشخیص ورود یا خروج بازیگران بزرگ (نهنگ‌ها) کافی یا یک‌طرفه نبوده است.",
    // توضیحات هر خط از «دلایل تحلیل هوشمند» — کلید هر آیتم زیررشته‌ای منحصربه‌فرد
    // از همان متنی است که در AIEngine تولید می‌شود؛ در نمایش، هر خط با این‌ها
    // مچ می‌شود تا آیکون اطلاعات کنارش تولتیپ اختصاصی نشان دهد.
    "اشباع فروش تکنیکال" to "شاخص قدرت نسبی (RSI) به زیر ۳۰ رسیده — یعنی فروشندگان اخیراً بیش‌ازحد فعال بوده‌اند و آماری از نظر تکنیکال احتمال برگشت قیمت به سمت بالا افزایش می‌یابد.",
    "اشباع خرید تکنیکال" to "شاخص قدرت نسبی (RSI) به بالای ۷۰ رسیده — یعنی خریداران اخیراً بیش‌ازحد فعال بوده‌اند و آماری از نظر تکنیکال احتمال برگشت قیمت به سمت پایین افزایش می‌یابد.",
    "تقاطع مثبت مکدی" to "خط MACD از خط سیگنال خودش به سمت بالا عبور کرده — این یکی از رایج‌ترین نشانه‌های تغییر مومنتوم به نفع خریداران است.",
    "تقاطع منفی مکدی" to "خط MACD از خط سیگنال خودش به سمت پایین عبور کرده — این یکی از رایج‌ترین نشانه‌های تغییر مومنتوم به نفع فروشندگان است.",
    "روند صعودی بلندمدت" to "قیمت فعلی بالاتر از میانگین متحرک ۲۰۰ دوره‌ای (EMA 200) است — این میانگین معمولاً روند کلی و بلندمدت بازار را نشان می‌دهد؛ بالاتر بودن قیمت از آن، نشانه‌ی یک روند صعودی گسترده‌تر است.",
    "روند نزولی بلندمدت" to "قیمت فعلی پایین‌تر از میانگین متحرک ۲۰۰ دوره‌ای (EMA 200) است — این میانگین معمولاً روند کلی و بلندمدت بازار را نشان می‌دهد؛ پایین‌تر بودن قیمت از آن، نشانه‌ی یک روند نزولی گسترده‌تر است.",
    "بلاک خرید فعال" to "یک بلاک سفارش خرید (Order Block) نزدیک قیمت فعلی شناسایی شده — ناحیه‌ای که پیش‌تر یک بازیگر بزرگ حجم زیادی خرید انجام داده و احتمالاً به‌عنوان حمایت عمل می‌کند.",
    "بلاک فروش فعال" to "یک بلاک سفارش فروش (Order Block) نزدیک قیمت فعلی شناسایی شده — ناحیه‌ای که پیش‌تر یک بازیگر بزرگ حجم زیادی فروش انجام داده و احتمالاً به‌عنوان مقاومت عمل می‌کند.",
    "گپ نقدینگی صعودی" to "یک شکاف ارزش منصفانه‌ی (FVG) صعودی نزدیک قیمت شناسایی شده — ناحیه‌ای که قیمت به‌خاطر حرکت سریع بازار از آن به‌سرعت عبور کرده و معمولاً تمایل دارد بعداً به آن برگردد.",
    "گپ نقدینگی نزولی" to "یک شکاف ارزش منصفانه‌ی (FVG) نزولی نزدیک قیمت شناسایی شده — ناحیه‌ای که قیمت به‌خاطر حرکت سریع بازار از آن به‌سرعت عبور کرده و معمولاً تمایل دارد بعداً به آن برگردد.",
    "قدرت روند بالا" to "شاخص ADX بالای ۲۵ است — یعنی روند فعلی بازار (صعودی یا نزولی) از قدرت و مومنتوم قابل‌توجهی برخوردار است، نه یک نوسان ضعیف و بی‌جهت.",
    "قدرت روند نزولی" to "شاخص ADX بالای ۲۵ است — یعنی روند نزولی فعلی از قدرت و مومنتوم قابل‌توجهی برخوردار است، نه یک نوسان ضعیف و بی‌جهت.",
    "حجم بالای تراکنش" to "حجم معاملات اخیر به‌طور قابل‌توجهی از میانگین عادی بالاتر رفته — نشانه‌ای از ورود سرمایه‌ی بیشتر و جدیت بیشتر بازار پشت این حرکت قیمتی.",
    "انبساط صعودی بازار" to "موتور تشخیص رژیم بازار، شرایط فعلی را «انبساط صعودی» تشخیص داده — یعنی نوسان و حجم هر دو در حال افزایش هستند و روند صعودی در حال قدرت گرفتن است.",
    "انبساط نزولی بازار" to "موتور تشخیص رژیم بازار، شرایط فعلی را «انبساط نزولی» تشخیص داده — یعنی نوسان و حجم هر دو در حال افزایش هستند و روند نزولی در حال قدرت گرفتن است.",
    "فشار خرید در جریان سفارش" to "بر اساس تحلیل ریزساختار سفارش‌ها (Footprint)، حجم خرید به‌وضوح بر حجم فروش غلبه دارد — نشانه‌ای از تقاضای واقعی و فعال در بازار. توجه: این تقریبی از محل بسته‌شدن قیمت هر کندل نسبت به بازه‌ی آن (CLV) است، نه داده‌ی واقعی تیک‌به‌تیک یا Order Book.",
    "فشار فروش در جریان سفارش" to "بر اساس تحلیل ریزساختار سفارش‌ها (Footprint)، حجم فروش به‌وضوح بر حجم خرید غلبه دارد — نشانه‌ای از عرضه‌ی واقعی و فعال در بازار. توجه: این تقریبی از محل بسته‌شدن قیمت هر کندل نسبت به بازه‌ی آن (CLV) است، نه داده‌ی واقعی تیک‌به‌تیک یا Order Book.",
    "جذب سفارش خرید" to "یک بازیگر بزرگ حجم زیادی سفارش فروش را در قیمت فعلی «جذب» (خریداری) کرده بدون این‌که قیمت پایین بیاید — معمولاً نشانه‌ی حمایت قوی و پنهان در این سطح قیمتی است.",
    "جذب سفارش فروش" to "یک بازیگر بزرگ حجم زیادی سفارش خرید را در قیمت فعلی «جذب» (فروخته) کرده بدون این‌که قیمت بالا برود — معمولاً نشانه‌ی مقاومت قوی و پنهان در این سطح قیمتی است.",
    "شکست مقاومت با تأیید حجم" to "قیمت یک سطح مقاومت مهم اخیر را شکسته، و این شکست با حجم معاملات بالاتر از حد عادی همراه بوده — یعنی این یک شکست واقعی و قدرتمند است، نه یک نوسان کاذب و بی‌پشتوانه.",
    "شکست حمایت با تأیید حجم" to "قیمت یک سطح حمایت مهم اخیر را شکسته، و این شکست با حجم معاملات بالاتر از حد عادی همراه بوده — یعنی این یک شکست واقعی و قدرتمند است، نه یک نوسان کاذب و بی‌پشتوانه.",
    "واگرایی صعودی قیمت و RSI" to "قیمت پایین‌تری ثبت کرده اما RSI پایین‌تر نرفته (بلکه بالاتر رفته) — این ناهم‌خوانی بین قیمت و اندیکاتور معمولاً یک هشدار زودهنگام برای برگشت احتمالی روند به سمت بالاست.",
    "واگرایی نزولی قیمت و RSI" to "قیمت بالاتری ثبت کرده اما RSI بالاتر نرفته (بلکه پایین‌تر رفته) — این ناهم‌خوانی بین قیمت و اندیکاتور معمولاً یک هشدار زودهنگام برای برگشت احتمالی روند به سمت پایین است.",
    "تأیید نهایی لایه‌ی پریمیوم" to "این سیگنال از یک فیلتر نهایی و سخت‌گیرانه عبور کرده که فقط وقتی هم حجم معاملات کافی باشد و هم شاخص ADX قدرت روند را تأیید کند، اجازه‌ی صدور می‌دهد — این لایه برای کاهش سیگنال‌های کاذب و ضعیف اضافه شده.",
    "توسط لایه‌ی پریمیوم رد شد" to "تحلیل اولیه به یک جهت رسیده بود، اما فیلتر نهایی (که حجم معاملات و قدرت روند/ADX را چک می‌کند) آن را تأیید نکرد — یعنی شواهد کافی برای یک سیگنال قوی و قابل‌اعتماد در حال حاضر وجود ندارد، پس سیستم محافظه‌کارانه در حالت انتظار می‌ماند.",
    "بازار در حال نوسان و بی رونق" to "نه میانگین‌های متحرک جهت مشخصی نشان می‌دهند، نه مکدی و نه ADX قدرت روند قابل‌توجهی دارند — بازار در یک محدوده‌ی رنج بدون جهت غالب در حال نوسان است. در این حالت معمولاً بهترین کار صبر کردن تا مشخص‌شدن جهت است.",
    "روند ضعیف بازار" to "شاخص ADX زیر ۲۰ است — یعنی صرف‌نظر از جهت قیمت، قدرت و مومنتوم پشت این حرکت ضعیف است و ممکن است به‌راحتی معکوس شود.",
    "نبود نوسانات مکدی" to "فاصله‌ی خط MACD تا خط سیگنال آن بسیار کم است — یعنی مومنتوم بازار در حال حاضر تقریباً صاف و بدون شتاب مشخصی است.",
    "تطابق شرایط تکنیکال با عدم قطعیت بازار" to "شرایط فعلی بازار به هیچ‌کدام از الگوهای قوی و شناخته‌شده‌ی موتور (نه روند قوی صعودی، نه نزولی) به‌طور واضح شباهت ندارد — سیستم به‌جای حدس‌زدن، صادقانه این عدم قطعیت را اعلام می‌کند."
)

// چون خطوط «دلایل تحلیل هوشمند» متن آزاد هستند (نه یک کلید ثابت)، این تابع
// بررسی می‌کند آیا متن یکی از عبارات شناخته‌شده‌ی بالا را در خود دارد یا نه، تا
// آیکون تولتیپ کنار هر خط فقط وقتی نمایش داده شود که توضیحی واقعی برایش موجود باشد.
fun findReasonTooltipKey(reason: String): String? {
    return technicalTermExplanations.keys.firstOrNull { key ->
        key.length > 4 && reason.contains(key)
    }
}

@Composable
fun TooltipExplanationBox(termKey: String, onDismiss: () -> Unit) {
    val explanation = technicalTermExplanations[termKey] ?: return
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(ObsidianDark)
                .border(1.5.dp, NeonCyan.copy(alpha = 0.4f), RoundedCornerShape(18.dp))
                .padding(16.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = explanation,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = IceWhite,
                        lineHeight = 20.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "بستن",
                        tint = MutedSteel,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { onDismiss() }
                            .padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

fun formatPriceText(symbol: String, price: Double): String {
    val isToman = symbol.contains("TOMAN") ||
                  symbol.contains("IRT") ||
                  symbol.contains("COIN") ||
                  symbol == "GOLD_18K" ||
                  symbol == "AED_DIRHAM"

    return if (isToman) {
        "${com.h2signals.app.utils.PersianDateTimeUtils.formatLongWithCommas(price.toLong())} تومان"
    } else {
        // نرخ دلار به تومان از کش زنده خوانده می‌شود (هر ۵ دقیقه به‌روزرسانی می‌شود)
        val usdToTomanRate = com.h2signals.app.utils.ExchangeRateCache.usdToToman.value
        val tomanPrice = (price * usdToTomanRate).toLong()
        val usdStr = "$${PersianDateTimeUtils.formatDouble(price, if (price < 1.0) 4 else 2)}"
        val tomanStr = "${com.h2signals.app.utils.PersianDateTimeUtils.formatLongWithCommas(tomanPrice)} تومان"
        "$usdStr ($tomanStr)"
    }
}