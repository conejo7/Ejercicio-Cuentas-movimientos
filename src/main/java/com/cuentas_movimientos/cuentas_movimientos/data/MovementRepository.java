package com.cuentas_movimientos.cuentas_movimientos.data;


import com.cuentas_movimientos.cuentas_movimientos.model.pojo.Movement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovementRepository extends JpaRepository<Movement,Long> {

    // Buscar todos los movimientos por cuentaId (sin paginación)
    Optional<Movement> findFirstByAccountNumeroCuentaOrderByIdDesc(String numeroCuenta);

    // Buscar movimientos por cuentaId con paginación
    Page<Movement> findByAccountId(Long cuentaId, Pageable pageable);

//    Page<Movement> findByCuentaIdAndFechaBetween(Long cuentaId, LocalDate fechaInicio, LocalDate fechaFin, Pageable pageable);
}
