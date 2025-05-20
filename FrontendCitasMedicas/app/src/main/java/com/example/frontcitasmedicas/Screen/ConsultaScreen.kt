package com.example.frontcitasmedicas.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.* // Asegúrate de que este import sea para Material 3
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frontcitasmedicas.Model.Consulta
import com.example.frontcitasmedicas.ViewModel.ConsultaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class) // <--- ¡Agrega esta línea aquí!
@Composable
fun ConsultaScreen(
    viewModel: ConsultaViewModel = viewModel(),
    onEditar: (Consulta) -> Unit = {},
    onRegistrar: () -> Unit = {}
) {
    val consultas by viewModel.consultas.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(true) {
        viewModel.cargarConsultas()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Consultas") }) // Esta es la API experimental
        },
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            LazyColumn {
                items(consultas) { consulta ->
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Fecha: ${consulta.fecha_consulta}")
                            Text("Motivo: ${consulta.motivo}")
                            Text("Diagnóstico: ${consulta.diagnostico}")

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(onClick = { onEditar(consulta) }) {
                                    Text("Editar")
                                }

                                Button(onClick = {
                                    coroutineScope.launch {
                                        viewModel.eliminarConsulta(consulta.id_consulta)
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