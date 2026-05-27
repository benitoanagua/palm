package com.a2.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val A2Typography = Typography(
    displayLarge  = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.SemiBold, lineHeight = 34.sp, letterSpacing = 0.28.sp),
    displayMedium = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium,   lineHeight = 26.sp, letterSpacing = 0.2.sp),
    headlineLarge = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold, lineHeight = 24.sp, letterSpacing = 0.18.sp),
    titleLarge    = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold, lineHeight = 22.sp),
    titleMedium   = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium,   lineHeight = 20.sp),
    bodyLarge     = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal,   lineHeight = 23.sp),
    bodyMedium    = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Normal,   lineHeight = 20.sp),
    labelSmall    = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Medium,   lineHeight = 14.sp),
)

object A2TextStyle {
    val DisplayLg   = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold, lineHeight = 26.sp, letterSpacing = 0.2.sp)
    val Headline    = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold, lineHeight = 24.sp, letterSpacing = 0.18.sp)
    val TitleLg     = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold, lineHeight = 22.sp)
    val TitleMd     = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium,   lineHeight = 20.sp)
    val Body        = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal,   lineHeight = 23.sp)
    val BodyMedium  = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Normal,   lineHeight = 20.sp)
    val BodySmall   = BodyMedium
    val Small       = BodyMedium
    val Caption     = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Medium,   lineHeight = 14.sp)
    val ButtonLabel = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.SemiBold, lineHeight = 13.sp)
}
