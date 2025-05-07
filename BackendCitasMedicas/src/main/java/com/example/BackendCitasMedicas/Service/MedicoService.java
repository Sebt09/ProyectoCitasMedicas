package com.example.BackendCitasMedicas.Service;

import com.example.BackendCitasMedicas.Model.Medico;
import com.example.BackendCitasMedicas.Repository.MedicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicoService {
    
    private final MedicoRepository medicoRepository;

    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public Medico guardarMedico(Medico medico){
        try{
            if(medico.getEspecialidad() == null){
                throw new IllegalArgumentException("Ingrese una especialidad para el medico");
            }
            return medicoRepository.save(medico);
        }catch(Exception e){
            throw new RuntimeException("No se pudo guardar al medico ", e);
        }
    }

    public List<Medico> listarMedico(){
        try{
            return medicoRepository.findAll();
        }catch (Exception e){
            throw new RuntimeException("Error al listar los medicos");
        }
    }

    public Optional<Medico> listarMedicoPorId(long id) {
        try{
            if(!medicoRepository.existsById(id)){
                throw new IllegalArgumentException("No se encontro al medico con ID: " + id);
            }
            return medicoRepository.findById(id);
        }catch(Exception e){
            throw new RuntimeException("Error al buscar al medico por ID: " + id + e.getMessage(), e);
        }
    }

    public void eliminarMedico(long id){
        try{
            if(!medicoRepository.existsById(id)){
                throw new IllegalArgumentException("No se encontro al medico con ID: " + id);
            }
            medicoRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("Error al eliminar al medico");
        }
    }
}
