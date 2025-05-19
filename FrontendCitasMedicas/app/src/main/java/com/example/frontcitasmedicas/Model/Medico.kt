package com.example.frontcitasmedicas.Model

data class Medico(
    val id_medico: Long = 0,
    val nombre: String = "",
    val apellido: String = "",
    val correo: String = "",
    val telefono: String = "",
    val especialidad: String = "",
    val consultas: List<Consulta> = emptyList(),
    val citas: List<Cita> = emptyList()
)
