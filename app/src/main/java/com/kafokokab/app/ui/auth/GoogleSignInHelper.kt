/*
نام فایل: GoogleSignInHelper.kt
مسیر: app/.../ui/auth/
وظیفه: کمک‌کننده برای راه‌اندازی و اجرای Google Sign-In
نویسنده: AI Principal Engineer
تاریخ: 2026-08-02

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
     * نتیجه Activity را پردازش می‌کند و idToken را برمی‌گرداند.
     */
    fun extractIdToken(data: Intent?): String? {
        return try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            account?.idToken
        } catch (e: ApiException) {
            null
        }
    }

    fun signOut() {
        googleSignInClient.signOut()
    }
}
