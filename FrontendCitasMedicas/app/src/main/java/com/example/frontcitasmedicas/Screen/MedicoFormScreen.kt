package com.example.frontcitasmedicas.Screen

import android.util.Log // Importa Log para depuración, si lo necesitas
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

    // 1. Observa el nuevo StateFlow de `operacionCompletada`
    val operacionCompletada by viewModel.operacionCompletada.collectAsState()

    LaunchedEffect(medicoId) {
        if (isEditing) {
            viewModel.obtenerMedicoPorId(medicoId!!)
        } else {
            viewModel.limpiarFormulario()
        }
    }

    // 2. Usa LaunchedEffect para reaccionar a `operacionCompletada`
    LaunchedEffect(operacionCompletada) {
        when (operacionCompletada) {
            true -> {
                // La operación fue exitosa, ahora podemos navegar de vuelta
                onBack()
                // Es crucial resetear el estado para que no se dispare de nuevo
                viewModel.resetOperacionCompletada()
                Log.d("MedicoFormScreen", "Navegando de vuelta después de operación exitosa.")
            }
            false -> {
                // La operación falló, puedes mostrar un Snackbar o un mensaje de error
                Log.e("MedicoFormScreen", "La operación de guardar/actualizar falló.")
                // También resetea el estado para permitir futuros intentos
                viewModel.resetOperacionCompletada()
            }
            null -> {
                // Estado inicial o reseteado, no hacemos nada
            }
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
                        // Asegúrate de que esta función exista en tu MedicoViewModel y sea `agregarMedico()`
                        // o cambia el nombre aquí a `agregarMedico()` si es la función correcta.
                        viewModel.agregarMedico() // Asumo que `crearMedico` es `agregarMedico` en tu ViewModel
                    }
                    // 3. Ya no llamamos a onBack() directamente aquí
                }
            ) {
                Text(text = if (isEditing) "Actualizar" else "Guardar")
            }

            // El botón de cancelar sigue navegando de inmediato
            OutlinedButton(onClick = onBack) {
                Text("Cancelar")
            }
        }
    }
}