package com.cuentas_movimientos.cuentas_movimientos.controller;

import com.cuentas_movimientos.cuentas_movimientos.model.pojo.Account;
import com.cuentas_movimientos.cuentas_movimientos.model.request.CreateAccountRequest;
import com.cuentas_movimientos.cuentas_movimientos.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/cuentas")
    public ResponseEntity<List<Account>> getAccounts(@RequestHeader Map<String, String> headers) {
        log.info("headers: {}", headers);
        List<Account> accounts = accountService.getAccounts();
        return ResponseEntity.ok(Objects.requireNonNullElse(accounts, Collections.emptyList()));
    }

    @GetMapping("/cuentas/{cuentasId}")
    public ResponseEntity<Account> getAccount(@PathVariable String cuentasId) {

        log.info("Request received for product {}", cuentasId);
        Account account = accountService.getAccount(cuentasId);

        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/cuentas/{cuentasId}")
    public ResponseEntity<Void> deleteClient(@PathVariable String cuentasId) {

        Boolean removed = accountService.removeAccount(cuentasId);

        if (Boolean.TRUE.equals(removed)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/cuentas")
    public ResponseEntity<Account> createClient(@RequestBody CreateAccountRequest request) {

        Account createdClient = accountService.createAccount(request);

        if (createdClient != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("/cuentas/{cuentasId}")
    public ResponseEntity<Account> updateClient(@PathVariable String cuentasId, @RequestBody CreateAccountRequest clientRequest) {
        Account client = accountService.updateAccount(cuentasId,clientRequest);
        if (client != null) {
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
