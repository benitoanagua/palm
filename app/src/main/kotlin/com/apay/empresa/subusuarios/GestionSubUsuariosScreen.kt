package com.apay.empresa.subusuarios

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestionSubUsuariosScreen(
    empresaId: String,
    onNavigateBack: () -> Unit = {},
    viewModel: SubUsuariosViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()) }

    LaunchedEffect(empresaId) {
        viewModel.cargarSubUsuarios(empresaId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Sub-Usuarios") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.mostrarDialogoAgregar() }) {
                        Icon(Icons.Default.Add, contentDescription = "Agregar sub-usuario")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.mostrarDialogoAgregar() },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar sub-usuario")
            }
        }
    ) { paddingValues ->
        if (uiState.cargando) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.subUsuarios.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.PersonAdd,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "No hay sub-usuarios registrados",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(onClick = { viewModel.mostrarDialogoAgregar() }) {
                        Text("Agregar primer sub-usuario")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(uiState.subUsuarios, key = { it.id }) { subUsuario ->
                    SubUsuarioCard(
                        subUsuario = subUsuario,
                        dateFormat = dateFormat,
                        onEliminar = { viewModel.eliminarSubUsuario(subUsuario.id) },
                        onEditar = { /* Implementar edición */ }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }

        // Dialogo para agregar sub-usuario
        if (uiState.mostrandoDialogoAgregar) {
            DialogoAgregarSubUsuario(
                onConfirmar = { nombre, email, rol, limiteGasto ->
                    val nuevoSubUsuario = SubUsuario(
                        id = java.util.UUID.randomUUID().toString(),
                        empresaId = empresaId,
                        uid = "", // Se asignará al crear la cuenta en Firebase Auth
                        nombre = nombre,
                        email = email,
                        rol = rol,
                        limiteGastoPorEnvio = limiteGasto,
                        activo = true
                    )
                    viewModel.agregarSubUsuario(nuevoSubUsuario)
                },
                onDismiss = { viewModel.ocultarDialogoAgregar() }
            )
        }

        // Snackbar para errores
        if (uiState.error != null) {
            Snackbar(
                modifier = Modifier.padding(16.dp),
                action = {
                    TextButton(onClick = { viewModel.limpiarError() }) {
                        Text("Cerrar")
                    }
                }
            ) {
                Text(uiState.error!!)
            }
        }

        // Snackbar para éxito en eliminación
        if (uiState.subUsuarioEliminadoExitosamente) {
            Snackbar(
                modifier = Modifier.padding(16.dp),
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Text("Sub-usuario eliminado exitosamente")
            }
        }
    }
}

@Composable
private fun SubUsuarioCard(
    subUsuario: SubUsuario,
    dateFormat: SimpleDateFormat,
    onEliminar: () -> Unit,
    onEditar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = subUsuario.nombre,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = subUsuario.email,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Row {
                    Badge(
                        containerColor = when (subUsuario.rol) {
                            "ADMIN" -> MaterialTheme.colorScheme.primaryContainer
                            "LIMITADO" -> MaterialTheme.colorScheme.tertiaryContainer
                            else -> MaterialTheme.colorScheme.secondaryContainer
                        }
                    ) {
                        Text(subUsuario.rol)
                    }
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    IconButton(onClick = onEliminar) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            Divider()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Límite por envío:",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "$${String.format("%.2f", subUsuario.limiteGastoPorEnvio)}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium
                )
            }

            if (subUsuario.rutasFrecuentes.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Rutas frecuentes:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${subUsuario.rutasFrecuentes.size} rutas",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Estado:",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .padding(end = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp),
                            color = if (subUsuario.activo) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                    }
                    Text(
                        text = if (subUsuario.activo) "Activo" else "Inactivo",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = if (subUsuario.activo) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }
            }

            if (subUsuario.ultimoAcceso != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Último acceso:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = dateFormat.format(Date(subUsuario.ultimoAcceso)),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun DialogoAgregarSubUsuario(
    onConfirmar: (String, String, String, Double) -> Unit,
    onDismiss: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var rol by remember { mutableStateOf("USUARIO") }
    var limiteGasto by remember { mutableStateOf("0.0") }
    var error by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Agregar Sub-Usuario") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre completo") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = limiteGasto,
                    onValueChange = { limiteGasto = it },
                    label = { Text("Límite de gasto por envío (USD)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenuBox(
                    expanded = false,
                    onExpandedChange = {}
                ) {
                    OutlinedTextField(
                        value = rol,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Rol") },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) }
                    )
                }

                if (error != null) {
                    Text(
                        text = error!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (nombre.isBlank() || email.isBlank()) {
                        error = "Nombre y email son obligatorios"
                        return@Button
                    }
                    
                    val limite = limiteGasto.toDoubleOrNull() ?: 0.0
                    onConfirmar(nombre.trim(), email.trim(), rol, limite)
                }
            ) {
                Text("Agregar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
