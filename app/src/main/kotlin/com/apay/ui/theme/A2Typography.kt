package com.a2.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val A2Typography = Typography(
    displayLarge  = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold,      lineHeight = 38.sp),
    displayMedium = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold,      lineHeight = 28.sp),
    headlineLarge = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.SemiBold,  lineHeight = 24.sp),
    titleLarge    = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.SemiBold,  lineHeight = 22.sp),
    titleMedium   = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Medium,    lineHeight = 20.sp),
    bodyLarge     = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Normal,    lineHeight = 20.sp),
    bodyMedium    = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Normal,    lineHeight = 16.sp),
    labelSmall    = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Medium,    lineHeight = 14.sp),
)

// ── Aliases de tipografía para componentes ────────────────────────────────────
object A2TextStyle {
    val DisplayLg   = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold,     lineHeight = 28.sp)
    val Headline    = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.SemiBold, lineHeight = 24.sp)
    val TitleLg     = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.SemiBold, lineHeight = 22.sp)
    val TitleMd     = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Medium,   lineHeight = 20.sp)
    val Body        = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Normal,   lineHeight = 20.sp)
    val BodyMedium  = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Normal,   lineHeight = 16.sp)
    val Small       = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Normal,   lineHeight = 16.sp)
    val Caption     = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Medium,   lineHeight = 14.sp)
    val ButtonLabel = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.SemiBold, lineHeight = 13.sp)
}
