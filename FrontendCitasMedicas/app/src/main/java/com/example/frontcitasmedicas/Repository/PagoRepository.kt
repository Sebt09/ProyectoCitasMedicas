package com.example.frontcitasmedicas.Repository

import com.example.frontcitasmedicas.Model.Pago
import com.example.frontcitasmedicas.Service.RetrofitClient

class PagoRepository {

    private val api = RetrofitClient.pagoService

    suspend fun getPagos() = api.getAllPagos()

    suspend fun getPago(id: Long) = api.getPagoById(id)

    suspend fun addPago(pago: Pago) = api.addPago(pago)

    suspend fun updatePago(pago: Pago) = api.updatePago(pago)

    suspend fun deletePago(id: Long) = api.deletePago(id)
}
