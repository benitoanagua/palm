package com.a2.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val A2ColorScheme = lightColorScheme(
    primary          = Terracota,
    onPrimary        = White,
    primaryContainer = PrimaryContainer,
    secondary        = Salvia,
    onSecondary      = White,
    tertiary         = MaderaTostada,
    onTertiary       = White,
    background       = FondoBase,
    onBackground     = TextoGrafito,
    surface          = SuperficiePrimaria,
    onSurface        = TextoGrafito,
    surfaceVariant   = SuperficieSecundaria,
    error            = Error,
    onError          = White,
    outline          = BordeSutil,
    outlineVariant   = BordeSutil,
)

@Composable
fun A2Theme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = A2ColorScheme,
        typography  = A2Typography,
        content     = content
    )
}
