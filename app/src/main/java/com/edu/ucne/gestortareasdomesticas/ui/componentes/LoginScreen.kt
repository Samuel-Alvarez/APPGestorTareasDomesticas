package com.edu.ucne.gestortareasdomesticas.ui.componentes

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.edu.ucne.gestortareasdomesticas.R
import com.edu.ucne.gestortareasdomesticas.util.Screen
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navHostController: NavHostController, viewModel: LoginViewModel = hiltViewModel(), login: (Int) -> Unit) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

            Image(
                painter = painterResource(id = R.drawable.gestor),
                contentDescription = "header",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Bienvenidos/as!",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color(0xFF006FC7),
                style = MaterialTheme.typography.titleLarge,
            )
            val uiState = viewModel.uiState.collectAsState()

            OutLinedTextField(
                value = uiState.value.email,
                onValueChange = { viewModel.setEmail(it) },
                maxLines = 1,
                singleLine = true,
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Email, null, tint = Color(0xFFFCB1AC))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            var passwordVisible by remember { mutableStateOf(false) }

            OutLinedTextField(
                value = uiState.value.clave,
                onValueChange = { viewModel.setClave(it) },
                maxLines = 1,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Lock, null, tint = Color(0xFFFCB1AC))
                },

                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }

                },
                label = { Text("Clave") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                onClick = {
                    viewModel.setLogin()
                    navHostController.navigate(Screen.listadoTareas.route)
                    if ( uiState.value.id == 0) {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "No se pudo loggear"
                            )
                        }
                    } else {
                        login(uiState.value.id)
                    }

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color(0xFFFFFFFF)
                )

            ) {
                Icon(imageVector = Icons.Filled.Key, contentDescription = "LogIn")
                Text(
                    text = "LogIn",
                    fontWeight = FontWeight.Black,
                )
            }
        }
    }

}