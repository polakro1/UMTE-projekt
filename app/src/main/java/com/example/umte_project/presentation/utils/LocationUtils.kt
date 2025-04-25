package com.example.umte_project.presentation.utils

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.nestedscroll.NestedScrollSource.Companion.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun rememberCurrentLocationWithPermission(): LatLng? {
    val context = LocalContext.current
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    var currentLocation by remember { mutableStateOf<LatLng?>(null) }

    LaunchedEffect(permissionState.status) {
        if (permissionState.status.isGranted) {
            val client = LocationServices.getFusedLocationProviderClient(context)
            try {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    val location = client.getCurrentLocation(
                        com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
                        null
                    ).await()
                    if (location != null) {
                        currentLocation = LatLng(location.latitude, location.longitude)
                    }
                }
                Log.d("TAG", currentLocation.toString())
            } catch (e: SecurityException) {
                Log.w("Location", "SecurityException: ${e.message}")
            } catch (e: Exception) {
                Log.w("Location", "General error: ${e.message}")
            }
        }
    }

    SideEffect {
        if (permissionState.status.shouldShowRationale || !permissionState.status.isGranted) {
            permissionState.launchPermissionRequest()
        }
    }

    return currentLocation
}