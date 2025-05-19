package com.example.frontcitasmedicas.Model

data class Tratamiento(
    val id_tratamiento: Long = 0,
    val tipo: String = "",
    val descripcion: String = "",
    val frecuencia: String = "",
    val duracion: String = "",
    val consulta: Consulta? = null
)
