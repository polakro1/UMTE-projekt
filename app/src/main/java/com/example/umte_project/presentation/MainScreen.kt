package com.example.umte_project.presentation


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.umte_project.presentation.navigation.NavigationHost
import com.example.umte_project.presentation.navigation.NavigationUtils
import com.example.umte_project.presentation.navigation.Routes
import com.example.umte_project.presentation.navigation.getTopBarActionsForRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val actions = getTopBarActionsForRoute(currentRoute, navController)


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = NavigationUtils.getTitleForRoute(currentRoute)) },
                actions = {
                    actions.forEach { action ->
                        IconButton(onClick = action.onClick) {
                            Icon(action.icon, action.contentDescription)
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Routes.ADD_EXPENSE) {
                    navController.graph.startDestinationRoute?.let { screenRoute ->
                        popUpTo(screenRoute) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }) { }
        }
    ) { innerPadding ->
        NavigationHost(navController, innerPadding)
    }
}