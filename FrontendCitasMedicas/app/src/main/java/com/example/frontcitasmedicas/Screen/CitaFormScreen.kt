package com.example.frontcitasmedicas.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frontcitasmedicas.Model.Cita
import com.example.frontcitasmedicas.ViewModel.CitaViewModel

@Composable
fun CitaFormScreen(
    citaId: Long? = null,
    onBack: () -> Unit,
    viewModel: CitaViewModel = viewModel()
) {
    val cita by viewModel.citaActual.collectAsState()
    val isEditing = citaId != null

    LaunchedEffect(citaId) {
        if (isEditing) {
            viewModel.obtenerCitaPorId(citaId!!)
        } else {
            viewModel.limpiarFormulario()
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

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
