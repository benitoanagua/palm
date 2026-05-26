package com.apay.navigation

sealed class Screen(val route: String) {
    data object Login        : Screen("login")
    data object CargaHome    : Screen("carga_home")
    data object PublicarRuta : Screen("publicar_ruta")
    data object BuscarCarga  : Screen("buscar_carga")
    data object MisEnvios    : Screen("mis_envios")
    data object DetalleEnvio : Screen("detalle_envio/{envioId}") {
        fun createRoute(envioId: String) = "detalle_envio/$envioId"
    }
    data object Perfil       : Screen("perfil")
    
    // Pantallas legacy (para mantener compatibilidad)
    data object Chats        : Screen("chats")
    data object Tasa         : Screen("tasa")
    data object Comprobante  : Screen("comprobante")
    data object Cuenta       : Screen("cuenta")
    data object Historial    : Screen("historial")
    data object NuevoChat    : Screen("nuevo_chat")
    data object Conversacion : Screen("conversacion/{chatId}") {
        fun createRoute(chatId: String) = "conversacion/$chatId"
    }

    companion object {
        val BOTTOM_NAV_SCREENS by lazy {
            listOf(CargaHome.route, MisEnvios.route, Perfil.route)
        }
    }
}
