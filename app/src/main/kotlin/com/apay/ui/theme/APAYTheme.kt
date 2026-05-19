package com.apay.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val APAYColorScheme = darkColorScheme(
    primary          = PrimaryElectric,
    onPrimary        = TextOnColor,
    primaryContainer = PrimaryContainer,
    secondary        = SecondaryElectric,
    tertiary         = TertiaryElectric,
    background       = FondoEspacial,
    surface          = SuperficieCristal,
    onBackground     = TextPrimary,
    onSurface        = TextPrimary,
    error            = Error,
    outline          = Outline,
)

@Composable
fun APAYTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = APAYColorScheme,
        typography  = APAYTypography,
        content     = content
    )
}
