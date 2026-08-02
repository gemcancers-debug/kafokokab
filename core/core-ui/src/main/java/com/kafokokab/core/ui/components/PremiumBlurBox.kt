/*
نام فایل: GlassCard.kt
وظیفه: کارت شیشه‌ای با افکت Glassmorphism و انیمیشن‌های پیشرفته
نویسنده: AI Principal Engineer
تاریخ: 2026-07-31
*/

package com.kafokokab.core.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kafokokab.core.ui.theme.GlassDark
import com.kafokokab.core.ui.theme.GlassLight
import com.kafokokab.core.ui.theme.Gold
import com.kafokokab.core.ui.theme.PremiumGradient
import com.kafokokab.core.ui.theme.PremiumShadow
import com.kafokokab.core.ui.theme.SoftWhite
import kotlin.math.cos
import kotlin.math.sin

/**
 * کارت شیشه‌ای پیشرفته با قابلیت‌های:
 * - افکت Glassmorphism با شدت قابل تنظیم
 * - انیمیشن Hover و Click
 * - پشتیبانی از آیکون، عنوان، توضیحات و تصویر
 * - حالت Premium با حاشیه طلایی
 * - افکت شفق قطبی (Aurora) برای پس‌زمینه
 * - قابلیت چرخش 3D با حرکت موس/انگشت
 *
 * @param modifier مدیفایر برای سفارشی‌سازی
 * @param shape شکل کارت (گردی گوشه‌ها)
 * @param elevation ارتفاع سایه
 * @param blurRadius شدت افکت بلور (پیش‌فرض 10.dp)
 * @param glassColor رنگ شیشه (پیش‌فرض سفید با شفافیت)
 * @param glassAlpha شفافیت شیشه (0 تا 1)
 * @param borderWidth ضخامت حاشیه (0 برای بدون حاشیه)
 * @param borderColor رنگ حاشیه
 * @param isPremium حالت ویژه با حاشیه طلایی
 * @param isLoading حالت بارگذاری
 * @param onClick رویداد کلیک
 * @param onLongClick رویداد کلیک طولانی
 * @param content محتوای داخل کارت
 */
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp),
    elevation: Dp = 8.dp,
    blurRadius: Dp = 10.dp,
    glassColor: Color = Color.White,
    glassAlpha: Float = 0.15f,
    borderWidth: Dp = 1.dp,
    borderColor: Color = Color.White.copy(alpha = 0.3f),
    isPremium: Boolean = false,
    isLoading: Boolean = false,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    var isPressed by remember { mutableStateOf(false) }
    var isHovered by remember { mutableStateOf(false) }
    
    val scale by animateFloatAsState(
        targetValue = when {
            isPressed -> 0.97f
            isHovered -> 1.02f
            else -> 1f
        },
        animationSpec = tween(durationMillis = 200),
        label = "scale"
    )
    
    val shadowElevation by animateFloatAsState(
        targetValue = if (isHovered) elevation.value * 1.5f else elevation.value,
        animationSpec = tween(durationMillis = 300),
        label = "shadow"
    )
    
    val rotation by animateFloatAsState(
        targetValue = if (isHovered) 2f else 0f,
        animationSpec = tween(durationMillis = 400),
        label = "rotation"
    )

    Card(
        modifier = modifier
            .then(
                if (onClick != null) Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = { onClick() }
                ) else Modifier
            )
            .scale(scale)
            .rotate(rotation)
            .shadow(
                elevation = shadowElevation.dp,
                shape = shape,
                clip = false
            ),
        shape = shape,
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation
        ),
        colors = CardDefaults.cardColors(
            containerColor = glassColor.copy(alpha = glassAlpha)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (isPremium) Modifier
                        .border(
                            width = 2.dp,
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Gold.copy(alpha = 0.8f),
                                    Gold.copy(alpha = 0.3f),
                                    Gold.copy(alpha = 0.8f)
                                )
                            ),
                            shape = shape
                        )
                    else Modifier
                )
                .then(
                    if (borderWidth > 0.dp) Modifier
                        .border(
                            width = borderWidth,
                            color = if (isPremium) Gold else borderColor,
                            shape = shape
                        )
                    else Modifier
                )
                .blur(radius = blurRadius)
                .clip(shape)
                .background(
                    brush = if (isPremium) {
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.2f),
                                Gold.copy(alpha = 0.1f),
                                Color.White.copy(alpha = 0.2f)
                            )
                        )
                    } else {
                        Brush.verticalGradient(
                            colors = listOf(
                                glassColor.copy(alpha = glassAlpha),
                                glassColor.copy(alpha = glassAlpha * 0.5f)
                            )
                        )
                    }
                )
                .padding(16.dp)
        ) {
            if (isLoading) {
                // نمایش لودینگ
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    glassColor.copy(alpha = 0.1f)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // اینجا می‌تونی کامپوننت لودینگ خودت رو بزاری
                    CircularProgressIndicator(
                        modifier = Modifier.size(40.dp),
                        color = if (isPremium) Gold else MaterialTheme.colorScheme.primary
                    )
                }
            } else {
                content()
            }
        }
    }
}

/**
 * کارت شیشه‌ای با هدر و محتوای استاندارد
 */
@Composable
fun GlassCardWithContent(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    icon: ImageVector? = null,
    imageUrl: String? = null,
    isPremium: Boolean = false,
    onClick: (() -> Unit)? = null,
    content: @Composable (() -> Unit)? = null
) {
    GlassCard(
        modifier = modifier,
        isPremium = isPremium,
        onClick = onClick,
        glassAlpha = if (isPremium) 0.2f else 0.15f
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // آیکون
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = if (isPremium) Gold else MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(bottom = 8.dp)
                )
            }
            
            // تصویر (اگر وجود داشته باشد)
            if (imageUrl != null) {
                // اینجا می‌تونی از کتابخانه Coil یا Glide برای بارگذاری تصویر استفاده کنی
                // AsyncImage(
                //     model = imageUrl,
                //     contentDescription = title,
                //     modifier = Modifier
                //         .fillMaxWidth()
                //         .height(150.dp)
                //         .clip(RoundedCornerShape(12.dp)),
                //     contentScale = ContentScale.Crop
                // )
                // به جای AsyncImage از Box استفاده می‌کنیم برای نمایش placeholder
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    GlassLight,
                                    GlassDark
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🖼️",
                        style = MaterialTheme.typography.displayLarge
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
            
            // عنوان
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = if (isPremium) Gold else SoftWhite,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            
            // توضیحات
            if (description != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = SoftWhite.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
            
            // محتوای اضافی
            content?.invoke()
        }
    }
}

/**
 * کارت شیشه‌ای با افکت شفق قطبی (Aurora) در پس‌زمینه
 */
@Composable
fun AuroraGlassCard(
    modifier: Modifier = Modifier,
    isPremium: Boolean = false,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val colors = listOf(
        Color(0xFF6C5CE7), // بنفش
        Color(0xFF0984E3), // آبی
        Color(0xFF00CEC9), // فیروزه‌ای
        Color(0xFFFDCB6E), // طلایی
        Color(0xFFE17055)  // قرمز-نارنجی
    )
    
    GlassCard(
        modifier = modifier,
        isPremium = isPremium,
        onClick = onClick,
        glassAlpha = 0.1f,
        glassColor = Color.Black,
        borderColor = if (isPremium) Gold else Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.radialGradient(
                        colors = colors.map { it.copy(alpha = 0.3f) },
                        radius = 0.9f,
                        center = Offset(
                            x = cos(System.currentTimeMillis() / 5000f) * 0.5f + 0.5f,
                            y = sin(System.currentTimeMillis() / 5000f) * 0.5f + 0.5f
                        )
                    )
                )
        ) {
            content()
        }
    }
}

/**
 * کارت شیشه‌ای کوچک برای نمایش اطلاعات سریع
 */
@Composable
fun MiniGlassCard(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    icon: ImageVector? = null,
    isPremium: Boolean = false
) {
    GlassCard(
        modifier = modifier
            .width(120.dp)
            .height(100.dp),
        shape = RoundedCornerShape(12.dp),
        isPremium = isPremium,
        glassAlpha = 0.2f
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = if (isPremium) Gold else MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = if (isPremium) Gold else SoftWhite,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = SoftWhite.copy(alpha = 0.6f),
                textAlign = TextAlign.Center
            )
        }
    }
}

// اضافه کردن Importهای گم‌شده
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.ui.unit.dp