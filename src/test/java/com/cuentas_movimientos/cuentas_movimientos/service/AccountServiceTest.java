package com.cuentas_movimientos.cuentas_movimientos.service;

import com.cuentas_movimientos.cuentas_movimientos.data.AccountRepository;
import com.cuentas_movimientos.cuentas_movimientos.facade.MovementFacade;
import com.cuentas_movimientos.cuentas_movimientos.model.pojo.Account;
import com.cuentas_movimientos.cuentas_movimientos.model.pojo.Customer;
import com.cuentas_movimientos.cuentas_movimientos.model.request.CreateAccountRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    MovementFacade movementFacade;

    @Mock
    AccountRepository accountRepository;

    @Mock
    Account account;

    @InjectMocks
    AccountServiceImpl accountService;



    @Test
    void test_create_account_successfully() {

        Customer customer = new Customer();
        customer.setClienteid(1L);
        CreateAccountRequest accountRequest = new CreateAccountRequest(
                "12341234", "Corriente", 1000.0, true, customer
        );

        when(movementFacade.getClientById("1")).thenReturn(customer);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account result = accountService.createAccount(accountRequest);

        assertNotNull(result);
        verify(accountRepository, times(1)).save(any(Account.class));
    }
}
