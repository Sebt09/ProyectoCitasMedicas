package com.example.BackendCitasMedicas.Service;

import com.example.BackendCitasMedicas.Model.Paciente;
import com.example.BackendCitasMedicas.Repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    
    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente guardarPaciente(Paciente paciente){
        try{
            if(paciente.getFecha_nacimiento() == null){
                throw new IllegalArgumentException("Ingrese una fecha de nacimiento para el paciente");
            }
            return pacienteRepository.save(paciente);
        }catch(Exception e){
            throw new RuntimeException("No se pudo guardar al paciente ", e);
        }
    }

    public List<Paciente> listarPaciente(){
        try{
            return pacienteRepository.findAll();
        }catch (Exception e){
            throw new RuntimeException("Error al listar los pacientes");
        }
    }

    public Optional<Paciente> listarPacientePorId(long id) {
        try{
            if(!pacienteRepository.existsById(id)){
                throw new IllegalArgumentException("No se encontro al paciente con ID: " + id);
            }
            return pacienteRepository.findById(id);
        }catch(Exception e){
            throw new RuntimeException("Error al buscar al paciente por ID: " + id + e.getMessage(), e);
        }
    }

    public void eliminarPaciente(long id){
        try{
            if(!pacienteRepository.existsById(id)){
                throw new IllegalArgumentException("No se encontro al paciente con ID: " + id);
            }
            pacienteRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("Error al eliminar al paciente");
        }
    }
}
