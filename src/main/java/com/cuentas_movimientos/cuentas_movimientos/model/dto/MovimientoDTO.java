package com.cuentas_movimientos.cuentas_movimientos.model.dto;

import java.time.LocalDateTime;

public record MovimientoDTO(
        LocalDateTime fecha,
        Double monto,
        String tipoMovimiento,
        Double saldo
) {}