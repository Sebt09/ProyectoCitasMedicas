package com.example.BackendCitasMedicas.Repository;

import com.example.BackendCitasMedicas.Model.Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {
}
