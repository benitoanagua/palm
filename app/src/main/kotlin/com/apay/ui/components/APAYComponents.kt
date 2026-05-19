package com.apay.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apay.ui.theme.*

// ─── APAY Design System Components ───────────────────────────────────────────

// ── Backgrounds ───────────────────────────────────────────────────────────────

@Composable
fun APAYBackground(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(FondoEspacial)
    ) {
        // Ambient glow top-left
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset((-80).dp, (-80).dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(PrimaryGlow, Color.Transparent),
                        radius = 300f
                    )
                )
        )
        // Ambient glow bottom-right
        Box(
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.BottomEnd)
                .offset(60.dp, 60.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(SecondaryGlow, Color.Transparent),
                        radius = 250f
                    )
                )
        )
    }
}

// ── Typography ─────────────────────────────────────────────────────────────────

@Composable
fun APAYGradientDisplayLg(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            brush = APAYGradients.displayGradient(),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    )
}

@Composable
fun APAYHeadline(text: String, modifier: Modifier = Modifier, color: Color = TextPrimary) {
    Text(text = text, modifier = modifier, style = APAYTextStyle.Headline, color = color)
}

@Composable
fun APAYTitle(text: String, modifier: Modifier = Modifier, color: Color = TextPrimary) {
    Text(text = text, modifier = modifier, style = APAYTextStyle.TitleLg, color = color)
}

@Composable
fun APAYBody(text: String, modifier: Modifier = Modifier, color: Color = TextSecondary) {
    Text(text = text, modifier = modifier, style = APAYTextStyle.Body, color = color)
}

@Composable
fun APAYBodySmall(text: String, modifier: Modifier = Modifier, color: Color = TextMuted) {
    Text(text = text, modifier = modifier, style = APAYTextStyle.BodyMedium, color = color)
}

@Composable
fun APAYCaption(text: String, modifier: Modifier = Modifier, color: Color = TextMuted) {
    Text(text = text, modifier = modifier, style = APAYTextStyle.Caption, color = color)
}

// ── Buttons ────────────────────────────────────────────────────────────────────

enum class APAYButtonVariant { Primary, Secondary, Ghost }

@Composable
fun APAYButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: APAYButtonVariant = APAYButtonVariant.Primary,
    icon: ImageVector? = null,
    enabled: Boolean = true,
    loading: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.96f else 1f,
        animationSpec = tween(150),
        label = "scale"
    )

    val shape = APAYShapes.Button

    val baseModifier = modifier
        .height(APAYTokens.ButtonHeight)
        .graphicsLayer { scaleX = scale; scaleY = scale }
        .then(if (!enabled) Modifier.graphicsLayer { alpha = 0.6f } else Modifier)

    when (variant) {
        APAYButtonVariant.Primary -> {
            Box(
                modifier = baseModifier
                    .clip(shape)
                    .background(APAYGradients.primary())
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        enabled = enabled && !loading,
                        role = Role.Button,
                        onClick = onClick
                    )
                    .padding(horizontal = APAYSpacing.lg, vertical = APAYSpacing.xs),
                contentAlignment = Alignment.Center
            ) {
                if (loading) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp), color = TextPrimary, strokeWidth = 2.dp)
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(APAYSpacing.xs)
                    ) {
                        icon?.let { Icon(it, null, Modifier.size(16.dp), tint = TextPrimary) }
                        Text(text, color = TextPrimary, style = APAYTextStyle.ButtonLabel)
                    }
                }
            }
        }
        APAYButtonVariant.Secondary -> {
            Box(
                modifier = baseModifier
                    .clip(shape)
                    .border(1.dp, Brush.linearGradient(listOf(PrimaryElectric, PrimaryLight)), shape)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        enabled = enabled && !loading,
                        role = Role.Button,
                        onClick = onClick
                    )
                    .padding(horizontal = APAYSpacing.lg, vertical = APAYSpacing.xs),
                contentAlignment = Alignment.Center
            ) {
                if (loading) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp), color = PrimaryElectric, strokeWidth = 2.dp)
                } else {
                    Text(text, style = TextStyle(brush = APAYGradients.textGradient(), fontSize = 12.sp, fontWeight = FontWeight.SemiBold))
                }
            }
        }
        APAYButtonVariant.Ghost -> {
            TextButton(
                onClick = onClick,
                enabled = enabled && !loading,
                modifier = baseModifier
            ) {
                Text(text, color = PrimaryElectric, style = APAYTextStyle.ButtonLabel)
            }
        }
    }
}

@Composable
fun APAYTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = PrimaryElectric
) {
    TextButton(onClick = onClick, modifier = modifier) {
        Text(text, color = color, style = APAYTextStyle.BodyMedium, fontWeight = FontWeight.SemiBold)
    }
}

// ── Cards ──────────────────────────────────────────────────────────────────────

@Composable
fun APAYCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .clip(APAYShapes.Card)
            .background(SuperficieCristal)
            .border(0.8.dp, BorderSubtle, APAYShapes.Card)
            .padding(APAYSpacing.xl),
        content = content
    )
}

@Composable
fun APAYCardGlass(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .clip(APAYShapes.Card)
            .background(APAYGradients.cardGlass())
            .border(0.8.dp, BorderMedium, APAYShapes.Card)
            .padding(APAYSpacing.xl),
        content = content
    )
}

// ── Input ──────────────────────────────────────────────────────────────────────

@Composable
fun APAYInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    placeholder: String = "",
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    isPassword: Boolean = false,
    enabled: Boolean = true,
    errorMessage: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
) {
    val focusManager = LocalFocusManager.current
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(APAYSpacing.xs)) {
        if (label.isNotEmpty()) {
            Text(label, style = APAYTextStyle.Caption, color = TextMuted)
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(APAYTokens.InputHeight + 20.dp),
            enabled = enabled,
            placeholder = { Text(placeholder, style = APAYTextStyle.BodyMedium, color = TextMuted) },
            leadingIcon = leadingIcon?.let { { Icon(it, null, Modifier.size(16.dp), tint = TextMuted) } },
            trailingIcon = trailingIcon?.let {
                {
                    if (onTrailingIconClick != null) {
                        IconButton(onClick = onTrailingIconClick, modifier = Modifier.size(32.dp)) {
                            Icon(it, null, Modifier.size(16.dp), tint = TextMuted)
                        }
                    } else {
                        Icon(it, null, Modifier.size(16.dp), tint = TextMuted)
                    }
                }
            },
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            isError = errorMessage != null,
            singleLine = true,
            shape = APAYShapes.Input,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                focusedBorderColor = PrimaryElectric,
                unfocusedBorderColor = BorderSubtle,
                errorBorderColor = Error,
                cursorColor = PrimaryElectric,
                focusedContainerColor = SuperficieElevada,
                unfocusedContainerColor = SuperficieElevada,
                errorContainerColor = ErrorBannerBg,
            ),
            textStyle = APAYTextStyle.Body
        )
        if (errorMessage != null) {
            Text(errorMessage, style = APAYTextStyle.Caption, color = Error)
        }
    }
}

// ── Banner ──────────────────────────────────────────────────────────────────────

enum class APAYBannerType { Success, Error, Warning, Info }

@Composable
fun APAYBanner(
    message: String,
    type: APAYBannerType = APAYBannerType.Info,
    modifier: Modifier = Modifier,
    dismissible: Boolean = false,
    onDismiss: (() -> Unit)? = null
) {
    val (bg, textColor, borderColor) = when (type) {
        APAYBannerType.Success -> Triple(SuccessBannerBg, PrimaryLight, PrimaryElectric)
        APAYBannerType.Error   -> Triple(ErrorBannerBg, Error, Error)
        APAYBannerType.Warning -> Triple(WarningBannerBg, Warning, Warning)
        APAYBannerType.Info    -> Triple(InfoBannerBg, SecondaryElectric, SecondaryElectric)
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(APAYShapes.Card)
            .background(bg)
            .border(0.8.dp, borderColor.copy(0.4f), APAYShapes.Card)
            .padding(horizontal = APAYSpacing.lg, vertical = APAYSpacing.md),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(message, modifier = Modifier.weight(1f), style = APAYTextStyle.BodyMedium, color = textColor)
        if (dismissible && onDismiss != null) {
            Spacer(Modifier.width(8.dp))
            TextButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
                Text("×", color = textColor, fontSize = 16.sp)
            }
        }
    }
}

// ── Tags ───────────────────────────────────────────────────────────────────────

enum class APAYTagVariant { Filled, Outlined, Subtle }

@Composable
fun APAYTag(
    text: String,
    modifier: Modifier = Modifier,
    variant: APAYTagVariant = APAYTagVariant.Filled,
    color: Color = PrimaryElectric
) {
    val (bg, textColor, border) = when (variant) {
        APAYTagVariant.Filled   -> Triple(color.copy(0.15f), color, color.copy(0f))
        APAYTagVariant.Outlined -> Triple(Color.Transparent, color, color)
        APAYTagVariant.Subtle   -> Triple(color.copy(0.08f), color.copy(0.7f), Color.Transparent)
    }
    Box(
        modifier = modifier
            .clip(APAYShapes.Pill)
            .background(bg)
            .then(if (border != Color.Transparent) Modifier.border(0.8.dp, border, APAYShapes.Pill) else Modifier)
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(text, style = APAYTextStyle.Caption, color = textColor, fontWeight = FontWeight.SemiBold)
    }
}

// ── Divider ────────────────────────────────────────────────────────────────────

@Composable
fun APAYDivider(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(0.8.dp)
            .background(
                Brush.horizontalGradient(
                    listOf(Color.Transparent, BorderSubtle, PrimaryElectric.copy(0.3f), BorderSubtle, Color.Transparent)
                )
            )
    )
}

// ── Avatar ─────────────────────────────────────────────────────────────────────

@Composable
fun APAYAvatar(
    initials: String,
    modifier: Modifier = Modifier,
    size: Dp = APAYTokens.AvatarMd
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(APAYGradients.primary()),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials.take(2).uppercase(),
            color = TextPrimary,
            fontSize = (size.value * 0.35f).sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// ── Loading ────────────────────────────────────────────────────────────────────

@Composable
fun APAYLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = PrimaryElectric, modifier = Modifier.size(32.dp))
    }
}

// ── Top Bar ────────────────────────────────────────────────────────────────────

@Composable
fun APAYTopBar(
    title: String = "APAY",
    actions: @Composable RowScope.() -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        APAYGradientDisplayLg(text = title)
        Row(content = actions)
    }
}

// ── Bottom Nav ─────────────────────────────────────────────────────────────────

data class APAYNavItem(val label: String, val icon: ImageVector)

@Composable
fun APAYBottomNav(
    items: List<APAYNavItem>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier.navigationBarsPadding(),
        containerColor = SuperficieCristal,
        tonalElevation = 0.dp
    ) {
        items.forEachIndexed { index, item ->
            val selected = index == selectedIndex
            NavigationBarItem(
                selected = selected,
                onClick = { onItemSelected(index) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        modifier = Modifier.size(20.dp)
                    )
                },
                label = { Text(item.label, style = APAYTextStyle.Caption) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PrimaryElectric,
                    selectedTextColor = PrimaryElectric,
                    unselectedIconColor = TextMuted,
                    unselectedTextColor = TextMuted,
                    indicatorColor = PrimaryContainer
                )
            )
        }
    }
}
