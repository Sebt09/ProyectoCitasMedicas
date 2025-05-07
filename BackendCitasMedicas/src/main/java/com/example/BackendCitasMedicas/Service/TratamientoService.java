package com.example.BackendCitasMedicas.Service;

import com.example.BackendCitasMedicas.Model.Tratamiento;
import com.example.BackendCitasMedicas.Repository.TratamientoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TratamientoService {
    
    private final TratamientoRepository tratamientoRepository;
    
    public TratamientoService(TratamientoRepository tratamientoRepository) {
        this.tratamientoRepository = tratamientoRepository;
    }

    public Tratamiento guardarTratamiento(Tratamiento tratamiento){
        try{
            if(tratamiento.getConsulta() == null){
                throw new IllegalArgumentException("Ingrese una consulta para el tratamiento");
            }
            return tratamientoRepository.save(tratamiento);
        }catch(Exception e){
            throw new RuntimeException("No se pudo guardar el tratamiento ", e);
        }
    }

    public List<Tratamiento> listarTratamiento(){
        try{
            return tratamientoRepository.findAll();
        }catch (Exception e){
            throw new RuntimeException("Error al listar los tratamientos");
        }
    }

    public Optional<Tratamiento> listarTratamientoPorId(long id) {
        try{
            if(!tratamientoRepository.existsById(id)){
                throw new IllegalArgumentException("No se encontro el tratamiento con ID: " + id);
            }
            return tratamientoRepository.findById(id);
        }catch(Exception e){
            throw new RuntimeException("Error al buscar el tratamiento por ID: " + id + e.getMessage(), e);
        }
    }

    public void eliminarTratamiento(long id){
        try{
            if(!tratamientoRepository.existsById(id)){
                throw new IllegalArgumentException("No se encontro el tratamiento con ID: " + id);
            }
            tratamientoRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("Error al eliminar el tratamiento");
        }
    }
}
