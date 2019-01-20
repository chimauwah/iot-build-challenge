package com.captech.ioteam.ping;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PingServiceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

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

    @Before
    public void setUp() {
        pingService.setPingRepository(pingRepository);
        pingService.setDecisionService(decisionService);
        decisionService.setDecisionRepository(decisionRepository);
        decisionService.setTwilioService(twilioService);
        decisionService.setEmailService(emailService);
        emailService.setEmailSender(emailSender);
    }

    @Test
    public void test() throws Exception {
        pingService.execute(port);
    }
}