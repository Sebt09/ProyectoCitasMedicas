package com.example.frontcitasmedicas.Screen

import android.util.Log // Importa Log para depuración, si lo necesitas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frontcitasmedicas.Model.Pago
import com.example.frontcitasmedicas.ViewModel.PagoViewModel

@Composable
fun PagoFormScreen(
    pagoId: Long? = null,
    onBack: () -> Unit,
    viewModel: PagoViewModel = viewModel()
) {
    val pago by viewModel.pagoActual.collectAsState()
    val isEditing = pagoId != null

    // 1. Observa el nuevo StateFlow de `operacionCompletada`
    val operacionCompletada by viewModel.operacionCompletada.collectAsState()

    LaunchedEffect(pagoId) {
        if (isEditing) {
            viewModel.obtenerPagoPorId(pagoId!!)
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
                Log.d("PagoFormScreen", "Navegando de vuelta después de operación exitosa.")
            }
            false -> {
                // La operación falló, puedes mostrar un Snackbar o un mensaje de error
                Log.e("PagoFormScreen", "La operación de guardar/actualizar falló.")
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
            text = if (isEditing) "Editar Pago" else "Registrar Pago",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = pago.fecha_pago,
            onValueChange = { viewModel.actualizarCampo("fecha_pago", it) },
            label = { Text("Fecha de Pago (AAAA-MM-DD)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = pago.monto.toString(),
            onValueChange = { viewModel.actualizarCampo("monto", it) },
            label = { Text("Monto") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = pago.metodo_pago,
            onValueChange = { viewModel.actualizarCampo("metodo_pago", it) },
            label = { Text("Método de Pago") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = pago.referencia,
            onValueChange = { viewModel.actualizarCampo("referencia", it) },
            label = { Text("Referencia") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(
                onClick = {
                    if (isEditing) {
                        viewModel.actualizarPago()
                    } else {
                        // Asegúrate de que esta función exista en tu PagoViewModel y sea `agregarPago()`
                        // o cambia el nombre aquí a `agregarPago()` si es la función correcta.
                        viewModel.agregarPago() // Asumo que `crearPago` es `agregarPago` en tu ViewModel
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