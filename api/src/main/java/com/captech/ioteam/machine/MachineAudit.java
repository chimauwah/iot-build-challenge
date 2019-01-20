package com.captech.ioteam.machine;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.hateoas.Identifiable;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 */
@Entity
@Table(name = "machine_audit")
@Data
@EntityListeners(AuditingEntityListener.class)
public class MachineAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long machineAuditId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "machineId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Machine machine;

    @Column
    private String previousLevel;

    @Column
    private String currentLevel;

    @Column(name = "created", nullable = false, updatable = false)
    @CreatedDate
    private Date created = new Date();

}
