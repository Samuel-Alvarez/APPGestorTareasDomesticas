package com.edu.ucne.gestortareasdomesticas.data.repository

import com.edu.ucne.gestortareasdomesticas.data.remote.TareaApi
import com.edu.ucne.gestortareasdomesticas.data.remote.dto.TareaDto
import com.edu.ucne.gestortareasdomesticas.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TareasRepository @Inject constructor(
    private val api: TareaApi
) {
    fun getsTareas(): Flow<Resource<List<TareaDto>>> = flow {
        try {
            emit(Resource.Loading())
            val tarea = api.getsTareas()
            emit(Resource.Success(tarea))
        }catch (e: HttpException){
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        }catch (e: IOException){
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }

    suspend fun putTareas(id:Int, tareaDto: TareaDto){
        api.putTareas(id, tareaDto)
    }


    fun getTareasbyId(id: Int): Flow<Resource<TareaDto>> = flow {
        try {
            emit(Resource.Loading())
            val tarea = api.getTareasbyId(id)
            emit(Resource.Success(tarea))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }

}