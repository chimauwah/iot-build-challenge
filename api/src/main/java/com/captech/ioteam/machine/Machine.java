package com.captech.ioteam.machine;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.hateoas.Identifiable;

import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "machines")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Machine implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer quantity;

    @Column
    private Integer capacity;

    @Column
    private Integer rate;
}
