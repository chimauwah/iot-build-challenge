package com.captech.ioteam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IOTeamApplication {

    public static void main(String[] args) {
        SpringApplication.run(IOTeamApplication.class, args);
    }
}
