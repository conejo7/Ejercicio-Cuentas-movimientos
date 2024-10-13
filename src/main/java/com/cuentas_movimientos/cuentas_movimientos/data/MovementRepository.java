package com.cuentas_movimientos.cuentas_movimientos.data;


import com.cuentas_movimientos.cuentas_movimientos.model.pojo.Movement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDateTime;

import java.util.Optional;

public interface MovementRepository extends JpaRepository<Movement,Long> {

    Optional<Movement> findFirstByAccountNumeroCuentaOrderByIdDesc(String numeroCuenta);

    Page<Movement> findByAccountIdAndFechaBetween(Long accountId, LocalDateTime fechaInicio, LocalDateTime fechaFin, Pageable pageable);
}
