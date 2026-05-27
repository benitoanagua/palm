package com.a2.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.apay.R

val Literata = FontFamily(
    Font(R.font.literata_medium, FontWeight.Medium),
    Font(R.font.literata_semibold, FontWeight.SemiBold),
)

val Manrope = FontFamily(
    Font(R.font.manrope_regular, FontWeight.Normal),
    Font(R.font.manrope_medium, FontWeight.Medium),
    Font(R.font.manrope_semibold, FontWeight.SemiBold),
)

val A2Typography = Typography(
    displayLarge  = TextStyle(fontFamily = Literata, fontSize = 28.sp, fontWeight = FontWeight.SemiBold, lineHeight = 34.sp, letterSpacing = 0.28.sp),
    displayMedium = TextStyle(fontFamily = Literata, fontSize = 22.sp, fontWeight = FontWeight.Medium,   lineHeight = 28.sp, letterSpacing = 0.22.sp),
    headlineLarge = TextStyle(fontFamily = Literata, fontSize = 20.sp, fontWeight = FontWeight.SemiBold, lineHeight = 26.sp, letterSpacing = 0.2.sp),
    titleLarge    = TextStyle(fontFamily = Literata, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, lineHeight = 24.sp),
    titleMedium   = TextStyle(fontFamily = Literata, fontSize = 16.sp, fontWeight = FontWeight.Medium,   lineHeight = 22.sp),
    bodyLarge     = TextStyle(fontFamily = Manrope, fontSize = 16.sp, fontWeight = FontWeight.Normal,   lineHeight = 26.sp),
    bodyMedium    = TextStyle(fontFamily = Manrope, fontSize = 14.sp, fontWeight = FontWeight.Normal,   lineHeight = 23.sp),
    labelSmall    = TextStyle(fontFamily = Manrope, fontSize = 12.sp, fontWeight = FontWeight.Medium,   lineHeight = 16.sp),
)

object A2TextStyle {
    val DisplayXxl  = TextStyle(fontFamily = Literata, fontSize = 32.sp, fontWeight = FontWeight.SemiBold, lineHeight = 38.sp, letterSpacing = 0.32.sp)
    val DisplayLg   = TextStyle(fontFamily = Literata, fontSize = 22.sp, fontWeight = FontWeight.SemiBold, lineHeight = 28.sp, letterSpacing = 0.22.sp)
    val Headline    = TextStyle(fontFamily = Literata, fontSize = 20.sp, fontWeight = FontWeight.SemiBold, lineHeight = 26.sp, letterSpacing = 0.2.sp)
    val TitleLg     = TextStyle(fontFamily = Literata, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, lineHeight = 24.sp)
    val TitleMd     = TextStyle(fontFamily = Literata, fontSize = 16.sp, fontWeight = FontWeight.Medium,   lineHeight = 22.sp)
    val Body        = TextStyle(fontFamily = Manrope, fontSize = 16.sp, fontWeight = FontWeight.Normal,   lineHeight = 26.sp)
    val BodyMedium  = TextStyle(fontFamily = Manrope, fontSize = 14.sp, fontWeight = FontWeight.Normal,   lineHeight = 23.sp)
    val BodySmall   = BodyMedium
    val Small       = BodyMedium
    val Caption     = TextStyle(fontFamily = Manrope, fontSize = 12.sp, fontWeight = FontWeight.Medium,   lineHeight = 16.sp)
    val ButtonLabel = TextStyle(fontFamily = Manrope, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, lineHeight = 14.sp)
    val DataLabel   = TextStyle(fontFamily = Manrope, fontSize = 13.sp, fontWeight = FontWeight.Medium,   lineHeight = 18.sp)
}
