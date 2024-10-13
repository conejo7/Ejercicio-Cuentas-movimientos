package com.cuentas_movimientos.cuentas_movimientos.service;



import com.cuentas_movimientos.cuentas_movimientos.model.pojo.Account;
import com.cuentas_movimientos.cuentas_movimientos.model.request.CreateAccountRequest;

import java.util.List;

public interface AccountService {


    List<Account> getAccounts();

    Account getAccount(String accountId);

    Boolean removeAccount(String accountId);

    Account createAccount(CreateAccountRequest request);

    Account updateAccount(String clientId, CreateAccountRequest cuentaRequest);
}
