package com.proyecto.operationservicems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OperationServiceMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OperationServiceMsApplication.class, args);
    }

}
