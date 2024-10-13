package com.cuentas_movimientos.cuentas_movimientos.service;


import com.cuentas_movimientos.cuentas_movimientos.data.AccountRepository;
import com.cuentas_movimientos.cuentas_movimientos.exception.GeneralException;
import com.cuentas_movimientos.cuentas_movimientos.facade.MovementFacade;
import com.cuentas_movimientos.cuentas_movimientos.model.pojo.Account;
import com.cuentas_movimientos.cuentas_movimientos.model.pojo.Customer;
import com.cuentas_movimientos.cuentas_movimientos.model.request.CreateAccountRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {


    private final AccountRepository accountRepository;

    private final MovementFacade movementFacade;


    public AccountServiceImpl(AccountRepository accountRepository, MovementFacade movementFacade) {
        this.accountRepository = accountRepository;
        this.movementFacade = movementFacade;
    }


    @Override
    public List<Account> getAccounts(){
        List<Account> accounts = accountRepository.findAll();
        return accounts.isEmpty() ? null : accounts;
    }

    @Override
    public Account getAccount(String accountId) {

        return accountRepository.findById(Long.valueOf(accountId)).orElse(null);
    }

    @Override
    public Boolean removeAccount(String accountId) {

        Account account = accountRepository.findById(Long.valueOf(accountId)).orElse(null);

        if (account != null) {
            accountRepository.delete(account);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public Account createAccount(CreateAccountRequest request) {

        if (request != null && StringUtils.hasLength(request.numberAccount().trim())
                && StringUtils.hasLength(request.typeAccount().trim())
                && StringUtils.hasLength(String.valueOf(request.initialBalance()))) {

            Optional<Account> existingAccount = accountRepository.findByNumeroCuenta(request.numberAccount().trim());
            if (existingAccount.isPresent()) {
                throw new GeneralException("El número de cuenta ya se encuentra registrado.");
            }

            Customer cliente = movementFacade.getClientById(String.valueOf(request.customer().getClienteid()));

            if (cliente == null ) {
                throw new GeneralException("Cliente no encontrado ");
            }

            Account account = Account.builder().numeroCuenta(request.numberAccount()).tipoCuenta(request.typeAccount())
                    .saldoInicial(request.initialBalance()).estado(request.state()).customer(cliente).build();

            return accountRepository.save(account);
        } else {
            return null;
        }
    }

    @Override
    public Account updateAccount(String accountId, CreateAccountRequest clientRequest) {
        Account account = accountRepository.findById(Long.valueOf(accountId)).orElse(null);
        if (account !=null){
            account.setNumeroCuenta(clientRequest.numberAccount());
            account.setTipoCuenta(clientRequest.typeAccount());
            account.setSaldoInicial(clientRequest.initialBalance());
            account.setEstado(clientRequest.state());

            accountRepository.save(account);
        }
        return account;
    }


}
