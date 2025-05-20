// Archivo: com/example/frontcitasmedicas/MainActivity.kt
package com.example.frontcitasmedicas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.frontcitasmedicas.Screen.* // Asegúrate de importar DashboardScreen
import com.example.frontcitasmedicas.ui.theme.FrontCitasMedicasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FrontCitasMedicasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Cambia la ruta de inicio a "dashboard"
    NavHost(navController = navController, startDestination = "dashboard") {

        // Nueva ruta para el Dashboard
        composable("dashboard") {
            DashboardScreen(
                onNavigateToPacientes = { navController.navigate("pacientes") },
                onNavigateToMedicos = { navController.navigate("medicos") },
                onNavigateToPagos = { navController.navigate("pagos") },
                onNavigateToTratamientos = { navController.navigate("tratamientos") },
                onNavigateToCitas = { navController.navigate("citas") },
                onNavigateToConsultas = { navController.navigate("consultas") }
            )
        }

        // --- Tus rutas existentes (Pacientes, Medicos, Pagos, Tratamientos, Citas, Consultas) ---
        // Pacientes
        composable("pacientes") {
            PacienteScreen(
                onEditar = { paciente -> navController.navigate("editarPaciente/${paciente.id_paciente}") },
                onRegistrar = { navController.navigate("registrarPaciente") }
            )
        }
        composable("registrarPaciente") {
            PacienteFormScreen(onBack = { navController.popBackStack() })
        }
        composable("editarPaciente/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
            if (id != null) {
                PacienteFormScreen(pacienteId = id, onBack = { navController.popBackStack() })
            } else {
                navController.popBackStack()
            }
        }

        // Medicos
        composable("medicos") {
            MedicoScreen(
                onEditar = { medico -> navController.navigate("editarMedico/${medico.id_medico}") },
                onRegistrar = { navController.navigate("registrarMedico") }
            )
        }
        composable("registrarMedico") {
            MedicoFormScreen(onBack = { navController.popBackStack() })
        }
        composable("editarMedico/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
            if (id != null) {
                MedicoFormScreen(medicoId = id, onBack = { navController.popBackStack() })
            } else {
                navController.popBackStack()
            }
        }

        // Pagos
        composable("pagos") {
            PagoScreen(
                onEditar = { pago -> navController.navigate("editarPago/${pago.id_pago}") },
                onRegistrar = { navController.navigate("registrarPago") }
            )
        }
        composable("registrarPago") {
            PagoFormScreen(onBack = { navController.popBackStack() })
        }
        composable("editarPago/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
            if (id != null) {
                PagoFormScreen(pagoId = id, onBack = { navController.popBackStack() })
            } else {
                navController.popBackStack()
            }
        }

        // Tratamientos
        composable("tratamientos") {
            TratamientoScreen(
                onEditar = { tratamiento -> navController.navigate("editarTratamiento/${tratamiento.id_tratamiento}") },
                onRegistrar = { navController.navigate("registrarTratamiento") }
            )
        }
        composable("registrarTratamiento") {
            TratamientoFormScreen(onBack = { navController.popBackStack() })
        }
        composable("editarTratamiento/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
            if (id != null) {
                TratamientoFormScreen(tratamientoId = id, onBack = { navController.popBackStack() })
            } else {
                navController.popBackStack()
            }
        }

        // Citas
        composable("citas") {
            CitaScreen(
                onEditar = { cita -> navController.navigate("editarCita/${cita.id_cita}") },
                onRegistrar = { navController.navigate("registrarCita") }
            )
        }
        composable("registrarCita") {
            CitaFormScreen(onBack = { navController.popBackStack() })
        }
        composable("editarCita/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
            if (id != null) {
                CitaFormScreen(citaId = id, onBack = { navController.popBackStack() })
            } else {
                navController.popBackStack()
            }
        }

        // Consultas
        composable("consultas") {
            ConsultaScreen(
                onEditar = { consulta -> navController.navigate("editarConsulta/${consulta.id_consulta}") },
                onRegistrar = { navController.navigate("registrarConsulta") }
            )
        }
        composable("registrarConsulta") {
            ConsultaFormScreen(onBack = { navController.popBackStack() })
        }
        composable("editarConsulta/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
            if (id != null) {
                ConsultaFormScreen(consultaId = id, onBack = { navController.popBackStack() })
            } else {
                navController.popBackStack()
            }
        }
    }
}