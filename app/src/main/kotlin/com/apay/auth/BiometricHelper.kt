package com.apay.auth

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

object BiometricHelper {

    private val AUTHENTICATORS = BIOMETRIC_STRONG or BIOMETRIC_WEAK or DEVICE_CREDENTIAL
    private val SOLO_BIOMETRICO = BIOMETRIC_STRONG or BIOMETRIC_WEAK

    fun isDisponible(activity: FragmentActivity): Boolean {
        val manager = BiometricManager.from(activity)
        return manager.canAuthenticate(AUTHENTICATORS) == BiometricManager.BIOMETRIC_SUCCESS
    }

    fun tieneBiometria(activity: FragmentActivity): Boolean {
        val manager = BiometricManager.from(activity)
        return manager.canAuthenticate(SOLO_BIOMETRICO) == BiometricManager.BIOMETRIC_SUCCESS
    }

    fun autenticar(
        activity: FragmentActivity,
        onExito: () -> Unit,
        onError: (String) -> Unit,
        onCancelado: () -> Unit = {}
    ) {
        val executor = ContextCompat.getMainExecutor(activity)

        val prompt = BiometricPrompt(
            activity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    onExito()
                }
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    when (errorCode) {
                        BiometricPrompt.ERROR_USER_CANCELED,
                        BiometricPrompt.ERROR_NEGATIVE_BUTTON,
                        BiometricPrompt.ERROR_CANCELED -> onCancelado()
                        else -> onError(mapBiometricError(errorCode, errString.toString()))
                    }
                }
                override fun onAuthenticationFailed() {
                    // Intento fallido — la UI lo maneja con el propio prompt
                }
            }
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Acceder a Apay")
            .setSubtitle(
                if (tieneBiometria(activity)) "Usa tu huella o cara para continuar"
                else "Usa tu PIN o patrón para continuar"
            )
            .setAllowedAuthenticators(AUTHENTICATORS)
            .build()

        prompt.authenticate(promptInfo)
    }

    private fun mapBiometricError(code: Int, msg: String): String = when (code) {
        BiometricPrompt.ERROR_LOCKOUT,
        BiometricPrompt.ERROR_LOCKOUT_PERMANENT -> "Demasiados intentos. Desbloquea el dispositivo primero."
        BiometricPrompt.ERROR_NO_BIOMETRICS     -> "No hay huella o cara registrada."
        BiometricPrompt.ERROR_HW_UNAVAILABLE    -> "El sensor biométrico no está disponible."
        BiometricPrompt.ERROR_NO_DEVICE_CREDENTIAL -> "Configura un PIN o patrón en tu dispositivo."
        else -> "Error de autenticación. Intenta de nuevo."
    }
}
