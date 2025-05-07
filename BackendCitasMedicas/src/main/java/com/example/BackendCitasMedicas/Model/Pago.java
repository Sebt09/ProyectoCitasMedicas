package com.example.BackendCitasMedicas.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_pago;
    private LocalDate fecha_pago;
    private int monto;
    private String metodo_pago;
    private String referencia;

    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;
}
