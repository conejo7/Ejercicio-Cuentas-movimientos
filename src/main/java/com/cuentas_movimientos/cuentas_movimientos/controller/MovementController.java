
package com.cuentas_movimientos.cuentas_movimientos.controller;

import com.cuentas_movimientos.cuentas_movimientos.model.pojo.Movement;
import com.cuentas_movimientos.cuentas_movimientos.model.request.MovementRequest;
import com.cuentas_movimientos.cuentas_movimientos.service.MovementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MovementController {

    private final MovementService movementService;

    @GetMapping("/movimientos")
    public ResponseEntity<List<Movement>> getMovements(@RequestHeader Map<String, String> headers) {
        log.info("headers: {}", headers);
        List<Movement> movements = movementService.getMovements();
        if (movements != null) {
            return ResponseEntity.ok(movements);
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/movimientos/{movimientosId}")
    public ResponseEntity<Movement> getMovement(@PathVariable String movimientosId) {

        log.info("Request received for product {}", movimientosId);
        Movement movement = movementService.getMovement(movimientosId);

        if (movement != null) {
            return ResponseEntity.ok(movement);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/movimientos/{movimientosId}")
    public ResponseEntity<Void> deleteMovement(@PathVariable String movimientosId) {

        Boolean removed = movementService.removeMovement(movimientosId);

        if (Boolean.TRUE.equals(removed)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }


    @PutMapping("/movimientos/{movimientosId}")
    public ResponseEntity<Movement> updateMovement(@PathVariable String movimientosId, @RequestBody MovementRequest movementRequest) {
        Movement movement = movementService.updateMovement(movimientosId,movementRequest);
        if (movement != null) {
            return ResponseEntity.ok(movement);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/movimientos")
    public ResponseEntity<Movement> registerMovement(@RequestBody MovementRequest request) {

        Movement createdClient = movementService.createMovement(request);

        if (createdClient != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

}
