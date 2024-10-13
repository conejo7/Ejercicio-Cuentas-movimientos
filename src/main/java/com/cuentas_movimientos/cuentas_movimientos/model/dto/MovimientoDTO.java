package com.cuentas_movimientos.cuentas_movimientos.model.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MovimientoDTO {
    private LocalDate fecha;
    private Double monto;
    private String tipoMovimiento;
    private Double saldo;
}
