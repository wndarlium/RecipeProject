package com.jose.pruebtecnicakoaliti.util.validators

data class LoginValidationResult(
    val emailError: String? = null,
    val passwordError: String? = null,
    val isValid: Boolean = false
)

fun validateLogin(email: String, password: String): LoginValidationResult {
    var emailError: String? = null
    var passwordError: String? = null

    if (email.isBlank()) {
        emailError = "El email no puede estar vacío"
    }
    if (password.isBlank()) {
        passwordError = "La contraseña no puede estar vacía"
    }
    if (emailError == null && passwordError == null) {
        if (email != "info@koalit.dev" || password != "koalit123") {
            emailError = "Credenciales incorrectas"
        }
    }

    return LoginValidationResult(
        emailError = emailError,
        passwordError = passwordError,
        isValid = emailError == null && passwordError == null
    )
}

