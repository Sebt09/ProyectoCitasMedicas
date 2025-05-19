package com.example.frontcitasmedicas.Service

import com.example.frontcitasmedicas.Model.Tratamiento
import retrofit2.Response
import retrofit2.http.*

interface TratamientoService {

    @GET("/apitratamiento/listar")
    suspend fun getAllTratamientos(): List<Tratamiento>

    @GET("/apitratamiento/buscar/{id}")
    suspend fun getTratamientoById(@Path("id") id: Long): Tratamiento

    @POST("/apitratamiento/guardar")
    suspend fun addTratamiento(@Body tratamiento: Tratamiento): Response<Tratamiento>

    @PUT("/apitratamiento/actualizar")
    suspend fun updateTratamiento(@Body tratamiento: Tratamiento): Response<Tratamiento>

    @DELETE("/apitratamiento/eliminar/{id}")
    suspend fun deleteTratamiento(@Path("id") id: Long): Response<Unit>
}
