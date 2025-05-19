package com.example.frontcitasmedicas.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontcitasmedicas.Model.Cita
import com.example.frontcitasmedicas.Repository.CitaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CitaViewModel : ViewModel() {

    private val repository = CitaRepository()

    private val _citas = MutableStateFlow<List<Cita>>(emptyList())
    val citas: StateFlow<List<Cita>> = _citas

    private val _citaActual = MutableStateFlow(Cita())
    val citaActual: StateFlow<Cita> = _citaActual

    fun cargarCitas() {
        viewModelScope.launch {
            try {
                _citas.value = repository.getCitas()
            } catch (e: Exception) {
                Log.e("CitaViewModel", "Error cargando citas", e)
            }
        }
    }

    fun agregarCita(cita: Cita) {
        viewModelScope.launch {
            try {
                repository.addCita(cita)
                cargarCitas()
            } catch (e: Exception) {
                Log.e("CitaViewModel", "Error agregando cita", e)
            }
        }
    }

    fun eliminarCita(id: Long) {
        viewModelScope.launch {
            try {
                repository.deleteCita(id)
                cargarCitas()
            } catch (e: Exception) {
                Log.e("CitaViewModel", "Error eliminando cita", e)
            }
        }
    }

    fun actualizarCita() {
        viewModelScope.launch {
            try {
                repository.updateCita(_citaActual.value)
                cargarCitas()
            } catch (e: Exception) {
                Log.e("CitaViewModel", "Error actualizando cita", e)
            }
        }
    }

    fun obtenerCitaPorId(id: Long) {
        viewModelScope.launch {
            try {
                _citaActual.value = repository.getCita(id)
            } catch (e: Exception) {
                Log.e("CitaViewModel", "Error obteniendo cita", e)
            }
        }
    }

    fun limpiarFormulario() {
        _citaActual.value = Cita()
    }
}
