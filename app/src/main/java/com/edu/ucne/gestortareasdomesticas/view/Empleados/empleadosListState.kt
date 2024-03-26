package com.edu.ucne.gestortareasdomesticas.view.Empleados

import com.edu.ucne.gestortareasdomesticas.data.remote.dto.EmpleadoDto
import com.edu.ucne.gestortareasdomesticas.data.remote.dto.TareaDto

data class EmpleadosListState(
    val isLoading: Boolean = false,
    val Empleados: List<EmpleadoDto> = emptyList(),
    val error: String = ""
)
