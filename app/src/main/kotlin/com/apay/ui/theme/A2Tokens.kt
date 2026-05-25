package com.a2.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ─── A2 Design Tokens ───────────────────────────────────────────────────────

object A2Tokens {
    // Spacing (8pt grid)
    val Space0  = 0.dp
    val Space1  = 2.dp
    val Space2  = 4.dp
    val Space3  = 6.dp
    val Space4  = 8.dp
    val Space5  = 10.dp
    val Space6  = 12.dp
    val Space8  = 16.dp
    val Space10 = 20.dp
    val Space12 = 24.dp
    val Space16 = 32.dp

    // Border Radius
    val RadiusPill   = 9999.dp
    val RadiusFino   = 2.dp
    val RadiusSmall  = 4.dp
    val RadiusMedium = 8.dp
    val RadiusLarge  = 12.dp
    val RadiusMax    = 16.dp

    val RadiusButton = RadiusPill
    val RadiusInput  = RadiusFino
    val RadiusCard   = RadiusMax
    val RadiusCardLg = 24.dp

    // Component sizes
    val ButtonHeight = 36.dp
    val InputHeight  = 36.dp
    val TouchTarget  = 44.dp

    // Avatar sizes
    val AvatarXs = 24.dp
    val AvatarSm = 32.dp
    val AvatarMd = 40.dp
    val AvatarLg = 48.dp
    val AvatarXl = 56.dp
    val AvatarXxl = 72.dp

    // Icon sizes
    val IconSm = 12.dp
    val IconMd = 16.dp
    val IconLg = 18.dp
    val IconXL = 20.dp

    // Animations (ms)
    val AnimFast = 120
    val AnimBase = 200
    val AnimSlow = 300
}

// ── Spacing shorthand ─────────────────────────────────────────────────────────
object A2Spacing {
    val xs  = A2Tokens.Space1
    val sm  = A2Tokens.Space2
    val md  = A2Tokens.Space3
    val lg  = A2Tokens.Space5
    val xl  = A2Tokens.Space6
    val xxl = A2Tokens.Space10
}

object A2Radius {
    val input  = A2Tokens.RadiusInput
    val button = A2Tokens.RadiusButton
    val card   = A2Tokens.RadiusCard
    val tag    = A2Tokens.RadiusPill
}

object A2Shapes {
    val Button = RoundedCornerShape(A2Tokens.RadiusButton)
    val Card   = RoundedCornerShape(A2Tokens.RadiusCard)
    val Input  = RoundedCornerShape(A2Tokens.RadiusInput)
    val Pill   = RoundedCornerShape(A2Tokens.RadiusPill)
}
