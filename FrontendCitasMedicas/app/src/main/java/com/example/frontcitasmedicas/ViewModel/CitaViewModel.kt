package com.example.frontcitasmedicas.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontcitasmedicas.Model.Cita
import com.example.frontcitasmedicas.Model.Medico
import com.example.frontcitasmedicas.Model.Paciente
import com.example.frontcitasmedicas.Repository.CitaRepository
import com.example.frontcitasmedicas.Repository.MedicoRepository
import com.example.frontcitasmedicas.Repository.PacienteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CitaViewModel : ViewModel() {

    private val citaRepository = CitaRepository()
    private val pacienteRepository = PacienteRepository()
    private val medicoRepository = MedicoRepository()

    private val _citas = MutableStateFlow<List<Cita>>(emptyList())
    val citas: StateFlow<List<Cita>> = _citas

    private val _citaActual = MutableStateFlow(Cita())
    val citaActual: StateFlow<Cita> = _citaActual

    private val _pacientes = MutableStateFlow<List<Paciente>>(emptyList())
    val pacientes: StateFlow<List<Paciente>> = _pacientes

    private val _medicos = MutableStateFlow<List<Medico>>(emptyList())
    val medicos: StateFlow<List<Medico>> = _medicos

    fun cargarCitas() {
        viewModelScope.launch {
            try {
                _citas.value = citaRepository.getCitas()
            } catch (e: Exception) {
                Log.e("CitaViewModel", "Error cargando citas", e)
            }
        }
    }

    fun crearCita() {
        viewModelScope.launch {
            try {
                citaRepository.addCita(_citaActual.value)
                cargarCitas()
            } catch (e: Exception) {
                Log.e("CitaViewModel", "Error creando cita", e)
            }
        }
    }

    fun eliminarCita(id: Long) {
        viewModelScope.launch {
            try {
                citaRepository.deleteCita(id)
                cargarCitas()
            } catch (e: Exception) {
                Log.e("CitaViewModel", "Error eliminando cita", e)
            }
        }
    }

    fun actualizarCita() {
        viewModelScope.launch {
            try {
                citaRepository.updateCita(_citaActual.value)
                cargarCitas()
            } catch (e: Exception) {
                Log.e("CitaViewModel", "Error actualizando cita", e)
            }
        }
    }

    fun obtenerCitaPorId(id: Long) {
        viewModelScope.launch {
            try {
                _citaActual.value = citaRepository.getCita(id)
            } catch (e: Exception) {
                Log.e("CitaViewModel", "Error obteniendo cita", e)
            }
        }
    }

    fun actualizarCampo(campo: String, valor: String) {
        _citaActual.update { cita ->
            when (campo) {
                "fecha_hora" -> cita.copy(fecha_hora = valor)
                "estado" -> cita.copy(estado = valor)
                else -> cita
            }
        }
    }

    fun asignarPaciente(paciente: Paciente) {
        _citaActual.update { it.copy(paciente = paciente) }
    }

    fun asignarMedico(medico: Medico) {
        _citaActual.update { it.copy(medico = medico) }
    }

    fun limpiarFormulario() {
        _citaActual.value = Cita()
    }

    fun cargarPacientes() {
        viewModelScope.launch {
            try {
                _pacientes.value = pacienteRepository.getPacientes()
            } catch (e: Exception) {
                Log.e("CitaViewModel", "Error cargando pacientes", e)
            }
        }
    }

    fun cargarMedicos() {
        viewModelScope.launch {
            try {
                _medicos.value = medicoRepository.getMedicos()
            } catch (e: Exception) {
                Log.e("CitaViewModel", "Error cargando médicos", e)
            }
        }
    }
}
