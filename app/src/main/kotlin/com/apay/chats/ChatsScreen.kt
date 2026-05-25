package com.apay.chats

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.MessageCirclePlus
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.apay.navigation.Screen
import com.apay.ui.components.*
import com.apay.ui.theme.*

@Composable
fun ChatsScreen(
    onLogout: () -> Unit,
    viewModel: ChatsViewModel = hiltViewModel(),
    // navController is not passed here; FAB navigation must be handled via callback
    onNuevoChat: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()

    Box(Modifier.fillMaxSize()) {
        when {
            state.isLoading -> {
                A2Loading()
            }
            state.chats.isEmpty() -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    A2BodySmall(text = "No hay conversaciones aún")
                    Spacer(Modifier.height(16.dp))
                    A2Button(text = "Nueva conversación", onClick = onNuevoChat)
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.chats) { chat ->
                        A2CardGlass(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.abrirChat(chat.id) }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                A2Avatar(initials = chat.titulo.take(2), size = 40.dp)
                                Column(Modifier.weight(1f)) {
                                    A2BodySmall(text = chat.titulo, color = TextPrimary)
                                    A2Caption(text = chat.ultimoMensaje)
                                }
                                if (chat.noLeidos > 0) {
                                    A2Tag(
                                        text    = chat.noLeidos.toString(),
                                        variant = A2TagVariant.Filled,
                                        color   = PrimaryElectric
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = onNuevoChat,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = PrimaryElectric,
            contentColor   = TextPrimary
        ) {
            Icon(Lucide.MessageCirclePlus, "Nuevo chat")
        }
    }
}
