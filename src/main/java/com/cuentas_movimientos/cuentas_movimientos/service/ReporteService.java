package com.cuentas_movimientos.cuentas_movimientos.service;

import com.cuentas_movimientos.cuentas_movimientos.data.AccountRepository;
import com.cuentas_movimientos.cuentas_movimientos.data.MovementRepository;
import com.cuentas_movimientos.cuentas_movimientos.exception.GeneralException;
import com.cuentas_movimientos.cuentas_movimientos.model.dto.CuentaDTO;
import com.cuentas_movimientos.cuentas_movimientos.model.dto.MovimientoDTO;
import com.cuentas_movimientos.cuentas_movimientos.model.dto.ReporteDTO;
import com.cuentas_movimientos.cuentas_movimientos.model.pojo.Account;
import com.cuentas_movimientos.cuentas_movimientos.model.pojo.Movement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReporteService {

    private final AccountRepository cuentaRepository;

    private final MovementRepository movimientoRepository;

    public ReporteService(AccountRepository cuentaRepository, MovementRepository movimientoRepository) {
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
    }

    public ReporteDTO generarReporte(String fechaInicio, String fechaFin, Long clienteId, int page, int size) {

        LocalDateTime inicio = LocalDate.parse(fechaInicio).atStartOfDay();
        LocalDateTime fin = LocalDate.parse(fechaFin).atTime(23,59,59);

        List<Account> cuentas = cuentaRepository.findByCustomer_Clienteid(clienteId);

        if (cuentas.isEmpty()) {
            throw new GeneralException("No se encontró la cuenta para el cliente con ID: " + clienteId);
        }

        ReporteDTO reporte = new ReporteDTO();
        reporte.setClienteId(clienteId);
        reporte.setNombreCliente(cuentas.get(0).getCustomer().getPerson().getNombre());

        for (Account cuenta : cuentas) {
            CuentaDTO cuentaDTO = new CuentaDTO();
            cuentaDTO.setNumeroCuenta(cuenta.getNumeroCuenta());
            cuentaDTO.setTipoCuenta(cuenta.getTipoCuenta());
            cuentaDTO.setSaldoInicial(cuenta.getSaldoInicial());
            cuentaDTO.setEstado(cuenta.getEstado());

            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Movement> movimientos = movimientoRepository.findByAccountIdAndFechaBetween(
                    cuenta.getId(), inicio, fin, pageRequest
            );

            for (Movement movimiento : movimientos) {
                MovimientoDTO movDTO = new MovimientoDTO();
                movDTO.setFecha(LocalDate.from(movimiento.getFecha()));

                movDTO.setMonto(movimiento.getValor());
                movDTO.setTipoMovimiento(movimiento.getTipoMovimiento());
                movDTO.setSaldo(movimiento.getSaldo());

                cuentaDTO.addMovimiento(movDTO);
            }
            cuentaDTO.setTotalMovimientos(movimientos.getTotalElements());
            cuentaDTO.setTotalPaginas(movimientos.getTotalPages());
            cuentaDTO.setPaginaActual(movimientos.getNumber());

            reporte.addCuenta(cuentaDTO);

        }

        return reporte;
    }

}
