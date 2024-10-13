package com.cuentas_movimientos.cuentas_movimientos.model.request;

import com.cuentas_movimientos.cuentas_movimientos.model.pojo.Customer;


public record CreateAccountRequest(
        String numberAccount,
        String typeAccount,
        Double initialBalance,
        boolean state,
        Customer customer
) {}
