package com.edu.ucne.gestortareasdomesticas.ui.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TareaRow(navHostController: NavHostController, tarea: TareaDto, viewModel: tareaViewModel = hiltViewModel()) {
    val backgroundColor = when (tarea.estado) {
        "En Proceso" -> Color(android.graphics.Color.parseColor("#FFFFE0"))
        "Terminada" -> Color(android.graphics.Color.parseColor("#90EE90"))
        else -> Color(android.graphics.Color.parseColor("#FFC0CB"))
    }

    var codigoAcceso by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }
    var showCodigoAccesoDialog by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                showCodigoAccesoDialog = true
            }
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

        if (showCodigoAccesoDialog) {
            AlertDialog(
                onDismissRequest = {
                    showCodigoAccesoDialog = false
                },
                title = {
                    Text(text = "Código de Acceso")
                },
                text = {
                    TextField(
                        value = codigoAcceso,
                        onValueChange = { codigoAcceso = it },
                        maxLines = 1,
                        singleLine = true,
                        label = { Text("Ingrese el código") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardActions = KeyboardActions.Default
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (codigoAcceso == tarea.codigoAcceso) {
                                navHostController.navigate(Screen.actualizarEstado.route + "/${tarea.tareaId}")
                            } else {
                                showErrorDialog = true
                            }
                            showCodigoAccesoDialog = false
                        }
                    ) {
                        Text("Aceptar")
                    }
                }
            )
        }
    }
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = {
                showErrorDialog = false
            },
            title = {
                Text(text = "Error")
            },
            text = {
                Text(text = "Código Incorrecto.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showErrorDialog = false
                    }
                ) {
                    Text("Aceptar")
                }
            }
        )
    }
}

