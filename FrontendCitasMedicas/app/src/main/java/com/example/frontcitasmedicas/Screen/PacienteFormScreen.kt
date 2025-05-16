package com.example.frontcitasmedicas.Screen


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frontcitasmedicas.ViewModel.PacienteViewModel
import androidx.compose.runtime.livedata.observeAsState
import com.example.frontcitasmedicas.Model.Paciente

@Composable
fun PacienteFormScreen(
    pacienteId: Long? = null,
    onBack: () -> Unit,
    viewModel: PacienteViewModel = viewModel()
) {
    // Usamos collectAsState para observar el StateFlow de pacienteActual
    val paciente by viewModel.pacienteActual.collectAsState()

    val isEditing = pacienteId != null

    // Cargar datos si es edición
    LaunchedEffect(pacienteId) {
        if (isEditing) {
            viewModel.obtenerPacientePorId(pacienteId!!)
        } else {
            viewModel.limpiarFormulario()
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text(
            text = if (isEditing) "Editar Paciente" else "Registrar Paciente",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = paciente.nombre,
            onValueChange = { viewModel.actualizarCampo("nombre", it) },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = paciente.apellido,
            onValueChange = { viewModel.actualizarCampo("apellido", it) },
            label = { Text("Apellido") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = paciente.numero_identificacion,
            onValueChange = { viewModel.actualizarCampo("numero_identificacion", it) },
            label = { Text("Número de Identificación") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = paciente.fecha_nacimiento,
            onValueChange = { viewModel.actualizarCampo("fecha_nacimiento", it) },
            label = { Text("Fecha de Nacimiento (AAAA-MM-DD)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = paciente.correo,
            onValueChange = { viewModel.actualizarCampo("correo", it) },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = paciente.telefono,
            onValueChange = { viewModel.actualizarCampo("telefono", it) },
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = paciente.direccion,
            onValueChange = { viewModel.actualizarCampo("direccion", it) },
            label = { Text("Dirección") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    if (isEditing) {
                        viewModel.actualizarPaciente()
                    } else {
                        viewModel.crearPaciente()
                    }
                    onBack()
                }
            ) {
                Text(text = if (isEditing) "Actualizar" else "Guardar")
            }

            OutlinedButton(onClick = onBack) {
                Text("Cancelar")
            }
        }
    }
}
