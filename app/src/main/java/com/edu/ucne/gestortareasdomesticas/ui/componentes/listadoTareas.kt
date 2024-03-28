package com.edu.ucne.gestortareasdomesticas.ui.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.edu.ucne.gestortareasdomesticas.data.remote.dto.TareaDto
import com.edu.ucne.gestortareasdomesticas.util.Screen
import com.edu.ucne.gestortareasdomesticas.view.Tareas.tareaViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun listadoTareas(navHostController: NavHostController, viewModel: tareaViewModel = hiltViewModel()){

    Scaffold(
        topBar ={
            TopAppBar(title = { Text(text = "Tareas Asignadas") })
        },


        ){it
        val uiState by viewModel.uiState.collectAsState()

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)
        ) {
            TareaListBody(navHostController = navHostController, uiState.Tareas, Onclick = {}, viewModel)
        }

    }
}

@Composable
fun TareaListBody( navHostController: NavHostController, tareaList: List<TareaDto>, Onclick : (TareaDto) -> Unit,
                    viewModel: tareaViewModel = hiltViewModel()) {
    val tareasPendientes = tareaList.filter { it.estado != "Terminada" }

    Column(modifier = Modifier.fillMaxWidth()) {
        LazyColumn {
            items(tareasPendientes) { tarea ->
                TareaRow(navHostController = navHostController, tarea = tarea, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun TareaRow( navHostController: NavHostController,tarea: TareaDto, viewModel: tareaViewModel = hiltViewModel()) {
    val backgroundColor = when (tarea.estado) {
        "En Proceso" -> Color(android.graphics.Color.parseColor("#FFFFE0"))
        "Terminada" -> Color(android.graphics.Color.parseColor("#90EE90"))
        else -> Color(android.graphics.Color.parseColor("#FFC0CB"))
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navHostController.navigate(Screen.actualizarEstado.route + "/${tarea.tareaId}") }
            .background(color = backgroundColor)
    ) {

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)) {

            Column(

            ) {

                Row(

                ) {
                    Text(
                        text = tarea.descripcion,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.weight(3f)
                    )
                    Text(
                        text = tarea.fecha,
                        textAlign = TextAlign.End,
                        modifier = Modifier.weight(2f)
                    )


                }

                Row(modifier = Modifier.fillMaxWidth()){
                    Text(
                        text = tarea.nombre,
                    )

                    Text(
                        text = "",
                        textAlign = TextAlign.End,
                        modifier = Modifier.weight(2f)
                    )

                    Icon(
                        imageVector = when (tarea.estado) {

                            "En Proceso" -> {
                                Icons.Default.Star
                            }
                            "Terminada" -> {
                                Icons.Default.TaskAlt
                            }
                            else -> {
                                Icons.Default.Update

                            }
                        }, contentDescription = tarea.estado,
                    )
                }
            }

        }
        Divider(Modifier.fillMaxWidth())
    }
}