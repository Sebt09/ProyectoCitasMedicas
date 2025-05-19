package com.example.frontcitasmedicas.Model

import java.time.LocalDate

data class Pago(
    val id_pago: Long = 0,
    val fecha_pago: String = "", // O `LocalDate`
    val monto: Int = 0,
    val metodo_pago: String = "",
    val referencia: String = "",
    val paciente: Paciente? = null
)
