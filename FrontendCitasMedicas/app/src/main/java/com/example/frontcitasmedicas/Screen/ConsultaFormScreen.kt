package com.example.frontcitasmedicas.Screen

import android.util.Log // Importa Log para depuración, si lo necesitas
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

    // 1. Observa el nuevo StateFlow de `operacionCompletada`
    val operacionCompletada by viewModel.operacionCompletada.collectAsState()

    LaunchedEffect(consultaId) {
        if (isEditing) {
            viewModel.obtenerConsultaPorId(consultaId!!)
        } else {
            viewModel.limpiarFormulario()
        }
    }

    // 2. Usar LaunchedEffect para reaccionar a `operacionCompletada`
    LaunchedEffect(operacionCompletada) {
        when (operacionCompletada) {
            true -> {
                // La operación fue exitosa, ahora podemos navegar de vuelta
                onBack()
                // Es crucial resetear el estado para que no se dispare de nuevo
                viewModel.resetOperacionCompletada()
                Log.d("ConsultaFormScreen", "Navegando de vuelta después de operación exitosa.")
            }
            false -> {
                // La operación falló, puedes mostrar un Snackbar o un mensaje de error
                Log.e("ConsultaFormScreen", "La operación de guardar/actualizar falló.")
                // También resetea el estado para permitir futuros intentos
                viewModel.resetOperacionCompletada()
            }
            null -> {
                // Estado inicial o reseteado, no hacemos nada
            }
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text(
            text = if (isEditing) "Editar Consulta" else "Registrar Consulta",
            style = MaterialTheme.typography.titleLarge // Usa un estilo de texto de Material 3
        )

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

        // Asumiendo que también necesitas campos para fecha y notas, si existen en tu modelo Consulta
        OutlinedTextField(
            value = consulta.fecha_consulta,
            onValueChange = { viewModel.actualizarCampo("fecha_consulta", it) },
            label = { Text("Fecha de Consulta (AAAA-MM-DD)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = consulta.notas,
            onValueChange = { viewModel.actualizarCampo("notas", it) },
            label = { Text("Notas") },
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = {
                // 3. Ya no llamamos a onBack() directamente aquí
                if (isEditing) viewModel.actualizarConsulta() else viewModel.agregarConsulta()
            }) {
                Text(text = if (isEditing) "Actualizar" else "Guardar")
            }

            // El botón de cancelar sigue navegando de inmediato
            OutlinedButton(onClick = onBack) {
                Text("Cancelar")
            }
        }
    }
}