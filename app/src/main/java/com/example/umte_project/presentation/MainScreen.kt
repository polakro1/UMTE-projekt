package com.example.umte_project.presentation


import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.umte_project.data.datastore.PreferenceManager
import com.example.umte_project.presentation.navigation.NavigationHost
import com.example.umte_project.presentation.navigation.NavigationUtils
import com.example.umte_project.presentation.navigation.Routes
import com.example.umte_project.presentation.navigation.getTopBarActionsForRoute
import com.example.umte_project.presentation.ui_components.BottomNavigationBar
import com.example.umte_project.utils.BiometricAuthManager
import com.example.umte_project.utils.findActivity
import com.example.umte_project.utils.findFragmentActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val actions = getTopBarActionsForRoute(currentRoute, navController)

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val biometricEnabledFlow = PreferenceManager.getBiometricEnabled(context)
    val biometricEnabled by biometricEnabledFlow.collectAsState(initial = false)

    LaunchedEffect(Unit) {
        if (biometricEnabled) {
            BiometricAuthManager.authenticate(
                context = context,
                onSuccess = {

                },
                onFailure = {
                    Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show()
                    context.findFragmentActivity()?.finishAffinity()
                }
            )
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                if (biometricEnabled) {
                    BiometricAuthManager.authenticate(
                        context = context,
                        onSuccess = {
                            // Ověření úspěšné
                        },
                        onFailure = {
                            Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT)
                                .show()
                            context.findFragmentActivity()?.finishAffinity()
                        }
                    )
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = NavigationUtils.getTitleForRoute(currentRoute)) },
                navigationIcon = {
                    if (NavigationUtils.shouldShowBackButton(currentRoute)) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                },
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
            if (NavigationUtils.shouldShowFab(currentRoute)) {
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
                }
                ) { Icon(Icons.Default.Add, contentDescription = "Add expense") }
            }
        },
        bottomBar = {
            BottomNavigationBar(navController = navController, currentRoute = currentRoute)
        }
    ) { innerPadding ->
        NavigationHost(navController, innerPadding)
    }
}