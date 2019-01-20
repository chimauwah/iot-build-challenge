package com.captech.ioteam.ping;

import com.captech.ioteam.machine.Machine;
import com.captech.ioteam.machine.MachineAudit;
import com.captech.ioteam.machine.PrintOSMachine;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class PingService {

    @Autowired
    @Setter
    private PingRepository pingRepository;

    @Autowired
    @Setter
    private DecisionService decisionService;


    public Map execute(int port) {
        System.out.println("Ping");

        String uri = String.format("http://localhost:%s/api/machines/printos", port);

        RestTemplate restTemplate = new RestTemplate();
        List<LinkedHashMap> result = restTemplate.getForObject(uri, List.class);

        Map machineResourceMap = new HashMap<>();
        List<PrintOSMachine> listOfMachines = new ArrayList<>();
        for (LinkedHashMap map : result) {
            PrintOSMachine currentMachineInfo = new ObjectMapper().convertValue(map, PrintOSMachine.class);
            machineResourceMap.put(currentMachineInfo.getId(), currentMachineInfo.getResourceLevel());
            listOfMachines.add(currentMachineInfo);

            // get previous level
            List<String> previousLevelsForMachineId = pingRepository
                    .findFirstByMachineIdPreviousLevelOrderByCreatedDesc(currentMachineInfo.getId()); // TODO: why is this not returning only the 1?
            String mostRecentPreviousLevel = previousLevelsForMachineId.isEmpty() ? null : previousLevelsForMachineId.get(0);

            if (!Objects.equals(mostRecentPreviousLevel, currentMachineInfo.getResourceLevel())) {
                // TODO: if change in level, refer to decision table to know what to do
                decisionService.execute(mostRecentPreviousLevel, currentMachineInfo.getResourceLevel());

            }

            //save new machine audit record
            MachineAudit machineAudit = new MachineAudit();
            machineAudit.setCurrentLevel(currentMachineInfo.getResourceLevel());
            machineAudit.setPreviousLevel(mostRecentPreviousLevel);
            Machine machine = new Machine();
            machine.setId(currentMachineInfo.getId());
            machineAudit.setMachine(machine);
            pingRepository.save(machineAudit);


//
//            Iterable<MachineAudit> all = pingRepository.findAll();
//            List<MachineAudit> audits = pingRepository.findByMachineOrderByCreatedDesc(machine);
//            System.out.println("");
//
//            // see if audit record already exists
//            // get most recent audit record
//            List<MachineAudit> allAudit = pingRepository.findAll(new Sort(Sort.Direction.DESC, "created"));
//            System.out.println("");


        }


        return machineResourceMap;

    }

}
