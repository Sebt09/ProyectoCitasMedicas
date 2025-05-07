package com.example.BackendCitasMedicas.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_medico;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String especialidad;

    @OneToMany(mappedBy = "medico")
    @JsonIgnore
    private List<Consulta> consultas;

    @OneToMany(mappedBy = "medico")
    @JsonIgnore
    private List<Cita> citas;
}
