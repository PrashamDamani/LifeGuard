package com.lifeguard.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lifeguard.app.ui.auth.AuthScreen
import com.lifeguard.app.ui.dashboard.DashboardScreen
import com.lifeguard.app.ui.onboarding.FeaturesScreen
import com.lifeguard.app.ui.splash.SplashScreen

object Routes {
    const val SPLASH = "splash"
    const val FEATURES = "features"
    const val AUTH = "auth"
    const val DASHBOARD = "dashboard"
}

@Composable
fun LifeGuardNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(
                onFinished = {
                    navController.navigate(Routes.FEATURES) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.FEATURES) {
            FeaturesScreen(
                onContinue = {
                    navController.navigate(Routes.AUTH) {
                        popUpTo(Routes.FEATURES) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.AUTH) {
            AuthScreen(
                onAuthSuccess = {
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.AUTH) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.DASHBOARD) {
            DashboardScreen(
                onLogout = {
                    navController.navigate(Routes.AUTH) {
                        popUpTo(Routes.DASHBOARD) { inclusive = true }
                    }
                }
            )
        }
    }
}
