package com.cuentas_movimientos.cuentas_movimientos.model.request;

import com.cuentas_movimientos.cuentas_movimientos.model.pojo.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest {

    private String numberAccount;

    private String typeAccount;

    private Double initialBalance;

    private boolean state;

    private Customer customer;





}
