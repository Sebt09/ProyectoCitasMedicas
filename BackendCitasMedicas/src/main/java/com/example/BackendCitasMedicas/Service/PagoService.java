package com.example.BackendCitasMedicas.Service;

import com.example.BackendCitasMedicas.Model.Pago;
import com.example.BackendCitasMedicas.Repository.PagoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagoService {
    
    private final PagoRepository pagoRepository;

    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    public Pago guardarPago(Pago pago){
        try{
            if(pago.getPaciente() == null){
                throw new IllegalArgumentException("Ingrese un paciente para el pago");
            }
            return pagoRepository.save(pago);
        }catch(Exception e){
            throw new RuntimeException("No se pudo guardar al pago ", e);
        }
    }

    public List<Pago> listarPago(){
        try{
            return pagoRepository.findAll();
        }catch (Exception e){
            throw new RuntimeException("Error al listar los pagos");
        }
    }

    public Optional<Pago> listarPagoPorId(long id) {
        try{
            if(!pagoRepository.existsById(id)){
                throw new IllegalArgumentException("No se encontro el pago con ID: " + id);
            }
            return pagoRepository.findById(id);
        }catch(Exception e){
            throw new RuntimeException("Error al buscar el pago por ID: " + id + e.getMessage(), e);
        }
    }

    public void eliminarPago(long id){
        try{
            if(!pagoRepository.existsById(id)){
                throw new IllegalArgumentException("No se encontro el pago con ID: " + id);
            }
            pagoRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("Error al eliminar el pago");
        }
    }
}
