package com.example.frontcitasmedicas.Service

import com.example.frontcitasmedicas.Model.Paciente
import retrofit2.Response
import retrofit2.http.*

interface PacienteService {

    @GET("/apipaciente/listar")
    suspend fun getAllPacientes(): List<Paciente>

    @GET("/apipaciente/buscar/{id}")
    suspend fun getPacienteById(@Path("id") id: Long): Paciente

    @POST("/apipaciente/guardar")
    suspend fun addPaciente(@Body paciente: Paciente): Response<Paciente>

    @PUT("/apipaciente/actualizar")
    suspend fun updatePaciente(@Body paciente: Paciente): Response<Paciente>

    @DELETE("/apipaciente/eliminar/{id}")
    suspend fun deletePaciente(@Path("id") id: Long): Response<Unit>
}
