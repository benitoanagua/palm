package com.apay.chats

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Search
import com.apay.ui.components.*
import com.apay.ui.theme.*

@Composable
fun NuevoChatScreen(
    onChatIniciado: (chatId: String, titulo: String) -> Unit,
    onVolver: () -> Unit,
    viewModel: NuevoChatViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(16.dp)
    ) {
        // ── Top bar ────────────────────────────────────────────────────────
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onVolver) {
                Icon(Lucide.ArrowLeft, "Volver", tint = TextPrimary)
            }
            Spacer(Modifier.width(8.dp))
            A2Headline(text = "Nueva conversación")
        }

        Spacer(Modifier.height(12.dp))

        // ── Search ────────────────────────────────────────────────────────
        A2Input(
            value         = state.query,
            onValueChange = viewModel::buscar,
            placeholder   = "Buscar usuario...",
            leadingIcon   = Lucide.Search
        )

        Spacer(Modifier.height(12.dp))

        // ── Results ───────────────────────────────────────────────────────
        when {
            state.isLoading -> A2Loading(modifier = Modifier.height(100.dp))
            state.usuarios.isEmpty() && state.query.isNotEmpty() -> {
                Box(Modifier.fillMaxWidth().padding(32.dp), Alignment.Center) {
                    A2BodySmall(text = "No se encontraron usuarios")
                }
            }
            else -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(state.usuarios) { usuario ->
                        A2CardGlass(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.iniciarChat(usuario.uid, usuario.nombre) { chatId ->
                                        onChatIniciado(chatId, usuario.nombre)
                                    }
                                }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                A2Avatar(initials = usuario.nombre.take(2), size = 40.dp)
                                Column {
                                    A2BodySmall(text = usuario.nombre, color = TextPrimary)
                                    A2Caption(text = usuario.email)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
