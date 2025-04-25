package com.example.umte_project.presentation.select_location

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.umte_project.presentation.ui_components.ConfirmButton
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

    Box(modifier = Modifier.fillMaxSize()) {
        Map(initialLatLng = selectedPosition)

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