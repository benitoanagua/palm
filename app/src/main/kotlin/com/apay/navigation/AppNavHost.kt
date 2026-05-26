package com.apay.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.composables.icons.lucide.*
import com.apay.chats.ChatsScreen
import com.apay.comprobante.ComprobanteScreen
import com.apay.cuenta.CuentaScreen
import com.apay.tasa.TasaScreen
import com.apay.ui.components.*
import com.apay.ui.theme.*

@Composable
fun AppNavHost(
    initialChatId: String? = null,
    onLogout: () -> Unit = {}
) {
    val navController = rememberNavController()

    val handleLogout: () -> Unit = {
        navController.navigate(Screen.Chats.route) {
            popUpTo(0) { inclusive = true }
        }
        onLogout()
    }

    NavHost(
        navController    = navController,
        startDestination = Screen.Chats.route
    ) {
        composable(Screen.Chats.route) {
            MainShell(navController, onLogout = handleLogout) {
                ChatsScreen(onLogout = handleLogout)
            }
        }
        composable(Screen.Tasa.route) {
            MainShell(navController, onLogout = handleLogout) {
                TasaScreen(
                    onIrAComprobante = { navController.navigate(Screen.Comprobante.route) { launchSingleTop = true } },
                    onIrACuenta      = { navController.navigate(Screen.Cuenta.route) { launchSingleTop = true } },
                    onIrAChats       = { navController.navigate(Screen.Chats.route) { launchSingleTop = true } }
                )
            }
        }
        composable(Screen.Comprobante.route) {
            MainShell(navController, onLogout = handleLogout) {
                ComprobanteScreen(onVolver = { navController.popBackStack() })
            }
        }
        composable(Screen.Cuenta.route) {
            MainShell(navController, onLogout = handleLogout) {
                CuentaScreen(
                    onLogout = handleLogout,
                    onIrAHistorial = { navController.navigate(Screen.Historial.route) }
                )
            }
        }
        composable(Screen.Historial.route) {
            com.apay.cuenta.HistorialScreen(onVolver = { navController.popBackStack() })
        }
        composable(
            route     = "conversacion/{chatId}?titulo={titulo}",
            arguments = listOf(
                navArgument("chatId") { type = NavType.StringType },
                navArgument("titulo") { type = NavType.StringType; defaultValue = "Chat" }
            )
        ) {
            com.apay.conversacion.ConversacionScreen(onVolver = { navController.popBackStack() })
        }
        composable(Screen.NuevoChat.route) {
            com.apay.chats.NuevoChatScreen(
                onChatIniciado = { chatId, titulo ->
                    val encoded = java.net.URLEncoder.encode(titulo, "UTF-8")
                    navController.navigate("conversacion/$chatId?titulo=$encoded") {
                        popUpTo(Screen.NuevoChat.route) { inclusive = true }
                    }
                },
                onVolver = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun MainShell(
    navController: NavController,
    onLogout: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val shouldShowChrome = Screen.BOTTOM_NAV_SCREENS.any { route ->
        currentRoute?.startsWith(route.split("/{")[0]) == true
    }

    val navItems = listOf(
        A2NavItem("Chats",       Lucide.MessageSquare),
        A2NavItem("Tasa",        Lucide.ChartLine),
        A2NavItem("Comprobante", Lucide.Upload),
        A2NavItem("Cuenta",      Lucide.User),
    )
    val navRoutes = listOf(Screen.Chats.route, Screen.Tasa.route, Screen.Comprobante.route, Screen.Cuenta.route)
    val selectedIndex = navRoutes.indexOfFirst { route ->
        currentRoute?.startsWith(route.split("/{")[0]) == true
    }.coerceAtLeast(0)

    Column(modifier = Modifier.fillMaxSize()) {
        if (shouldShowChrome) {
            A2TopBar(
                title = "A2",
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Lucide.LogOut, "Cerrar sesión", tint = SecondaryElectric)
                    }
                }
            )
        } else {
            Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
        }

        Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
            content()
        }

        if (shouldShowChrome) {
            A2BottomNav(
                items         = navItems,
                selectedIndex = selectedIndex,
                onItemSelected = { index ->
                    val route = navRoutes[index]
                    if (currentRoute?.startsWith(route.split("/{")[0]) != true) {
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState    = true
                        }
                    }
                }
            )
        } else {
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
        }
    }
}
