package com.edu.ucne.gestortareasdomesticas

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.edu.ucne.gestortareasdomesticas.ui.componentes.LoginScreen
import com.edu.ucne.gestortareasdomesticas.ui.componentes.actualizarEstado
import com.edu.ucne.gestortareasdomesticas.ui.componentes.listadoTareas
import com.edu.ucne.gestortareasdomesticas.ui.theme.GestorTareasDomesticasTheme
import com.edu.ucne.gestortareasdomesticas.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GestorTareasDomesticasTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    myApp()

                }
            }
        }
    }
}

@Composable
fun myApp(){

    val navHostController = rememberNavController()
    var idUsuarioLogin by remember {
        mutableStateOf(0)
    }
    NavHost(navController = navHostController, startDestination = Screen.LoginScreen.route) {

        composable(Screen.LoginScreen.route){
            LoginScreen(login = {
                idUsuarioLogin = it
                navHostController.navigate(Screen.listadoTareas.route)
            },
                navHostController = navHostController
            )
        }
       composable(Screen.listadoTareas.route) {
            listadoTareas(navHostController = navHostController)
        }
        composable(Screen.actualizarEstado.route + "/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            })
        ){
            Log.d("Args", it.arguments?.getInt("id").toString())
            actualizarEstado(navHostController = navHostController, Id = it.arguments?.getInt("id")?: 0)
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    GestorTareasDomesticasTheme {
        myApp()
    }
}