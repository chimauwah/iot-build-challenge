package com.captech.ioteam.ping;

import com.captech.ioteam.machine.MachineController;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PingServiceTest {

    @LocalServerPort
    private int port;

    @Spy
    private PingService pingService;

    @Spy
    private DecisionService decisionService;

    @Autowired
    private PingRepository pingRepository;

    @Autowired
    private DecisionRepository decisionRepository;

    @Autowired
    private TwilioService twilioService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private MachineController machineController;

    @Before
    public void setUp() {
        pingService.setPingRepository(pingRepository);
        pingService.setDecisionService(decisionService);
        decisionService.setDecisionRepository(decisionRepository);
        decisionService.setTwilioService(twilioService);
        decisionService.setEmailService(emailService);
        emailService.setEmailSender(emailSender);
        pingService.setMachineController(machineController);
        System.setProperty("server.port", String.valueOf(port));
    }

    @Test
    @Ignore
    public void test() throws Exception {
        pingService.execute();
    }
}