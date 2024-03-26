package com.edu.ucne.gestortareasdomesticas.util

sealed class Screen(val route: String) {
    object listadoTareas: Screen("listadoTareas")
    object actualizarEstado: Screen("actualizarEstado")
    object LoginScreen : Screen("login")

}