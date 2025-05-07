package com.example.BackendCitasMedicas.Controller;

import com.example.BackendCitasMedicas.Model.Cita;
import com.example.BackendCitasMedicas.Service.CitaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("apicita/")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @PostMapping("guardar")
    public Cita guardarCita(@RequestBody Cita cita){
        return citaService.guardarCita(cita);
    }

    @GetMapping("listar")
    public List<Cita> listarCitas(){
        return citaService.listarCita();
    }

    @GetMapping("buscar/{id}")
    public Optional<Cita> listarCitaPorId(@PathVariable long id){
        return citaService.listarCitaPorId(id);
    }

    @DeleteMapping("eliminar/{id}")
    public void eliminarCita(@PathVariable long id){
        citaService.eliminarCita(id);
    }

    @PutMapping("actualizar")
    public Cita actualizarCita(@RequestBody Cita cita){
        return citaService.guardarCita(cita);
    }

}
