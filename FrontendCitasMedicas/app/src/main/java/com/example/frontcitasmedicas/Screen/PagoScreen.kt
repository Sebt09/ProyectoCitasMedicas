package com.example.frontcitasmedicas.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frontcitasmedicas.Model.Pago
import com.example.frontcitasmedicas.ViewModel.PagoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class) // <--- Agrega esta línea aquí para TopAppBar
@Composable
fun PagoScreen(
    viewModel: PagoViewModel = viewModel(),
    onEditar: (Pago) -> Unit = {},
    onRegistrar: () -> Unit = {}
) {
    val pagos by viewModel.pagos.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(true) {
        viewModel.cargarPagos()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Pagos") },
                actions = {
                    IconButton(onClick = onRegistrar) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Registrar Pago")
                    }
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            // Manejo de paciente nulo:
            // Tu modelo `Pago` tiene `paciente: Paciente? = null`.
            // Si en algún momento decides mostrar el nombre del paciente aquí,
            // asegúrate de usar el operador de navegación segura `?.` y Elvis `?:`
            // Ejemplo: Text("Paciente: ${pago.paciente?.nombre ?: "N/A"}")

            LazyColumn {
                items(pagos) { pago ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Fecha: ${pago.fecha_pago}")
                            Text("Monto: ${pago.monto}")
                            Text("Método: ${pago.metodo_pago}")
                            Text("Referencia: ${pago.referencia}")
                            // Ejemplo de cómo mostrar el paciente si no es nulo
                            Text("Paciente: ${pago.paciente?.nombre ?: "N/A"} ${pago.paciente?.apellido ?: ""}")

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(onClick = { onEditar(pago) }) {
                                    Text("Editar")
                                }

                                Button(
                                    onClick = {
                                        coroutineScope.launch {
                                            viewModel.eliminarPago(pago.id_pago)
                                            snackbarHostState.showSnackbar("Pago eliminado")
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
                                ) {
                                    Text("Eliminar")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}