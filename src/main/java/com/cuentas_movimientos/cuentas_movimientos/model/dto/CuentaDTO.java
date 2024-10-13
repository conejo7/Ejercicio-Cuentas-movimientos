package com.cuentas_movimientos.cuentas_movimientos.model.dto;

import java.util.List;

public record CuentaDTO (
     String numeroCuenta,
     String tipoCuenta,
     Double saldoInicial,
     boolean estado,
     List<MovimientoDTO> movimientos ,

     int paginaActual,
     int totalPaginas,
     long totalMovimientos)
{
}
