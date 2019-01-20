package com.captech.ioteam.ping;

import com.captech.ioteam.machine.Machine;
import com.captech.ioteam.machine.MachineAudit;
import com.captech.ioteam.machine.PrintOSMachine;
import com.captech.ioteam.machine.ResourceLevel;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.util.List;

@Service
public class PingService {

    private static final Logger LOG = LoggerFactory.getLogger(PingService.class);

    @Autowired
    @Setter
    private PingRepository pingRepository;

    @Autowired
    @Setter
    private DecisionService decisionService;

    //    @Value("${server.port}:8080")
    private int port = 8080;

    private String uri = "http://localhost:%s/api/machines/printos";

    @Scheduled(fixedDelay = 5000, initialDelay = 20000)
    void execute() throws MessagingException {
        LOG.info("Checking for level change : " + new DateTime());

        for (Object singleMachineData : getAllPrinterData()) {
            PrintOSMachine currentMachineInfo = new ObjectMapper().convertValue(singleMachineData, PrintOSMachine.class);

            // TODO: for testing only - to only work with Armani machine
//            if (currentMachineInfo.getPressName().equals("Armani")) {

            // get previous level
            List<String> previousLevelsForMachineId = pingRepository
                    .findFirstByMachineIdPreviousLevelOrderByCreatedDesc(currentMachineInfo.getId()); // TODO: why is this not returning only the 1?
            String mostRecentPreviousLevelStr = previousLevelsForMachineId.isEmpty() ? null : previousLevelsForMachineId.get(0);
            ResourceLevel mostRecentPreviousLevel = ResourceLevel.DEFAULT;
            if (mostRecentPreviousLevelStr != null) {
                mostRecentPreviousLevel = ResourceLevel.valueOf(mostRecentPreviousLevelStr);
            }

            // if change in level, refer to decision table to take action
            if (!currentMachineInfo.getResourceLevel().equals(mostRecentPreviousLevel)) {
                decisionService.execute(currentMachineInfo, mostRecentPreviousLevel, currentMachineInfo.getResourceLevel());
            }

            //save new machine audit record
            MachineAudit machineAudit = new MachineAudit();
            machineAudit.setLastReadLevel(currentMachineInfo.getResourceLevel());
            Machine machine = new Machine();
            machine.setId(currentMachineInfo.getId());
            machineAudit.setMachine(machine);
            pingRepository.save(machineAudit);

//            }
        }

    }

    /**
     * Call API to retrieve all printer info
     *
     * @return
     */
    List getAllPrinterData() {
        // get printer data
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(String.format(uri, port), List.class);
    }

}
