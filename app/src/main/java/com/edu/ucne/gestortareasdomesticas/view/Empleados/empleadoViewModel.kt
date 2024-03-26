package com.edu.ucne.gestortareasdomesticas.view.Empleados

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.ucne.gestortareasdomesticas.data.remote.dto.EmpleadoDto
import com.edu.ucne.gestortareasdomesticas.data.remote.dto.TareaDto
import com.edu.ucne.gestortareasdomesticas.data.repository.EmpleadoRepository
import com.edu.ucne.gestortareasdomesticas.data.repository.TareasRepository
import com.edu.ucne.gestortareasdomesticas.util.Resource
import com.edu.ucne.gestortareasdomesticas.view.Tareas.TareasListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EmpleadoUiState(
    val isLoading: Boolean = false,
    val empleado: EmpleadoDto? =  null,
    val error: String = ""
)
data class EmpleadoListState(
    val isLoading: Boolean = false,
    val empleado: List<EmpleadoDto> = emptyList(),
    val error: String = ""
)

@HiltViewModel
class empleadoViewModel @Inject constructor(
    private val empleadoRepository: EmpleadoRepository
): ViewModel() {

    var uiState = MutableStateFlow(EmpleadosListState())
        private set

    var uiStateEmpleado = MutableStateFlow(EmpleadoUiState())
        private set

    var empleadoId by mutableStateOf(0)
    var nombre by mutableStateOf("")
    var email by mutableStateOf("")
    var clave by mutableStateOf("")

    private fun Limpiar() {
        nombre = ""
        email = ""
        clave = ""
    }


    fun setEmpleado(id: Int) {
        empleadoId = id
        Limpiar()
        empleadoRepository.getEmpleadosbyId(empleadoId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    uiStateEmpleado.update { it.copy(isLoading = true) }
                }
                is Resource.Success -> {
                    uiStateEmpleado.update {
                        it.copy(empleado = result.data)
                    }
                    nombre = uiStateEmpleado.value.empleado!!.nombre
                    email = uiStateEmpleado.value.empleado!!.email
                    clave = uiStateEmpleado.value.empleado!!.clave
                }
                is Resource.Error -> {
                    uiStateEmpleado.update { it.copy(error = result.message ?: "Error desconocido") }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun modificar() {
        viewModelScope.launch {
            empleadoRepository.putEmpleados(
                empleadoId.toInt(),
                EmpleadoDto(
                    nombre = nombre,
                    empleadoId = empleadoId.toInt(),
                    email = email,
                    clave = clave
                )
            )
        }
    }

}
