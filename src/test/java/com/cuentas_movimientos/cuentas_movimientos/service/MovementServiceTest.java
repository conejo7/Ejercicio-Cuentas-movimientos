package com.cuentas_movimientos.cuentas_movimientos.service;


import com.cuentas_movimientos.cuentas_movimientos.data.AccountRepository;
import com.cuentas_movimientos.cuentas_movimientos.data.MovementRepository;
import com.cuentas_movimientos.cuentas_movimientos.exception.GeneralException;
import com.cuentas_movimientos.cuentas_movimientos.model.pojo.Account;
import com.cuentas_movimientos.cuentas_movimientos.model.pojo.Customer;
import com.cuentas_movimientos.cuentas_movimientos.model.pojo.Movement;
import com.cuentas_movimientos.cuentas_movimientos.model.request.CreateAccountRequest;
import com.cuentas_movimientos.cuentas_movimientos.model.request.MovementRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovementServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private MovementRepository movementRepository;

    @Mock
    private Customer customer;

    @InjectMocks
    private MovementServiceImpl movementService; // Clase que contiene el método a probar

    @Mock
    private MovementRequest request;

    @Mock
    private Account account;

//    @BeforeEach
//    void setUp() {
//        // Configurar la cuenta y la solicitud de movimiento
//        account = new Account();
//        account.setNumeroCuenta("1231243");
//        account.setSaldoInicial(1000.0);
//
//        CreateAccountRequest accountRequest = new CreateAccountRequest();
//        accountRequest.setNumberAccount("1231243");  // Asegúrate de asignar el número de cuenta
//        accountRequest.setTypeAccount("deposito");
//        accountRequest.setState(true);
//        accountRequest.setInitialBalance(1000.0);
//        accountRequest.setCustomer(customer);
//
//        request = new MovementRequest();
//        request.setAccountRequest(accountRequest);
//        request.setTypeMovement("deposito");
//        request.setValue(500.0);
//        request.setDateTime(LocalDateTime.now());
//    }

    @Test
    void testCreateMovement_Deposit_Success() {
        CreateAccountRequest createAccountRequest = new CreateAccountRequest(
                "1231243", "ahorros", 500.0, true, customer
        );

//        Account account = Account.builder()
//                .id(1L)
//                .numeroCuenta("1231243")
//                .tipoCuenta("ahorros")
//                .saldoInicial(500.0)
//                .estado(true)
//                .build();

        MovementRequest request = new MovementRequest(
                1L, LocalDateTime.now(), "deposito", 100.0, 0.0, createAccountRequest
        );
        Movement expectedMovement = Movement.builder()
                .fecha(request.getDateTime())
                .tipoMovimiento(request.getTypeMovement())
                .valor(request.getValue())
                .saldo(600.0)
                .account(account)
                .build();

        when(accountRepository.findByNumeroCuenta("1231243")).thenReturn(Optional.of(account));
        when(movementRepository.findFirstByAccountNumeroCuentaOrderByIdDesc("1231243"))
                .thenReturn(Optional.empty()); // No hay movimientos previos
        when(movementRepository.save(any())).thenReturn(expectedMovement);

//        when(movementRepository.save(any(Movement.class)))
//                .thenAnswer(invocation -> invocation.getArgument(0)); // Retorna el movimiento guardado


        Movement result = movementService.createMovement(request);

        assertNotNull(result);
        assertEquals(600.0, result.getSaldo());
        verify(movementRepository, times(1)).save(any(Movement.class));
    }



    @Test
    public void test_create_movement_invalid_account() {
        MovementRepository movementRepository = Mockito.mock(MovementRepository.class);
        AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
        MovementServiceImpl movementService = new MovementServiceImpl(movementRepository, accountRepository);

        Mockito.when(accountRepository.findByNumeroCuenta(Mockito.anyString()))
                .thenReturn(Optional.empty());

        MovementRequest request = new MovementRequest();
        CreateAccountRequest accountRequest = new CreateAccountRequest();
        accountRequest.setNumberAccount("invalid");
        request.setAccountRequest(accountRequest);

        Assertions.assertThrows(GeneralException.class, () -> {
            movementService.createMovement(request);
        });
    }

}
