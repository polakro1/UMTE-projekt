package com.example.umte_project.utils

import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

object BiometricAuthManager {
    fun authenticate(context: Context, onSuccess: () -> Unit, onFailure: () -> Unit) {
        val fragmentActivity = context as? FragmentActivity
        if (fragmentActivity == null) {
            onFailure()
            return
        }

        val executor = ContextCompat.getMainExecutor(fragmentActivity)

        val biometricPrompt = BiometricPrompt(
            context,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Log.d("BiometricAuth", "Authentication succeeded")
                    onSuccess()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Log.d("BiometricAuth", "Authentication error: $errString")
                    onFailure()
                }

                override fun onAuthenticationFailed() {
                    Log.d("BiometricAuth", "Authentication failed")
                    super.onAuthenticationFailed()
                }
            }
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Authentication Required")
            .setSubtitle("Authenticate to continue")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}