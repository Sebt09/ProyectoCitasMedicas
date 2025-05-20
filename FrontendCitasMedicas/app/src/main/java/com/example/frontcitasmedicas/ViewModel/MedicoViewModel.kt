package com.example.frontcitasmedicas.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontcitasmedicas.Model.Medico
import com.example.frontcitasmedicas.Repository.MedicoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MedicoViewModel : ViewModel() {

    private val repository = MedicoRepository()

    // Lista de todos los médicos
    private val _medicos = MutableStateFlow<List<Medico>>(emptyList())
    val medicos: StateFlow<List<Medico>> = _medicos

    // Médico actual para el formulario
    private val _medicoActual = MutableStateFlow(Medico())
    val medicoActual: StateFlow<Medico> = _medicoActual

    // Nuevo StateFlow para indicar si una operación (guardar/actualizar) se completó exitosamente
    private val _operacionCompletada = MutableStateFlow<Boolean?>(null)
    val operacionCompletada: StateFlow<Boolean?> = _operacionCompletada

    fun cargarMedicos() {
        viewModelScope.launch {
            try {
                val lista = repository.getMedicos()
                Log.d("MedicoViewModel", "Médicos cargados: ${lista.size}")
                lista.forEach { medico -> Log.d("MedicoViewModel", "Médico cargado: ${medico.nombre} - ID: ${medico.id_medico}") }
                _medicos.value = lista
            } catch (e: Exception) {
                Log.e("MedicoViewModel", "Error cargando médicos", e)
            }
        }
    }

    // Modificamos agregarMedico para emitir el estado de completado
    fun agregarMedico() { // Asume que agrega _medicoActual.value
        viewModelScope.launch {
            try {
                // Si `addMedico` en el repositorio requiere un parámetro, usa _medicoActual.value
                repository.addMedico(_medicoActual.value)
                cargarMedicos() // Recarga la lista para actualizar la UI
                _operacionCompletada.value = true // Indica que la operación fue exitosa
            } catch (e: Exception) {
                Log.e("MedicoViewModel", "Error agregando médico", e)
                _operacionCompletada.value = false // Indica que hubo un error
            }
        }
    }

    fun eliminarMedico(id: Long) {
        viewModelScope.launch {
            try {
                repository.deleteMedico(id)
                cargarMedicos()
            } catch (e: Exception) {
                Log.e("MedicoViewModel", "Error eliminando médico", e)
            }
        }
    }

    // Modificamos actualizarMedico para emitir el estado de completado
    fun actualizarMedico() {
        viewModelScope.launch {
            try {
                repository.updateMedico(_medicoActual.value)
                cargarMedicos() // Recarga la lista para actualizar la UI
                _operacionCompletada.value = true // Indica que la operación fue exitosa
            } catch (e: Exception) {
                Log.e("MedicoViewModel", "Error actualizando médico", e)
                _operacionCompletada.value = false // Indica que hubo un error
            }
        }
    }

    fun obtenerMedicoPorId(id: Long) {
        viewModelScope.launch {
            try {
                val medico = repository.getMedico(id)
                _medicoActual.value = medico
            } catch (e: Exception) {
                Log.e("MedicoViewModel", "Error obteniendo médico", e)
            }
        }
    }

    fun limpiarFormulario() {
        _medicoActual.value = Medico()
    }

    // Nuevo método para resetear el estado de operación completada
    fun resetOperacionCompletada() {
        _operacionCompletada.value = null
    }

    // Nuevo método para actualizar campos específicos del médico actual
    fun actualizarCampo(campo: String, valor: String) {
        _medicoActual.value = when (campo) {
            "nombre" -> _medicoActual.value.copy(nombre = valor)
            "apellido" -> _medicoActual.value.copy(apellido = valor)
            "correo" -> _medicoActual.value.copy(correo = valor)
            "telefono" -> _medicoActual.value.copy(telefono = valor)
            "especialidad" -> _medicoActual.value.copy(especialidad = valor)
            else -> _medicoActual.value
        }
    }
}