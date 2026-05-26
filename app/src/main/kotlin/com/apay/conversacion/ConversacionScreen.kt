package com.apay.conversacion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Send
import com.apay.ui.components.*
import com.apay.ui.theme.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ConversacionScreen(
    onVolver: () -> Unit,
    viewModel: ConversacionViewModel = hiltViewModel()
) {
    val state     by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val scope     = rememberCoroutineScope()
    var inputTexto by remember { mutableStateOf("") }

    // Auto-scroll al último mensaje
    LaunchedEffect(state.mensajes.size) {
        if (state.mensajes.isNotEmpty()) {
            scope.launch { listState.animateScrollToItem(state.mensajes.size - 1) }
        }
    }

    Column(modifier = Modifier.fillMaxSize().systemBarsPadding()) {
        // ── Top bar ────────────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SuperficieCristal)
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onVolver) {
                Icon(Lucide.ArrowLeft, "Volver", tint = TextPrimary)
            }
            Spacer(Modifier.width(4.dp))
            Column(Modifier.weight(1f)) {
                A2BodySmall(text = state.titulo, color = TextPrimary)
                if (state.escribiendoTexto != null) {
                    A2Caption(text = "escribiendo...", color = PrimaryElectric)
                }
            }
        }

        A2Divider()

        // ── Mensajes ───────────────────────────────────────────────────────
        when {
            state.isLoading -> A2Loading(modifier = Modifier.weight(1f))
            state.mensajes.isEmpty() -> {
                Box(Modifier.weight(1f).fillMaxWidth(), Alignment.Center) {
                    A2BodySmall(text = "Comienza la conversación...")
                }
            }
            else -> {
                LazyColumn(
                    state   = listState,
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(state.mensajes) { mensaje ->
                        val esMio = mensaje.remitenteId == viewModel.miUid
                        BurbujaMensaje(
                            texto     = mensaje.texto,
                            hora      = formatHora(mensaje.timestamp),
                            esMio     = esMio,
                            remitente = if (!esMio) mensaje.remitenteNombre else null
                        )
                    }
                }
            }
        }

        A2Divider()

        // ── Input ──────────────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SuperficieCristal)
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .navigationBarsPadding()
                .imePadding(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value            = inputTexto,
                onValueChange    = { inputTexto = it },
                modifier         = Modifier.weight(1f),
                placeholder      = { Text("Mensaje...", color = TextMuted, style = A2TextStyle.Body) },
                singleLine       = false,
                maxLines         = 5,
                shape            = RoundedCornerShape(16.dp),
                keyboardOptions  = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions  = KeyboardActions(onSend = {
                    if (inputTexto.isNotBlank()) {
                        viewModel.enviarMensaje(inputTexto.trim())
                        inputTexto = ""
                    }
                }),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor      = TextPrimary,
                    unfocusedTextColor    = TextPrimary,
                    focusedBorderColor    = PrimaryElectric,
                    unfocusedBorderColor  = BorderSubtle,
                    focusedContainerColor = SuperficieElevada,
                    unfocusedContainerColor = SuperficieElevada,
                    cursorColor           = PrimaryElectric
                ),
                textStyle = A2TextStyle.Body
            )

            // Send button
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(50))
                    .background(
                        if (inputTexto.isNotBlank()) PrimaryElectric
                        else SuperficieElevada
                    ),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        if (inputTexto.isNotBlank()) {
                            viewModel.enviarMensaje(inputTexto.trim())
                            inputTexto = ""
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        Lucide.Send, "Enviar",
                        tint     = if (inputTexto.isNotBlank()) TextPrimary else TextMuted,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun BurbujaMensaje(
    texto: String,
    hora: String,
    esMio: Boolean,
    remitente: String?
) {
    Column(
        modifier            = Modifier.fillMaxWidth(),
        horizontalAlignment = if (esMio) Alignment.End else Alignment.Start
    ) {
        if (remitente != null) {
            A2Caption(
                text     = remitente,
                color    = PrimaryElectric,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
            )
        }
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .clip(
                    RoundedCornerShape(
                        topStart    = 16.dp,
                        topEnd      = 16.dp,
                        bottomStart = if (esMio) 16.dp else 4.dp,
                        bottomEnd   = if (esMio) 4.dp else 16.dp
                    )
                )
                .background(if (esMio) PrimaryContainer else SuperficieElevada)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Column {
                Text(texto, color = TextPrimary, style = A2TextStyle.Body)
                Spacer(Modifier.height(2.dp))
                Text(
                    hora,
                    color  = TextMuted,
                    style  = A2TextStyle.Caption,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

private fun formatHora(timestamp: Long): String {
    if (timestamp == 0L) return ""
    return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(timestamp))
}
