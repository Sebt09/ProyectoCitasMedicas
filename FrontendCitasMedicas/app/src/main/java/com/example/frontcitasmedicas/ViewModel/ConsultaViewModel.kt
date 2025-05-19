package com.example.frontcitasmedicas.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontcitasmedicas.Model.Consulta
import com.example.frontcitasmedicas.Repository.ConsultaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ConsultaViewModel : ViewModel() {

    private val repository = ConsultaRepository()

    private val _consultas = MutableStateFlow<List<Consulta>>(emptyList())
    val consultas: StateFlow<List<Consulta>> = _consultas

    private val _consultaActual = MutableStateFlow(Consulta())
    val consultaActual: StateFlow<Consulta> = _consultaActual

    fun cargarConsultas() {
        viewModelScope.launch {
            try {
                _consultas.value = repository.getConsultas()
            } catch (e: Exception) {
                Log.e("ConsultaViewModel", "Error cargando consultas", e)
            }
        }
    }

    fun agregarConsulta(consulta: Consulta) {
        viewModelScope.launch {
            try {
                repository.addConsulta(consulta)
                cargarConsultas()
            } catch (e: Exception) {
                Log.e("ConsultaViewModel", "Error agregando consulta", e)
            }
        }
    }

    fun eliminarConsulta(id: Long) {
        viewModelScope.launch {
            try {
                repository.deleteConsulta(id)
                cargarConsultas()
            } catch (e: Exception) {
                Log.e("ConsultaViewModel", "Error eliminando consulta", e)
            }
        }
    }

    fun actualizarConsulta() {
        viewModelScope.launch {
            try {
                repository.updateConsulta(_consultaActual.value)
                cargarConsultas()
            } catch (e: Exception) {
                Log.e("ConsultaViewModel", "Error actualizando consulta", e)
            }
        }
    }

    fun obtenerConsultaPorId(id: Long) {
        viewModelScope.launch {
            try {
                _consultaActual.value = repository.getConsulta(id)
            } catch (e: Exception) {
                Log.e("ConsultaViewModel", "Error obteniendo consulta", e)
            }
        }
    }

    fun limpiarFormulario() {
        _consultaActual.value = Consulta()
    }
}
