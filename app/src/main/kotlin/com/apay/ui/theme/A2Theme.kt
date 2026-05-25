package com.a2.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val A2ColorScheme = darkColorScheme(
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
fun A2Theme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = A2ColorScheme,
        typography  = A2Typography,
        content     = content
    )
}
