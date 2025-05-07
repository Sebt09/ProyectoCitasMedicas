package com.example.BackendCitasMedicas.Controller;

import com.example.BackendCitasMedicas.Model.Paciente;
import com.example.BackendCitasMedicas.Service.PacienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("apipaciente/")
public class PacienteController {
    
    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping("guardar")
    public Paciente guardarPaciente(@RequestBody Paciente paciente){
        return pacienteService.guardarPaciente(paciente);
    }

    @GetMapping("listar")
    public List<Paciente> listarPacientes(){
        return pacienteService.listarPaciente();
    }

    @GetMapping("buscar/{id}")
    public Optional<Paciente> listarPacientePorId(@PathVariable long id){
        return pacienteService.listarPacientePorId(id);
    }

    @DeleteMapping("eliminar/{id}")
    public void eliminarPaciente(@PathVariable long id){
        pacienteService.eliminarPaciente(id);
    }

    @PutMapping("actualizar")
    public Paciente actualizarPaciente(@RequestBody Paciente paciente){
        return pacienteService.guardarPaciente(paciente);
    }
}
