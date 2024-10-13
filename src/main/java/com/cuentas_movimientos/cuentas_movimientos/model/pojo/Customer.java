package com.cuentas_movimientos.cuentas_movimientos.model.pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "cliente")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clienteid", nullable = false)
    private Long clienteid;

    @Column(name = "contrasena", length = 100)
    private String contrasena;

    @Column(name = "estado")
    private boolean estado;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "identificacion")
    private Person person;

}