package com.example.frontcitasmedicas.Service

import com.example.frontcitasmedicas.Model.Pago
import retrofit2.Response
import retrofit2.http.*

interface PagoService {

    @GET("/apipago/listar")
    suspend fun getAllPagos(): List<Pago>

    @GET("/apipago/buscar/{id}")
    suspend fun getPagoById(@Path("id") id: Long): Pago

    @POST("/apipago/guardar")
    suspend fun addPago(@Body pago: Pago): Response<Pago>

    @PUT("/apipago/actualizar")
    suspend fun updatePago(@Body pago: Pago): Response<Pago>

    @DELETE("/apipago/eliminar/{id}")
    suspend fun deletePago(@Path("id") id: Long): Response<Unit>
}
