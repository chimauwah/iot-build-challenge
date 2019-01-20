package com.captech.ioteam.ping;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Before
    public void setUp() {
        pingService.setPingRepository(pingRepository);
        pingService.setDecisionService(decisionService);
        decisionService.setDecisionRepository(decisionRepository);
    }

    @Test
    public void test() throws Exception {
        String uri = "http://localhost:8080/api/ping";

        Map res = pingService.execute(port);
        assertNotNull(res);


//        List<LinkedHashMap> result = restTemplate.getForObject(uri, List.class);
//        Assert.assertNotNull(result);

//
//        Object forObject = this.restTemplate.getForObject("http://localhost:" + port + "api/ping",
//                Object.class);
//        assertNotNull(forObject);
    }
}