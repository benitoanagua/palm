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

// US-21 y US-22: Modelos para Dashboard Empresarial
data class Empresa(
    val id: String = "",
    val nombre: String = "",
    val nit: String = "",
    val email: String = "",
    val telefono: String = "",
    val direccion: String = "",
    val ciudad: String = "",
    val propietarioId: String = "", // UID del usuario principal
    val limiteGastoMensual: Double = 0.0,
    val gastoActualMes: Double = 0.0,
    val subUsuariosMaximos: Int = 10,
    val subUsuariosActivos: Int = 0,
    val fechaRegistro: Long = System.currentTimeMillis(),
    val activa: Boolean = true
)

data class SubUsuario(
    val id: String = "",
    val empresaId: String = "",
    val uid: String = "", // UID del usuario en Firebase Auth
    val nombre: String = "",
    val email: String = "",
    val rol: String = "USUARIO", // "ADMIN" | "USUARIO" | "LIMITADO"
    val limiteGastoPorEnvio: Double = 0.0,
    val rutasFrecuentes: List<String> = emptyList(),
    val activo: Boolean = true,
    val fechaCreacion: Long = System.currentTimeMillis(),
    val ultimoAcceso: Long? = null
)

data class ReporteEnvios(
    val empresaId: String = "",
    val periodoInicio: Long = 0L,
    val periodoFin: Long = 0L,
    val totalEnvios: Int = 0,
    val costoTotal: Double = 0.0,
    val enviosPorEstado: Map<String, Int> = emptyMap(),
    val enviosPorTipoCarga: Map<String, Int> = emptyMap(),
    val rutasMasUsadas: List<RutaFrecuente> = emptyList(),
    val transportistasFrecuentes: List<TransportistaFrecuente> = emptyList(),
    val ahorroEstimado: Double = 0.0,
    val emisionesCO2Evitadas: Double = 0.0, // en kg
    val timestamp: Long = System.currentTimeMillis()
)

data class RutaFrecuente(
    val origen: String = "",
    val destino: String = "",
    val cantidad: Int = 0,
    val costoPromedio: Double = 0.0
)

data class TransportistaFrecuente(
    val transportistaId: String = "",
    val transportistaNombre: String = "",
    val cantidadEnvios: Int = 0,
    val calificacionPromedio: Double = 0.0
)

data class EstadisticaMensual(
    val empresaId: String = "",
    val mes: Int = 0, // 1-12
    val anio: Int = 0,
    val totalEnvios: Int = 0,
    val costoTotal: Double = 0.0,
    val costoPromedioPorEnvio: Double = 0.0,
    val pesoTotalTransportado: Int = 0, // en kg
    val rutasUnicas: Int = 0,
    val transportistasUnicos: Int = 0,
    val tasaEntregaATiempo: Double = 0.0, // porcentaje
    val calificacionPromedio: Double = 0.0,
    val timestamp: Long = System.currentTimeMillis()
)
