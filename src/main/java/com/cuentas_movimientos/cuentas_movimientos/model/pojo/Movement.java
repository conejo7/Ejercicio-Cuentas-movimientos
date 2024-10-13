package com.cuentas_movimientos.cuentas_movimientos.model.pojo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "movimiento")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movimiento_id_gen")
    @SequenceGenerator(name = "movimiento_id_gen", sequenceName = "movimiento_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "tipo_movimiento", nullable = false, length = 50)
    private String tipoMovimiento;

    @Column(name = "valor", nullable = false)
    private Double valor;

    @Column(name = "saldo", nullable = false)
    private Double saldo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cuenta_id", nullable = false)
    private Account account;

}
