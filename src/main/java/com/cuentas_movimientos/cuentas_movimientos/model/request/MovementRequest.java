package com.cuentas_movimientos.cuentas_movimientos.model.request;


import java.time.LocalDateTime;


public record MovementRequest(
        Long accountId,
        LocalDateTime dateTime,
        String typeMovement,
        Double value,
        Double balance,
        CreateAccountRequest accountRequest
) {
}
