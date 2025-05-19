package com.example.frontcitasmedicas.Model

import java.time.LocalDateTime

data class Cita(
    val id_cita: Long = 0,
    val fecha_hora: String = "", // Se puede manejar como String o LocalDateTime, depende de cómo lo recibas en la API
    val estado: String = "",
    val paciente: Paciente? = null,
    val medico: Medico? = null
)