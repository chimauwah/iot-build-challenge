package com.captech.ioteam.ping;

import com.captech.ioteam.machine.Machine;
import com.captech.ioteam.machine.ResourceLevel;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "decision_table")
@Data
@EntityListeners(AuditingEntityListener.class)
public class DecisionTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "machineId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Machine machine;

    @Enumerated(EnumType.STRING)
    private ResourceLevel fromLevel;

    @Enumerated(EnumType.STRING)
    private ResourceLevel toLevel;

    @Enumerated(EnumType.STRING)
    private AlertAction alertAction;

    @Column
    private String recipients;
}
