package com.example.frontcitasmedicas.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontcitasmedicas.Model.Tratamiento
import com.example.frontcitasmedicas.Repository.TratamientoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TratamientoViewModel : ViewModel() {

    private val repository = TratamientoRepository()

    private val _tratamientos = MutableStateFlow<List<Tratamiento>>(emptyList())
    val tratamientos: StateFlow<List<Tratamiento>> = _tratamientos

    private val _tratamientoActual = MutableStateFlow(Tratamiento())
    val tratamientoActual: StateFlow<Tratamiento> = _tratamientoActual

    fun cargarTratamientos() {
        viewModelScope.launch {
            try {
                _tratamientos.value = repository.getTratamientos()
            } catch (e: Exception) {
                Log.e("TratamientoViewModel", "Error cargando tratamientos", e)
            }
        }
    }

    fun agregarTratamiento(tratamiento: Tratamiento) {
        viewModelScope.launch {
            try {
                repository.addTratamiento(tratamiento)
                cargarTratamientos()
            } catch (e: Exception) {
                Log.e("TratamientoViewModel", "Error agregando tratamiento", e)
            }
        }
    }

    fun eliminarTratamiento(id: Long) {
        viewModelScope.launch {
            try {
                repository.deleteTratamiento(id)
                cargarTratamientos()
            } catch (e: Exception) {
                Log.e("TratamientoViewModel", "Error eliminando tratamiento", e)
            }
        }
    }

    fun actualizarTratamiento() {
        viewModelScope.launch {
            try {
                repository.updateTratamiento(_tratamientoActual.value)
                cargarTratamientos()
            } catch (e: Exception) {
                Log.e("TratamientoViewModel", "Error actualizando tratamiento", e)
            }
        }
    }

    fun obtenerTratamientoPorId(id: Long) {
        viewModelScope.launch {
            try {
                _tratamientoActual.value = repository.getTratamiento(id)
            } catch (e: Exception) {
                Log.e("TratamientoViewModel", "Error obteniendo tratamiento", e)
            }
        }
    }

    fun limpiarFormulario() {
        _tratamientoActual.value = Tratamiento()
    }
}
