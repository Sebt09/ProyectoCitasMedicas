package com.example.frontcitasmedicas.Service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val pacienteService: PacienteService = retrofit.create(PacienteService::class.java)
    val citaService: CitaService = retrofit.create(CitaService::class.java)
    val consultaService: ConsultaService = retrofit.create(ConsultaService::class.java)
    val medicoService: MedicoService = retrofit.create(MedicoService::class.java)
    val pagoService: PagoService = retrofit.create(PagoService::class.java)
    val tratamientoService: TratamientoService = retrofit.create((TratamientoService::class.java))
}
