package com.nonylog;

import com.nonylog.global.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class NonylogApplication {

    public static void main(String[] args) {
        SpringApplication.run(NonylogApplication.class, args);
    }

}
