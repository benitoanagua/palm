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

    companion object {
        val BOTTOM_NAV_SCREENS by lazy {
            listOf(CargaHome.route, MisEnvios.route, Perfil.route)
        }
    }
}
