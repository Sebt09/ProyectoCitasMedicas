package com.example.frontcitasmedicas.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api // Necesario para TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToPacientes: () -> Unit,
    onNavigateToMedicos: () -> Unit,
    onNavigateToPagos: () -> Unit,
    onNavigateToTratamientos: () -> Unit,
    onNavigateToCitas: () -> Unit,
    onNavigateToConsultas: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Menú Principal") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Bienvenido a Citas Médicas",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onNavigateToPacientes,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("Gestionar Pacientes")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onNavigateToMedicos,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("Gestionar Médicos")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onNavigateToCitas,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("Gestionar Citas")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onNavigateToConsultas,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("Gestionar Consultas")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onNavigateToPagos,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("Gestionar Pagos")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onNavigateToTratamientos,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("Gestionar Tratamientos")
            }
        }
    }
}