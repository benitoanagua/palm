package com.a2.ui.theme

import androidx.compose.ui.graphics.Color

// ─── A2 — Color System "Primary Dominante" ──────────────────────────────────
// Filosofía: 90% Primary (Teal), 8% Secondary (Púrpura), 2% Tertiary (Violeta)
// Identidad: "A2 = Teal Profundo" — Secondary y Tertiary SOLO para casos excepcionales

// ─── BACKGROUNDS ───────────────────────────────────────────────────────────────
val FondoEspacial      = Color(0xFF020914)
val SuperficieCristal  = Color(0xFF091424)
val SuperficieElevada  = Color(0xFF122238)
val SuperficieHighest  = Color(0xFF203754)

// ─── PRIMARY (90% de uso) — Teal Profundo ──────────────────────────────────────
val PrimaryElectric    = Color(0xFF009688)
val PrimaryLight       = Color(0xFF4DB6AC)
val PrimaryDark        = Color(0xFF00695F)
val PrimaryContainer   = Color(0xFF003D35)
val PrimaryMuted       = Color(0xFF1A5C55)

// ─── SECONDARY (8% de uso) — Púrpura Místico ───────────────────────────────────
val SecondaryElectric  = Color(0xFF9C27B0)
val SecondaryLight     = Color(0xFFBA68C8)
val SecondaryDark      = Color(0xFF6A1B9A)
val SecondaryContainer = Color(0xFF3D1440)

// ─── TERTIARY (2% de uso) — Violeta Eléctrico ─────────────────────────────────
val TertiaryElectric   = Color(0xFF673AB7)
val TertiaryLight      = Color(0xFF8B6BC7)
val TertiaryDark       = Color(0xFF4527A0)
val TertiaryContainer  = Color(0xFF1F1140)

// ─── SEMÁNTICOS ────────────────────────────────────────────────────────────────
val Error              = Color(0xFFFF4545)
val Warning            = Color(0xFFFF9F29)
val Info               = SecondaryElectric
val Success            = PrimaryElectric

val InfoBannerBg       = Color(0xFF0C2A31)
val SuccessBannerBg    = Color(0xFF0C2A1D)
val WarningBannerBg    = Color(0xFF2A1F0C)
val ErrorBannerBg      = Color(0xFF2A0C0C)

// ─── TEXTO ─────────────────────────────────────────────────────────────────────
val TextPrimary        = Color(0xFFF0F5FA)
val TextSecondary      = Color(0xFFBACAD6)
val TextTertiary       = Color(0xFF859EA1)
val TextMuted          = Color(0xFF52667A)
val TextOnColor        = Color(0xFF001526)

// ─── GLOWS ─────────────────────────────────────────────────────────────────────
val PrimaryGlow        = Color(0x40009688)
val PrimaryGlowHover   = Color(0x73009688)
val PrimaryGlowMax     = Color(0xB3009688)
val SecondaryGlow      = Color(0x409C27B0)
val ErrorGlow          = Color(0x40FF4545)
val GlowSoft           = Color(0x33009688)

// ─── BORDERS ───────────────────────────────────────────────────────────────────
val BorderSubtle       = Color(0x2699C2FF)
val BorderMedium       = Color(0x4D99C2FF)
val BorderStrong       = Color(0x8099C2FF)
val Outline            = Color(0xFF243B54)
val OutlineVariant     = Color(0xFF162536)

// ─── OVERLAYS ──────────────────────────────────────────────────────────────────
val SombraSuave        = Color(0x99000814)
val BlancoSuave        = Color(0x1399C2FF)
val SuperficieAlpha    = Color(0x1F99C2FF)
val DisabledAlpha      = Color(0x4DBACAD6)
val Scrim              = Color(0xE6010814)

val ShimmerBase        = Color(0x1F99C2FF)
val ShimmerMid         = Color(0x4D99C2FF)

// ─── ALIASES ───────────────────────────────────────────────────────────────────
val Background         = FondoEspacial
val AccentElectric     = PrimaryElectric
val BrandTeal          = PrimaryElectric
