package com.a2.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a2.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun A2Background(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(FondoBase)
    )
}

@Composable
fun A2GradientDisplayLg(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        style = A2TextStyle.DisplayLg.copy(color = TerracotaProfunda)
    )
}

@Composable
fun A2Headline(text: String, modifier: Modifier = Modifier, color: Color = TextoGrafito) {
    Text(text = text, modifier = modifier, style = A2TextStyle.Headline, color = color)
}

@Composable
fun A2Title(text: String, modifier: Modifier = Modifier, color: Color = TextoGrafito) {
    Text(text = text, modifier = modifier, style = A2TextStyle.TitleLg, color = color)
}

@Composable
fun A2Body(text: String, modifier: Modifier = Modifier, color: Color = TextoTierra) {
    Text(text = text, modifier = modifier, style = A2TextStyle.Body, color = color)
}

@Composable
fun A2BodySmall(text: String, modifier: Modifier = Modifier, color: Color = TextoTierra) {
    Text(text = text, modifier = modifier, style = A2TextStyle.BodyMedium, color = color)
}

@Composable
fun A2Caption(text: String, modifier: Modifier = Modifier, color: Color = TextoMutado) {
    Text(text = text, modifier = modifier, style = A2TextStyle.Caption, color = color)
}

// ─── BUTTONS ────────────────────────────────────────────────────────────────

enum class A2ButtonVariant { Primary, Secondary, Ghost }

@Composable
fun A2Button(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: A2ButtonVariant = A2ButtonVariant.Primary,
    icon: ImageVector? = null,
    enabled: Boolean = true,
    loading: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val bgAlpha by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.85f else 1f,
        animationSpec = tween(150),
        label = "alpha"
    )
    val shape = A2Shapes.Button
    val baseModifier = modifier
        .height(A2Tokens.ButtonHeight)
        .then(if (!enabled) Modifier.alpha(0.45f) else Modifier)

    when (variant) {
        A2ButtonVariant.Primary -> {
            Box(
                modifier = baseModifier
                    .clip(shape)
                    .background(TerracotaProfunda.copy(alpha = bgAlpha))
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        enabled = enabled && !loading,
                        role = Role.Button,
                        onClick = onClick
                    )
                    .padding(horizontal = A2Spacing.xl, vertical = A2Spacing.md),
                contentAlignment = Alignment.Center
            ) {
                if (loading) {
                    CircularProgressIndicator(modifier = Modifier.size(18.dp), color = White, strokeWidth = 2.dp)
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(A2Spacing.sm)
                    ) {
                        icon?.let { Icon(it, null, Modifier.size(18.dp), tint = White) }
                        Text(text, color = White, style = A2TextStyle.ButtonLabel)
                    }
                }
            }
        }
        A2ButtonVariant.Secondary -> {
            Box(
                modifier = baseModifier
                    .clip(shape)
                    .border(A2Tokens.StrokeThick, TerracotaProfunda, shape)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        enabled = enabled && !loading,
                        role = Role.Button,
                        onClick = onClick
                    )
                    .padding(horizontal = A2Spacing.xl, vertical = A2Spacing.md),
                contentAlignment = Alignment.Center
            ) {
                if (loading) {
                    CircularProgressIndicator(modifier = Modifier.size(18.dp), color = TerracotaProfunda, strokeWidth = 2.dp)
                } else {
                    Text(text, color = TerracotaProfunda, style = A2TextStyle.ButtonLabel)
                }
            }
        }
        A2ButtonVariant.Ghost -> {
            TextButton(
                onClick = onClick,
                enabled = enabled && !loading,
                modifier = baseModifier
            ) {
                Text(text, color = TerracotaProfunda, style = A2TextStyle.ButtonLabel)
            }
        }
    }
}

@Composable
fun A2TextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = TerracotaProfunda
) {
    TextButton(onClick = onClick, modifier = modifier) {
        Text(text, color = color, style = A2TextStyle.BodyMedium, fontWeight = FontWeight.SemiBold)
    }
}

// ─── CARDS ──────────────────────────────────────────────────────────────────

@Composable
fun A2Card(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        shape = A2Shapes.Card,
        colors = CardDefaults.cardColors(
            containerColor = SuperficiePrimaria
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = A2Tokens.ElevationCard),
        border = BorderStroke(A2Tokens.StrokeThin, BordeCard)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = A2Tokens.CardPadH, vertical = A2Tokens.CardPadV),
            content = content
        )
    }
}

@Composable
fun A2CardGlass(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        shape = A2Shapes.Card,
        colors = CardDefaults.cardColors(
            containerColor = SuperficieSecundaria
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = A2Tokens.ElevationCard),
        border = BorderStroke(A2Tokens.StrokeThin, BordeCard)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = A2Tokens.CardPadH, vertical = A2Tokens.CardPadV),
            content = content
        )
    }
}

@Composable
fun A2CardElevated(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        shape = A2Shapes.Card,
        colors = CardDefaults.cardColors(
            containerColor = SuperficieElevada
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = A2Tokens.ElevationRaised),
        border = BorderStroke(A2Tokens.StrokeThin, BordeCard)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = A2Tokens.CardPadH, vertical = A2Tokens.CardPadV),
            content = content
        )
    }
}

// ─── INPUT ─────────────────────────────────────────────────────────────────

@Composable
fun A2Input(
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
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(A2Spacing.sm)) {
        if (label.isNotEmpty()) {
            Text(label, style = A2TextStyle.Caption, color = TextoTierra)
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = A2Tokens.InputHeight),
            enabled = enabled,
            placeholder = {
                Text(placeholder, style = A2TextStyle.BodyMedium, color = TextoMutado)
            },
            leadingIcon = leadingIcon?.let {
                { Icon(it, null, Modifier.size(A2Tokens.IconMd), tint = TextoTierra) }
            },
            trailingIcon = trailingIcon?.let {
                {
                    if (onTrailingIconClick != null) {
                        IconButton(onClick = onTrailingIconClick, modifier = Modifier.size(A2Tokens.TouchTarget)) {
                            Icon(it, null, Modifier.size(A2Tokens.IconMd), tint = TextoTierra)
                        }
                    } else {
                        Icon(it, null, Modifier.size(A2Tokens.IconMd), tint = TextoTierra)
                    }
                }
            },
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            isError = errorMessage != null,
            singleLine = true,
            shape = A2Shapes.Input,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextoGrafito,
                unfocusedTextColor = TextoGrafito,
                focusedBorderColor = TerracotaProfunda,
                unfocusedBorderColor = BordeSutil,
                errorBorderColor = Error,
                cursorColor = TerracotaProfunda,
                focusedContainerColor = SuperficieElevada,
                unfocusedContainerColor = SuperficiePrimaria,
                errorContainerColor = ErrorBannerBg,
            ),
            textStyle = A2TextStyle.Body
        )
        if (errorMessage != null) {
            Text(errorMessage, style = A2TextStyle.Caption, color = Error)
        }
    }
}

// ─── BANNER ─────────────────────────────────────────────────────────────────

enum class A2BannerType { Success, Error, Warning, Info }

@Composable
fun A2Banner(
    message: String,
    type: A2BannerType = A2BannerType.Info,
    modifier: Modifier = Modifier,
    dismissible: Boolean = false,
    onDismiss: (() -> Unit)? = null
) {
    val (bg, textColor, _) = when (type) {
        A2BannerType.Success -> Triple(SuccessBannerBg, Success, Success)
        A2BannerType.Error   -> Triple(ErrorBannerBg, Error, Error)
        A2BannerType.Warning -> Triple(WarningBannerBg, Warning, Warning)
        A2BannerType.Info    -> Triple(InfoBannerBg, Salvia, Salvia)
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(A2Shapes.Card)
            .background(bg)
            .border(A2Tokens.StrokeLight, BordeCard, A2Shapes.Card)
            .padding(horizontal = A2Spacing.lg, vertical = A2Spacing.md),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(message, modifier = Modifier.weight(1f), style = A2TextStyle.BodyMedium, color = textColor)
        if (dismissible && onDismiss != null) {
            Spacer(Modifier.width(A2Spacing.sm))
            TextButton(onClick = onDismiss, modifier = Modifier.size(A2Tokens.BannerDismissSize)) {
                Text("×", color = textColor, fontSize = A2TextStyle.Caption.fontSize)
            }
        }
    }
}

// ─── TAG ────────────────────────────────────────────────────────────────────

enum class A2TagVariant { Filled, Outlined, Subtle }

@Composable
fun A2Tag(
    text: String,
    modifier: Modifier = Modifier,
    variant: A2TagVariant = A2TagVariant.Filled,
    color: Color = Salvia
) {
    val (bg, textColor, border) = when (variant) {
        A2TagVariant.Filled   -> Triple(color.copy(0.12f), color, Color.Transparent)
        A2TagVariant.Outlined -> Triple(Color.Transparent, color, color)
        A2TagVariant.Subtle   -> Triple(color.copy(0.06f), color.copy(0.7f), Color.Transparent)
    }
    Box(
        modifier = modifier
            .clip(A2Shapes.Pill)
            .background(bg)
            .then(if (border != Color.Transparent) Modifier.border(A2Tokens.StrokeLight, border, A2Shapes.Pill) else Modifier)
            .padding(horizontal = A2Tokens.TagPadH, vertical = A2Tokens.TagPadV)
    ) {
        Text(text, style = A2TextStyle.Caption, color = textColor, fontWeight = FontWeight.SemiBold)
    }
}

// ─── DIVIDER ────────────────────────────────────────────────────────────────

@Composable
fun A2Divider(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(A2Tokens.StrokeThin)
            .background(BordeCard)
    )
}

// ─── AVATAR ─────────────────────────────────────────────────────────────────

@Composable
fun A2Avatar(
    initials: String,
    modifier: Modifier = Modifier,
    size: Dp = A2Tokens.AvatarMd
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(TerracotaProfunda),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials.take(2).uppercase(),
            color = White,
            fontSize = (size.value * 0.38f).sp,
            fontWeight = FontWeight.Medium
        )
    }
}

// ─── LOADING ────────────────────────────────────────────────────────────────

@Composable
fun A2Loading(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = TerracotaProfunda, modifier = Modifier.size(32.dp))
    }
}

// ─── TOP BAR ────────────────────────────────────────────────────────────────

@Composable
fun A2TopBar(
    title: String = "APAY",
    actions: @Composable RowScope.() -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(FondoBase)
            .statusBarsPadding()
            .padding(horizontal = A2Tokens.TopBarPadH, vertical = A2Tokens.TopBarPadV),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        A2GradientDisplayLg(text = title)
        Row(content = actions)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(0.5.dp)
            .background(BordeSutil)
    )
}

// ─── BOTTOM NAV ─────────────────────────────────────────────────────────────

data class A2NavItem(val label: String, val icon: ImageVector)

@Composable
fun A2BottomNav(
    items: List<A2NavItem>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.navigationBarsPadding(),
        color = SuperficiePrimaria,
        tonalElevation = 0.dp,
        shadowElevation = A2Tokens.ElevationRaised
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
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
                            modifier = Modifier.size(A2Tokens.IconXL)
                        )
                    },
                    label = { Text(item.label, style = A2TextStyle.Caption) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = TerracotaProfunda,
                        selectedTextColor = TerracotaProfunda,
                        unselectedIconColor = TextoMutado,
                        unselectedTextColor = TextoMutado,
                        indicatorColor = PrimaryContainer.copy(0.15f)
                    )
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════
// NUEVOS COMPONENTES WAYFARER
// ═══════════════════════════════════════════════════════════════════════════

// ─── SEARCH BAR ──────────────────────────────────────────────────────────

@Composable
fun A2SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Buscar...",
    onSearch: (() -> Unit)? = null,
) {
    val focusManager = LocalFocusManager.current
    val searchShape = RoundedCornerShape(A2Tokens.RadiusSearch)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(A2Tokens.ButtonHeight)
            .clip(searchShape)
            .background(SuperficieElevada)
            .border(A2Tokens.StrokeLight, BordeSutil, searchShape)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = A2Spacing.xl),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (query.isEmpty()) placeholder else query,
                color = if (query.isEmpty()) TextoMutado else TextoGrafito,
                style = A2TextStyle.BodyMedium,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        TextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            placeholder = {},
            singleLine = true,
            textStyle = A2TextStyle.BodyMedium.copy(color = Color.Transparent),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch?.invoke()
                    focusManager.clearFocus()
                }
            )
        )
    }
}

// ─── PILL FILTER ─────────────────────────────────────────────────────────

@Composable
fun A2PillFilter(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val bg = if (selected) TerracotaProfunda else SuperficieElevada
    val txt = if (selected) White else TextoTierra
    val borderColor = if (selected) TerracotaProfunda else BordeSutil

    Box(
        modifier = modifier
            .clip(A2Shapes.Pill)
            .background(bg)
            .border(A2Tokens.StrokeRegular, borderColor, A2Shapes.Pill)
            .clickable(onClick = onClick)
            .padding(horizontal = A2Spacing.xl, vertical = A2Spacing.sm)
    ) {
        Text(text, color = txt, style = A2TextStyle.Caption, fontWeight = FontWeight.SemiBold)
    }
}

// ─── PROGRESS STEPPER ────────────────────────────────────────────────────

@Composable
fun A2ProgressStepper(
    steps: List<String>,
    currentStep: Int,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            steps.forEachIndexed { index, label ->
                val isCompleted = index < currentStep
                val isCurrent = index == currentStep
                val circleColor = when {
                    isCompleted -> TerracotaProfunda
                    isCurrent -> TerracotaProfunda
                    else -> BordeCard
                }
                val textColor = when {
                    isCompleted || isCurrent -> TerracotaProfunda
                    else -> TextoMutado
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(circleColor),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isCompleted) {
                            Text("✓", color = White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        } else {
                            Text("${index + 1}", color = if (isCurrent) White else TextoMutado, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                    Spacer(Modifier.height(A2Spacing.sm))
                    Text(
                        text = label,
                        color = textColor,
                        style = A2TextStyle.Caption,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
            }
        }
        Spacer(Modifier.height(A2Spacing.md))
        LinearProgressIndicator(
            progress = { (currentStep.toFloat() / (steps.size - 1).coerceAtLeast(1)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .clip(A2Shapes.Pill),
            color = TerracotaProfunda,
            trackColor = BordeCard,
        )
    }
}

// ─── JOURNEY TIMELINE ────────────────────────────────────────────────────

@Composable
fun A2JourneyTimeline(
    stops: List<A2TimelineStop>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        stops.forEachIndexed { index, stop ->
            val isFirst = index == 0
            val isLast = index == stops.lastIndex
            val isActive = stop.active

            Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(32.dp)
                ) {
                    if (!isFirst) {
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(8.dp)
                                .background(if (isActive) TerracotaProfunda else BordeCard)
                        )
                    } else {
                        Spacer(Modifier.height(8.dp))
                    }
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(if (isActive) TerracotaProfunda else BordeCard),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isActive) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(White)
                            )
                        }
                    }
                    if (!isLast) {
                        val nextActive = stops.getOrNull(index + 1)?.active ?: false
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .weight(1f)
                                .background(if (isActive && nextActive) TerracotaProfunda else BordeCard)
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = A2Spacing.xl, bottom = if (isLast) 0.dp else A2Spacing.xl)
                ) {
                    Text(
                        text = stop.title,
                        style = if (isActive) A2TextStyle.TitleMd else A2TextStyle.BodyMedium,
                        color = if (isActive) TextoGrafito else TextoTierra
                    )
                    if (stop.subtitle.isNotEmpty()) {
                        Text(
                            text = stop.subtitle,
                            style = A2TextStyle.Caption,
                            color = TextoMutado
                        )
                    }
                }
            }
        }
    }
}

data class A2TimelineStop(
    val title: String,
    val subtitle: String = "",
    val active: Boolean = false
)

// ─── HERO IMAGE ──────────────────────────────────────────────────────────

@Composable
fun A2HeroImage(
    modifier: Modifier = Modifier,
    height: Dp = 200.dp,
    imageOverlayText: String? = null,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(A2Shapes.Card)
            .background(TerracotaProfunda.copy(0.15f))
    ) {
        if (imageOverlayText != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                TerracotaProfunda.copy(0.6f)
                            )
                        )
                    ),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = imageOverlayText,
                    style = A2TextStyle.DisplayLg,
                    color = White,
                    modifier = Modifier.padding(A2Spacing.xl)
                )
            }
        }
    }
}

// ─── STATS GRID ──────────────────────────────────────────────────────────

@Composable
fun A2StatsGrid(
    stats: List<A2StatItem>,
    modifier: Modifier = Modifier,
    columns: Int = 4
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(A2Spacing.md)) {
        stats.chunked(columns).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(A2Spacing.md)
            ) {
                row.forEach { stat ->
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stat.value,
                            style = A2TextStyle.Headline,
                            color = TerracotaProfunda
                        )
                        Text(
                            text = stat.label,
                            style = A2TextStyle.Caption,
                            color = TextoTierra,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                repeat(columns - row.size) {
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

data class A2StatItem(val value: String, val label: String)

// ─── PROMO CARD ──────────────────────────────────────────────────────────

@Composable
fun A2PromoCard(
    title: String,
    description: String,
    actionLabel: String,
    onAction: () -> Unit,
    modifier: Modifier = Modifier,
    accentColor: Color = SalviaProfunda,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(A2Tokens.RadiusCard),
        colors = CardDefaults.cardColors(containerColor = SuperficiePrimaria),
        elevation = CardDefaults.cardElevation(defaultElevation = A2Tokens.ElevationCard),
        border = BorderStroke(A2Tokens.StrokeThin, BordeCard)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(A2Spacing.xl),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = A2TextStyle.TitleLg,
                    color = TextoGrafito
                )
                Spacer(Modifier.height(A2Spacing.sm))
                Text(
                    text = description,
                    style = A2TextStyle.BodyMedium,
                    color = TextoTierra
                )
                Spacer(Modifier.height(A2Spacing.md))
                A2Button(
                    text = actionLabel,
                    onClick = onAction,
                    variant = A2ButtonVariant.Primary
                )
            }
        }
    }
}

// ─── MAP PLACEHOLDER ─────────────────────────────────────────────────────

@Composable
fun A2MapPlaceholder(
    modifier: Modifier = Modifier,
    height: Dp = 180.dp,
    label: String = "Mapa"
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(A2Tokens.RadiusCard))
            .background(SuperficieSecundaria)
            .border(A2Tokens.StrokeThin, BordeCard, RoundedCornerShape(A2Tokens.RadiusCard)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "🗺",
                fontSize = 28.sp
            )
            Spacer(Modifier.height(A2Spacing.sm))
            Text(
                text = label,
                style = A2TextStyle.BodyMedium,
                color = TextoMutado
            )
        }
    }
}

// ─── RATING BAR ──────────────────────────────────────────────────────────

@Composable
fun A2RatingBar(
    rating: Float,
    maxStars: Int = 5,
    modifier: Modifier = Modifier,
    starSize: Dp = A2Tokens.IconMd,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(A2Spacing.sm / 2)
    ) {
        for (i in 1..maxStars) {
            val filled = i <= rating.toInt()
            Box(
                modifier = Modifier
                    .size(starSize)
                    .clip(CircleShape)
                    .background(if (filled) Estrella else BordeCard)
            )
        }
    }
}

// ─── DRIVER PROFILE CARD ────────────────────────────────────────────────

@Composable
fun A2DriverProfileCard(
    name: String,
    rating: Float,
    tripsCount: Int,
    modifier: Modifier = Modifier,
    avatarInitials: String = "",
    subtitle: String = "",
) {
    A2Card(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            A2Avatar(
                initials = avatarInitials.ifEmpty { name.take(2) },
                size = A2Tokens.AvatarLg
            )
            Spacer(Modifier.width(A2Spacing.xl))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    style = A2TextStyle.TitleLg,
                    color = TextoGrafito
                )
                if (subtitle.isNotEmpty()) {
                    Text(
                        text = subtitle,
                        style = A2TextStyle.Caption,
                        color = TextoTierra
                    )
                }
                Spacer(Modifier.height(A2Spacing.sm))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    A2RatingBar(rating = rating)
                    Spacer(Modifier.width(A2Spacing.sm))
                    Text(
                        text = "$rating · $tripsCount viajes",
                        style = A2TextStyle.Caption,
                        color = TextoMutado
                    )
                }
            }
        }
    }
}
