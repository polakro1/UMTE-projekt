package com.example.umte_project.presentation.select_location

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.umte_project.presentation.ui_components.Map
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun SelectLocationScreen(
    initialLatLng: LatLng? = null,
    navController: NavController
) {
    var selectedPosition by remember {
        mutableStateOf(initialLatLng ?: LatLng(50.0755, 14.4378))
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(selectedPosition, 14f)
    }

    Column {
        IconButton(onClick = {
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set("selected_location", selectedPosition)
            navController.popBackStack()
        }) {
            Icon(Icons.Default.Check, contentDescription = "Confirm")
        }

        Map(initialLatLng = selectedPosition)
    }

}