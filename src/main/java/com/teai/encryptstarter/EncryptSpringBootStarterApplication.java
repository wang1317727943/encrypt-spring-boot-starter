package com.teai.encryptstarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class EncryptSpringBootStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(EncryptSpringBootStarterApplication.class, args);
    }

}
