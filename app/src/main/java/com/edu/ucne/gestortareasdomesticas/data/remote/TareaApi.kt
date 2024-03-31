package com.edu.ucne.gestortareasdomesticas.data.remote

import com.edu.ucne.gestortareasdomesticas.data.remote.dto.EmpleadoDto
import com.edu.ucne.gestortareasdomesticas.data.remote.dto.TareaDto
import retrofit2.Response
import retrofit2.http.*

interface TareaApi {
    //Tareas
    @GET("api/Tareas")
    suspend fun getsTareas(): List<TareaDto>

    @GET("api/Tareas/{id}")
    suspend fun getTareasbyId(@Path("id") id: Int): TareaDto

    @PUT("api/Tareas/{id}")
    suspend fun putTareas(@Path("id") id: Int, @Body tareaDto: TareaDto): Response<Unit>


    //Empleados
    @GET("api/Empleados/{email},{clave}")
    suspend fun getEmpleadosByLogin(
        @Path("email") email: String,
        @Path("clave") clave: String
    ): List<EmpleadoDto>

    @GET("api/Empleados/{id}")
    suspend fun getEmpleadosbyId(@Path("id") id: Int): EmpleadoDto

    @PUT("api/Empleados/{id}")
    suspend fun putEmpleados(@Path("id") id: Int, @Body empleadoDto: EmpleadoDto): Response<Unit>

}