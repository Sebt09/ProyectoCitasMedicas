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
import com.example.frontcitasmedicas.Model.Paciente
import com.example.frontcitasmedicas.Screen.PacienteFormScreen
import com.example.frontcitasmedicas.Screen.PacienteScreen
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

    NavHost(navController = navController, startDestination = "pacientes") {
        // Pantalla principal de pacientes
        composable("pacientes") {
            PacienteScreen(
                onEditar = { paciente ->
                    navController.navigate("editar/${paciente.id_paciente}")
                },
                onRegistrar = {
                    navController.navigate("registrar")
                },
                onBuscar = {
                    // Implementar lógica de búsqueda personalizada si es necesario
                }
            )
        }

        // Formulario para crear nuevo paciente
        composable("registrar") {
            PacienteFormScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // Formulario para editar paciente (con id)
        composable("editar/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
            if (id != null) {
                PacienteFormScreen(
                    pacienteId = id,
                    onBack = { navController.popBackStack() }
                )
            }
        }

        // mas rutas
    }
}
