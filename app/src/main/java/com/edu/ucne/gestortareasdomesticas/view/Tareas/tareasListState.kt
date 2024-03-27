package com.edu.ucne.gestortareasdomesticas.view.Tareas

import com.edu.ucne.gestortareasdomesticas.data.remote.dto.TareaDto

data class TareasListState(
    val isLoading: Boolean = false,
    val Tareas: List<TareaDto> = emptyList(),
    val error: String = ""
)