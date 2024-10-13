package com.cuentas_movimientos.cuentas_movimientos.facade;


import com.cuentas_movimientos.cuentas_movimientos.model.pojo.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class MovementFacade {

    private final CustomerFeign clientFeign;

    public Customer getClientById(String id) {
        try {
            return clientFeign.getClientById(id);
        } catch (HttpClientErrorException e) {
            log.error("Client Error: {}, Product with ID {}", e.getStatusCode(), id);
            return null;
        } catch (Exception e) {
            log.error("Unexpected error while fetching client with ID {}", id, e);
            return null;
        }
    }


}
