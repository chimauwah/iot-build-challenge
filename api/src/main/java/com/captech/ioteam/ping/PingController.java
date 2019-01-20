package com.captech.ioteam.ping;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ping")
public class PingController {

    private final PingService pingService;

    @RequestMapping(method = RequestMethod.GET)
    public Map ping() throws IOException {
        return pingService.execute(8080);
    }

}
