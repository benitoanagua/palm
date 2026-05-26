package com.apay.data.repository

import com.apay.domain.model.*
import com.apay.domain.repository.ReporteRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreReporteRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : ReporteRepository {

    private val enviosCollection get() = firestore.collection("envios")
    private val estadisticasCollection get() = firestore.collection("estadisticas_mensuales")

    override suspend fun generarReporteEnvios(
        empresaId: String,
        fechaInicio: Long,
        fechaFin: Long
    ): Result<ReporteEnvios> {
        return try {
            // Obtener todos los envíos de la empresa en el período
            val snapshot = enviosCollection
                .whereEqualTo("cargadorEmpresa", empresaId)
                .whereGreaterThanOrEqualTo("timestamp", fechaInicio)
                .whereLessThanOrEqualTo("timestamp", fechaFin)
                .get()
                .await()

            val envios = snapshot.documents.mapNotNull { 
                it.toObject(Envio::class.java) 
            }

            // Calcular estadísticas por estado
            val enviosPorEstado = envios.groupingBy { it.estado }
                .eachCount()

            // Calcular estadísticas por tipo de carga
            val enviosPorTipoCarga = envios.groupingBy { it.tipoCarga }
                .eachCount()

            // Calcular rutas más usadas
            val rutasAgrupadas = envios.groupBy { "${it.origen} -> ${it.destino}" }
            val rutasMasUsadas = rutasAgrupadas
                .map { (ruta, enviosRuta) ->
                    RutaFrecuente(
                        origen = enviosRuta.first().origen,
                        destino = enviosRuta.first().destino,
                        cantidad = enviosRuta.size,
                        costoPromedio = enviosRuta.map { it.precioTotal }.average()
                    )
                }
                .sortedByDescending { it.cantidad }
                .take(10)

            // Calcular transportistas frecuentes
            val transportistasAgrupados = envios.groupBy { it.transportistaId }
            val transportistasFrecuentes = transportistasAgrupados
                .map { (id, enviosTransportista) ->
                    TransportistaFrecuente(
                        transportistaId = id,
                        transportistaNombre = enviosTransportista.first().transportistaNombre,
                        cantidadEnvios = enviosTransportista.size,
                        calificacionPromedio = enviosTransportista
                            .mapNotNull { it.calificacionTransportista }
                            .average()
                            .takeIf { !it.isNaN() } ?: 0.0
                    )
                }
                .sortedByDescending { it.cantidadEnvios }
                .take(10)

            // Calcular costo total
            val costoTotal = envios.sumOf { it.precioTotal }

            // Estimación de ahorro (comparado con tarifa informal promedio ~20% más alta)
            val ahorroEstimado = costoTotal * 0.20

            // Estimación de emisiones CO2 evitadas (aproximadamente 0.1 kg CO2 por kg-km)
            // Asumiendo distancia promedio de 300 km por envío
            val pesoTotalKg = envios.sumOf { it.pesoKg }
            val emisionesCO2Evitadas = pesoTotalKg * 300 * 0.1

            val reporte = ReporteEnvios(
                empresaId = empresaId,
                periodoInicio = fechaInicio,
                periodoFin = fechaFin,
                totalEnvios = envios.size,
                costoTotal = costoTotal,
                enviosPorEstado = enviosPorEstado,
                enviosPorTipoCarga = enviosPorTipoCarga,
                rutasMasUsadas = rutasMasUsadas,
                transportistasFrecuentes = transportistasFrecuentes,
                ahorroEstimado = ahorroEstimado,
                emisionesCO2Evitadas = emisionesCO2Evitadas
            )

            Result.success(reporte)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun observarEstadisticasMensuales(empresaId: String, anio: Int): Flow<List<EstadisticaMensual>> = callbackFlow {
        val subscription = estadisticasCollection
            .whereEqualTo("empresaId", empresaId)
            .whereEqualTo("anio", anio)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(emptyList())
                    return@addSnapshotListener
                }

                val estadisticas = snapshot?.documents?.mapNotNull { 
                    it.toObject(EstadisticaMensual::class.java) 
                }?.sortedBy { it.mes } ?: emptyList()
                
                trySend(estadisticas)
            }

        awaitClose { subscription.remove() }
    }

    override suspend fun guardarEstadisticaMensual(estadistica: EstadisticaMensual): Result<Unit> {
        return try {
            // Verificar si ya existe una estadística para ese mes/año
            val existing = estadisticasCollection
                .whereEqualTo("empresaId", estadistica.empresaId)
                .whereEqualTo("mes", estadistica.mes)
                .whereEqualTo("anio", estadistica.anio)
                .limit(1)
                .get()
                .await()

            if (existing.documents.isNotEmpty()) {
                // Actualizar existente
                val docId = existing.documents[0].id
                estadisticasCollection.document(docId).set(estadistica).await()
            } else {
                // Crear nueva
                estadisticasCollection.document().set(estadistica).await()
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun exportarReportePDF(empresaId: String, mes: Int, anio: Int): Result<String> {
        return try {
            // Generar URL simulada del PDF (en producción se integraría con un servicio de generación de PDFs)
            val nombreArchivo = "reporte_${empresaId}_${mes}_${anio}.pdf"
            val urlSimulada = "https://storage.googleapis.com/apay-reports/$nombreArchivo"
            
            // Aquí se implementaría la lógica real de generación de PDF
            // Usando una librería como iText o PdfDocument de Android
            
            Result.success(urlSimulada)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun exportarReporteExcel(empresaId: String, mes: Int, anio: Int): Result<String> {
        return try {
            // Generar URL simulada del Excel (en producción se integraría con Apache POI o similar)
            val nombreArchivo = "reporte_${empresaId}_${mes}_${anio}.xlsx"
            val urlSimulada = "https://storage.googleapis.com/apay-reports/$nombreArchivo"
            
            // Aquí se implementaría la lógica real de generación de Excel
            // Usando Apache POI para crear el archivo con las hojas correspondientes
            
            Result.success(urlSimulada)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
