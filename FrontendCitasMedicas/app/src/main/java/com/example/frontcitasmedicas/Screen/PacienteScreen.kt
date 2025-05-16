package com.example.frontcitasmedicas.Screen

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frontcitasmedicas.ViewModel.PacienteViewModel
import com.example.frontcitasmedicas.Model.Paciente
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PacienteScreen(
    viewModel: PacienteViewModel = viewModel(),
    onEditar: (Paciente) -> Unit = {}, // Navegación a formulario de edición
    onRegistrar: () -> Unit = {}, // Navegación a formulario de registro
    onBuscar: () -> Unit = {} // Acción para buscar pacientes
) {
    val pacientes by viewModel.pacientes.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(true) {
        viewModel.cargarPacientes()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Pacientes") },
                actions = {
                    // Aquí van los botones de Buscar y Registrar
                    IconButton(onClick = onBuscar) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
                    }
                    IconButton(onClick = onRegistrar) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Registrar")
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
            // Aquí colocamos los botones de Buscar y Registrar en la parte superior
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onBuscar,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Buscar Pacientes")
                }
                Button(
                    onClick = onRegistrar,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Registrar Paciente")
                }
            }

            Spacer(modifier = Modifier.height(16.dp)) // Espacio entre los botones y la lista

            // Lista de pacientes
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(pacientes) { paciente ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Nombre: ${paciente.nombre} ${paciente.apellido}", style = MaterialTheme.typography.titleMedium)
                            Text("Numero de Identificacion: ${paciente.numero_identificacion}")
                            Text("Correo: ${paciente.correo}")
                            Text("Teléfono: ${paciente.telefono}")
                            Text("Dirección: ${paciente.direccion}")
                            Text("Fecha de nacimiento: ${paciente.fecha_nacimiento}")
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(
                                    onClick = { onEditar(paciente) },
                                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                                ) {
                                    Text("Editar")
                                }

                                Button(
                                    onClick = {
                                        coroutineScope.launch {
                                            viewModel.eliminarPaciente(paciente.id_paciente)
                                            snackbarHostState.showSnackbar("Paciente eliminado")
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
