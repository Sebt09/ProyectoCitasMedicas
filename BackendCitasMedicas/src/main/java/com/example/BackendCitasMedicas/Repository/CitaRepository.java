package com.example.BackendCitasMedicas.Repository;

import com.example.BackendCitasMedicas.Model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
}
