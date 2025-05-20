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

    // Lista de todas las consultas
    private val _consultas = MutableStateFlow<List<Consulta>>(emptyList())
    val consultas: StateFlow<List<Consulta>> = _consultas

    // Consulta actual para el formulario
    private val _consultaActual = MutableStateFlow(Consulta())
    val consultaActual: StateFlow<Consulta> = _consultaActual

    // Nuevo StateFlow para indicar si una operación (guardar/actualizar) se completó exitosamente
    private val _operacionCompletada = MutableStateFlow<Boolean?>(null)
    val operacionCompletada: StateFlow<Boolean?> = _operacionCompletada

    fun cargarConsultas() {
        viewModelScope.launch {
            try {
                val lista = repository.getConsultas()
                Log.d("ConsultaViewModel", "Consultas cargadas: ${lista.size}")
                lista.forEach { consulta -> Log.d("ConsultaViewModel", "Consulta cargada: ${consulta.motivo} - ID: ${consulta.id_consulta}") }
                _consultas.value = lista
            } catch (e: Exception) {
                Log.e("ConsultaViewModel", "Error cargando consultas", e)
            }
        }
    }

    // Modificamos agregarConsulta para emitir el estado de completado
    fun agregarConsulta() { // Asume que agrega _consultaActual.value
        viewModelScope.launch {
            try {
                // Si `agregarConsulta` en el repositorio requiere un parámetro, usa _consultaActual.value
                repository.addConsulta(_consultaActual.value)
                cargarConsultas() // Recarga la lista para actualizar la UI
                _operacionCompletada.value = true // Indica que la operación fue exitosa
            } catch (e: Exception) {
                Log.e("ConsultaViewModel", "Error agregando consulta", e)
                _operacionCompletada.value = false // Indica que hubo un error
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

    // Modificamos actualizarConsulta para emitir el estado de completado
    fun actualizarConsulta() {
        viewModelScope.launch {
            try {
                repository.updateConsulta(_consultaActual.value)
                cargarConsultas() // Recarga la lista para actualizar la UI
                _operacionCompletada.value = true // Indica que la operación fue exitosa
            } catch (e: Exception) {
                Log.e("ConsultaViewModel", "Error actualizando consulta", e)
                _operacionCompletada.value = false // Indica que hubo un error
            }
        }
    }

    fun obtenerConsultaPorId(id: Long) {
        viewModelScope.launch {
            try {
                val consulta = repository.getConsulta(id)
                _consultaActual.value = consulta
            } catch (e: Exception) {
                Log.e("ConsultaViewModel", "Error obteniendo consulta", e)
            }
        }
    }

    fun limpiarFormulario() {
        _consultaActual.value = Consulta()
    }

    // Nuevo método para resetear el estado de operación completada
    fun resetOperacionCompletada() {
        _operacionCompletada.value = null
    }

    // Nuevo método para actualizar campos específicos de la consulta actual
    fun actualizarCampo(campo: String, valor: String) {
        _consultaActual.value = when (campo) {
            "fecha_consulta" -> _consultaActual.value.copy(fecha_consulta = valor)
            "motivo" -> _consultaActual.value.copy(motivo = valor)
            "diagnostico" -> _consultaActual.value.copy(diagnostico = valor)
            "notas" -> _consultaActual.value.copy(notas = valor)
            // No se incluyen paciente y medico aquí porque generalmente se seleccionan
            // a través de IDs o componentes separados que luego actualizan el objeto completo.
            // Si necesitas actualizar paciente/medico, requerirías lógica adicional
            // para buscar por ID y crear un nuevo objeto Paciente/Medico.
            else -> _consultaActual.value
        }
    }
}