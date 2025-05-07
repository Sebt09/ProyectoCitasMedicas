package com.example.BackendCitasMedicas.Service;

import com.example.BackendCitasMedicas.Model.Consulta;
import com.example.BackendCitasMedicas.Repository.ConsultaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService{
    
    private final ConsultaRepository consultaRepository;
    
    public ConsultaService(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public Consulta guardarConsulta(Consulta consulta){
        try{
            if(consulta.getPaciente() == null){
                throw new IllegalArgumentException("Ingrese un paciente para la consulta");
            }
            return consultaRepository.save(consulta);
        }catch(Exception e){
            throw new RuntimeException("No se pudo guardar la consulta ", e);
        }
    }

    public List<Consulta> listarConsulta(){
        try{
            return consultaRepository.findAll();
        }catch (Exception e){
            throw new RuntimeException("Error al listar las consultas");
        }
    }

    public Optional<Consulta> listarConsultaPorId(long id) {
        try{
            if(!consultaRepository.existsById(id)){
                throw new IllegalArgumentException("No se encontro la consulta con ID: " + id);
            }
            return consultaRepository.findById(id);
        }catch(Exception e){
            throw new RuntimeException("Error al buscar la Consulta por ID: " + id + e.getMessage(), e);
        }
    }

    public void eliminarConsulta(long id){
        try{
            if(!consultaRepository.existsById(id)){
                throw new IllegalArgumentException("No se encontro la consulta con ID: " + id);
            }
            consultaRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("Error al eliminar la consulta");
        }
    }
}
