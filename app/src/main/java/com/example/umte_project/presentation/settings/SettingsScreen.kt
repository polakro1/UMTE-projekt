package com.example.umte_project.presentation.settings

import android.widget.Space
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.umte_project.data.datastore.PreferenceManager
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val biometricEnabledFlow = PreferenceManager.getBiometricEnabled(context)
    val biometricEnabled by biometricEnabledFlow.collectAsState(initial = false)

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Biometric Authentication")
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = biometricEnabled,
                onCheckedChange = { isChecked ->
                    scope.launch {
                        PreferenceManager.setBiometricEnabled(context, isChecked)
                    }
                }
            )
        }
    }
}