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
    val viajesCompletados: Int = 0,
    val nit: String? = null, // Para cargadores empresariales
    val empresa: String? = null,
    val fechaRegistro: Long = System.currentTimeMillis()
)

data class Transportista(
    val uid: String = "",
    val nombre: String = "",
    val email: String = "",
    val telefono: String = "",
    val placa: String = "",
    val tipoVehiculo: String = "", // "CAMION", "FURGON", "PICKUP", "CISTERNA"
    val capacidadMaximaKg: Int = 0,
    val capacidadMaximaM3: Double = 0.0,
    val calificacion: Double = 0.0,
    val viajesCompletados: Int = 0,
    val miembroDesde: Long = 0L,
    val verificado: Boolean = false,
    val rutasActivas: Int = 0,
    val fotoVehiculoUrl: String? = null,
    val documentoHabilitacionUrl: String? = null,
    val ingresosTotales: Double = 0.0,
    val kmTotales: Double = 0.0
)

data class Ruta(
    val id: String = "",
    val transportistaId: String = "",
    val transportistaNombre: String = "",
    val transportistaCalificacion: Double = 0.0,
    val origen: String = "",
    val destino: String = "",
    val fechaSalida: Long = 0L,
    val horaSalida: String = "",
    val capacidadDisponibleKg: Int = 0,
    val capacidadDisponibleM3: Double = 0.0,
    val precioPorKg: Double = 0.0,
    val precioFlete: Double = 0.0, // Precio fijo alternativo
    val tiposCargaAceptados: List<String> = emptyList(), // "GENERAL", "FRAGIL", "REFRIGERADA", "PELIGROSA"
    val paradasIntermedias: List<String> = emptyList(),
    val estado: String = "DISPONIBLE", // DISPONIBLE | EN_CURSO | COMPLETADA | CANCELADA
    val descripcion: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

data class Envio(
    val id: String = "",
    val rutaId: String = "",
    val cargadorId: String = "",
    val cargadorNombre: String = "",
    val cargadorEmpresa: String? = null,
    val transportistaId: String = "",
    val transportistaNombre: String = "",
    val origen: String = "",
    val destino: String = "",
    val fechaSalida: Long = 0L,
    val fechaEntregaEstimada: Long = 0L,
    val pesoKg: Int = 0,
    val dimensiones: String = "", // formato: "largo x ancho x alto cm"
    val tipoCarga: String = "", // "GENERAL", "FRAGIL", "REFRIGERADA", "PELIGROSA"
    val valorDeclarado: Double = 0.0,
    val precioTotal: Double = 0.0,
    val metodoPago: String = "", // "TIGO_MONEY", "TARJETA", "EFECTIVO"
    val estado: String = "PENDIENTE", // PENDIENTE | CONFIRMADO | EN_TRANSITO | ENTREGADO | CANCELADO
    val evidenciaEntregaUrl: String? = null,
    val firmaReceptorUrl: String? = null,
    val calificacionTransportista: Int? = null,
    val comentarioTransportista: String? = null,
    val calificacionCargador: Int? = null,
    val comentarioCargador: String? = null,
    val codigoQR: String? = null,
    val ubicacionActualLat: Double? = null,
    val ubicacionActualLng: Double? = null,
    val ultimaUbicacionTimestamp: Long? = null,
    val timestamp: Long = System.currentTimeMillis()
)

data class Disputa(
    val id: String = "",
    val envioId: String = "",
    val cargadorId: String = "",
    val transportistaId: String = "",
    val motivo: String = "", // "DAÑO", "INCOMPLETO", "RETRASO", "OTRO"
    val descripcion: String = "",
    val fotosEvidencia: List<String> = emptyList(),
    val estado: String = "ABIERTA", // ABIERTA | EN_REVISION | RESUELTA | CERRADA
    val resolucion: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
