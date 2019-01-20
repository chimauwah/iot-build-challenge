package com.captech.ioteam.ping;

import com.captech.ioteam.machine.Machine;
import com.captech.ioteam.machine.MachineAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

@RepositoryRestResource
public interface PingRepository extends JpaRepository<MachineAudit, Long> {

    @Query("SELECT a FROM MachineAudit a where a.machine = :machine order by a.created desc")
    @Async
    List<MachineAudit> findByMachineOrderByCreatedDesc(@Param("machine") Machine machine);

    @Query(value = "SELECT m.last_read_level FROM machine_audit m where m.machine_id = :machineId order by m.created desc", nativeQuery = true)
    List<String> findFirstByMachineIdPreviousLevelOrderByCreatedDesc(@Param("machineId") long machineId);


}
