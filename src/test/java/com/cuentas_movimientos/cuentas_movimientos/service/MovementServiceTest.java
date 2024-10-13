package com.cuentas_movimientos.cuentas_movimientos.service;


import com.cuentas_movimientos.cuentas_movimientos.data.AccountRepository;
import com.cuentas_movimientos.cuentas_movimientos.data.MovementRepository;
import com.cuentas_movimientos.cuentas_movimientos.exception.GeneralException;
import com.cuentas_movimientos.cuentas_movimientos.model.pojo.Account;
import com.cuentas_movimientos.cuentas_movimientos.model.pojo.Customer;
import com.cuentas_movimientos.cuentas_movimientos.model.pojo.Movement;
import com.cuentas_movimientos.cuentas_movimientos.model.request.CreateAccountRequest;
import com.cuentas_movimientos.cuentas_movimientos.model.request.MovementRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovementServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private MovementRepository movementRepository;

    @Mock
    private Customer customer;

    @InjectMocks
    private MovementServiceImpl movementService; // Clase que contiene el método a probar



    @Test
    void testCreateMovementDepositSuccess() {
        CreateAccountRequest createAccountRequest = new CreateAccountRequest(
                "1231243", "ahorros", 500.0, true, customer
        );

        MovementRequest request = new MovementRequest(
                1L, LocalDateTime.now(), "deposito", 100.0, 0.0, createAccountRequest
        );
        Account account = new Account();
        account.setNumeroCuenta("1231243");
        account.setTipoCuenta("ahorros");
        account.setSaldoInicial(500.0);
        account.setEstado(true);

        when(accountRepository.findByNumeroCuenta("1231243")).thenReturn(Optional.of(account));
        when(movementRepository.findFirstByAccountNumeroCuentaOrderByIdDesc("1231243"))
                .thenReturn(Optional.empty());
        when(movementRepository.save(any())).then(returnsFirstArg());

        Movement result = movementService.createMovement(request);

        assertNotNull(result);
        assertEquals(600.0, result.getSaldo());
        verify(movementRepository, times(1)).save(any(Movement.class));
    }


    @Test
    void testGeneralException() {

        Account account = Account.builder()
                .id(1L)
                .numeroCuenta("123456")
                .saldoInicial(100.0)
                .build();

        MovementRequest request = new MovementRequest(
                1L,
                LocalDateTime.now(),
                "retiro",
                200.0,
                0.0,
                new CreateAccountRequest("123456", "savings", 100.0, true, null)
        );

        doReturn(Optional.of(account)).when(accountRepository).findByNumeroCuenta("123456");
        doReturn(Optional.empty()).when(movementRepository).findFirstByAccountNumeroCuentaOrderByIdDesc("123456");

        assertThrows(GeneralException.class, () -> movementService.createMovement(request));
    }


}
