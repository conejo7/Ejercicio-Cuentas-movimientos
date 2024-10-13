package com.cuentas_movimientos.cuentas_movimientos.facade;

import com.cuentas_movimientos.cuentas_movimientos.model.pojo.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "clientService", url = "${getClient.url}")
public interface CustomerFeign {

    @GetMapping("/{id}")
    Customer getClientById(@PathVariable("id") String id);
}
