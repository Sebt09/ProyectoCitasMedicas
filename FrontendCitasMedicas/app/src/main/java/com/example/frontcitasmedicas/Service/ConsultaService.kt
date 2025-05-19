package com.example.frontcitasmedicas.Service

import com.example.frontcitasmedicas.Model.Consulta
import retrofit2.Response
import retrofit2.http.*

interface ConsultaService {

    @GET("/apiconsulta/listar")
    suspend fun getAllConsultas(): List<Consulta>

    @GET("/apiconsulta/buscar/{id}")
    suspend fun getConsultaById(@Path("id") id: Long): Consulta

    @POST("/apiconsulta/guardar")
    suspend fun addConsulta(@Body consulta: Consulta): Response<Consulta>

    @PUT("/apiconsulta/actualizar")
    suspend fun updateConsulta(@Body consulta: Consulta): Response<Consulta>

    @DELETE("/apiconsulta/eliminar/{id}")
    suspend fun deleteConsulta(@Path("id") id: Long): Response<Unit>
}
