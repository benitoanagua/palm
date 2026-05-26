package com.apay.auth

// Firebase (comentado - activar cuando se configuren los servicios)
// import com.google.firebase.auth.FirebaseAuth
// import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
// import javax.inject.Inject

data class AuthState(
    val isLoggedIn: Boolean    = false,
    val isLoading: Boolean     = false,
    val error: String?         = null,
    val success: String?       = null,
    val justLoggedOut: Boolean = false
)

// @HiltViewModel
// class AuthViewModel @Inject constructor(
//     private val auth: FirebaseAuth
// ) : ViewModel() {

//     private val _authState = MutableStateFlow(
//         AuthState(isLoggedIn = auth.currentUser != null)
//     )
//     val authState: StateFlow<AuthState> = _authState.asStateFlow()

//     fun login(email: String, password: String) = viewModelScope.launch {
//         _authState.value = _authState.value.copy(isLoading = true, error = null)
//         auth.signInWithEmailAndPassword(email, password)
//             .addOnSuccessListener {
//                 _authState.value = AuthState(isLoggedIn = true)
//             }
//             .addOnFailureListener { e ->
//                 _authState.value = AuthState(
//                     isLoggedIn = false,
//                     error = mapFirebaseError(e.message)
//                 )
//             }
//     }

//     fun resetPassword(email: String) {
//         if (email.isBlank()) {
//             _authState.value = _authState.value.copy(error = "Ingresa tu email primero")
//             return
//         }
//         _authState.value = _authState.value.copy(isLoading = true, error = null, success = null)
//         auth.sendPasswordResetEmail(email.trim())
//             .addOnSuccessListener {
//                 _authState.value = _authState.value.copy(
//                     isLoading = false,
//                     success = "Revisa tu correo para restablecer la contraseña"
//                 )
//             }
//             .addOnFailureListener { e ->
//                 _authState.value = _authState.value.copy(
//                     isLoading = false,
//                     error = mapFirebaseError(e.message)
//                 )
//             }
//     }

//     fun logout() {
//         auth.signOut()
//         _authState.value = AuthState(isLoggedIn = false, justLoggedOut = true)
//     }

//     fun clearError() {
//         _authState.value = _authState.value.copy(error = null, success = null)
//     }

//     private fun mapFirebaseError(message: String?): String = when {
//         message == null                                              -> "Ocurrió un error inesperado"
//         "INVALID_EMAIL" in message || "badly formatted" in message  -> "El formato del email no es válido"
//         "WRONG_PASSWORD" in message || "password is invalid" in message -> "Contraseña incorrecta"
//         "USER_NOT_FOUND" in message || "no user record" in message  -> "No existe una cuenta con ese email"
//         "USER_DISABLED" in message                                   -> "Esta cuenta ha sido deshabilitada"
//         "TOO_MANY_REQUESTS" in message                               -> "Demasiados intentos. Espera unos minutos"
//         "NETWORK" in message || "network" in message                 -> "Sin conexión a internet"
//         else                                                         -> "Error de autenticación. Intenta de nuevo"
//     }
// }
