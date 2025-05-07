package com.example.BackendCitasMedicas.Controller;

import com.example.BackendCitasMedicas.Model.Pago;
import com.example.BackendCitasMedicas.Service.PagoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("apipago/")
public class PagoController {
    
    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @PostMapping("guardar")
    public Pago guardarPago(@RequestBody Pago pago){
        return pagoService.guardarPago(pago);
    }

    @GetMapping("listar")
    public List<Pago> listarPagos(){
        return pagoService.listarPago();
    }

    @GetMapping("buscar/{id}")
    public Optional<Pago> listarPagoPorId(@PathVariable long id){
        return pagoService.listarPagoPorId(id);
    }

    @DeleteMapping("eliminar/{id}")
    public void eliminarPago(@PathVariable long id){
        pagoService.eliminarPago(id);
    }

    @PutMapping("actualizar")
    public Pago actualizarPago(@RequestBody Pago pago){
        return pagoService.guardarPago(pago);
    }
}
