package com.example.frontcitasmedicas.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frontcitasmedicas.Model.Tratamiento
import com.example.frontcitasmedicas.ViewModel.TratamientoViewModel

@Composable
fun TratamientoFormScreen(
    tratamientoId: Long? = null,
    onBack: () -> Unit,
    viewModel: TratamientoViewModel = viewModel()
) {
    val tratamiento by viewModel.tratamientoActual.collectAsState()
    val isEditing = tratamientoId != null

    LaunchedEffect(tratamientoId) {
        if (isEditing) {
            viewModel.obtenerTratamientoPorId(tratamientoId!!)
        } else {
            viewModel.limpiarFormulario()
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text(
            text = if (isEditing) "Editar Tratamiento" else "Registrar Tratamiento",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = tratamiento.tipo,
            onValueChange = { viewModel.actualizarCampo("tipo", it) },
            label = { Text("Tipo") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = tratamiento.descripcion,
            onValueChange = { viewModel.actualizarCampo("descripcion", it) },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = tratamiento.frecuencia,
            onValueChange = { viewModel.actualizarCampo("frecuencia", it) },
            label = { Text("Frecuencia") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = tratamiento.duracion,
            onValueChange = { viewModel.actualizarCampo("duracion", it) },
            label = { Text("Duración") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(
                onClick = {
                    if (isEditing) {
                        viewModel.actualizarTratamiento()
                    } else {
                        viewModel.crearTratamiento()
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
