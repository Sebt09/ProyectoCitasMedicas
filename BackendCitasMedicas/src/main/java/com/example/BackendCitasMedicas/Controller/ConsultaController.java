package com.example.BackendCitasMedicas.Controller;

import com.example.BackendCitasMedicas.Model.Consulta;
import com.example.BackendCitasMedicas.Service.ConsultaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("apiconsulta/")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping("guardar")
    public Consulta guardarConsulta(@RequestBody Consulta consulta){
        return consultaService.guardarConsulta(consulta);
    }

    @GetMapping("listar")
    public List<Consulta> listarConsultas(){
        return consultaService.listarConsulta();
    }

    @GetMapping("buscar/{id}")
    public Optional<Consulta> listarConsultaPorId(@PathVariable long id){
        return consultaService.listarConsultaPorId(id);
    }

    @DeleteMapping("eliminar/{id}")
    public void eliminarConsulta(@PathVariable long id){
        consultaService.eliminarConsulta(id);
    }

    @PutMapping("actualizar")
    public Consulta actualizarConsulta(@RequestBody Consulta consulta){
        return consultaService.guardarConsulta(consulta);
    }
}
