package com.example.frontcitasmedicas.Model

import java.time.LocalDate

data class Consulta(
    val id_consulta: Long = 0,
    val fecha_consulta: String = "", // O `LocalDate` si decidimos implementar conversión
    val motivo: String = "",
    val diagnostico: String = "",
    val notas: String = "",
    val paciente: Paciente? = null,
    val medico: Medico? = null,
    val tratamientos: List<Tratamiento> = emptyList()
)
