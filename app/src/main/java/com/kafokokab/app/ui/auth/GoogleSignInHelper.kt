/*
نام فایل: GoogleSignInHelper.kt
مسیر: app/.../ui/auth/
وظیفه: کمک‌کننده برای راه‌اندازی و اجرای Google Sign-In
نویسنده: AI Principal Engineer
تاریخ: 2026-08-02
آخرین تغییر: 2026-08-04 - بهبود پیام خطا و برگرداندن کد وضعیت

نکته مهم:
اگر ورود شکست خورد، احتمالاً باید google-services.json را بعد از
فعال کردن Google Sign-In دوباره از Firebase دانلود کنید
تا بخش oauth_client پر شود.
*/

package com.kafokokab.app.ui.auth

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.kafokokab.app.R

/**
 * نتیجه استخراج توکن از Google Sign-In.
 */
sealed class GoogleSignInResult {
    data class Success(val idToken: String) : GoogleSignInResult()
    data class Failure(val message: String, val statusCode: Int? = null) : GoogleSignInResult()
}

/**
 * کلاس کمکی برای Google Sign-In.
 * از Activity/Composable صدا زده می‌شود.
 */
class GoogleSignInHelper(private val context: Context) {

    private val googleSignInClient: GoogleSignInClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        GoogleSignIn.getClient(context, gso)
    }

    /** Intent برای شروع صفحه انتخاب اکانت گوگل */
    fun getSignInIntent(): Intent = googleSignInClient.signInIntent

    /**
     * نتیجه Activity را پردازش می‌کند و Success یا Failure برمی‌گرداند.
     */
    fun processSignInResult(data: Intent?): GoogleSignInResult {
        return try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            val token = account?.idToken
            if (token.isNullOrBlank()) {
                GoogleSignInResult.Failure(
                    message = "توکن دریافت نشد. احتمالاً Web Client ID اشتباه است.",
                    statusCode = null
                )
            } else {
                GoogleSignInResult.Success(token)
            }
        } catch (e: ApiException) {
            val userMessage = when (e.statusCode) {
                10 -> "خطای تنظیمات (کد ۱۰). Web Client ID را در Firebase درست کنید."
                12501 -> "ورود لغو شد."
                7 -> "اتصال اینترنت برقرار نیست."
                else -> "ورود با گوگل ناموفق بود (کد ${e.statusCode})."
            }
            GoogleSignInResult.Failure(message = userMessage, statusCode = e.statusCode)
        } catch (e: Exception) {
            GoogleSignInResult.Failure(
                message = e.message ?: "خطای ناشناخته در ورود با گوگل",
                statusCode = null
            )
        }
    }

    fun signOut() {
        googleSignInClient.signOut()
    }
}
