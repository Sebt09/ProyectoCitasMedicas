package com.example.frontcitasmedicas.Repository


import com.example.frontcitasmedicas.Model.Paciente
import com.example.frontcitasmedicas.Service.RetrofitClient

class PacienteRepository {

    private val api = RetrofitClient.pacienteService

    suspend fun getPacientes() = api.getAllPacientes()

    suspend fun getPaciente(id: Long) = api.getPacienteById(id)

    suspend fun addPaciente(paciente: Paciente) = api.addPaciente(paciente)

    suspend fun updatePaciente(paciente: Paciente) = api.updatePaciente(paciente)

    suspend fun deletePaciente(id: Long) = api.deletePaciente(id)
}
