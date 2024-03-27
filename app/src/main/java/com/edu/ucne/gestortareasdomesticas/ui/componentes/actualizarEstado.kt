package com.edu.ucne.gestortareasdomesticas.ui.componentes

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.edu.ucne.gestortareasdomesticas.data.remote.dto.EmpleadoDto
import com.edu.ucne.gestortareasdomesticas.data.remote.dto.TareaDto
import com.edu.ucne.gestortareasdomesticas.util.Screen
import com.edu.ucne.gestortareasdomesticas.view.Tareas.tareaViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun actualizarEstado(navHostController: NavHostController, Id: Int, viewModel: tareaViewModel = hiltViewModel())
{
    remember {

        viewModel.setTarea(Id)
        0
    }

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val contexto = LocalContext.current


    val date = DatePickerDialog(
        contexto, { d, year, month, day ->
            val month = month + 1
            viewModel.fecha = "$year-$month-$day"
        }, year, month, day
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Actualizar estado") })
        },

        ) {
        it

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)

        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, vertical = 60.dp),
            ) {
                OutlinedTextField(
                    value = viewModel.descripcion,
                    onValueChange = {},
                    label = { Text(text = "Descripcion") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = null
                        )
                    }
                )
                OutlinedTextField(
                    value = viewModel.fecha,
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Fecha") },
                    readOnly = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = "",
                        )
                    },

                    trailingIcon = {
                        IconButton(
                            onClick = { date.show() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = "",
                            )
                        }
                    }

                )

                OutlinedTextField(
                    value = viewModel.nombre,
                    onValueChange = {},
                    label = { Text(text = "Encargado") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Subject,
                            contentDescription = null
                        )
                    }
                )
                OutlinedTextField(

                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.expanded = true },
                    value = viewModel.estado,
                    enabled = false, readOnly = true,
                    label = { Text(text = "Estado") },
                    onValueChange = { viewModel.estado = it },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.QueryStats,
                            contentDescription = null
                        )
                    }
                )

                DropdownMenu(
                    expanded = viewModel.expanded,
                    onDismissRequest = { viewModel.expanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)

                ) {
                    viewModel.tareasEstatus.forEach { opcion ->
                        DropdownMenuItem(
                            text = {
                                Text(text = opcion, textAlign = TextAlign.Center)
                            },
                            onClick = {
                                viewModel.expanded = false
                                viewModel.estado = opcion
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        )
                    }
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    onClick = {
                        viewModel.modificar()
                        navHostController.navigate(Screen.listadoTareas.route)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray,
                    contentColor = Color(0xFFFFFFFF)
                )
                ) {
                    Icon(imageVector = Icons.Filled.Save, contentDescription = "Update")
                    Text(
                        text = "Actualizar",
                        fontWeight = FontWeight.Black,
                    )
                }
            }
        }
    }
}
