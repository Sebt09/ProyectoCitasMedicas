package com.example.BackendCitasMedicas.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tratamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_tratamiento;
    private String tipo;
    private String descripcion;
    private String frecuencia;
    private String duracion;

    @ManyToOne
    @JoinColumn(name = "id_consulta")
    private Consulta consulta;
}
