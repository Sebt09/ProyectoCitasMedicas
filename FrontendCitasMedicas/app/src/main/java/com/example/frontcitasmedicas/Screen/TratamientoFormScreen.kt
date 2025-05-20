package com.example.frontcitasmedicas.Screen

import android.util.Log // Importa Log para depuración, si lo necesitas
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

    // 1. Observa el nuevo StateFlow de `operacionCompletada`
    val operacionCompletada by viewModel.operacionCompletada.collectAsState()

    LaunchedEffect(tratamientoId) {
        if (isEditing) {
            viewModel.obtenerTratamientoPorId(tratamientoId!!)
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
                Log.d("TratamientoFormScreen", "Navegando de vuelta después de operación exitosa.")
            }
            false -> {
                // La operación falló, puedes mostrar un Snackbar o un mensaje de error
                Log.e("TratamientoFormScreen", "La operación de guardar/actualizar falló.")
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
                        // Asegúrate de que esta función exista en tu TratamientoViewModel y sea `agregarTratamiento()`
                        // o cambia el nombre aquí a `agregarTratamiento()` si es la función correcta.
                        viewModel.agregarTratamiento() // Asumo que `crearTratamiento` es `agregarTratamiento` en tu ViewModel
                    }
                    // 3. Ya no llamamos a onBack() directamente aquí
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