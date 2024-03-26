package com.edu.ucne.gestortareasdomesticas.data.remote.dto

data class TareaDto(
    val tareaId: Int = 0,
    val empleadoId : Int = 0,
    val descripcion: String = "",
    val fecha: String = "",
    val nombre: String = "",
    val estado: String = "",
)
