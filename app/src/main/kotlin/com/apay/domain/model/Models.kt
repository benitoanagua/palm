package com.apay.domain.model

data class Tasa(
    val id: String = "",
    val valor: Double = 0.0,
    val publicadaPor: String = "",
    val timestamp: Long = 0L
)

data class Usuario(
    val uid: String = "",
    val nombre: String = "",
    val email: String = "",
    val rol: String = "",
    val avatarUrl: String? = null,
    val enLinea: Boolean = false
)

data class MovimientoCuenta(
    val id: String = "",
    val concepto: String = "",
    val monto: Double = 0.0,
    val fecha: Long = 0L,
    val tipo: String = "" // "CREDITO" | "DEBITO"
)

data class Chat(
    val id: String = "",
    val titulo: String = "",
    val ultimoMensaje: String = "",
    val fechaUltimoMensaje: Long = 0L,
    val participantes: List<String> = emptyList(),
    val noLeidos: Int = 0
)

data class Mensaje(
    val id: String = "",
    val chatId: String = "",
    val remitenteId: String = "",
    val remitenteNombre: String = "",
    val texto: String = "",
    val timestamp: Long = 0L,
    val leido: Boolean = false,
    val tieneImagen: Boolean = false,
    val imagenUrl: String? = null
)
