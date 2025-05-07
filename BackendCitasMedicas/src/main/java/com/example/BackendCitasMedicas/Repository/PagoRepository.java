package com.example.BackendCitasMedicas.Repository;

import com.example.BackendCitasMedicas.Model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
}
