package com.captech.ioteam.ping;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ping")
public class PingController {

    private final PingService pingService;

    @RequestMapping(method = RequestMethod.POST)
    public void ping() throws MessagingException {
        pingService.execute();
    }

    @RequestMapping(method = RequestMethod.GET)
    public List getCurrentPrinterData() {
        return pingService.getAllPrinterData();
    }

}
