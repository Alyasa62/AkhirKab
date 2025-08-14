package com.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.presentation.calculator.CalculatorScreen
import com.presentation.calculator.CalculatorViewModel
import com.presentation.dashboard.DashboardScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        startDestination = Route.DashboardScreen,
        navController = navController,

    ) {

        composable<Route.DashboardScreen> {
            DashboardScreen(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                navigateToCalculatorScreen = { occasionId ->
                    navController.navigate(Route.CalculatorScreen(occasionId))
                }
            )
        }
        composable<Route.CalculatorScreen> {
                val viewModel: CalculatorViewModel = koinViewModel()
                    val state by viewModel.uiState.collectAsStateWithLifecycle()

                    CalculatorScreen(
                        state = state,
                        onAction = viewModel :: onAction,
                        navigateUp = { navController.navigateUp() }

                 )
        }
    }

}