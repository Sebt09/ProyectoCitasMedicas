package com.example.frontcitasmedicas.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frontcitasmedicas.Model.Cita
import com.example.frontcitasmedicas.ViewModel.CitaViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitaScreen(
    viewModel: CitaViewModel = viewModel(),
    onEditar: (Cita) -> Unit = {},
    onRegistrar: () -> Unit = {}
) {
    val citas by viewModel.citas.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.cargarCitas()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Citas") },
                actions = {
                    IconButton(onClick = onRegistrar) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Registrar Cita")
                    }
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(citas) { cita ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Fecha y Hora: ${cita.fecha_hora}")
                            // Corrección para manejar pacientes y médicos nulos
                            Text("Paciente: ${cita.paciente?.nombre ?: "N/A"} ${cita.paciente?.apellido ?: ""}")
                            Text("Médico: ${cita.medico?.nombre ?: "N/A"} ${cita.medico?.apellido ?: ""}")
                            Text("Estado: ${cita.estado}")

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(onClick = { onEditar(cita) }) {
                                    Text("Editar")
                                }

                                Button(onClick = {
                                    coroutineScope.launch {
                                        viewModel.eliminarCita(cita.id_cita)
                                        snackbarHostState.showSnackbar("Cita eliminada")
                                    }
                                }) {
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