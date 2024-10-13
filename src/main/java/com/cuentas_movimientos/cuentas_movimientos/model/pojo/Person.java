
package com.cuentas_movimientos.cuentas_movimientos.model.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "persona")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Person {
    @Id
    @Column(name = "identificacion", nullable = false, length = 20)
    private String identificacion;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "genero", length = 10)
    private String genero;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono", length = 20)
    private String telefono;

}