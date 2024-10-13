package com.cuentas_movimientos.cuentas_movimientos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients
public class CuentasMovimientosApplication {


	public static void main(String[] args) {
		SpringApplication.run(CuentasMovimientosApplication.class, args);
	}

}
