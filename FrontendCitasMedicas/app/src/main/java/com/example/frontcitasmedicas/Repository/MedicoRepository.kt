package com.example.frontcitasmedicas.Repository

import com.example.frontcitasmedicas.Model.Medico
import com.example.frontcitasmedicas.Service.RetrofitClient

class MedicoRepository {

    private val api = RetrofitClient.medicoService

    suspend fun getMedicos() = api.getAllMedicos()

    suspend fun getMedico(id: Long) = api.getMedicoById(id)

    suspend fun addMedico(medico: Medico) = api.addMedico(medico)

    suspend fun updateMedico(medico: Medico) = api.updateMedico(medico)

    suspend fun deleteMedico(id: Long) = api.deleteMedico(id)
}
