package com.apay.domain.model

data class Usuario(
    val uid: String = "",
    val nombre: String = "",
    val email: String = "",
    val rol: String = "", // "TRANSPORTISTA" | "CARGADOR"
    val avatarUrl: String? = null,
    val enLinea: Boolean = false,
    val telefono: String = "",
    val calificacion: Double = 0.0,
    val viajesCompletados: Int = 0
)

data class Transportista(
    val uid: String = "",
    val nombre: String = "",
    val email: String = "",
    val telefono: String = "",
    val placa: String = "",
    val tipoVehiculo: String = "",
    val capacidadMaximaKg: Int = 0,
    val capacidadMaximaM3: Double = 0.0,
    val calificacion: Double = 0.0,
    val viajesCompletados: Int = 0,
    val miembroDesde: Long = 0L,
    val verificado: Boolean = false,
    val rutasActivas: Int = 0
)

data class Ruta(
    val id: String = "",
    val transportistaId: String = "",
    val transportistaNombre: String = "",
    val origen: String = "",
    val destino: String = "",
    val fechaSalida: Long = 0L,
    val capacidadDisponibleKg: Int = 0,
    val capacidadDisponibleM3: Double = 0.0,
    val precioPorKg: Double = 0.0,
    val tiposCargaAceptados: List<String> = emptyList(),
    val estado: String = "DISPONIBLE", // DISPONIBLE | EN_CURSO | COMPLETADA | CANCELADA
    val timestamp: Long = 0L
)

data class Envio(
    val id: String = "",
    val rutaId: String = "",
    val cargadorId: String = "",
    val cargadorNombre: String = "",
    val transportistaId: String = "",
    val transportistaNombre: String = "",
    val origen: String = "",
    val destino: String = "",
    val fechaSalida: Long = 0L,
    val fechaEntregaEstimada: Long = 0L,
    val pesoKg: Int = 0,
    val dimensiones: String = "",
    val tipoCarga: String = "",
    val precioTotal: Double = 0.0,
    val estado: String = "PENDIENTE", // PENDIENTE | CONFIRMADO | EN_TRANSITO | ENTREGADO | CANCELADO
    val evidenciaEntregaUrl: String? = null,
    val calificacionTransportista: Int? = null,
    val calificacionCargador: Int? = null,
    val timestamp: Long = 0L
)
