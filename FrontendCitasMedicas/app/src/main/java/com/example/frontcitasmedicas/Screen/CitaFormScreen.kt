package com.example.frontcitasmedicas.Screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frontcitasmedicas.ViewModel.CitaViewModel

@Composable
fun CitaFormScreen(
    citaId: Long? = null,
    onBack: () -> Unit,
    viewModel: CitaViewModel = viewModel()
) {
    val cita by viewModel.citaActual.collectAsState()
    val pacientes by viewModel.pacientes.collectAsState()
    val medicos by viewModel.medicos.collectAsState()
    val isEditing = citaId != null

    var expandedPaciente by remember { mutableStateOf(false) }
    var expandedMedico by remember { mutableStateOf(false) }

    LaunchedEffect(citaId) {
        if (isEditing) {
            viewModel.obtenerCitaPorId(citaId!!)
        } else {
            viewModel.limpiarFormulario()
        }
        viewModel.cargarPacientes()
        viewModel.cargarMedicos()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = if (isEditing) "Editar Cita" else "Registrar Cita")
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = cita.fecha_hora,
            onValueChange = { viewModel.actualizarCampo("fecha_hora", it) },
            label = { Text("Fecha y Hora") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = cita.estado,
            onValueChange = { viewModel.actualizarCampo("estado", it) },
            label = { Text("Estado") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown para paciente
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = cita.paciente?.nombre ?: "Seleccionar paciente",
                onValueChange = {},
                readOnly = true,
                label = { Text("Paciente") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expandedPaciente = true }
            )

            DropdownMenu(
                expanded = expandedPaciente,
                onDismissRequest = { expandedPaciente = false }
            ) {
                pacientes.forEach { paciente ->
                    DropdownMenuItem(
                        text = { Text("${paciente.nombre} ${paciente.apellido}") },
                        onClick = {
                            viewModel.asignarPaciente(paciente)
                            expandedPaciente = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown para médico
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = cita.medico?.nombre ?: "Seleccionar médico",
                onValueChange = {},
                readOnly = true,
                label = { Text("Médico") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expandedMedico = true }
            )

            DropdownMenu(
                expanded = expandedMedico,
                onDismissRequest = { expandedMedico = false }
            ) {
                medicos.forEach { medico ->
                    DropdownMenuItem(
                        text = { Text("${medico.nombre} ${medico.apellido}") },
                        onClick = {
                            viewModel.asignarMedico(medico)
                            expandedMedico = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = {
                if (isEditing) viewModel.actualizarCita() else viewModel.crearCita()
                onBack()
            }) {
                Text(text = if (isEditing) "Actualizar" else "Guardar")
            }

            OutlinedButton(onClick = onBack) {
                Text("Cancelar")
            }
        }
    }
}
