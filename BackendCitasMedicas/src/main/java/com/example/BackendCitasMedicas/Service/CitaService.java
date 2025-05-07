package com.example.BackendCitasMedicas.Service;

import com.example.BackendCitasMedicas.Model.Cita;
import com.example.BackendCitasMedicas.Repository.CitaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitaService {

    private final CitaRepository citaRepository;

    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    public Cita guardarCita(Cita cita){
        try{
            if(cita.getPaciente() == null){
                throw new IllegalArgumentException("Ingrese un paciente para la cita");
            }
            return citaRepository.save(cita);
        }catch(Exception e){
            throw new RuntimeException("No se pudo guardar la cita ", e);
        }
    }

    public List<Cita> listarCita(){
        try{
            return citaRepository.findAll();
        }catch (Exception e){
            throw new RuntimeException("Error al listar las citas");
        }
    }

    public Optional<Cita> listarCitaPorId(long id) {
        try{
            if(!citaRepository.existsById(id)){
                throw new IllegalArgumentException("No se encontro la cita con ID: " + id);
            }
            return citaRepository.findById(id);
        }catch(Exception e){
            throw new RuntimeException("Error al buscar la cita por ID: " + id + e.getMessage(), e);
        }
    }

    public void eliminarCita(long id){
        try{
            if(!citaRepository.existsById(id)){
                throw new IllegalArgumentException("No se encontro la cita con ID: " + id);
            }
            citaRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("Error al eliminar la cita");
        }
    }
}
