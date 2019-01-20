package com.captech.ioteam.ping;

import com.captech.ioteam.machine.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 *
 */
@RepositoryRestResource
public interface DecisionRepository extends JpaRepository<DecisionTable, Long> {

    List<DecisionTable> findAlertActionAndRecipientsByFromLevelAndToLevel(String fromLevel, String toLevel);
}
