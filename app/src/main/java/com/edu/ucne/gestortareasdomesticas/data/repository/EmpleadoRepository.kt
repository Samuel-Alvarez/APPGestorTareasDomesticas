package com.edu.ucne.gestortareasdomesticas.data.repository

import com.edu.ucne.gestortareasdomesticas.data.remote.TareaApi
import com.edu.ucne.gestortareasdomesticas.data.remote.dto.EmpleadoDto
import com.edu.ucne.gestortareasdomesticas.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class EmpleadoRepository @Inject constructor(
    private val api: TareaApi
) {
    interface Empleado1Repository {
        suspend fun putEmpleados(id: Int, empleadoDto: EmpleadoDto)
        fun getEmpleadosById(id: Int): Flow<Resource<EmpleadoDto>>
        fun getEmpleadosByLogin(email: String, clave:String ): Flow<Resource<EmpleadoDto>>

    }

   suspend fun putEmpleados(id:Int, empleadoDto: EmpleadoDto){
        api.putEmpleados(id, empleadoDto)
    }

    fun getEmpleadosbyId(id: Int): Flow<Resource<EmpleadoDto>> = flow {
        try {
            emit(Resource.Loading())
            val empleados = api.getEmpleadosbyId(id)
            emit(Resource.Success(empleados))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }
     fun getEmpleadoByLogin(email: String, clave: String): Flow<Resource<List<EmpleadoDto>>> = flow{
        try {
            emit(Resource.Loading())

            val persona = api.getEmpleadosByLogin(email, clave)

            emit (Resource.Success(persona))
        } catch (e: HttpException) {

            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            emit(Resource.Error(e.message ?: "Verificar tu conexion a internet"))
        }
    }

}