package com.example.frontcitasmedicas.Repository

import com.example.frontcitasmedicas.Model.Cita
import com.example.frontcitasmedicas.Service.RetrofitClient

class CitaRepository {

    private val api = RetrofitClient.citaService

    suspend fun getCitas() = api.getAllCitas()

    suspend fun getCita(id: Long) = api.getCitaById(id)

    suspend fun addCita(cita: Cita) = api.addCita(cita)

    suspend fun updateCita(cita: Cita) = api.updateCita(cita)

    suspend fun deleteCita(id: Long) = api.deleteCita(id)
}
