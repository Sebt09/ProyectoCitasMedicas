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

    private val _medicos = MutableStateFlow<List<Medico>>(emptyList())
    val medicos: StateFlow<List<Medico>> = _medicos

    private val _medicoActual = MutableStateFlow(Medico())
    val medicoActual: StateFlow<Medico> = _medicoActual

    fun cargarMedicos() {
        viewModelScope.launch {
            try {
                _medicos.value = repository.getMedicos()
            } catch (e: Exception) {
                Log.e("MedicoViewModel", "Error cargando medicos", e)
            }
        }
    }

    fun agregarMedico(medico: Medico) {
        viewModelScope.launch {
            try {
                repository.addMedico(medico)
                cargarMedicos()
            } catch (e: Exception) {
                Log.e("MedicoViewModel", "Error agregando medico", e)
            }
        }
    }

    fun eliminarMedico(id: Long) {
        viewModelScope.launch {
            try {
                repository.deleteMedico(id)
                cargarMedicos()
            } catch (e: Exception) {
                Log.e("MedicoViewModel", "Error eliminando medico", e)
            }
        }
    }

    fun actualizarMedico() {
        viewModelScope.launch {
            try {
                repository.updateMedico(_medicoActual.value)
                cargarMedicos()
            } catch (e: Exception) {
                Log.e("MedicoViewModel", "Error actualizando medico", e)
            }
        }
    }

    fun obtenerMedicoPorId(id: Long) {
        viewModelScope.launch {
            try {
                _medicoActual.value = repository.getMedico(id)
            } catch (e: Exception) {
                Log.e("MedicoViewModel", "Error obteniendo medico", e)
            }
        }
    }

    fun limpiarFormulario() {
        _medicoActual.value = Medico()
    }
}
