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
import java.util.ArrayList;
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
        LocalDateTime fin = LocalDate.parse(fechaFin).atTime(23, 59, 59);

        List<Account> cuentas = cuentaRepository.findByCustomer_Clienteid(clienteId);

        if (cuentas.isEmpty()) {
            throw new GeneralException("No se encontró la cuenta para el cliente con ID: " + clienteId);
        }

        String nombreCliente = cuentas.get(0).getCustomer().getPerson().getNombre();
        ReporteDTO reporte = new ReporteDTO(clienteId, nombreCliente, new ArrayList<>());

        List<CuentaDTO> cuentasDTO = cuentas.stream()
                .map(cuenta -> mapearCuentaDTO(cuenta, inicio, fin, page, size))
                .toList();

        cuentasDTO.forEach(reporte::addCuenta);

        return reporte;
    }

    private CuentaDTO mapearCuentaDTO(Account cuenta, LocalDateTime inicio, LocalDateTime fin, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        // Obtener los movimientos paginados de la cuenta
        Page<Movement> movimientos = movimientoRepository.findByAccountIdAndFechaBetween(
                cuenta.getId(), inicio, fin, pageRequest
        );

        // Convertir los movimientos a DTOs
        List<MovimientoDTO> movimientosDTO = movimientos.stream()
                .map(this::mapearMovimientoDTO)
                .toList();

        // Retornar un CuentaDTO inmutable (usando record)
        return new CuentaDTO(
                cuenta.getNumeroCuenta(),
                cuenta.getTipoCuenta(),
                cuenta.getSaldoInicial(),
                cuenta.getEstado(),
                movimientosDTO,
                movimientos.getTotalPages(),
                movimientos.getNumber(),
                movimientos.getTotalElements()
        );
    }

    private MovimientoDTO mapearMovimientoDTO(Movement movimiento) {
        return new MovimientoDTO(
                movimiento.getFecha(),
                movimiento.getValor(),
                movimiento.getTipoMovimiento(),
                movimiento.getSaldo()
        );
    }
}
