package com.a2.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush

object A2Gradients {
    fun primary(): Brush = Brush.linearGradient(
        colors = listOf(PrimaryElectric, PrimaryLight),
        start = Offset(0f, 0f), end = Offset(1f, 1f)
    )
    fun main(): Brush = primary()
    fun cta(): Brush = Brush.linearGradient(
        colors = listOf(PrimaryElectric, PrimaryLight),
        start = Offset(0f, 0f), end = Offset(1f, 0f)
    )
    fun cardTopBorder(): Brush = Brush.horizontalGradient(
        colors = listOf(PrimaryLight, PrimaryElectric),
        startX = 0f, endX = Float.POSITIVE_INFINITY
    )
    fun cardGlass(): Brush = Brush.verticalGradient(
        colors = listOf(SuperficieCristal.copy(alpha = 0.90f), SuperficieElevada.copy(alpha = 0.95f))
    )
    fun borderGradient(): Brush = Brush.linearGradient(
        colors = listOf(PrimaryElectric, PrimaryLight, PrimaryElectric),
        start = Offset(0f, 0f), end = Offset(1f, 1f)
    )
    fun textGradient(): Brush = Brush.horizontalGradient(
        colors = listOf(PrimaryLight, PrimaryLight)
    )
    fun shimmer(): Brush = Brush.horizontalGradient(
        colors = listOf(ShimmerBase, ShimmerMid, ShimmerBase)
    )
    fun displayGradient(): Brush = Brush.linearGradient(
        colors = listOf(PrimaryElectric, SecondaryElectric, TertiaryElectric),
        start = Offset(0f, 0f), end = Offset(1f, 0f)
    )
    fun secondaryToPrimary(): Brush = Brush.linearGradient(
        colors = listOf(SecondaryElectric, PrimaryElectric),
        start = Offset(0f, 0f), end = Offset(1f, 1f)
    )
}
