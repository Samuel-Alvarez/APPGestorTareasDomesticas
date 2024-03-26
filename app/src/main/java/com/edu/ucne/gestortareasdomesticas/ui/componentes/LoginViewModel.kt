package com.edu.ucne.gestortareasdomesticas.ui.componentes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.ucne.gestortareasdomesticas.data.repository.EmpleadoRepository
import com.edu.ucne.gestortareasdomesticas.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject


data class Login(
    val email: String = "",
    val clave: String ="",
    val id: Int = 0
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepos: EmpleadoRepository

): ViewModel(){

    var uiState = MutableStateFlow(Login())

    fun setLogin() {
        loginRepos.getEmpleadoByLogin(email = uiState.value.email, clave = uiState.value.clave) .onEach { resul ->
            when (resul) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    if (resul.data != null) {
                        uiState.update {
                            it.copy(
                                id = resul.data.first().empleadoId
                            )
                        }
                    }
                }
                is Resource.Error -> {
                }
            }
        }.launchIn(viewModelScope)
        if (uiState.value.id == 0){
            uiState.update {
                it.copy(clave = "")
            }
        }

    }

    fun setClave(clave: String) {
        uiState.update {
            it.copy(clave = clave)
        }
    }
    fun setEmail(email: String) {
        uiState.update {
            it.copy(email = email)
        }
    }
}