package com.example.BackendCitasMedicas.Controller;

import com.example.BackendCitasMedicas.Model.Tratamiento;
import com.example.BackendCitasMedicas.Service.TratamientoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("apitratamiento/")
public class TratamientoController {
    
    private final TratamientoService tratamientoService;

    public TratamientoController(TratamientoService tratamientoService) {
        this.tratamientoService = tratamientoService;
    }

    @PostMapping("guardar")
    public Tratamiento guardarTratamiento(@RequestBody Tratamiento tratamiento){
        return tratamientoService.guardarTratamiento(tratamiento);
    }

    @GetMapping("listar")
    public List<Tratamiento> listarTratamientos(){
        return tratamientoService.listarTratamiento();
    }

    @GetMapping("buscar/{id}")
    public Optional<Tratamiento> listarTratamientoPorId(@PathVariable long id){
        return tratamientoService.listarTratamientoPorId(id);
    }

    @DeleteMapping("eliminar/{id}")
    public void eliminarTratamiento(@PathVariable long id){
        tratamientoService.eliminarTratamiento(id);
    }

    @PutMapping("actualizar")
    public Tratamiento actualizarTratamiento(@RequestBody Tratamiento tratamiento){
        return tratamientoService.guardarTratamiento(tratamiento);
    }
}
