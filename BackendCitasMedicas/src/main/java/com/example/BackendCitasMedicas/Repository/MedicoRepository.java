package com.example.BackendCitasMedicas.Repository;

import com.example.BackendCitasMedicas.Model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
}
