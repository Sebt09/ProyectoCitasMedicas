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

    private val _pagos = MutableStateFlow<List<Pago>>(emptyList())
    val pagos: StateFlow<List<Pago>> = _pagos

    private val _pagoActual = MutableStateFlow(Pago())
    val pagoActual: StateFlow<Pago> = _pagoActual

    fun cargarPagos() {
        viewModelScope.launch {
            try {
                _pagos.value = repository.getPagos()
            } catch (e: Exception) {
                Log.e("PagoViewModel", "Error cargando pagos", e)
            }
        }
    }

    fun agregarPago(pago: Pago) {
        viewModelScope.launch {
            try {
                repository.addPago(pago)
                cargarPagos()
            } catch (e: Exception) {
                Log.e("PagoViewModel", "Error agregando pago", e)
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

    fun actualizarPago() {
        viewModelScope.launch {
            try {
                repository.updatePago(_pagoActual.value)
                cargarPagos()
            } catch (e: Exception) {
                Log.e("PagoViewModel", "Error actualizando pago", e)
            }
        }
    }

    fun obtenerPagoPorId(id: Long) {
        viewModelScope.launch {
            try {
                _pagoActual.value = repository.getPago(id)
            } catch (e: Exception) {
                Log.e("PagoViewModel", "Error obteniendo pago", e)
            }
        }
    }

    fun limpiarFormulario() {
        _pagoActual.value = Pago()
    }
}
