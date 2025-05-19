package com.example.frontcitasmedicas.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frontcitasmedicas.Model.Medico
import com.example.frontcitasmedicas.ViewModel.MedicoViewModel

@Composable
fun MedicoFormScreen(
    medicoId: Long? = null,
    onBack: () -> Unit,
    viewModel: MedicoViewModel = viewModel()
) {
    val medico by viewModel.medicoActual.collectAsState()
    val isEditing = medicoId != null

    LaunchedEffect(medicoId) {
        if (isEditing) {
            viewModel.obtenerMedicoPorId(medicoId!!)
        } else {
            viewModel.limpiarFormulario()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = if (isEditing) "Editar Médico" else "Registrar Médico",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = medico.nombre,
            onValueChange = { viewModel.actualizarCampo("nombre", it) },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = medico.apellido,
            onValueChange = { viewModel.actualizarCampo("apellido", it) },
            label = { Text("Apellido") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = medico.especialidad,
            onValueChange = { viewModel.actualizarCampo("especialidad", it) },
            label = { Text("Especialidad") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = medico.telefono,
            onValueChange = { viewModel.actualizarCampo("telefono", it) },
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = medico.correo,
            onValueChange = { viewModel.actualizarCampo("correo", it) },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(
                onClick = {
                    if (isEditing) {
                        viewModel.actualizarMedico()
                    } else {
                        viewModel.crearMedico()
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
