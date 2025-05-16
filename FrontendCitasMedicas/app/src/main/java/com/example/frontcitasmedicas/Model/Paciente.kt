package com.example.frontcitasmedicas.Model

data class Paciente(
    val id_paciente: Long = 0,
    val nombre: String = "",
    val apellido: String = "",
    val numero_identificacion: String = "",
    val fecha_nacimiento: String = "",
    val correo: String = "",
    val telefono: String = "",
    val direccion: String = ""
)
