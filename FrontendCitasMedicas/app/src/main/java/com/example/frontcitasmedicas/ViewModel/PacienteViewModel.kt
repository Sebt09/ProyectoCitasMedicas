package com.example.frontcitasmedicas.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontcitasmedicas.Model.Paciente
import com.example.frontcitasmedicas.Repository.PacienteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PacienteViewModel : ViewModel() {

    private val repository = PacienteRepository()

    // Lista de todos los pacientes
    private val _pacientes = MutableStateFlow<List<Paciente>>(emptyList())
    val pacientes: StateFlow<List<Paciente>> = _pacientes

    // Paciente actual para el formulario
    private val _pacienteActual = MutableStateFlow(
        Paciente(
            id_paciente = 0,
            nombre = "",
            apellido = "",
            numero_identificacion = "",
            fecha_nacimiento = "",
            correo = "",
            telefono = "",
            direccion = ""
        )
    )
    val pacienteActual: StateFlow<Paciente> = _pacienteActual

    fun cargarPacientes() {
        viewModelScope.launch {
            try {
                val lista = repository.getPacientes()
                Log.d("PacienteViewModel", "Pacientes cargados: ${lista.size}")
                lista.forEach { paciente -> Log.d("PacienteViewModel", "Paciente cargado: ${paciente.nombre}") }
                if (lista.isNotEmpty()) {
                    Log.d("PacienteViewModel", "Primer paciente cargado: ${lista.first()}")
                } else {
                    Log.d("PacienteViewModel", "La lista de pacientes está vacía")
                }
                _pacientes.value = lista
            } catch (e: Exception) {
                Log.e("PacienteViewModel", "Error cargando pacientes", e)
            }
        }
    }


    fun agregarPaciente(paciente: Paciente) {
        viewModelScope.launch {
            try {
                repository.addPaciente(paciente)
                cargarPacientes()
            } catch (e: Exception) {
                Log.e("PacienteViewModel", "Error agregando paciente", e)
            }
        }
    }

    fun eliminarPaciente(id: Long) {
        viewModelScope.launch {
            try {
                repository.deletePaciente(id)
                cargarPacientes()
            } catch (e: Exception) {
                Log.e("PacienteViewModel", "Error eliminando paciente", e)
            }
        }
    }

    fun actualizarPaciente() {
        Log.d("PacienteViewModel", "Función actualizarPaciente() llamada")
        viewModelScope.launch {
            try {
                repository.updatePaciente(_pacienteActual.value)
                cargarPacientes()
            } catch (e: Exception) {
                Log.e("PacienteViewModel", "Error actualizando paciente", e)
            }
            cargarPacientes()
        }
    }


    fun crearPaciente() {
        viewModelScope.launch {
            try {
                repository.addPaciente(_pacienteActual.value)
                cargarPacientes()
            } catch (e: Exception) {
                Log.e("PacienteViewModel", "Error creando paciente", e)
            }
        }
    }

    fun obtenerPacientePorId(id: Long) {
        viewModelScope.launch {
            try {
                val paciente = repository.getPaciente(id)
                _pacienteActual.value = paciente
            } catch (e: Exception) {
                Log.e("PacienteViewModel", "Error obteniendo paciente", e)
            }
        }
    }

    fun limpiarFormulario() {
        _pacienteActual.value = Paciente(
            id_paciente = 0,
            nombre = "",
            apellido = "",
            numero_identificacion = "",
            fecha_nacimiento = "",
            correo = "",
            telefono = "",
            direccion = ""
        )
    }

    fun actualizarCampo(campo: String, valor: String) {
        _pacienteActual.update { paciente ->
            when (campo) {
                "nombre" -> paciente.copy(nombre = valor)
                "apellido" -> paciente.copy(apellido = valor)
                "numero_identificacion" -> paciente.copy(numero_identificacion = valor)
                "fecha_nacimiento" -> paciente.copy(fecha_nacimiento = valor)
                "correo" -> paciente.copy(correo = valor)
                "telefono" -> paciente.copy(telefono = valor)
                "direccion" -> paciente.copy(direccion = valor)
                else -> paciente
            }
        }
    }
}
