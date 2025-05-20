package com.example.frontcitasmedicas.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.* // Asegúrate de que este import sea para Material 3
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frontcitasmedicas.Model.Medico
import com.example.frontcitasmedicas.ViewModel.MedicoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class) // <--- ¡Agrega esta línea aquí!
@Composable
fun MedicoScreen(
    viewModel: MedicoViewModel = viewModel(),
    onEditar: (Medico) -> Unit = {},
    onRegistrar: () -> Unit = {}
) {
    val medicos by viewModel.medicos.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(true) {
        viewModel.cargarMedicos()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar( // Esta es la API experimental que requiere @OptIn
                title = { Text("Médicos") },
                actions = {
                    IconButton(onClick = onRegistrar) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Registrar Médico")
                    }
                }
            )
        },
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(medicos) { medico ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Nombre: ${medico.nombre} ${medico.apellido}")
                            Text("Especialidad: ${medico.especialidad}")
                            Text("Teléfono: ${medico.telefono}")
                            Text("Correo: ${medico.correo}")

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(onClick = { onEditar(medico) }) {
                                    Text("Editar")
                                }

                                Button(
                                    onClick = {
                                        coroutineScope.launch {
                                            viewModel.eliminarMedico(medico.id_medico)
                                            snackbarHostState.showSnackbar("Médico eliminado")
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