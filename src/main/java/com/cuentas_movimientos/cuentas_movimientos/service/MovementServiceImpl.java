package com.cuentas_movimientos.cuentas_movimientos.service;


import com.cuentas_movimientos.cuentas_movimientos.data.AccountRepository;
import com.cuentas_movimientos.cuentas_movimientos.data.MovementRepository;
import com.cuentas_movimientos.cuentas_movimientos.exception.GeneralException;
import com.cuentas_movimientos.cuentas_movimientos.model.pojo.Account;
import com.cuentas_movimientos.cuentas_movimientos.model.pojo.Movement;
import com.cuentas_movimientos.cuentas_movimientos.model.request.MovementRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MovementServiceImpl implements MovementService {


    private final MovementRepository movementRepository;

    private final AccountRepository accountRepository;



    public MovementServiceImpl(MovementRepository movementRepository, AccountRepository accountRepository) {
        this.movementRepository = movementRepository;
        this.accountRepository = accountRepository;

    }

    @Override
    public List<Movement> getMovements(){
        List<Movement> movements = movementRepository.findAll();
        return movements.isEmpty() ? null : movements;
    }

    @Override
    public Movement getMovement(String movementId) {
        return movementRepository.findById(Long.valueOf(movementId)).orElse(null);
    }

    @Override
    public Boolean removeMovement(String movementId) {

        Movement account = movementRepository.findById(Long.valueOf(movementId)).orElse(null);

        if (account != null) {
            movementRepository.delete(account);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }


    @Override
    public Movement updateMovement(String movementId, MovementRequest movementRequest) {
        Movement movement = movementRepository.findById(Long.valueOf(movementId)).orElse(null);
        if (movement !=null){
            movement.setFecha(movementRequest.getDateTime());
            movement.setTipoMovimiento(movementRequest.getTypeMovement());
            movement.setValor(movementRequest.getValue());
            movement.setSaldo(movementRequest.getBalance());

            movementRepository.save(movement);
        }
        return movement;
    }



    @Override
    public Movement createMovement(MovementRequest request) {

        Account account = accountRepository
                .findByNumeroCuenta(request.getAccountRequest().getNumberAccount())
                .orElseThrow(() -> new GeneralException("Cuenta no encontrada o inactiva"));
        // Obtener el último movimiento, si existe
        double saldoActual = movementRepository
                .findFirstByAccountNumeroCuentaOrderByIdDesc(request.getAccountRequest().getNumberAccount())
                .map(Movement::getSaldo) // Si existe un movimiento, usar su saldo
                .orElse(account.getSaldoInicial()); // Si no existe, usar el saldo inicial

        Movement movement = null;
        if (request.getTypeMovement().equalsIgnoreCase("retiro")) { //F2
            if (saldoActual < request.getValue()) {
                throw new GeneralException("Saldo insuficiente para realizar el retiro"); //F3
            }
            double nuevoSaldo = saldoActual - request.getValue();
            movement = Movement.builder()
                    .fecha(request.getDateTime())
                    .tipoMovimiento(request.getTypeMovement())
                    .valor(request.getValue())
                    .saldo(nuevoSaldo)
                    .account(account)
                    .build();

        } else if (request.getTypeMovement().equalsIgnoreCase("deposito")) {
            double nuevoSaldo = saldoActual + request.getValue();
            movement = Movement.builder()
                    .fecha(request.getDateTime())
                    .tipoMovimiento(request.getTypeMovement())
                    .valor(request.getValue())
                    .saldo(nuevoSaldo)
                    .account(account)
                    .build();


        } else {
            throw new GeneralException("Tipo de movimiento no válido");
        }

        if (movement == null) {
            throw new GeneralException("No se pudo crear el movimiento");
        }

        return movementRepository.save(movement);
    }


}
