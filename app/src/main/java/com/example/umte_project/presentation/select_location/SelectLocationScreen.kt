package com.example.umte_project.presentation.select_location

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.umte_project.presentation.ui_components.ConfirmButton
import com.example.umte_project.presentation.ui_components.Map
import com.example.umte_project.presentation.utils.rememberCurrentLocationWithPermission
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng

@Composable
fun SelectLocationScreen(
    navController: NavController,
    initialLatLng: LatLng? = null
) {
    val currentLocation = rememberCurrentLocationWithPermission()
    val fallback = LatLng(50.0755, 14.4378) // fallback na Prahu

    var selectedPosition by remember {
        mutableStateOf(currentLocation ?: fallback)
    }

    LaunchedEffect(currentLocation) {
        currentLocation?.let {
            selectedPosition = it
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Map(
            initialLatLng = selectedPosition,
            onLocationSelected = { selectedPosition = it }
        )

        ConfirmButton(
            text = "Save location",
            onClick = {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("selected_location", selectedPosition)
                navController.popBackStack()
            },
            isLoading = false,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }

}