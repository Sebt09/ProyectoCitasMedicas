data class Cita(
    val id_cita: Long,
    val fecha_hora: String,
    val estado: String,
    val paciente: Paciente,
    val medico: Medico
)
