package com.example.frontcitasmedicas.Repository

import com.example.frontcitasmedicas.Model.Consulta
import com.example.frontcitasmedicas.Service.RetrofitClient

class ConsultaRepository {

    private val api = RetrofitClient.consultaService

    suspend fun getConsultas() = api.getAllConsultas()

    suspend fun getConsulta(id: Long) = api.getConsultaById(id)

    suspend fun addConsulta(consulta: Consulta) = api.addConsulta(consulta)

    suspend fun updateConsulta(consulta: Consulta) = api.updateConsulta(consulta)

    suspend fun deleteConsulta(id: Long) = api.deleteConsulta(id)
}
