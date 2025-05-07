package com.example.BackendCitasMedicas.Controller;

import com.example.BackendCitasMedicas.Model.Medico;
import com.example.BackendCitasMedicas.Service.MedicoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("apimedico/")
public class MedicoController {
    
    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @PostMapping("guardar")
    public Medico guardarMedico(@RequestBody Medico medico){
        return medicoService.guardarMedico(medico);
    }

    @GetMapping("listar")
    public List<Medico> listarMedicos(){
        return medicoService.listarMedico();
    }

    @GetMapping("buscar/{id}")
    public Optional<Medico> listarMedicoPorId(@PathVariable long id){
        return medicoService.listarMedicoPorId(id);
    }

    @DeleteMapping("eliminar/{id}")
    public void eliminarMedico(@PathVariable long id){
        medicoService.eliminarMedico(id);
    }

    @PutMapping("actualizar")
    public Medico actualizarMedico(@RequestBody Medico medico){
        return medicoService.guardarMedico(medico);
    }
}
