package com.captech.ioteam.ping;

import com.captech.ioteam.machine.ResourceLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 *
 */
@RepositoryRestResource
public interface DecisionRepository extends JpaRepository<DecisionTable, Long> {

    List<DecisionTable> findAlertActionAndRecipientsByFromLevelAndToLevel(ResourceLevel fromLevel, ResourceLevel toLevel);
}
