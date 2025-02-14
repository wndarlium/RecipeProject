package com.jose.pruebtecnicakoaliti.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.content.Context
import com.jose.pruebtecnicakoaliti.data.datastore.SessionManager

class LoginViewModel(private val context: Context) : ViewModel() {
    private val sessionManager = SessionManager(context)
    private val _loginState = MutableStateFlow(false)
    val loginState: StateFlow<Boolean> = _loginState

    fun login(email: String, password: String, remember: Boolean) {
        if (email == "info@koalit.dev" && password == "koalit123") {
            if (remember) {
                sessionManager.setLogin(true)
            }
            _loginState.value = true
        } else {
            _loginState.value = false
        }
    }

    fun isLoggedIn(): Boolean {
        return sessionManager.isLoggedIn()
    }

    fun logout() {
        sessionManager.logout()
        _loginState.value = false
    }
}