package com.example.nammamela.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nammamela.ui.screens.AIHelperScreen
import com.example.nammamela.ui.screens.CastListScreen
import com.example.nammamela.ui.screens.DashboardScreen
import com.example.nammamela.ui.screens.EventDetailScreen
import com.example.nammamela.ui.screens.FanWallScreen
import com.example.nammamela.ui.screens.LoginScreen
import com.example.nammamela.ui.screens.SeatReservationScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nammamela.ui.viewmodel.AIHelperViewModel
import com.example.nammamela.ui.viewmodel.FanWallViewModel
import com.example.nammamela.ui.viewmodel.PlayViewModel
import com.example.nammamela.ui.viewmodel.SeatMapViewModel
import com.example.nammamela.ui.viewmodel.ViewModelFactory

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
    object CastList : Screen("cast_list")
    object SeatReservation : Screen("seat_reservation/{playId}") {
        fun createRoute(playId: Int) = "seat_reservation/$playId"
    }
    object FanWall : Screen("fan_wall/{playId}") {
        fun createRoute(playId: Int) = "fan_wall/$playId"
    }
    object EventDetail : Screen("event_detail/{playId}") {
        fun createRoute(playId: Int) = "event_detail/$playId"
    }
    object AIHelper : Screen("ai_helper")
}

@Composable
fun AppNavigation(viewModelFactory: ViewModelFactory) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login.route) {

        composable(route = Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screen.Dashboard.route) {
            val viewModel: PlayViewModel = viewModel(factory = viewModelFactory)
            DashboardScreen(
                viewModel = viewModel,
                onNavigateToCast = { navController.navigate(Screen.CastList.route) },
                onNavigateToSeats = { },
                onNavigateToFanWall = { },
                onNavigateToEventDetail = { playId ->
                    navController.navigate(Screen.EventDetail.createRoute(playId))
                },
                onNavigateToAIHelper = { navController.navigate(Screen.AIHelper.route) },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screen.CastList.route) {
            val viewModel: PlayViewModel = viewModel(factory = viewModelFactory)
            CastListScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.SeatReservation.route,
            arguments = listOf(navArgument("playId") { type = NavType.IntType })
        ) { backStackEntry ->
            val playId = backStackEntry.arguments?.getInt("playId") ?: 1
            val viewModel: SeatMapViewModel = viewModel(factory = viewModelFactory)
            SeatReservationScreen(
                playId = playId,
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.FanWall.route,
            arguments = listOf(navArgument("playId") { type = NavType.IntType })
        ) { backStackEntry ->
            val playId = backStackEntry.arguments?.getInt("playId") ?: 1
            val viewModel: FanWallViewModel = viewModel(factory = viewModelFactory)
            FanWallScreen(
                playId = playId,
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.EventDetail.route,
            arguments = listOf(navArgument("playId") { type = NavType.IntType })
        ) { backStackEntry ->
            val playId = backStackEntry.arguments?.getInt("playId") ?: 1
            val viewModel: PlayViewModel = viewModel(factory = viewModelFactory)
            EventDetailScreen(
                playId = playId,
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onNavigateToSeats = { navController.navigate(Screen.SeatReservation.createRoute(playId)) },
                onNavigateToFanWall = { navController.navigate(Screen.FanWall.createRoute(playId)) },
                onNavigateToAIHelper = { navController.navigate(Screen.AIHelper.route) }
            )
        }

        composable(route = Screen.AIHelper.route) {
            val viewModel: AIHelperViewModel = viewModel(factory = viewModelFactory)
            AIHelperScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
