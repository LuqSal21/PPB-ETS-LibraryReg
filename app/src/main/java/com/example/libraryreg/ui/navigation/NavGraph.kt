package com.example.libraryreg.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.libraryreg.ui.screens.DaftarAnggotaScreen
import com.example.libraryreg.ui.screens.DetailAnggotaScreen
import com.example.libraryreg.ui.screens.EditAnggotaScreen
import com.example.libraryreg.ui.screens.FormulirPendaftaranScreen
import com.example.libraryreg.ui.screens.LoginPetugasScreen
import com.example.libraryreg.ui.screens.PendaftaranBerhasilScreen
import com.example.libraryreg.ui.screens.PilihPeranScreen
import com.example.libraryreg.ui.screens.SplashScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(Screen.PilihPeran.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.PilihPeran.route) {
            PilihPeranScreen(
                onSelectCalonAnggota = {
                    navController.navigate(Screen.FormulirPendaftaran.route)
                },
                onSelectPetugas = {
                    navController.navigate(Screen.LoginPetugas.route)
                }
            )
        }

        composable(Screen.FormulirPendaftaran.route) {
            FormulirPendaftaranScreen(
                onNavigateBack = { navController.popBackStack() },
                onSuccess = { memberId ->
                    navController.navigate(Screen.PendaftaranBerhasil.createRoute(memberId)) {
                        popUpTo(Screen.PilihPeran.route) { inclusive = false }
                    }
                }
            )
        }

        composable(
            route = Screen.PendaftaranBerhasil.route,
            arguments = listOf(navArgument("memberId") { type = NavType.IntType })
        ) { backStackEntry ->
            val memberId = backStackEntry.arguments?.getInt("memberId") ?: 0
            PendaftaranBerhasilScreen(
                memberId = memberId,
                onDaftarLain = {
                    navController.navigate(Screen.FormulirPendaftaran.route) {
                        popUpTo(Screen.PendaftaranBerhasil.route) { inclusive = true }
                    }
                },
                onKembaliBeranda = {
                    navController.navigate(Screen.PilihPeran.route) {
                        popUpTo(Screen.PilihPeran.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.LoginPetugas.route) {
            LoginPetugasScreen(
                onNavigateBack = { navController.popBackStack() },
                onLoginSuccess = {
                    navController.navigate(Screen.DaftarAnggota.route) {
                        popUpTo(Screen.LoginPetugas.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.DaftarAnggota.route) {
            DaftarAnggotaScreen(
                onNavigateToAdd = {
                    navController.navigate(Screen.FormulirPendaftaran.route)
                },
                onNavigateToDetail = { memberId ->
                    navController.navigate(Screen.DetailAnggota.createRoute(memberId))
                },
                onNavigateToEdit = { memberId ->
                    navController.navigate(Screen.EditAnggota.createRoute(memberId))
                },
                onLogout = {
                    navController.navigate(Screen.PilihPeran.route) {
                        popUpTo(Screen.PilihPeran.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.DetailAnggota.route,
            arguments = listOf(navArgument("memberId") { type = NavType.IntType })
        ) { backStackEntry ->
            val memberId = backStackEntry.arguments?.getInt("memberId") ?: 0
            DetailAnggotaScreen(
                memberId = memberId,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToEdit = { id ->
                    navController.navigate(Screen.EditAnggota.createRoute(id))
                }
            )
        }

        composable(
            route = Screen.EditAnggota.route,
            arguments = listOf(navArgument("memberId") { type = NavType.IntType })
        ) { backStackEntry ->
            val memberId = backStackEntry.arguments?.getInt("memberId") ?: 0
            EditAnggotaScreen(
                memberId = memberId,
                onNavigateBack = { navController.popBackStack() },
                onSuccess = { navController.popBackStack() }
            )
        }
    }
}
