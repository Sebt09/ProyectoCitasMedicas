package com.example.frontcitasmedicas.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontcitasmedicas.Model.Pago
import com.example.frontcitasmedicas.Repository.PagoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PagoViewModel : ViewModel() {

    private val repository = PagoRepository()

    // Lista de todos los pagos
    private val _pagos = MutableStateFlow<List<Pago>>(emptyList())
    val pagos: StateFlow<List<Pago>> = _pagos

    // Pago actual para el formulario
    private val _pagoActual = MutableStateFlow(Pago())
    val pagoActual: StateFlow<Pago> = _pagoActual

    // Nuevo StateFlow para indicar si una operación (guardar/actualizar) se completó exitosamente
    private val _operacionCompletada = MutableStateFlow<Boolean?>(null)
    val operacionCompletada: StateFlow<Boolean?> = _operacionCompletada

    fun cargarPagos() {
        viewModelScope.launch {
            try {
                val lista = repository.getPagos()
                Log.d("PagoViewModel", "Pagos cargados: ${lista.size}")
                lista.forEach { pago -> Log.d("PagoViewModel", "Pago cargado: ${pago.metodo_pago} - ID: ${pago.id_pago}") }
                _pagos.value = lista
            } catch (e: Exception) {
                Log.e("PagoViewModel", "Error cargando pagos", e)
            }
        }
    }

    // Modificamos agregarPago para emitir el estado de completado
    fun agregarPago() { // Asume que agrega _pagoActual.value
        viewModelScope.launch {
            try {
                // Si `addPago` en el repositorio requiere un parámetro, usa _pagoActual.value
                repository.addPago(_pagoActual.value)
                cargarPagos() // Recarga la lista para actualizar la UI
                _operacionCompletada.value = true // Indica que la operación fue exitosa
            } catch (e: Exception) {
                Log.e("PagoViewModel", "Error agregando pago", e)
                _operacionCompletada.value = false // Indica que hubo un error
            }
        }
    }

    fun eliminarPago(id: Long) {
        viewModelScope.launch {
            try {
                repository.deletePago(id)
                cargarPagos()
            } catch (e: Exception) {
                Log.e("PagoViewModel", "Error eliminando pago", e)
            }
        }
    }

    // Modificamos actualizarPago para emitir el estado de completado
    fun actualizarPago() {
        viewModelScope.launch {
            try {
                repository.updatePago(_pagoActual.value)
                cargarPagos() // Recarga la lista para actualizar la UI
                _operacionCompletada.value = true // Indica que la operación fue exitosa
            } catch (e: Exception) {
                Log.e("PagoViewModel", "Error actualizando pago", e)
                _operacionCompletada.value = false // Indica que hubo un error
            }
        }
    }

    fun obtenerPagoPorId(id: Long) {
        viewModelScope.launch {
            try {
                val pago = repository.getPago(id)
                _pagoActual.value = pago
            } catch (e: Exception) {
                Log.e("PagoViewModel", "Error obteniendo pago", e)
            }
        }
    }

    fun limpiarFormulario() {
        _pagoActual.value = Pago()
    }

    // Nuevo método para resetear el estado de operación completada
    fun resetOperacionCompletada() {
        _operacionCompletada.value = null
    }

    // Nuevo método para actualizar campos específicos del pago actual
    fun actualizarCampo(campo: String, valor: String) {
        _pagoActual.value = when (campo) {
            "fecha_pago" -> _pagoActual.value.copy(fecha_pago = valor)
            "monto" -> _pagoActual.value.copy(monto = valor.toIntOrNull() ?: 0) // Convertir a Int
            "metodo_pago" -> _pagoActual.value.copy(metodo_pago = valor)
            "referencia" -> _pagoActual.value.copy(referencia = valor)
            // No se incluye `paciente` aquí; su selección se manejaría con un componente separado
            else -> _pagoActual.value
        }
    }
}