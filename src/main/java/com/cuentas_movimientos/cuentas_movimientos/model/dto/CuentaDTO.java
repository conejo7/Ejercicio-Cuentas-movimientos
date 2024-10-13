package com.cuentas_movimientos.cuentas_movimientos.model.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class CuentaDTO {
    private String numeroCuenta;
    private String tipoCuenta;
    private Double saldoActual;
    private List<MovimientoDTO> movimientos = new ArrayList<>();

    private int paginaActual;
    private int totalPaginas;
    private long totalMovimientos;

    public void addMovimiento(MovimientoDTO movimiento) {
        this.movimientos.add(movimiento);
    }
}
