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
import com.apay.carga.home.CargaHomeScreen
import com.apay.carga.rutas.PublicarRutaScreen
import com.apay.carga.buscar.BuscarCargaScreen
import com.apay.carga.envios.MisEnviosScreen
import com.apay.carga.detalle.DetalleEnvioScreen
import com.apay.carga.perfil.PerfilScreen
import com.apay.ui.components.*
import com.apay.ui.theme.*

@Composable
fun AppNavHost(
    onLogout: () -> Unit = {}
) {
    val navController = rememberNavController()

    NavHost(
        navController    = navController,
        startDestination = Screen.CargaHome.route
    ) {
        composable(Screen.CargaHome.route) {
            MainShell(navController, onLogout = onLogout) {
                CargaHomeScreen(
                    onPublicarRuta = { navController.navigate(Screen.PublicarRuta.route) },
                    onBuscarCarga = { navController.navigate(Screen.BuscarCarga.route) },
                    onVerEnvios = { navController.navigate(Screen.MisEnvios.route) },
                    onIrAPerfil = { navController.navigate(Screen.Perfil.route) }
                )
            }
        }
        composable(Screen.PublicarRuta.route) {
            MainShell(navController, onLogout = onLogout) {
                PublicarRutaScreen(
                    onVolver = { navController.popBackStack() },
                    onPublicar = { /* TODO: Implementar lógica de publicación */ }
                )
            }
        }
        composable(Screen.BuscarCarga.route) {
            MainShell(navController, onLogout = onLogout) {
                BuscarCargaScreen(
                    onVolver = { navController.popBackStack() },
                    onReservar = { /* TODO: Implementar lógica de reserva */ }
                )
            }
        }
        composable(Screen.MisEnvios.route) {
            MainShell(navController, onLogout = onLogout) {
                MisEnviosScreen(
                    onVolver = { navController.popBackStack() },
                    onVerDetalle = { envio -> 
                        navController.navigate(Screen.DetalleEnvio.createRoute(envio.id))
                    }
                )
            }
        }
        composable(
            route = Screen.DetalleEnvio.route,
            arguments = listOf(navArgument("envioId") { type = NavType.StringType })
        ) { backStackEntry ->
            val envioId = backStackEntry.arguments?.getString("envioId") ?: ""
            MainShell(navController, onLogout = onLogout) {
                DetalleEnvioScreen(
                    envioId = envioId,
                    onVolver = { navController.popBackStack() },
                    onConfirmarEntrega = { /* TODO: Implementar confirmación de entrega */ }
                )
            }
        }
        composable(Screen.Perfil.route) {
            MainShell(navController, onLogout = onLogout) {
                PerfilScreen(
                    esTransportista = true,
                    onVolver = { navController.popBackStack() },
                    onEditarPerfil = { /* TODO: Implementar edición de perfil */ },
                    onCerrarSesion = onLogout
                )
            }
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
        A2NavItem("Inicio",      Lucide.Home),
        A2NavItem("Mis Envíos",  Lucide.Package),
        A2NavItem("Perfil",      Lucide.User),
    )
    val navRoutes = listOf(Screen.CargaHome.route, Screen.MisEnvios.route, Screen.Perfil.route)
    val selectedIndex = navRoutes.indexOfFirst { route ->
        currentRoute?.startsWith(route.split("/{")[0]) == true
    }.coerceAtLeast(0)

    Column(modifier = Modifier.fillMaxSize()) {
        if (shouldShowChrome) {
            A2TopBar(
                title = "Apay Carga",
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
