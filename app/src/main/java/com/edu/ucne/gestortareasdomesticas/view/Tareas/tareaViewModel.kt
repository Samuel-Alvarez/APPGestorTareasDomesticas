package com.edu.ucne.gestortareasdomesticas.view.Tareas

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.edu.ucne.gestortareasdomesticas.data.remote.dto.TareaDto
import com.edu.ucne.gestortareasdomesticas.data.repository.TareasRepository
import com.edu.ucne.gestortareasdomesticas.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TareaState(
    val isLoading: Boolean = false,
    val tarea: TareaDto ? =  null,
    val error: String = ""
)

@HiltViewModel
class tareaViewModel @Inject constructor(
    private val tareasRepository: TareasRepository
): ViewModel() {

    val tareasEstatus = listOf("Por Hacer", "En Proceso", "Terminada")
    var expanded by mutableStateOf(false)

    var uiState = MutableStateFlow(TareasListState())
        private set

    var uiStateTarea = MutableStateFlow(TareaState())
        private set

    var tareaId by mutableStateOf(0)
    var descripcion by mutableStateOf("")
    var fecha by mutableStateOf("")
    var nombre by mutableStateOf("")
    var estado by mutableStateOf("")
    var empleadoId by mutableStateOf(0)


    private var _state = mutableStateOf(TareasListState())
    val state: State<TareasListState> = _state

    init {
        tareasRepository.getsTareas().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    uiState.value = uiState.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    uiState.value = uiState.value.copy(Tareas = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    uiState.value =
                        uiState.value.copy(error = result.message ?: "Error desconocido")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun setTarea(id: Int) {
        tareaId = id
        tareasRepository.getTareasbyId(tareaId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    uiStateTarea.update { it.copy(isLoading = true) }
                }
                is Resource.Success -> {
                    uiStateTarea.update {
                        it.copy(tarea = result.data)
                    }
                    descripcion = uiStateTarea.value.tarea!!.descripcion
                    fecha = uiStateTarea.value.tarea!!.fecha
                    estado = uiStateTarea.value.tarea!!.estado
                    nombre = uiStateTarea.value.tarea!!.nombre
                    empleadoId = uiStateTarea.value.tarea!!.empleadoId
                }
                is Resource.Error -> {
                    uiState.update { it.copy(error = result.message ?: "Error desconocido") }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun modificar() {
        viewModelScope.launch {
            tareasRepository.putTareas(
                tareaId,
                TareaDto(
                    tareaId = tareaId,
                    empleadoId = empleadoId,
                    descripcion = descripcion,
                    fecha = fecha,
                    nombre = nombre,
                    estado = estado
                )
            )

        }
    }
}