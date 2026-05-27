package com.a2.ui.theme

import androidx.compose.ui.graphics.Color

// ─── 30° — Ivory Linen / Warm Structure ────────────────────────────────────
// Fondos: lino, blanco hueso, lino medio
// Acento: terracota templada, salvia, madera tostada
// Texto:  grafito cálido, tierra media

// ─── FONDOS ─────────────────────────────────────────────────────────────────
val FondoBase            = Color(0xFFF8F5EF)
val SuperficiePrimaria   = Color(0xFFFAFAF8)
val SuperficieSecundaria = Color(0xFFF2EEE7)

// ─── ACENTOS ────────────────────────────────────────────────────────────────
val Terracota            = Color(0xFFB05E3C)
val Salvia               = Color(0xFF6A8B74)
val MaderaTostada        = Color(0xFF8C7B6A)

// ─── TEXTO ──────────────────────────────────────────────────────────────────
val TextoGrafito         = Color(0xFF3A3530)
val TextoTierra          = Color(0xFF7A6E65)
val TextoMutado          = Color(0xFFA89F96)

// ─── SEMÁNTICOS ─────────────────────────────────────────────────────────────
val Error                = Color(0xFFC0392B)
val Warning              = Color(0xFFD4A24E)
val Success              = Color(0xFF4A7C59)
val Info                 = Salvia

val ErrorBannerBg        = Color(0xFFFDF0EF)
val SuccessBannerBg      = Color(0xFFF0F7F2)
val WarningBannerBg      = Color(0xFFFDF8F0)
val InfoBannerBg         = Color(0xFFF2F0EE)

// ─── BORDES ─────────────────────────────────────────────────────────────────
val BordeSutil           = Color(0x338C7B6A)
val BordeTerracota       = Color(0xFFB05E3C)

// ─── SOMBRAS ────────────────────────────────────────────────────────────────
val SombraE1             = Color(0x0F3A3530)
val SombraE2             = Color(0x143A3530)
val SombraE3             = Color(0x1F3A3530)
val SombraE4             = Color(0x2E3A3530)

// ─── OVERLAYS ───────────────────────────────────────────────────────────────
val Scrim                = Color(0x803A3530)
val DisabledAlpha        = Color(0x733A3530)

// ─── ALIASES PRIMARIOS ──────────────────────────────────────────────────────
val PrimaryElectric      = Terracota
val PrimaryLight         = Color(0xFFC97452)
val PrimaryDark          = Color(0xFF8E4A2E)
val PrimaryContainer     = Color(0xFFF5E6E0)
val PrimaryMuted         = Color(0xFFD4A89A)

// ─── ALIASES SECUNDARIOS ────────────────────────────────────────────────────
val SecondaryElectric    = Salvia
val SecondaryLight       = Color(0xFF8DAA94)
val SecondaryDark        = Color(0xFF4E6B56)
val SecondaryContainer   = Color(0xFFE4EDE6)

// ─── ALIASES TERCIARIOS ─────────────────────────────────────────────────────
val TertiaryElectric     = MaderaTostada
val TertiaryLight        = Color(0xFFA89888)
val TertiaryDark         = Color(0xFF6E5F52)
val TertiaryContainer    = Color(0xFFF0EDEA)

// ─── BACKGROUND ALIASES ─────────────────────────────────────────────────────
val Background           = FondoBase
val Surface              = SuperficiePrimaria
val SurfaceVariant       = SuperficieSecundaria

// ─── TEXT ALIASES ───────────────────────────────────────────────────────────
val TextPrimary          = TextoGrafito
val TextSecondary        = TextoTierra
val TextTertiary         = Color(0xFFB5ADA6)
val TextMuted            = TextoMutado
val TextOnColor          = Color(0xFFFFFFFF)

// ─── LEGACY ALIASES ─────────────────────────────────────────────────────────
val PrimaryDeepBlue      = TextoGrafito
val White                = Color(0xFFFFFFFF)
val AccentElectric       = Terracota
val BrandTeal            = Terracota

// ─── SHIMMER ────────────────────────────────────────────────────────────────
val ShimmerBase          = Color(0x1F8C7B6A)
val ShimmerMid           = Color(0x4D8C7B6A)
