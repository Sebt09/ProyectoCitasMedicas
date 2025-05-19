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
import com.example.frontcitasmedicas.Model.Tratamiento
import com.example.frontcitasmedicas.ViewModel.TratamientoViewModel
import kotlinx.coroutines.launch

@Composable
fun TratamientoScreen(
    viewModel: TratamientoViewModel = viewModel(),
    onEditar: (Tratamiento) -> Unit = {},
    onRegistrar: () -> Unit = {}
) {
    val tratamientos by viewModel.tratamientos.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(true) {
        viewModel.cargarTratamientos()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Tratamientos") },
                actions = {
                    IconButton(onClick = onRegistrar) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Registrar Tratamiento")
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
            LazyColumn {
                items(tratamientos) { tratamiento ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Tipo: ${tratamiento.tipo}")
                            Text("Descripción: ${tratamiento.descripcion}")
                            Text("Frecuencia: ${tratamiento.frecuencia}")
                            Text("Duración: ${tratamiento.duracion}")

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(onClick = { onEditar(tratamiento) }) {
                                    Text("Editar")
                                }

                                Button(
                                    onClick = {
                                        coroutineScope.launch {
                                            viewModel.eliminarTratamiento(tratamiento.id_tratamiento)
                                            snackbarHostState.showSnackbar("Tratamiento eliminado")
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
