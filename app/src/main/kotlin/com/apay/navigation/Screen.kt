package com.apay.navigation

sealed class Screen(val route: String) {
    data object Login        : Screen("login")
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
            listOf(Chats.route, Tasa.route, Comprobante.route, Cuenta.route)
        }
    }
}
