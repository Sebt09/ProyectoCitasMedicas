package com.example.frontcitasmedicas.Service

import com.example.frontcitasmedicas.Model.Medico
import retrofit2.Response
import retrofit2.http.*

interface MedicoService {

    @GET("/apimedico/listar")
    suspend fun getAllMedicos(): List<Medico>

    @GET("/apimedico/buscar/{id}")
    suspend fun getMedicoById(@Path("id") id: Long): Medico

    @POST("/apimedico/guardar")
    suspend fun addMedico(@Body medico: Medico): Response<Medico>

    @PUT("/apimedico/actualizar")
    suspend fun updateMedico(@Body medico: Medico): Response<Medico>

    @DELETE("/apimedico/eliminar/{id}")
    suspend fun deleteMedico(@Path("id") id: Long): Response<Unit>
}
