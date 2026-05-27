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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a2.ui.theme.*

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
        style = A2TextStyle.DisplayLg.copy(color = Terracota)
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
                    .background(Terracota.copy(alpha = bgAlpha))
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
                    .border(1.5.dp, Terracota, shape)
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
                    CircularProgressIndicator(modifier = Modifier.size(18.dp), color = Terracota, strokeWidth = 2.dp)
                } else {
                    Text(text, color = Terracota, style = A2TextStyle.ButtonLabel)
                }
            }
        }
        A2ButtonVariant.Ghost -> {
            TextButton(
                onClick = onClick,
                enabled = enabled && !loading,
                modifier = baseModifier
            ) {
                Text(text, color = Terracota, style = A2TextStyle.ButtonLabel)
            }
        }
    }
}

@Composable
fun A2TextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = Terracota
) {
    TextButton(onClick = onClick, modifier = modifier) {
        Text(text, color = color, style = A2TextStyle.BodyMedium, fontWeight = FontWeight.SemiBold)
    }
}

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
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(0.5.dp, BordeSutil)
    ) {
        Column(
            modifier = Modifier.padding(A2Spacing.xl),
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
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = BorderStroke(0.5.dp, BordeSutil)
    ) {
        Column(
            modifier = Modifier.padding(A2Spacing.xl),
            content = content
        )
    }
}

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
                .heightIn(min = A2Tokens.InputHeight + 20.dp),
            enabled = enabled,
            placeholder = {
                Text(placeholder, style = A2TextStyle.BodyMedium, color = TextoMutado)
            },
            leadingIcon = leadingIcon?.let {
                { Icon(it, null, Modifier.size(18.dp), tint = TextoTierra) }
            },
            trailingIcon = trailingIcon?.let {
                {
                    if (onTrailingIconClick != null) {
                        IconButton(onClick = onTrailingIconClick, modifier = Modifier.size(36.dp)) {
                            Icon(it, null, Modifier.size(18.dp), tint = TextoTierra)
                        }
                    } else {
                        Icon(it, null, Modifier.size(18.dp), tint = TextoTierra)
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
                focusedBorderColor = Terracota,
                unfocusedBorderColor = BordeSutil,
                errorBorderColor = Error,
                cursorColor = Terracota,
                focusedContainerColor = SuperficiePrimaria,
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
            .border(0.8.dp, BordeSutil, A2Shapes.Card)
            .padding(horizontal = A2Spacing.lg, vertical = A2Spacing.md),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(message, modifier = Modifier.weight(1f), style = A2TextStyle.BodyMedium, color = textColor)
        if (dismissible && onDismiss != null) {
            Spacer(Modifier.width(8.dp))
            TextButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
                Text("×", color = textColor, fontSize = 16.sp)
            }
        }
    }
}

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
            .then(if (border != Color.Transparent) Modifier.border(0.8.dp, border, A2Shapes.Pill) else Modifier)
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(text, style = A2TextStyle.Caption, color = textColor, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun A2Divider(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(0.5.dp)
            .background(BordeSutil)
    )
}

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
            .background(Terracota),
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

@Composable
fun A2Loading(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = Terracota, modifier = Modifier.size(32.dp))
    }
}

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
            .padding(horizontal = 16.dp, vertical = 10.dp),
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
        shadowElevation = 4.dp
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
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = { Text(item.label, style = A2TextStyle.Caption) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Terracota,
                        selectedTextColor = Terracota,
                        unselectedIconColor = TextoMutado,
                        unselectedTextColor = TextoMutado,
                        indicatorColor = PrimaryContainer
                    )
                )
            }
        }
    }
}
