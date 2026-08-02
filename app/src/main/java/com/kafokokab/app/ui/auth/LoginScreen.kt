/*
نام فایل: LoginScreen.kt
وظیفه: صفحه ورود اصلی + اتصال واقعی به Google Sign-In
نویسنده: AI Principal Engineer
تاریخ: 2026-08-01
آخرین تغییر: 2026-08-02 - اضافه شدن منطق واقعی ورود با گوگل
*/

package com.kafokokab.app.ui.auth

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier.modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
 * صفحه ورود اصلی.
 *
 * @param onLoginSuccess بعد از ورود موفق صدا زده می‌شود
 * @param onPhoneClick کلیک روی ورود با شماره تلفن (فعلاً فقط UI)
 */
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {},
    onPhoneClick: () -> Unit = {},
    viewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val googleSignInHelper = remember { GoogleSignInHelper(context) }

    // لانچر برای دریافت نتیجه صفحه انتخاب اکانت گوگل
    val signInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val idToken = googleSignInHelper.extractIdToken(result.data)
            if (idToken != null) {
                viewModel.signInWithGoogle(idToken)
            } else {
                Toast.makeText(context, "ورود با گوگل ناموفق بود", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // واکنش به تغییر وضعیت ورود
    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is AuthUiState.Success -> {
                onLoginSuccess()
                viewModel.resetState()
            }
            is AuthUiState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
                viewModel.resetState()
            }
            else -> Unit
        }
    }

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
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // عنوان
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 80.dp)
            ) {
                Text(
                    text = "کف و کوکب",
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp
                    ),
                    color = SoftWhite,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "آسترولوژی، زبان نمادین آسمان است.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = SoftWhite.copy(alpha = 0.75f),
                    textAlign = TextAlign.Center
                )
            }

            // نماد ماه
            ZodiacMoonPlaceholder()

            // دکمه‌ها
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 48.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // دکمه ورود با گوگل
                LoginButton(
                    text = "ورود با جیمیل",
                    enabled = uiState !is AuthUiState.Loading,
                    trailingContent = {
                        Text(
                            text = "G",
                            color = Color(0xFF4285F4),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    },
                    onClick = {
                        signInLauncher.launch(googleSignInHelper.getSignInIntent())
                    }
                )

                // دکمه ورود با شماره تلفن (فعلاً فقط UI)
                LoginButton(
                    text = "ورود با شماره تلفن",
                    enabled = uiState !is AuthUiState.Loading,
                    trailingContent = {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null,
                            tint = SoftWhite,
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    onClick = onPhoneClick
                )
            }
        }

        // نمایش لودینگ
        if (uiState is AuthUiState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.45f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = NeonPink)
            }
        }
    }
}

@Composable
private fun LoginButton(
    text: String,
    enabled: Boolean = true,
    trailingContent: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        MysticPurple.copy(alpha = if (enabled) 0.35f else 0.15f),
                        NeonPink.copy(alpha = if (enabled) 0.25f else 0.1f)
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        SoftWhite.copy(alpha = 0.25f),
                        Color.Transparent
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = SoftWhite.copy(alpha = if (enabled) 1f else 0.5f),
            modifier = Modifier.align(Alignment.Center)
        )
        Box(modifier = Modifier.align(Alignment.CenterEnd)) {
            trailingContent()
        }
    }
}

@Composable
private fun ZodiacMoonPlaceholder() {
    Box(
        modifier = Modifier
            .size(220.dp)
            .clip(RoundedCornerShape(50))
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        MysticPurple.copy(alpha = 0.4f),
                        Color.Transparent
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "☽",
            fontSize = 96.sp,
            color = Gold.copy(alpha = 0.9f)
        )
    }
}
