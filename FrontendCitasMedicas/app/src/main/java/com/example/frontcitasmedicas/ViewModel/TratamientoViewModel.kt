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

    // Lista de todos los tratamientos
    private val _tratamientos = MutableStateFlow<List<Tratamiento>>(emptyList())
    val tratamientos: StateFlow<List<Tratamiento>> = _tratamientos

    // Tratamiento actual para el formulario
    private val _tratamientoActual = MutableStateFlow(Tratamiento())
    val tratamientoActual: StateFlow<Tratamiento> = _tratamientoActual

    // Nuevo StateFlow para indicar si una operación (guardar/actualizar) se completó exitosamente
    private val _operacionCompletada = MutableStateFlow<Boolean?>(null)
    val operacionCompletada: StateFlow<Boolean?> = _operacionCompletada

    fun cargarTratamientos() {
        viewModelScope.launch {
            try {
                val lista = repository.getTratamientos()
                Log.d("TratamientoViewModel", "Tratamientos cargados: ${lista.size}")
                lista.forEach { tratamiento -> Log.d("TratamientoViewModel", "Tratamiento cargado: ${tratamiento.tipo} - ID: ${tratamiento.id_tratamiento}") }
                _tratamientos.value = lista
            } catch (e: Exception) {
                Log.e("TratamientoViewModel", "Error cargando tratamientos", e)
            }
        }
    }

    // Modificamos agregarTratamiento para emitir el estado de completado
    fun agregarTratamiento() { // Asume que agrega _tratamientoActual.value
        viewModelScope.launch {
            try {
                // Si `addTratamiento` en el repositorio requiere un parámetro, usa _tratamientoActual.value
                repository.addTratamiento(_tratamientoActual.value)
                cargarTratamientos() // Recarga la lista para actualizar la UI
                _operacionCompletada.value = true // Indica que la operación fue exitosa
            } catch (e: Exception) {
                Log.e("TratamientoViewModel", "Error agregando tratamiento", e)
                _operacionCompletada.value = false // Indica que hubo un error
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

    // Modificamos actualizarTratamiento para emitir el estado de completado
    fun actualizarTratamiento() {
        viewModelScope.launch {
            try {
                repository.updateTratamiento(_tratamientoActual.value)
                cargarTratamientos() // Recarga la lista para actualizar la UI
                _operacionCompletada.value = true // Indica que la operación fue exitosa
            } catch (e: Exception) {
                Log.e("TratamientoViewModel", "Error actualizando tratamiento", e)
                _operacionCompletada.value = false // Indica que hubo un error
            }
        }
    }

    fun obtenerTratamientoPorId(id: Long) {
        viewModelScope.launch {
            try {
                val tratamiento = repository.getTratamiento(id)
                _tratamientoActual.value = tratamiento
            } catch (e: Exception) {
                Log.e("TratamientoViewModel", "Error obteniendo tratamiento", e)
            }
        }
    }

    fun limpiarFormulario() {
        _tratamientoActual.value = Tratamiento()
    }

    // Nuevo método para resetear el estado de operación completada
    fun resetOperacionCompletada() {
        _operacionCompletada.value = null
    }

    // Nuevo método para actualizar campos específicos del tratamiento actual
    fun actualizarCampo(campo: String, valor: String) {
        _tratamientoActual.value = when (campo) {
            "tipo" -> _tratamientoActual.value.copy(tipo = valor)
            "descripcion" -> _tratamientoActual.value.copy(descripcion = valor)
            "frecuencia" -> _tratamientoActual.value.copy(frecuencia = valor)
            "duracion" -> _tratamientoActual.value.copy(duracion = valor)
            // No se incluye `consulta` aquí; su selección se manejaría con un componente separado
            else -> _tratamientoActual.value
        }
    }
}