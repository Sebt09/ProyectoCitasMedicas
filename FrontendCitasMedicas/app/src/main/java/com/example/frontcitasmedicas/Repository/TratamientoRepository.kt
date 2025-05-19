package com.example.frontcitasmedicas.Repository

import com.example.frontcitasmedicas.Model.Tratamiento
import com.example.frontcitasmedicas.Service.RetrofitClient

class TratamientoRepository {

    private val api = RetrofitClient.tratamientoService

    suspend fun getTratamientos() = api.getAllTratamientos()

    suspend fun getTratamiento(id: Long) = api.getTratamientoById(id)

    suspend fun addTratamiento(tratamiento: Tratamiento) = api.addTratamiento(tratamiento)

    suspend fun updateTratamiento(tratamiento: Tratamiento) = api.updateTratamiento(tratamiento)

    suspend fun deleteTratamiento(id: Long) = api.deleteTratamiento(id)
}
