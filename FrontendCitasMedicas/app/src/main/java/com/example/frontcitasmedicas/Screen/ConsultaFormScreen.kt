package com.example.frontcitasmedicas.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frontcitasmedicas.Model.Consulta
import com.example.frontcitasmedicas.ViewModel.ConsultaViewModel

@Composable
fun ConsultaFormScreen(
    consultaId: Long? = null,
    onBack: () -> Unit,
    viewModel: ConsultaViewModel = viewModel()
) {
    val consulta by viewModel.consultaActual.collectAsState()
    val isEditing = consultaId != null

    LaunchedEffect(consultaId) {
        if (isEditing) {
            viewModel.obtenerConsultaPorId(consultaId!!)
        } else {
            viewModel.limpiarFormulario()
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text(text = if (isEditing) "Editar Consulta" else "Registrar Consulta")

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = consulta.motivo,
            onValueChange = { viewModel.actualizarCampo("motivo", it) },
            label = { Text("Motivo") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = consulta.diagnostico,
            onValueChange = { viewModel.actualizarCampo("diagnostico", it) },
            label = { Text("Diagnóstico") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = {
                if (isEditing) viewModel.actualizarConsulta() else viewModel.crearConsulta()
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
