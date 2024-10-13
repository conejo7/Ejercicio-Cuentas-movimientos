package com.cuentas_movimientos.cuentas_movimientos.model.dto;

import java.util.List;

public record ReporteDTO (
     Long clienteId,
     String nombreCliente,
     List<CuentaDTO> cuentas )
{
    public void addCuenta(CuentaDTO cuenta) {
        this.cuentas.add(cuenta);
    }
}
