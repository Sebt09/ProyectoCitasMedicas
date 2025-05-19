package com.example.frontcitasmedicas.Service

import com.example.frontcitasmedicas.Model.Cita
import retrofit2.Response
import retrofit2.http.*

interface CitaService {

    @GET("/apicita/listar")
    suspend fun getAllCitas(): List<Cita>

    @GET("/apicita/buscar/{id}")
    suspend fun getCitaById(@Path("id") id: Long): Cita

    @POST("/apicita/guardar")
    suspend fun addCita(@Body cita: Cita): Response<Cita>

    @PUT("/apicita/actualizar")
    suspend fun updateCita(@Body cita: Cita): Response<Cita>

    @DELETE("/apicita/eliminar/{id}")
    suspend fun deleteCita(@Path("id") id: Long): Response<Unit>
}
